import java.util.*;
import java.io.*;

class DecisionTree {
  private static int cols;
  private static int rows;
  private static String[][] data = new String[250][250];
  private static boolean[] visitedAttribs;
  private static String[] valuesInClass;
  private static double[] entropyValue;
  private static boolean[] auxArray;

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.print("Nome do Ficheiro: ");
    String fileName = input.next();
    fileName = fileName + ".csv";

    readFile(fileName);

    int columns = cols-1;
    visitedAttribs = new boolean[cols-1];
    auxArray = new boolean[rows];
    entropyValue = new double[cols-1];

    valuesInClass = differentValues(cols-1);
    //columnValueGain();
    entropyValue = columnValueGain(entropyValue);
    int best = checkBest();

    LinkedList<String> decisionTree = new LinkedList<String>();

    if(best == -1) {
      decisionTree = createDecisionTree(1, differentValues(1), data, decisionTree, -1);
    }
    else {
      decisionTree = createDecisionTree(best, differentValues(best), data, decisionTree, -1);
    }
  }

  public static void readFile(String fileName) {
    BufferedReader file = null;

    String line;
    int nRows = 0;

    try {
      file = new BufferedReader(new FileReader(fileName));

      String[] auxLine;

      while((line = file.readLine()) != null) {
        auxLine = line.split(",");

        for (int i=0; i<auxLine.length; i++) {
          data[nRows][i] = auxLine[i];
        }

        nRows++;
        cols = auxLine.length;
      }

      rows = nRows;
    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    finally {
      if(file != null) {
        try {
          file.close();
        }
        catch(IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static String[] differentValues(int columns) {
    int nTypes = 0;
    int n = 0;
    int aux = 0;
    String[] auxValues = new String[rows-1];
    Arrays.fill(auxValues, "null");

    for(int i=1; i<rows; i++) {
      for(int j=0; j<n+1; j++) {
        if(auxArray[i] == false) {
          if(data[i][columns].equals(auxValues[j])) {
            aux = 1;
          }
        }
      }

      if(aux == 0) {
        auxValues[n] = data[i][columns];
        n++;
      }

      aux = 0;
    }

    String[] values = new String[n];

    for (int i=0; i<n; i++) {
      values[i] = auxValues[i];
    }

    return values;
  }

  public static double[] columnValueGain(double[] entropyValue) {
    String[] colValues;
    double inicialEntropy = entropy();

    for(int i=1; i<cols-1; i++) {
      colValues = differentValues(i);
      entropyValue[i] = entropy(colValues, i, inicialEntropy);
    }

    return entropyValue;
  }

  public static double entropy() {
    double entropy;
    double p;
    double m;
    int yes = 0;
    int no = 0;
    int total = 0;

    for(int i=1; i<rows; i++) {
      if(auxArray[i] == false) {
        total++;
      }
    }

    for(int i=1; i<rows; i++) {
      if(auxArray[i] == false) {
        if(data[i][cols-1].equals("No") || data[i][cols-1].equals("no")) {
          no++;
        }
        if(data[i][cols-1].equals("Yes") || data[i][cols-1].equals("yes")) {
          yes++;
        }
      }
    }

    m = (double)no/(double)(rows-1);
    p = (double)yes/(double)(rows-1);
    entropy = -(p*(Math.log(p)/Math.log(2))) - (m*(Math.log(m)/Math.log(2)));

    if(Double.isNaN(entropy)) {
      return 0;
    }
    else {
      return entropy;
    }
  }

  public static double entropy(String[] colValues, int n, double inicialEntropy) {
    double[] entropy = new double[colValues.length];
    int[] nValues = new int[colValues.length];
    int yes = 0;
    int no = 0;
    double gainValue;
    double p;
    double m;
    double entropyTotal = 0;

    for(int i=0; i<nValues.length; i++) {
      for(int j=1; j<rows; j++) {
        if(auxArray[j] == false) {
          if(data[j][n].equals(colValues[i])) {
            nValues[i]++;
          }
        }
      }
    }
    for(int i=0; i<nValues.length; i++) {
      for(int j= 1; j<rows; j++) {
        if(auxArray[j] == false) {
          if((data[j][cols-1].equals("Yes") || data[j][cols-1].equals("yes")) && data[j][n].equals(colValues[i])) {
            yes++;
          }
          if((data[j][cols-1].equals("No") || data[j][cols-1].equals("no")) && data[j][n].equals(colValues[i])) {
            no++;
          }
        }
      }

      m = (double)no/(double)(nValues[i]);
      p = (double)yes/(double)(nValues[i]);
      entropy[i] = -(p*(Math.log(p)/Math.log(2))) - (m*(Math.log(m)/Math.log(2)));

      if(Double.isNaN(entropy[i])) {
        entropy[i] = 0;
      }

      p = 0;
      m = 0;
      yes = 0;
      no = 0;
    }
    for(int i=0; i<nValues.length; i++) {
      entropyTotal = entropyTotal + ((double)nValues[i]/(double)(rows-1))*entropy[i];
    }

    gainValue = inicialEntropy - entropyTotal;

    return gainValue;
  }

  public static int checkBest() {
    int best = -1;
    double max = 0.0;

    Arrays.fill(entropyValue, 0.0);
    columnValueGain(entropyValue);

    for(int i=1; i<entropyValue.length; i++) {
      //System.out.println(entropyValue[i] + " " + max);
      if(entropyValue[i] > max && visitedAttribs[i] == false) {
        max = entropyValue[i];
        best = i;
      }
    }

    if(best == -1) {
      return -1;
    }

    visitedAttribs[best] = true;

    return best;
  }

  public static double probability(int best, int x, String example) {
    double result;
    int n = 0;
    int nBest = 0;

    for(int i=1; i<rows; i++) {
      if(auxArray[i] == false) {
        if(data[i][best].equals(example)) {
          nBest++;

          if(data[i][cols-1].equals(valuesInClass[x])) {
            n++;
          }
        }
      }
    }

    result = (double)n/(double)nBest;

    return result;
  }

  public static int numberOfX(int best, String example) {
    int n = 0;

    for(int i=1; i<rows; i++) {
      if(auxArray[i] == false) {
        if(data[i][best].equals(example)) {
          n++;
        }
      }
    }

    return n;
  }

  public static void numberOfSpaces(int it) {
    for (int i=0; i<it*2; i++) {
      System.out.print(" ");
    }
  }

  public static LinkedList<String> createDecisionTree(int best, String[] examples, String[][] atributesData, LinkedList<String> decisionTree, int it) {
    boolean auxExamples[] = new boolean[examples.length];
    int n;
    it++;

    decisionTree.add(atributesData[0][best]);
    numberOfSpaces(it);

    System.out.println("-> " + atributesData[0][best] + " <-");

    for(int i=0; i<valuesInClass.length; i++) {
      n = 0;

      for(int j=0; j<examples.length; j++) {
        if(probability(best, i, examples[j]) == 1.0) {
          n++;
        }
      }

      if(examples.length == n) {
        for(int l=0; l<examples.length; l++) {
          numberOfSpaces(it);

          System.out.println(examples[l] + ": " + valuesInClass[i] + " -> " + n);
        }

        return decisionTree;
      }
    }
    for(int i=0; i<valuesInClass.length; i++) {
      for(int j=0; j<examples.length; j++) {
        if(auxExamples[j] == false) {
          if(probability(best, i, examples[j]) == 1.0) {
            int nn = numberOfX(best, examples[j]);
            decisionTree.add(valuesInClass[i]);
            auxExamples[j] = true;

            numberOfSpaces(it);

            System.out.println(examples[j] + ": " + valuesInClass[i] + " -> " + nn);

            for(int l=1; l<rows; l++) {
              if(data[l][best].equals(examples[j]))
                auxArray[l] = true;
            }
          }
        }
      }
    }
    for(int i=0; i<examples.length; i++) {
      if(auxExamples[i] == false) {
        int newBest = checkBest();

        if(newBest == -1) {
          return decisionTree;
        }

        numberOfSpaces(it);

        System.out.println(examples[i]);

        createDecisionTree(newBest, differentValues(newBest), atributesData, decisionTree, it);
      }
    }

    return decisionTree;
  }
}

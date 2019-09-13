import java.util.*;
import java.lang.*;

class Puzzle15 {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.println();
    System.out.println("***********************");
    System.out.println("****** 15 PUZZLE ******");
    System.out.println("***********************");
    System.out.println();
    System.out.println();

    // Criar os tabuleiros inicial e final
    System.out.println("Initial Board:");
    GameBoard initialBoard = new GameBoard(input);
    System.out.println();
    System.out.println("Final Board:");
    GameBoard finalBoard = new GameBoard(input);

    // x vai ser igual a 0 se da configuração inicial dá para chegar à final
    int x = checkValidation(initialBoard);

    if(x == 0) {
      strategyChoice(initialBoard, finalBoard, input);
    }
    else {
      System.out.println("ERROR: You can not reach the final configuration through your initial configuration");
    }
  }

  public static int checkValidation(GameBoard initialBoard) {
    int board[] = initialBoard.getterBoard();
    int line = 0;
    int n = 0;

    // Descobrir em que linha da matriz "imaginaria" o 0 se encontra
    for(int i=1; i<17; i++) {
      if(i == 5 || i == 8 || i == 13) {
        line++;

        if(board[i] == 0) {
          break;
        }
      }
      else {
        if(board[i] == 0) {
          break;
        }
      }
    }
    // Conta quantas vezes um valor do tabuleiro é menor que os valores à sua frente
    for(int i=1; i<17; i++) {
      for(int j=i+1; j<17; j++) {
        if((board[i] < board[j]) && (board[i] != 0)) {
          n++;
        }
      }
    }

    if((line == 0 || line == 2) && n%2 == 0) {
      return 0;
    }
    else if((line == 1 || line == 3) && n%2 != 0) {
      return 0;
    }
    else {
      return 1;
    }
  }

  public static void strategyChoice(GameBoard initialBoard, GameBoard finalBoard, Scanner input) {
    System.out.println();
    System.out.println();
    System.out.println("What strategy do you want to implement:");
    System.out.println("1) Search in Width (BFS)");
    System.out.println("2) Search in Depth (DFS)");
    System.out.println("3) Iterative Search in Depth");
    System.out.println("4) Search A*");
    System.out.println("5) Search Greedy");
    System.out.println();
    System.out.print(">>> ");

    int choice = input.nextInt();

    if(choice == 1) {
      TypesOfSearch.bfs(initialBoard, finalBoard);
    }
    else if(choice == 2) {
      TypesOfSearch.dfs(initialBoard, finalBoard);
    }
    else if(choice == 3) {
      System.out.println();
      System.out.println();
      System.out.print("Maximum Depth Intended: ");

      int maxDepth = input.nextInt();

      TypesOfSearch.iDFS(initialBoard, finalBoard, maxDepth);
    }
    else if(choice == 4) {
      System.out.println();
      System.out.println();
      System.out.println("Which heuristic to use:");
      System.out.println("1) Off-site parts count");
      System.out.println("2) Manhattan distance");
      System.out.println();
      System.out.print(">>> ");

      int choice2 = input.nextInt();

      TypesOfSearch.a(initialBoard, finalBoard, choice2);
    }
    else if(choice == 5) {
      System.out.println();
      System.out.println();
      System.out.println("Which heuristic to use:");
      System.out.println("1) Off-site parts count");
      System.out.println("2) Manhattan distance");
      System.out.println();
      System.out.print(">>> ");

      int choice2 = input.nextInt();

      TypesOfSearch.greedy(initialBoard, finalBoard, choice2);
    }
    else {
      System.out.println();
      System.out.println("That option does not exist !!!");
      System.out.println();

      strategyChoice(initialBoard, finalBoard, input);
    }
  }
}

class GameBoard {
  // Tabuleiro pai
  public GameBoard father;
  // Tabuleiro
  public int board[];
  // Posição do 0
  public int zeroPosition;
  // Movimento
  public char move;
  // Profundidade
  public int depth;
  // Custo
  public int cost;

  // Cria o tabuleiro atraves de um input
  public GameBoard(Scanner input) {
    board = new int[17];
    depth = 0;

    for(int i=1; i<17; i++) {
      board[i] = input.nextInt();

      if(board[i] == 0) {
        zeroPosition = i;
      }
    }
  }
  // Cria o tabuleiro atraves de um array
  public GameBoard(int son[]) {
    board = new int[17];

    for(int i=1; i<17; i++) {
      board[i] = son[i];

      if(son[i] == 0) {
        zeroPosition = i;
      }
    }
  }

  // Obter o pai
  public GameBoard getterFather() {
    return this.father;
  }
  // Obter a posicao do 0
  public int getterZero() {
    return this.zeroPosition;
  }
  // Obter o movimento que fez
  public char getterMove() {
    return this.move;
  }
  // Obter o tabuleiro
  public int[] getterBoard() {
    return this.board;
  }
  // Obter a profundidade
  public int getterDepth() {
    return this.depth;
  }
  // Obter o custo
  public int getterCost() {
    return this.cost;
  }

  // Retorna 0 se o zero nao faz parte da linha topo
  public int top() {
    int zero = this.getterZero();

    if(zero-4 <= 0) {
      return -1;
    }
    else {
      return 0;
    }
  }
  // Retorna 0 se o zer nao faz parte da linha baixo
  public int down() {
    int zero = this.getterZero();

    if((zero+4)>16) {
      return -1;
    }
    else {
      return 0;
    }
  }
  // Retorna 0 se o zero nao faz parte da fila mais á esquerda
  public int left() {
    int zero = this.getterZero();

    if((zero-1)%4 == 0) {
      return -1;
    }
    else {
      return 0;
    }
  }
  // Retorna 0 se o zero nao faz parte da fila mais á direita
  public int right() {
    int zero = this.getterZero();

    if(zero%4 == 0) {
      return 1;
    }
    else {
      return 0;
    }
  }

  // Passar o tabuleiro para String
  public String toString() {
    String str = "";
    str = str + this.board[1];

    for(int i=2; i<17; i++) {
      str = str + " " + this.board[i];
    }

    return str;
  }
}

class Heuristic {
	public static void heuristicChoice(GameBoard board, GameBoard finalBoard, int choice, int x) {
		if(choice == 1 && x == 1) {
      // O custo do tabuleiro vai ser igual ao numero de peças fora do lugar em comparação com o tabuleiro final
			board.cost = heuristicOption1(board, finalBoard);
		}
		else if(choice == 2 && x == 1) {
      // O custo do tabuleiro vai ser igual Manhattan distance
			board.cost = heuristicOption2(board, finalBoard);
		}
    else if(choice == 1 && x == 2) {
      // O custo do tabuleiro vai ser igual ao numero de peças fora do lugar em comparação com o tabuleiro final + a profundidade
			board.cost = board.depth + heuristicOption1(board, finalBoard);
		}
		else if(choice == 2 && x == 2) {
      // O custo do tabuleiro vai ser igual Manhattan distance + a profundidade
			board.cost = board.depth + heuristicOption2(board, finalBoard);
		}
		else {
      System.out.println();
      System.out.println("That heuristic option does not exist");
      // Sai do programa automaticamente
      System.exit(0);
		}

		return;
	}

	public static int heuristicOption1(GameBoard board, GameBoard finalBoard) {
		int outsidePositions = 0;
    int boardAux1[] = board.getterBoard();
    int boardAux2[] = finalBoard.getterBoard();

    for(int i=1; i<17; i++) {
    	if(boardAux1[i] != boardAux2[i]) {
    		outsidePositions++;
    	}
    }

    return outsidePositions;
	}

	public static int heuristicOption2(GameBoard board, GameBoard finalBoard) {
    int boardAux1[] = board.getterBoard();
    int boardAux2[] = finalBoard.getterBoard();
		int matrixBoard1[][] = new int[5][5];
    int matrixBoard2[][] = new int[5][5];
		int aux[] = new int[2];
    int i = 1;
		int result = 0;
		int value = 0;

		for(int j=1; j<5; j++) {
			for(int k=1; k<5; k++) {
				matrixBoard1[j][k] = boardAux1[i];
				matrixBoard2[j][k] = boardAux2[i];
				i++;
			}
		}

		for(int j=1; j<5; j++) {
			for(int k=1; k<5; k++) {
				value = matrixBoard1[j][k];
				aux = helper(matrixBoard2, value);

				if((value != 0) && (value != (j+k-1))) {
					result = result + (Math.abs(j-aux[0]) + Math.abs(k-aux[1]));
				}
			}
		}

		return result;
	}

	public static int[] helper(int matrix[][], int value) {
		int position[] = new int[2];

		for(int i=1; i<5; i++) {
			for(int j=1; j<5; j++) {
				if(matrix[i][j] == value) {
					position[0] = i;
					position[1] = j;

					return position;
				}
			}
    }

    System.out.println();
    System.out.println("The board do not have the same numbers!!");
    // Sai do programa automaticamente
    System.exit(0);

    return position;
	}
}

class TypesOfSearch {
  public static void bfs(GameBoard initialBoard, GameBoard finalBoard) {
    // Momento(tempo) em que entrou no BFS
    long timeStart = System.currentTimeMillis();
    // Tabelas visitados
    int visited = 0;
    // Tabelas gerados
    int generated = 0;
    // Profundidade
    int depth = 0;
    String startBoard = initialBoard.toString();
    String endBoard = finalBoard.toString();
    String child;

    // Lista de tabelas
    LinkedList<GameBoard> BoardList = new LinkedList<GameBoard>();
    // SetOfVisited é onde se vai guardar as tabelas visitadas
    Set<String> SetOfVisited = new HashSet<String>();
    BoardList.add(initialBoard);
    SetOfVisited.add(startBoard);
    generated++;

    while(!BoardList.isEmpty()) {
      // Remove a "cabeça" da lista
      GameBoard board = BoardList.remove();
      String removedBoard = board.toString();
      visited++;

      // Verificar se a tabela retirada é a final
      if(removedBoard.equals(endBoard)) {
        // Momento(tempo) em que entrou no if()
        long timeEnd= System.currentTimeMillis();
        depth = board.getterDepth();
        char moves[] = new char[depth+1];
        int x = depth;
        System.out.print("Movement from start to finish: ");

        while(board.father != null) {
          // Imprime os movimentos do o ultimo até o primeiro
          moves[x] = board.move;
          x--;
          // A variavel tabuleiro vai mudar para ser igual ao tabuleiro do pai
          board = board.getterFather();
        }
        for(int i=0; i<depth+1; i++) {
          if(moves[i] == 't') {
            System.out.print("Top ");
          }
          else if(moves[i] == 'd') {
            System.out.print("Down ");
          }
          else if(moves[i] == 'l') {
            System.out.print("Left ");
          }
          else if(moves[i] == 'r') {
            System.out.print("Right ");
          }
          if(i<depth) {
            System.out.print("-> ");
          }
        }

        System.out.println();
        System.out.println();
        System.out.println("Solution found!!");
        System.out.println("Elapsed time: " + (double)((timeEnd - timeStart)/1000.0) + " seconds");
        System.out.println("Depth of the solution: " + depth);
        System.out.println("Number of tables generated: " + generated);
        System.out.println("Number of tables visited: " + visited);

        return;
      }

      // Tabuleiros para cada movimento(top, down, left, right)
      int board1[] = board.getterBoard();
      GameBoard top = new GameBoard(board1);
      GameBoard down = new GameBoard(board1);
      GameBoard left = new GameBoard(board1);
      GameBoard right = new GameBoard(board1);
      // A variavel zero vai guardar a posição do zero
      int zero = board.getterZero();
      // posMovements[x] = 0  (onde x = 0/1/2/3) se podemos mexer o zero para cima/baixo/esquerda/direita
      int posMovements[] = new int[4];

      posMovements[0] = board.top();
      posMovements[1] = board.down();
      posMovements[2] = board.left();
      posMovements[3] = board.right();

      if(posMovements[0] == 0) {
        int boardTop[] = top.getterBoard();
        boardTop[zero] = boardTop[zero-4];
        boardTop[zero-4] = 0;
        GameBoard newBoardTop = new GameBoard(boardTop);
        child = newBoardTop.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardTop.move = 't';
          generated++;
          SetOfVisited.add(child);
          newBoardTop.father = board;
          newBoardTop.depth = board.getterDepth() + 1;
          BoardList.add(newBoardTop);
        }
      }
      if(posMovements[1] == 0) {
        int boardDown[] = down.getterBoard();
        boardDown[zero] = boardDown[zero+4];
        boardDown[zero+4] = 0;
        GameBoard newBoardDown = new GameBoard(boardDown);
        child = newBoardDown.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardDown.move = 'd';
          generated++;
          SetOfVisited.add(child);
          newBoardDown.father = board;
          newBoardDown.depth = board.getterDepth() + 1;
          BoardList.add(newBoardDown);
        }
      }
      if(posMovements[2] == 0) {
        int boardLeft[] = left.getterBoard();
        boardLeft[zero] = boardLeft[zero-1];
        boardLeft[zero-1] = 0;
        GameBoard newBoardLeft = new GameBoard(boardLeft);
        child = newBoardLeft.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardLeft.move = 'l';
          generated++;
          SetOfVisited.add(child);
          newBoardLeft.father = board;
          newBoardLeft.depth = board.getterDepth() + 1;
          BoardList.add(newBoardLeft);
        }
      }
      if(posMovements[3] == 0) {
        int boardRight[] = right.getterBoard();
        boardRight[zero] = boardRight[zero+1];
        boardRight[zero+1] = 0;
        GameBoard newBoardRight = new GameBoard(boardRight);
        child = newBoardRight.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardRight.move = 'r';
          generated++;
          SetOfVisited.add(child);
          newBoardRight.father = board;
          newBoardRight.depth = board.getterDepth() + 1;
          BoardList.add(newBoardRight);
        }
      }
    }

    System.out.println("No solution found!!");
  }

  public static void dfs(GameBoard initialBoard, GameBoard finalBoard) {
    // Momento(tempo) em que entrou no BFS
    long timeStart = System.currentTimeMillis();
    // Tabelas visitados
    int visited = 0;
    // Tabelas gerados
    int generated = 0;
    // Profundidade
    int depth = 0;
    String startBoard = initialBoard.toString();
    String endBoard = finalBoard.toString();
    String child;

    Stack<GameBoard> StackOfBoard = new Stack<GameBoard>();
    // SetOfVisited é onde se vai guardar as tabelas visitadas
    Set<String> SetOfVisited = new HashSet<String>();
    StackOfBoard.push(initialBoard);
    SetOfVisited.add(startBoard);
    generated++;


    while(!StackOfBoard.isEmpty()) {
      // Remove a "cabeça" da lista
      GameBoard board = StackOfBoard.pop();
      String removedBoard = board.toString();
      visited++;
      depth = board.getterDepth();

      // Verificar se a tabela retirada é a final
      if(removedBoard.equals(endBoard)) {
        // Momento(tempo) em que entrou no if()
        long timeEnd= System.currentTimeMillis();
        depth = board.getterDepth();
        char moves[] = new char[depth+1];
        int x = depth;
        System.out.print("Movement from start to finish: ");

        while(board.father != null) {
          // Imprime os movimentos do o ultimo até o primeiro
          moves[x] = board.move;
          x--;
          // A variavel tabuleiro vai mudar para ser igual ao tabuleiro do pai
          board = board.getterFather();
        }
        for(int i=0; i<depth+1; i++) {
          if(moves[i] == 't') {
            System.out.print("Top ");
          }
          else if(moves[i] == 'd') {
            System.out.print("Down ");
          }
          else if(moves[i] == 'l') {
            System.out.print("Left ");
          }
          else if(moves[i] == 'r') {
            System.out.print("Right ");
          }
          if(i<depth) {
            System.out.print("-> ");
          }
        }

        System.out.println();
        System.out.println();
        System.out.println("Solution found!!");
        System.out.println("Elapsed time: " + (double)((timeEnd - timeStart)/1000.0) + " seconds");
        System.out.println("Depth of the solution: " + depth);
        System.out.println("Number of tables generated: " + generated);
        System.out.println("Number of tables visited: " + visited);

        return;
      }

      // Tabuleiros para cada movimento(top, down, left, right)
      int board1[] = board.getterBoard();
      GameBoard top = new GameBoard(board1);
      GameBoard down = new GameBoard(board1);
      GameBoard left = new GameBoard(board1);
      GameBoard right = new GameBoard(board1);
      // A variavel zero vai guardar a posição do zero
      int zero = board.getterZero();
      // posMovements[x] = 0  (onde x = 0/1/2/3) se podemos mexer o zero para cima/baixo/esquerda/direita
      int posMovements[] = new int[4];

      posMovements[0] = board.top();
      posMovements[1] = board.down();
      posMovements[2] = board.left();
      posMovements[3] = board.right();

      for(int i=0; i<4 && depth<15; i++) {
        if(posMovements[3] == 0) {
          int boardRight[] = right.getterBoard();
          posMovements[3] = 1;
          boardRight[zero] = boardRight[zero+1];
          boardRight[zero+1] = 0;
          GameBoard newBoardRight = new GameBoard(boardRight);
          child = newBoardRight.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardRight.move = 'r';
            generated++;
            SetOfVisited.add(child);
            newBoardRight.father = board;
            newBoardRight.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardRight);
          }
        }
        else if(posMovements[2] == 0) {
          int boardLeft[] = left.getterBoard();
          posMovements[2] = 1;
          boardLeft[zero] = boardLeft[zero-1];
          boardLeft[zero-1] = 0;
          GameBoard newBoardLeft = new GameBoard(boardLeft);
          child = newBoardLeft.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardLeft.move = 'l';
            generated++;
            SetOfVisited.add(child);
            newBoardLeft.father = board;
            newBoardLeft.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardLeft);
          }
        }
        else if(posMovements[1] == 0) {
          int boardDown[] = down.getterBoard();
          posMovements[1] = 1;
          boardDown[zero] = boardDown[zero+4];
          boardDown[zero+4] = 0;
          GameBoard newBoardDown = new GameBoard(boardDown);
          child = newBoardDown.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardDown.move = 'd';
            generated++;
            SetOfVisited.add(child);
            newBoardDown.father = board;
            newBoardDown.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardDown);
          }
        }
        else if(posMovements[0] == 0) {
          int boardTop[] = top.getterBoard();
          posMovements[0] = 1;
          boardTop[zero] = boardTop[zero-4];
          boardTop[zero-4] = 0;
          GameBoard newBoardTop = new GameBoard(boardTop);
          child = newBoardTop.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardTop.move = 't';
            generated++;
            SetOfVisited.add(child);
            newBoardTop.father = board;
            newBoardTop.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardTop);
          }
        }
      }
    }

    System.out.println("No solution found!!");
  }

  public static void iDFS(GameBoard initialBoard, GameBoard finalBoard, int maxDepth) {
    // Momento(tempo) em que entrou no BFS
    long timeStart = System.currentTimeMillis();
    // Tabelas visitados
    int visited = 0;
    // Tabelas gerados
    int generated = 0;
    // Profundidade
    int depth = 0;
    String startBoard = initialBoard.toString();
    String endBoard = finalBoard.toString();
    String child;

    Stack<GameBoard> StackOfBoard = new Stack<GameBoard>();
    // SetOfVisited é onde se vai guardar as tabelas visitadas
    Set<String> SetOfVisited = new HashSet<String>();
    StackOfBoard.push(initialBoard);
    SetOfVisited.add(startBoard);
    generated++;


    while(!StackOfBoard.isEmpty()) {
      // Remove a "cabeça" da lista
      GameBoard board = StackOfBoard.pop();
      String removedBoard = board.toString();
      visited++;
      depth = board.getterDepth();

      // Verificar se a tabela retirada é a final
      if(removedBoard.equals(endBoard)) {
        // Momento(tempo) em que entrou no if()
        long timeEnd= System.currentTimeMillis();
        depth = board.getterDepth();
        char moves[] = new char[depth+1];
        int x = depth;
        System.out.print("Movement from start to finish: ");

        while(board.father != null) {
          // Imprime os movimentos do o ultimo até o primeiro
          moves[x] = board.move;
          x--;
          // A variavel tabuleiro vai mudar para ser igual ao tabuleiro do pai
          board = board.getterFather();
        }
        for(int i=0; i<depth+1; i++) {
          if(moves[i] == 't') {
            System.out.print("Top ");
          }
          else if(moves[i] == 'd') {
            System.out.print("Down ");
          }
          else if(moves[i] == 'l') {
            System.out.print("Left ");
          }
          else if(moves[i] == 'r') {
            System.out.print("Right ");
          }
          if(i<depth) {
            System.out.print("-> ");
          }
        }

        System.out.println();
        System.out.println();
        System.out.println("Solution found!!");
        System.out.println("Elapsed time: " + (double)((timeEnd - timeStart)/1000.0) + " seconds");
        System.out.println("Depth of the solution: " + depth);
        System.out.println("Number of tables generated: " + generated);
        System.out.println("Number of tables visited: " + visited);

        return;
      }

      // Tabuleiros para cada movimento(top, down, left, right)
      int board1[] = board.getterBoard();
      GameBoard top = new GameBoard(board1);
      GameBoard down = new GameBoard(board1);
      GameBoard left = new GameBoard(board1);
      GameBoard right = new GameBoard(board1);
      // A variavel zero vai guardar a posição do zero
      int zero = board.getterZero();
      // posMovements[x] = 0  (onde x = 0/1/2/3) se podemos mexer o zero para cima/baixo/esquerda/direita
      int posMovements[] = new int[4];

      posMovements[0] = board.top();
      posMovements[1] = board.down();
      posMovements[2] = board.left();
      posMovements[3] = board.right();

      for(int i=0; i<4 && depth<maxDepth; i++) {
        if(posMovements[3] == 0) {
          int boardRight[] = right.getterBoard();
          posMovements[3] = 1;
          boardRight[zero] = boardRight[zero+1];
          boardRight[zero+1] = 0;
          GameBoard newBoardRight = new GameBoard(boardRight);
          child = newBoardRight.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardRight.move = 'r';
            generated++;
            SetOfVisited.add(child);
            newBoardRight.father = board;
            newBoardRight.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardRight);
          }
        }
        else if(posMovements[2] == 0) {
          int boardLeft[] = left.getterBoard();
          posMovements[2] = 1;
          boardLeft[zero] = boardLeft[zero-1];
          boardLeft[zero-1] = 0;
          GameBoard newBoardLeft = new GameBoard(boardLeft);
          child = newBoardLeft.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardLeft.move = 'l';
            generated++;
            SetOfVisited.add(child);
            newBoardLeft.father = board;
            newBoardLeft.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardLeft);
          }
        }
        else if(posMovements[1] == 0) {
          int boardDown[] = down.getterBoard();
          posMovements[1] = 1;
          boardDown[zero] = boardDown[zero+4];
          boardDown[zero+4] = 0;
          GameBoard newBoardDown = new GameBoard(boardDown);
          child = newBoardDown.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardDown.move = 'd';
            generated++;
            SetOfVisited.add(child);
            newBoardDown.father = board;
            newBoardDown.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardDown);
          }
        }
        else if(posMovements[0] == 0) {
          int boardTop[] = top.getterBoard();
          posMovements[0] = 1;
          boardTop[zero] = boardTop[zero-4];
          boardTop[zero-4] = 0;
          GameBoard newBoardTop = new GameBoard(boardTop);
          child = newBoardTop.toString();

          if(!SetOfVisited.contains(child)) {
            newBoardTop.move = 't';
            generated++;
            SetOfVisited.add(child);
            newBoardTop.father = board;
            newBoardTop.depth = board.getterDepth() + 1;
            StackOfBoard.push(newBoardTop);
          }
        }
      }
    }

    System.out.println("No solution found!!");
  }

  public static void greedy(GameBoard initialBoard, GameBoard finalBoard, int choice) {
    // Momento(tempo) em que entrou no BFS
    long timeStart = System.currentTimeMillis();
    // Tabelas visitados
    int visited = 0;
    // Tabelas gerados
    int generated = 0;
    // Profundidade
    int depth = 0;
    String startBoard = initialBoard.toString();
    String endBoard = finalBoard.toString();
    String child;

    // Lista de tabelas
    PriorityQueue<GameBoard> BoardQueue = new PriorityQueue<GameBoard>(new BoardComparator());
    // SetOfVisited é onde se vai guardar as tabelas visitadas
    Set<String> SetOfVisited = new HashSet<String>();
    BoardQueue.add(initialBoard);
    SetOfVisited.add(startBoard);
    generated++;

    while(!BoardQueue.isEmpty()) {
      GameBoard board = BoardQueue.poll();
      String removedBoard = board.toString();
      visited++;

      // Verificar se a tabela retirada é a final
      if(removedBoard.equals(endBoard)) {
        // Momento(tempo) em que entrou no if()
        long timeEnd= System.currentTimeMillis();
        depth = board.getterDepth();
        char moves[] = new char[depth+1];
        int x = depth;
        System.out.print("Movement from start to finish: ");

        while(board.father != null) {
          // Imprime os movimentos do o ultimo até o primeiro
          moves[x] = board.move;
          x--;
          // A variavel tabuleiro vai mudar para ser igual ao tabuleiro do pai
          board = board.getterFather();
        }
        for(int i=0; i<depth+1; i++) {
          if(moves[i] == 't') {
            System.out.print("Top ");
          }
          else if(moves[i] == 'd') {
            System.out.print("Down ");
          }
          else if(moves[i] == 'l') {
            System.out.print("Left ");
          }
          else if(moves[i] == 'r') {
            System.out.print("Right ");
          }
          if(i<depth) {
            System.out.print("-> ");
          }
        }

        System.out.println();
        System.out.println();
        System.out.println("Solution found!!");
        System.out.println("Elapsed time: " + (double)((timeEnd - timeStart)/1000.0) + " seconds");
        System.out.println("Depth of the solution: " + depth);
        System.out.println("Number of tables generated: " + generated);
        System.out.println("Number of tables visited: " + visited);

        return;
      }

      // Tabuleiros para cada movimento(top, down, left, right)
      int board1[] = board.getterBoard();
      GameBoard top = new GameBoard(board1);
      GameBoard down = new GameBoard(board1);
      GameBoard left = new GameBoard(board1);
      GameBoard right = new GameBoard(board1);
      // A variavel zero vai guardar a posição do zero
      int zero = board.getterZero();
      // posMovements[x] = 0  (onde x = 0/1/2/3) se podemos mexer o zero para cima/baixo/esquerda/direita
      int posMovements[] = new int[4];

      posMovements[0] = board.top();
      posMovements[1] = board.down();
      posMovements[2] = board.left();
      posMovements[3] = board.right();

      if(posMovements[0] == 0) {
        int boardTop[] = top.getterBoard();
        boardTop[zero] = boardTop[zero-4];
        boardTop[zero-4] = 0;
        GameBoard newBoardTop = new GameBoard(boardTop);
        child = newBoardTop.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardTop.move = 't';
          generated++;
          SetOfVisited.add(child);
          newBoardTop.father = board;
          newBoardTop.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardTop, finalBoard, choice, 1);
          BoardQueue.offer(newBoardTop);
        }
      }
      if(posMovements[1] == 0) {
        int boardDown[] = down.getterBoard();
        boardDown[zero] = boardDown[zero+4];
        boardDown[zero+4] = 0;
        GameBoard newBoardDown = new GameBoard(boardDown);
        child = newBoardDown.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardDown.move = 'd';
          generated++;
          SetOfVisited.add(child);
          newBoardDown.father = board;
          newBoardDown.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardDown, finalBoard, choice, 1);
          BoardQueue.offer(newBoardDown);
        }
      }
      if(posMovements[2] == 0) {
        int boardLeft[] = left.getterBoard();
        boardLeft[zero] = boardLeft[zero-1];
        boardLeft[zero-1] = 0;
        GameBoard newBoardLeft = new GameBoard(boardLeft);
        child = newBoardLeft.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardLeft.move = 'l';
          generated++;
          SetOfVisited.add(child);
          newBoardLeft.father = board;
          newBoardLeft.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardLeft, finalBoard, choice, 1);
          BoardQueue.offer(newBoardLeft);
        }
      }
      if(posMovements[3] == 0) {
        int boardRight[] = right.getterBoard();
        boardRight[zero] = boardRight[zero+1];
        boardRight[zero+1] = 0;
        GameBoard newBoardRight = new GameBoard(boardRight);
        child = newBoardRight.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardRight.move = 'r';
          generated++;
          SetOfVisited.add(child);
          newBoardRight.father = board;
          newBoardRight.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardRight, finalBoard, choice, 1);
          BoardQueue.offer(newBoardRight);
        }
      }
    }

    System.out.println("No solution found!!");
  }

  public static void a(GameBoard initialBoard, GameBoard finalBoard, int choice) {
    // Momento(tempo) em que entrou no BFS
    long timeStart = System.currentTimeMillis();
    // Tabelas visitados
    int visited = 0;
    // Tabelas gerados
    int generated = 0;
    // Profundidade
    int depth = 0;
    String startBoard = initialBoard.toString();
    String endBoard = finalBoard.toString();
    String child;

    // Lista de tabelas
    PriorityQueue<GameBoard> BoardQueue = new PriorityQueue<GameBoard>(new BoardComparator());
    // SetOfVisited é onde se vai guardar as tabelas visitadas
    Set<String> SetOfVisited = new HashSet<String>();
    BoardQueue.add(initialBoard);
    SetOfVisited.add(startBoard);
    generated++;

    while(!BoardQueue.isEmpty()) {
      GameBoard board = BoardQueue.poll();
      String removedBoard = board.toString();
      visited++;

      // Verificar se a tabela retirada é a final
      if(removedBoard.equals(endBoard)) {
        // Momento(tempo) em que entrou no if()
        long timeEnd= System.currentTimeMillis();
        depth = board.getterDepth();
        char moves[] = new char[depth+1];
        int x = depth;
        System.out.print("Movement from start to finish: ");

        while(board.father != null) {
          // Imprime os movimentos do o ultimo até o primeiro
          moves[x] = board.move;
          x--;
          // A variavel tabuleiro vai mudar para ser igual ao tabuleiro do pai
          board = board.getterFather();
        }
        for(int i=0; i<depth+1; i++) {
          if(moves[i] == 't') {
            System.out.print("Top ");
          }
          else if(moves[i] == 'd') {
            System.out.print("Down ");
          }
          else if(moves[i] == 'l') {
            System.out.print("Left ");
          }
          else if(moves[i] == 'r') {
            System.out.print("Right ");
          }
          if(i<depth) {
            System.out.print("-> ");
          }
        }

        System.out.println();
        System.out.println();
        System.out.println("Solution found!!");
        System.out.println("Elapsed time: " + (double)((timeEnd - timeStart)/1000.0) + " seconds");
        System.out.println("Depth of the solution: " + depth);
        System.out.println("Number of tables generated: " + generated);
        System.out.println("Number of tables visited: " + visited);

        return;
      }

      // Tabuleiros para cada movimento(top, down, left, right)
      int board1[] = board.getterBoard();
      GameBoard top = new GameBoard(board1);
      GameBoard down = new GameBoard(board1);
      GameBoard left = new GameBoard(board1);
      GameBoard right = new GameBoard(board1);
      // A variavel zero vai guardar a posição do zero
      int zero = board.getterZero();
      // posMovements[x] = 0  (onde x = 0/1/2/3) se podemos mexer o zero para cima/baixo/esquerda/direita
      int posMovements[] = new int[4];

      posMovements[0] = board.top();
      posMovements[1] = board.down();
      posMovements[2] = board.left();
      posMovements[3] = board.right();

      if(posMovements[0] == 0) {
        int boardTop[] = top.getterBoard();
        boardTop[zero] = boardTop[zero-4];
        boardTop[zero-4] = 0;
        GameBoard newBoardTop = new GameBoard(boardTop);
        child = newBoardTop.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardTop.move = 't';
          generated++;
          SetOfVisited.add(child);
          newBoardTop.father = board;
          newBoardTop.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardTop, finalBoard, choice, 2);
          BoardQueue.offer(newBoardTop);
        }
      }
      if(posMovements[1] == 0) {
        int boardDown[] = down.getterBoard();
        boardDown[zero] = boardDown[zero+4];
        boardDown[zero+4] = 0;
        GameBoard newBoardDown = new GameBoard(boardDown);
        child = newBoardDown.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardDown.move = 'd';
          generated++;
          SetOfVisited.add(child);
          newBoardDown.father = board;
          newBoardDown.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardDown, finalBoard, choice, 2);
          BoardQueue.offer(newBoardDown);
        }
      }
      if(posMovements[2] == 0) {
        int boardLeft[] = left.getterBoard();
        boardLeft[zero] = boardLeft[zero-1];
        boardLeft[zero-1] = 0;
        GameBoard newBoardLeft = new GameBoard(boardLeft);
        child = newBoardLeft.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardLeft.move = 'l';
          generated++;
          SetOfVisited.add(child);
          newBoardLeft.father = board;
          newBoardLeft.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardLeft, finalBoard, choice, 2);
          BoardQueue.offer(newBoardLeft);
        }
      }
      if(posMovements[3] == 0) {
        int boardRight[] = right.getterBoard();
        boardRight[zero] = boardRight[zero+1];
        boardRight[zero+1] = 0;
        GameBoard newBoardRight = new GameBoard(boardRight);
        child = newBoardRight.toString();

        // Verifica se o novo tabuleiro ja existe
        if(!SetOfVisited.contains(child)) {
          newBoardRight.move = 'r';
          generated++;
          SetOfVisited.add(child);
          newBoardRight.father = board;
          newBoardRight.depth = board.getterDepth() + 1;
          Heuristic.heuristicChoice(newBoardRight, finalBoard, choice, 2);
          BoardQueue.offer(newBoardRight);
        }
      }
    }

    System.out.println("No solution found!!");
  }
}

class BoardComparator implements Comparator<GameBoard> {
  public int compare(GameBoard board1, GameBoard board2) {
    if(board1.cost > board2.cost) {
      return 1;
    }
    else if(board1.cost < board2.cost) {
      return -1;
    }

    return 0;
  }
}

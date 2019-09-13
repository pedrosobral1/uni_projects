import java.util.*;
import java.lang.*;

class Connect4 {
  public static void main(String[] args) {
    System.out.println();
    System.out.println("************************");
    System.out.println("***** CONNECT FOUR *****");
    System.out.println("************************");

    Scanner input = new Scanner(System.in);


    System.out.println();
    System.out.println();
    System.out.println("Who do you want to play with?");
    System.out.println("1) Computer");
    System.out.println("2) Human");
    System.out.println();
    System.out.print(">>> ");
    int opponent = choice(input,2);

    if(opponent == 1) {
        HumanAgainstComputer(input);
    }
    else {
        HumanAgainstHuman(input);
    }
  }

  public static void drawBoard(char board[][]) {
    for(int i=0; i<6; i++) {
      for(int j=0; j<7; j++) {
        if (j==0) System.out.println("     ");
        System.out.print(" " + board[i][j]+" ");
        if (j==6) System.out.println();
      }
    }
    System.out.print(" 1  2  3  4  5  6  7 ");
    System.out.println();
    System.out.println();
  }

  public static int choice(Scanner input, int n) {
    int choice = input.nextInt();


    if (n==2){
      if(choice != 1 && choice != 2) {
          System.out.println();
          System.out.println("You chose an invalid number, try again!!");
          System.out.println();
          System.out.print(">>> ");
          choice = choice(input,2);
      }
    }

    else{
      if(choice != 1 && choice != 2 && choice != 3) {
          System.out.println();
          System.out.println("You chose an invalid number, try again!!");
          System.out.println();
          System.out.print(">>> ");
          choice = choice(input,3);
      }
    }
    return choice;
  }

  public static void HumanAgainstHuman(Scanner input) {

    String player1_name;
    String player2_name;
    System.out.println();
    System.out.println("Player 1 name: ");
    player1_name = input.next();
    System.out.println();
    System.out.println("Player 2 name: ");
    player2_name = input.next();

    System.out.println();
    System.out.println("Who plays first:");
    System.out.println("1) " + player1_name);
    System.out.println("2) " + player2_name);
    System.out.println();
    System.out.print(">>> ");
    int currentPlayer = choice(input,2);


    //choose the symbol to play with
    System.out.println();
    System.out.println("Symbol you want to play with:");
    System.out.println("1) X");
    System.out.println("2) O");
    System.out.println();
    System.out.print(">>> ");
    int symbol = choice(input,2);


    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("*************      LET'S PLAY!      ***********");
    System.out.println("!! Attention: columns are numbered from 1 to 7 !!");



    GameBoard game_board = new GameBoard('_');
    char Board[][] = game_board.getterBoard();
    //create the players
    Player player1;
    Player player2;

    if (currentPlayer == 1 ){
      if (symbol == 1){
        player1 = new Player('X',player1_name);
        player2 = new Player('O',player2_name);
      }
      else {
        player1 = new Player('O',player1_name);
        player2 = new Player('X',player2_name);
      }
    }
    else {
      if (symbol == 1){
        player2 = new Player('X',player2_name);
        player1 = new Player('O',player1_name);
      }
      else {
        player2 = new Player('O',player2_name);
        player1 = new Player('X',player1_name);
      }
    }
    game_board.setPlayers(player1,player2);
    int round = 1;

    while(game_board.endGame() == 'D'){
      System.out.println();
      System.out.println();
      System.out.println("**** ROUND "+round+" ****");
      System.out.println();
      drawBoard(Board);
      System.out.println("It's " + game_board.getPlayer(currentPlayer).getName() + "'s turn");
      System.out.print("Choose your coordinate to play: ");
      int move = input.nextInt();
      while (game_board.validMove(move)==false){
        System.out.println();
        System.out.println("You chose an invalid number, try again!!");
        System.out.println();
        System.out.print(">>> ");
        move = input.nextInt();
      }

      game_board.MoveGameBoard(move,currentPlayer);
      Board = game_board.getterBoard();
      round+=1;

      if (currentPlayer == 1){
        currentPlayer = 2;
      }
      else {
        currentPlayer = 1;
      }
    }

    System.out.println("**** GAME OVER ****");
    System.out.println();
    System.out.println();
    drawBoard(Board);
    if (game_board.getPlayer(1).getSymbol() == game_board.endGame()){
      System.out.println("The winner is: " + game_board.getPlayer(1).getName() + ".");
    }
    else if( game_board.getPlayer(2).getSymbol() == game_board.endGame()){
      System.out.println("The winner is: " + game_board.getPlayer(2).getName() + ".");
    }
    else{
      System.out.println("It's a draw. ");
    }
  }

  public static void HumanAgainstComputer(Scanner input) {

    //choose who plays first
    System.out.println();
    System.out.println("Who plays first:");
    System.out.println("1) Computer");
    System.out.println("2) You");
    System.out.println();
    System.out.print(">>> ");
    int currentPlayer = choice(input,2);

    //choose the symbol to play with
    System.out.println();
    System.out.println("Symbol you want to play with:");
    System.out.println("1) X");
    System.out.println("2) O");
    System.out.println();
    System.out.print(">>> ");
    int symbol = choice(input,2);

    System.out.println();
    System.out.println("Algorithm to be used by the computer in the game:");
    System.out.println("1) MinMax");
    System.out.println("2) AlfaBeta");
    System.out.println("3) Monte-Carlo");
    System.out.println();
    System.out.print(">>> ");
    int algorithm = choice(input,3);

    System.out.println();
    System.out.print("Maximum depth to be used in the algorithm: ");
    int depth = input.nextInt();

    System.out.println("Attention: columns are numbered from 1 to 7");
    System.out.println();
    System.out.println("LET'S PLAY!");
    System.out.println();

    //create current player and initial board
    GameBoard game_board = new GameBoard('_');
    char[][] Board = game_board.getterBoard();
    //create the players
    Player player1;
    Player player2;

    if (symbol == 1){
      player2 = new Player('X',"Human");
      player1 = new Player('O',"Computer");
    }
    else {
      player2 = new Player('O',"Human");
      player1 = new Player('X',"Computer");
    }

    game_board.setPlayers(player1,player2);
    game_board.setPrevMove(0);

    //game is playing
    int move = 0;
    int round = 1;
    while(game_board.endGame() == 'D') {

      Global.maxEval =Integer.MIN_VALUE;
      Global.minEval =Integer.MAX_VALUE;

      System.out.println("**** ROUND "+round+" ****");
      System.out.println();
      System.out.println();
      //System.out.println("CURR PLAYER " + currPlayer.getName());
      drawBoard(Board);

      //Computer's turn
      if(currentPlayer == 1) {

        System.out.println();
        System.out.println("It's " + game_board.getPlayer(1).getSymbol() + "'s turn");
        System.out.println();

        if(algorithm == 1) { //MINIMAX
          move = TypesOfSearch.minimax(depth,game_board);
        }
        else if (algorithm == 2) {
          move = TypesOfSearch.alpha_beta(depth,game_board);
        }
        else {
          move = TypesOfSearch.MCTS(depth,10000,game_board);
        }

        game_board.MoveGameBoard(move,currentPlayer);
        Board = game_board.getterBoard();
        currentPlayer = 2;
        round+=1;
      }

      //Human's turn
      else {

        System.out.println("It's " + game_board.getPlayer(2).getSymbol() + "'s turn");
        System.out.print("Choose your coordinate to play: ");
        move = input.nextInt();

        while (game_board.validMove(move) == false){
          System.out.println();
          System.out.println("You chose an invalid number, try again!!");
          System.out.println();
          System.out.print(">>> ");
          move = input.nextInt();
        }

        //update board
        game_board.MoveGameBoard(move,currentPlayer);
        Board = game_board.getterBoard();
        currentPlayer = 1;
        round+=1;
      }

    }

      //the game ended
      System.out.println("**** GAME OVER ****");
      System.out.println();
      System.out.println();
      drawBoard(Board);
      if (game_board.getPlayer(1).getSymbol() == game_board.endGame()){
        System.out.println("The winner is the Computer");
      }
      else if(game_board.getPlayer(2).getSymbol() == game_board.endGame()){
        System.out.println("The winner is you!");
      }
      else{
        System.out.println("It's a draw. ");
      }
  }
}

class GameBoard {
  public char board[][];
  public Player player1;
  public Player player2;
  public int prevMove;
  public int nextMove;
  public GameBoard prevBoard;
  public  ArrayList<GameBoard> Children;
  public int plays;
  //used in MCTS
  public double value;
  public double n_visits;
  public double n_choices;


  public GameBoard(char c) {
    board = new char[6][7];
    for(int i=0; i<6; i++) {
      for(int j=0; j<7; j++) {
        board[i][j] = c;
      }
    }
  }
  public GameBoard(char new_board[][]){
    char next_board[][] = new char[6][7];
    for(int i=0; i<6; i++) {
      for(int j=0; j<7; j++) {
        next_board[i][j]=new_board[i][j];
      }
    }
    setBoard(next_board);
  }

  //setters
  public void setBoard(char new_board[][]){
    board = new_board;
  }
  public char[][] getterBoard() {
    return board;
  }
  public boolean validMove(int move) {
    if (move >7 || move <0) return false;
    if (board[0][move-1]!='_') return false;
    else return true;
  }
  public void MoveGameBoard(int move, int currentPlayer) {
      // rows index from 0 to 5 on the board
      for (int i=0;i<5;i++){
        if (board[i+1][move-1] != '_'){
          setPrevMove(move);
          board[i][move-1] = getPlayer(currentPlayer).getSymbol();
          return;
        }
        if (i==4){
          setPrevMove(move);
          board[5][move-1] = getPlayer(currentPlayer).getSymbol();
          return;
        }
      }
  }
  public void setPrevMove(int move){
    prevMove = move;
  }
  public int getPrevMove(){
    return prevMove;
  }
  public void setNextMove(int move){
    nextMove = move;
  }
  public int getNextMove(){
    return nextMove;
  }
  public void setPlayers(Player player1, Player player2){
    createPlayer(player1,1);
    createPlayer(player2,2);
  }
  public void createPlayer(Player player, int i){
    char symbol;
    char name;
    if (i == 1){
      player1 = new Player(player.getSymbol(),player.getName());
    }
    else{
      player2 = new Player(player.getSymbol(),player.getName());
    }
  }
  public ArrayList<GameBoard> createChildren(int currentPlayer){
    Children = new ArrayList<GameBoard>();
    char[][] init_board = getterBoard();
    GameBoard prev = new GameBoard(init_board);
    int j = 1;
    int count = 0;
    while (j<8){
      if(validMove(j) == true) {
        GameBoard moving_board = new GameBoard(init_board);
        moving_board.setPlayers(getPlayer(1),getPlayer(2));
        moving_board.MoveGameBoard(j,currentPlayer);
        moving_board.setPrevBoard(prev);
        //only used in MCTS
        moving_board.setVisits(1);
        moving_board.setChoices(1);
        ///////////
        Children.add(moving_board);
      }
      j++;
    }
    return Children;
  }
  public ArrayList<GameBoard> getChildren(){
    return Children;
  }
  public Player getPlayer(int i){
    if (i==1) return player1;
    return player2;
  }


  //only used in MCTS
  public void setPrevBoard(GameBoard prev ){
    if (prev != null){
      prevBoard = new GameBoard(prev.getterBoard());
    }
  }
  public GameBoard getPrevBoard(){
    return prevBoard;
  }
  public void setValue(double evalUCB){
    value = evalUCB;
  }
  public double getValue(){
    return value;
  }
  public void setVisits(double visit){
    n_visits = visit;
  }
  public double getVisits(){
    return n_visits;
  }
  public void setChoices(double choice){
    n_choices = choice;
  }
  public double getChoices(){
    return n_choices;
  }
  public GameBoard copyBoard(){
   GameBoard copy = new GameBoard(getterBoard());
   copy.setPlayers(getPlayer(1),getPlayer(2));
   copy.setPrevMove(getPrevMove());
   copy.setValue(getValue());
   copy.setChoices(getChoices());
   copy.setVisits(getVisits());
   return copy;
  }


  //evaluation functions
  public int evaluatefunc(){
    int result = 0;
    int Xvalue,Ovalue;

    if (endGame()== getPlayer(1).getSymbol()){
      result = 512;
      return result;
    }
    else if (endGame()== getPlayer(2).getSymbol()){
      result = -512;
      return result;
    }
    else if (endGame() == 'E'){
      return result;
    }

    //evaluate rows
    Xvalue = 0;
    Ovalue = 0;
    for(int i=0; i<6; i++){
        for(int j=0; j<4; j++) {
          for (int k=0;k<4;k++){
            if(board[i][j+k] == 'X'){
              Xvalue++;
            }
            else if(board[i][j+k] == 'O'){
              Ovalue++;
            }
          }

          if (Xvalue == 3 && Ovalue == 0){
            if (getPlayer(1).getSymbol() == 'X') result+=50;
            else result-=50;
          }
          else if (Ovalue == 3 && Xvalue == 0){
            if (getPlayer(1).getSymbol() == 'O') result+=50;
            else result-=50;
          }
          if (Xvalue == 2 && Ovalue == 0){
            if (getPlayer(1).getSymbol() == 'X') result+=10;
            else result-=10;
          }
          else if (Ovalue == 2 && Xvalue == 0){
            if (getPlayer(1).getSymbol() == 'O') result+=10;
            else result-=10;
          }
          if (Xvalue == 1 && Ovalue == 0){
            if (getPlayer(1).getSymbol() == 'X') result+=1;
            else result-=1;
          }
          else if (Ovalue == 1 && Xvalue == 0){
            if (getPlayer(1).getSymbol() == 'O') result+=1;
            else result-=1;
          }
          Xvalue = 0;
          Ovalue = 0;
      }
    }



    //evaluate columns
    Xvalue = 0;
    Ovalue = 0;
    for(int j=0; j<7; j++) {
        for(int i=0; i<3; i++) {
          for (int k=0;k<4;k++){
            if(board[i+k][j] == 'X'){
              Xvalue++;
            }
            else if(board[i+k][j] == 'O'){
              Ovalue++;
            }
          }


          if (Xvalue == 3 && Ovalue == 0){
            if (getPlayer(1).getSymbol() == 'X') result+=50;
            else result-=50;
          }
          else if (Ovalue == 3 && Xvalue == 0){
            if (getPlayer(1).getSymbol() == 'O') result+=50;
            else result-=50;
          }
          if (Xvalue == 2 && Ovalue == 0){
            if (getPlayer(1).getSymbol() == 'X') result+=10;
            else result-=10;
          }
          else if (Ovalue == 2 && Xvalue == 0){
            if (getPlayer(1).getSymbol() == 'O') result+=10;
            else result-=10;
          }
          if (Xvalue == 1 && Ovalue == 0){
            if (getPlayer(1).getSymbol() == 'X') result+=1;
            else result-=1;
          }
          else if (Ovalue == 1 && Xvalue == 0){
            if (getPlayer(1).getSymbol() == 'O') result+=1;
            else result-=1;
          }
          Xvalue = 0;
          Ovalue = 0;
      }
    }


    //evaluate 1st diagonal
    Xvalue = 0;
    Ovalue = 0;
    for (int i=0;i<3;i++){
      for (int k=0;k<(3-i);k++) {
          for (int j=0;j<4;j++) {

            //System.out.println("i "+ (i+k+j) + " j " + (j+k));
            if(board[i+k+j][j+k] == 'X'){
              Xvalue++;
            }
            else if(board[i+k+j][j+k] == 'O'){
              Ovalue++;
            }
          }
         if (Xvalue == 3 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=50;
           else result-=50;
         }
         else if (Ovalue == 3 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=50;
           else result-=50;
         }
         if (Xvalue == 2 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=10;
           else result-=10;
         }
         else if (Ovalue == 2 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=10;
           else result-=10;
         }
         if (Xvalue == 1 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=1;
           else result-=1;
         }
         else if (Ovalue == 1 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=1;
           else result-=1;
         }
         Xvalue=0;
         Ovalue=0;
      }
    }
    Xvalue = 0;
    Ovalue = 0;
    for (int j=1;j<4;j++){
      for (int k=0;k<(4-j);k++){
          for (int i=0;i<4;i++){
            //System.out.println("i "+ (i+k) + " j " + (i+j+k));
            if(board[i+k][j+k+i] == 'X'){
              Xvalue++;
            }
            else if(board[i+k][j+k+i] == 'O'){
              Ovalue++;
            }
          }
         if (Xvalue == 3 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=50;
           else result-=50;
         }
         else if (Ovalue == 3 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=50;
           else result-=50;
         }
         if (Xvalue == 2 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=10;
           else result-=10;
         }
         else if (Ovalue == 2 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=10;
           else result-=10;
         }
         if (Xvalue == 1 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=1;
           else result-=1;
         }
         else if (Ovalue == 1 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=1;
           else result-=1;
         }
         Xvalue=0;
         Ovalue=0;
      }
    }


    //evaluate 2nd diagonal
    Xvalue = 0;
    Ovalue = 0;
    for (int i=0;i<3;i++){
      for (int k=0;k<(3-i);k++) {
          for (int j=0;j<4;j++) {
            //System.out.println("i "+ (i+k+j) + " j " + (6-(j+k)));
            if(board[i+k+j][6-(j+k)] == 'X'){
              Xvalue++;
            }
            else if(board[i+k+j][6-(j+k)] == 'O'){
              Ovalue++;
            }
          }
         if (Xvalue == 3 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=50;
           else result-=50;
         }
         else if (Ovalue == 3 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=50;
           else result-=50;
         }
         if (Xvalue == 2 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=10;
           else result-=10;
         }
         else if (Ovalue == 2 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=10;
           else result-=10;
         }
         if (Xvalue == 1 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=1;
           else result-=1;
         }
         else if (Ovalue == 1 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=1;
           else result-=1;
         }
         Xvalue=0;
         Ovalue=0;
      }
    }
    Xvalue = 0;
    Ovalue = 0;
    for (int j=1;j<4;j++){
      for (int k=0;k<(4-j);k++){
          for (int i=0;i<4;i++){
            //System.out.println("i "+ (i+k) + " j " + (6-(j+k+i)));
            if(board[i+k][6-(j+k+i)] == 'X'){
              Xvalue++;
            }
            else if(board[i+k][6-(j+k+i)] == 'O'){
              Ovalue++;
            }
          }
         if (Xvalue == 3 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=50;
           else result-=50;
         }
         else if (Ovalue == 3 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=50;
           else result-=50;
         }
         if (Xvalue == 2 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=10;
           else result-=10;
         }
         else if (Ovalue == 2 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=10;
           else result-=10;
         }
         if (Xvalue == 1 && Ovalue == 0){
           if (getPlayer(1).getSymbol() == 'X') result+=1;
           else result-=1;
         }
         else if (Ovalue == 1 && Xvalue == 0){
           if (getPlayer(1).getSymbol() == 'O') result+=1;
           else result-=1;
         }
         Xvalue=0;
         Ovalue=0;
      }
    }

    return result;
  }
  public char endGame() {
    int draw =0;
    //evaluate rows
    int Xvalue = 0;
    int Ovalue = 0;
    for(int i=0; i<6; i++) {
         for(int j=0; j<7; j++){
          if(board[i][j] == 'X'){
            Xvalue++;
            Ovalue=0;
          }
          else if(board[i][j] == 'O'){
            Ovalue++;
            Xvalue=0;
          }
          else {
              Xvalue = 0;
              Ovalue = 0;
          }
          if (Xvalue == 4) return 'X';
          else if (Ovalue ==4) return 'O';
        }

        Xvalue = 0;
        Ovalue = 0;
    }


    //evaluate columns
    for(int j=0; j<7; j++) {
        for(int i=0; i<6; i++) {
          if(board[i][j] == 'X'){
            Xvalue++;
            Ovalue=0;
          }
          else if(board[i][j] == 'O'){
            Ovalue++;
            Xvalue=0;
          }
          else {
              Xvalue = 0;
              Ovalue = 0;
          }
          if (Xvalue == 4) return 'X';
          else if (Ovalue ==4) return 'O';
        }

        Xvalue = 0;
        Ovalue = 0;
    }


    //evaluate 1st diagonal
    Xvalue=0;
    Ovalue=0;
    for (int i=0;i<3;i++){
      for (int k=0;k<(3-i);k++) {
          for (int j=0;j<4;j++) {
            //System.out.println("i "+ (i+k+j) + " j " + (j+k));
            if(board[i+k+j][j+k] == 'X'){
              Xvalue++;
            }
            else if(board[i+k+j][j+k] == 'O'){
              Ovalue++;
            }
          }
          if (Xvalue == 4) return 'X';
          else if (Ovalue ==4) return 'O';
          Xvalue=0;
          Ovalue=0;
      }
    }
    Xvalue = 0;
    Ovalue = 0;
    for (int j=1;j<4;j++){
      for (int k=0;k<(4-j);k++){
          for (int i=0;i<4;i++){
            //System.out.println("i "+ (i+k) + " j " + (i+j+k));
            if(board[i+k][j+k+i] == 'X'){
              Xvalue++;
            }
            else if(board[i+k][j+k+i] == 'O'){
              Ovalue++;
            }
          }
          if (Xvalue == 4) return 'X';
          else if (Ovalue ==4) return 'O';
          Xvalue=0;
          Ovalue=0;
      }
    }

    //evaluate 2nd diagonal
    Xvalue=0;
    Ovalue=0;
    for (int i=0;i<3;i++){
      for (int k=0;k<(3-i);k++) {
          for (int j=0;j<4;j++) {
            //System.out.println("i "+ (i+k+j) + " j " + (6-(j+k)));
            if(board[i+k+j][6-(j+k)] == 'X'){
              Xvalue++;
            }
            else if(board[i+k+j][6-(j+k)] == 'O'){
              Ovalue++;
            }
          }
          if (Xvalue == 4) return 'X';
          else if (Ovalue ==4) return 'O';
          Xvalue=0;
          Ovalue=0;
      }
    }
    Xvalue = 0;
    Ovalue = 0;
    for (int j=1;j<4;j++){
      for (int k=0;k<(4-j);k++){
          for (int i=0;i<4;i++){
            //System.out.println("i "+ (i+k) + " j " + (6-(j+k+i)));
            if(board[i+k][6-(j+k+i)] == 'X'){
              Xvalue++;
            }
            else if(board[i+k][6-(j+k+i)] == 'O'){
              Ovalue++;
            }
          }
          if (Xvalue == 4) return 'X';
          else if (Ovalue ==4) return 'O';
          Xvalue=0;
          Ovalue=0;
      }
    }



    //DRAW
    for(int i=0; i<6; i++) {
      for(int j=0; j<7; j++) {
          if(board[i][j] != '_') {
            draw++;
          }
      }
    }
    if(draw == 42) {
      return 'E';
    }

    return 'D';
  }
  //used in MCTS
  public double evaluateUCB(){
    double eval = (double)evaluatefunc(); //+16 ou -16
    double visits = getVisits();
    double choices = getChoices();
    double C = Math.sqrt(2); //podemos mudar grafico c diferentes valores???
    double b = Math.sqrt(2*Math.log(visits)/choices);
    return eval+C*Math.sqrt(2.*Math.log(visits)/choices);
  }


}

class Player {
  public char symbol;
  public String name;

  public Player(char s, String n){
    symbol = s;
    name = n;

  }

  public char getSymbol(){
    return symbol;
  }
  public String getName(){
    return name;
  }

  public Player(Player player){
    char copy_symbol;
    String copy_name;
    copy_symbol = player.getSymbol();
    copy_name = player.getName();
    symbol = copy_symbol;
    name = copy_name;
  }
}

class TypesOfSearch{
  public static int minimax(int depth, GameBoard initial) {

    Global.action=0;
    Global.worst=0;
    Global.best=0;
    Global.OP=0;
    Global.max_depth = depth;
    Global.plays=0;
    Global.total_nodes=0;
    long time = System.currentTimeMillis();
    int action = max(depth,initial);
    System.out.println("Number of expanded nodes: " + Global.total_nodes);
    long timef = System.currentTimeMillis()-time;
    long s1 = (timef)%1000;
    double s0 = (double)(timef-s1)/1000;
    double timef2 = s0+s1/(double)1000;
    System.out.println("Elapsed time = "+timef2+"s");
    System.out.println();

    if (action ==1000){
      return Global.best;
    }

    ArrayList<GameBoard> ChildrenList = initial.createChildren(1);
    int play = Global.worst;
    if (Global.action == -1000){
      if (Global.OP!=0){
        return Global.OP; //caso vá tapar vitoria do oponente
      }
      //encontra 2ª melhor jogada se nao der pra impedir vitoria na coluna da vitoria do humano
      int best = Integer.MIN_VALUE;
      for(GameBoard ChildBoard : ChildrenList){
        if (ChildBoard.getPrevMove()!= Global.worst){
          int value = ChildBoard.evaluatefunc();
          if (value>best){
            best = value;
            play = ChildBoard.getPrevMove();
          }
        }
      }
    }

    for(GameBoard ChildBoard : ChildrenList){
      if (ChildBoard.getPrevMove() == Global.best && ChildBoard.getPrevMove()!=Global.worst){
        return ChildBoard.getPrevMove(); //caso encontre melhor jogada para ele
      }
    }
    //ultimo caso : da a vitoria ao humano
    return play;
  }
  public static int max(int depth, GameBoard game_board){

    // terminal test
    if (game_board.endGame() != 'D' || depth == 0){
      return (game_board.evaluatefunc()-16);
    }

    int action =Integer.MIN_VALUE;
    Global.total_nodes+=1;

    ArrayList<GameBoard> ChildrenList = game_board.createChildren(1);

    // successors
    for(GameBoard ChildBoard : ChildrenList){
      Global.plays+=1;
      int value = min(depth-1,ChildBoard);
      if (value == 512+16) value-=(Global.max_depth-depth);
      action = Math.max(action,value);
      ///////
      if (value==action && Global.max_depth-depth == 0){ //melhor jogada do PC
        if (ChildBoard.evaluatefunc()==512 && Global.plays<2 ){ //jogada seguinte vitoria do pc
          Global.best = ChildBoard.getPrevMove();
          return 1000;
        }
        Global.best = ChildBoard.getPrevMove();
      }

      if (value==-1000 && Global.max_depth-depth==0 ){ //vitoria do Humano na seguinte jogada
        //tentar impedir de ganhar
        int move = ChildBoard.getNextMove();
        GameBoard father = new GameBoard(game_board.getterBoard());
        father.setPlayers(game_board.getPlayer(1),game_board.getPlayer(2));
        int stop = Stop_OP(father,move); // se é possivel interromper vitoria
        if (stop !=-1) {
          Global.OP= move; //vai ficar aqui interromper vitoria do Human
        }
        else { //se nao der
          Global.worst = move;
        }
        Global.action=-1000;
      }

      if (Global.max_depth-depth ==0){
        Global.plays=0;
      }

    }
    return action;
  }
  public static int min(int depth, GameBoard game_board){

    // terminal test
    if (game_board.endGame() != 'D' || depth == 0){
      return (game_board.evaluatefunc()+16);
    }
    Global.total_nodes+=1;
    int action = Integer.MAX_VALUE;

    ArrayList<GameBoard> ChildrenList = game_board.createChildren(2);
    // successors
    for(GameBoard ChildBoard : ChildrenList){
      int value = max(depth-1,ChildBoard);
      action = Math.min(action,value);
      if ( ChildBoard.evaluatefunc()==-512 && Global.plays<2 ){ /*caso seja vitoria do Human*/
        game_board.setNextMove(ChildBoard.getPrevMove());
        //return -1000; TIRAR????Dà????
      }
      if (Global.max_depth-depth ==1) Global.plays=1;
    }
    return action;
  }

  public static int alpha_beta(int depth, GameBoard initial) {

    Global.worst=0;
    Global.best=0;
    Global.OP=0;
    Global.max_depth = depth;
    Global.plays=0;
    Global.total_nodes=0;
    long time = System.currentTimeMillis();
    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;
    int action = max_alpha_beta(depth,alpha,beta,initial);
    System.out.println("Number of expanded nodes: " + Global.total_nodes);
    long timef = System.currentTimeMillis()-time;
    long s1 = (timef)%1000;
    double s0 = (double)(timef-s1)/1000;
    double timef2 = s0+s1/(double)1000;
    System.out.println("Elapsed time = "+timef2+"s");
    System.out.println();


    ArrayList<GameBoard> ChildrenList = initial.createChildren(1);
    int play = Global.worst;
    if (action ==1000){
      return Global.best;
    }
    if (Global.action == -1000){
      if (Global.OP!=0){
        return Global.OP; //caso vá tapar vitoria do oponente
      }
      //encontra 2ª melhor jogada se nao der pra impedir vitoria na coluna da vitoria do humano
      int best = Integer.MIN_VALUE;
      for(GameBoard ChildBoard : ChildrenList){
        if (ChildBoard.getPrevMove()!= Global.worst){
          int value = ChildBoard.evaluatefunc();
          if (value>best){
            best = value;
            play = ChildBoard.getPrevMove();
          }
        }
      }
    }
    for(GameBoard ChildBoard : ChildrenList){
      if (ChildBoard.getPrevMove() == Global.best && ChildBoard.getPrevMove()!=Global.worst){
        return ChildBoard.getPrevMove(); //caso encontre melhor jogada para ele
      }
    }
    //ultimo caso : da a vitoria ao humano
    return play;
  }
  public static int max_alpha_beta(int depth,int alpha, int beta, GameBoard game_board){

    // terminal test
    if (game_board.endGame() != 'D' || depth == 0){
      return (game_board.evaluatefunc()-16);
    }

    int action =Integer.MIN_VALUE;

    ArrayList<GameBoard> ChildrenList = game_board.createChildren(1);

    // successors
    int count = 0;
    for(GameBoard ChildBoard : ChildrenList){
      Global.plays+=1;
      Global.total_nodes+=1;
      int value = min_alpha_beta(depth-1,alpha,beta,ChildBoard);
      if (value == 512+16) value-=(Global.max_depth-depth);
      action = Math.max(action,value);

      ///////
      if (value==action && Global.max_depth-depth==0){ //melhor jogada do PC
        if (ChildBoard.evaluatefunc()==512  && Global.plays<2 ){ //jogada seguinte vitoria do pc
          Global.best = ChildBoard.getPrevMove();
          Global.best_plays = Global.plays;
          return 1000;
        }

        Global.best = ChildBoard.getPrevMove();
      }

      if (value==-1000 && Global.max_depth-depth==0){ //vitoria do Humano na seguinte jogada
        //tentar impedir de ganhar
        int move = ChildBoard.getNextMove();
        GameBoard father = new GameBoard(game_board.getterBoard());
        father.setPlayers(game_board.getPlayer(1),game_board.getPlayer(2));
        int stop = Stop_OP(father,move); // se é possivel interromper vitoria
        if (stop !=-1) {
          Global.OP= move; //vai ficar aqui interromper vitoria do Human
        }
        else { //se nao der
          Global.worst = move;
        }
        Global.action =-1000;
      }

      if (value>beta){
        //Global.best = ChildBoard.getPrevMove();
        //Global.best_plays = Global.plays;
        return action;
      }
      alpha = Math.max(alpha,value);
    }

    return action;
  }
  public static int min_alpha_beta(int depth,int alpha, int beta, GameBoard game_board){

    // terminal test
    if (game_board.endGame() != 'D' || depth == 0){
      return (game_board.evaluatefunc()+16);
    }

    int action = Integer.MAX_VALUE;

    ArrayList<GameBoard> ChildrenList = game_board.createChildren(2);
    // successors
    for(GameBoard ChildBoard : ChildrenList){
      Global.total_nodes+=1;
      int value = max_alpha_beta(depth-1,alpha,beta,ChildBoard);
      action = Math.min(action,value);
      if ( ChildBoard.evaluatefunc()==-512 && Global.plays<2 ){ /*caso seja vitoria do Human*/
        game_board.setNextMove(ChildBoard.getPrevMove());
      }
      if (Global.max_depth-depth ==1) Global.plays=1;
      if (value<alpha){
        return action;
      }
      beta=Math.min(beta,value);
    }
    return action;
  }

  public static int MCTS(int depth, int iter, GameBoard initial){

    Global.worst=0;
    Global.plays=0;
    Global.best_plays=Integer.MAX_VALUE;
    long time = System.currentTimeMillis();

    // meter jogada + rapida
    initial.setVisits(1); //tem de começar em 2 por causa da funçao de avaliação UCB
    initial.setChoices(1);
    double best_value = Integer.MIN_VALUE;
    int best_move = 0;


    for (int i=0;i<iter;i++){
      GameBoard node = initial.copyBoard();
      LinkedList<GameBoard> visited = new LinkedList<GameBoard>();
      visited.add(node);
      int count = depth;
      int player = 1;
      while (count>0){
        Global.plays+=1;
        ArrayList<GameBoard> ChildrenList = node.createChildren(player);
        ArrayList<GameBoard> Children_list = node.getChildren();
        for(GameBoard ChildBoard : Children_list){
          //vitoria na proxima jogada
          if (ChildBoard.evaluatefunc()==512 && Global.plays<2){
            return ChildBoard.getPrevMove(); //2º passo pq o 1º é a raiz
          }
          //check vitoria do Humano na prox jogada
          if (Global.plays==1){
            ArrayList<GameBoard> ChildrenList_Human = ChildBoard.createChildren(2);
            for(GameBoard ChildBoard_Human : ChildrenList_Human){
              if (ChildBoard_Human.evaluatefunc()==-512){ //vitoria na proxima jogada
                int move = ChildBoard_Human.getPrevMove();
                GameBoard father = new GameBoard(initial.getterBoard());
                father.setPlayers(initial.getPlayer(1),initial.getPlayer(2));
                int stop = Stop_OP(father,move); // se é possivel interromper vitoria
                if (stop !=-1) {
                  return move; //vai ficar aqui interromper vitoria do Human
                }
                else {
                  Global.worst=move; //se for possivel joga numa coluna diferente de worst
                  best_move=Global.worst;
                }
              }
            }
          }

          ChildBoard.setVisits(ChildBoard.getVisits()+1);
          ChildBoard.setValue(ChildBoard.evaluateUCB());
        }
        node = select(Children_list);
        node.setChoices(node.getChoices()+1);
        visited.add(node);
        count--;
        if (player == 1) player = 2;
        else player = 1;
      }


      GameBoard new_child = rollOut(node);
      double value = new_child.getValue();

      for (GameBoard node_visit : visited){
        node_visit.setValue(value);
      }
      GameBoard best_game = visited.get(visited.size()-2).copyBoard();
      if (best_game.getValue() > best_value && Global.plays<=Global.best_plays){
        best_move = best_game.getPrevMove();
        best_value = best_game.getValue();
        Global.best_plays = Global.plays;
      }
      Global.plays=0;
    }
    //encontrar jogada em q nao perca
    if (best_move==Global.worst){
      ArrayList<GameBoard> ChildrenList2 = initial.createChildren(1);
      //encontra 2ª melhor jogada se nao der pra impedir vitoria na coluna da vitoria do humano
      int best = Integer.MIN_VALUE;
      for(GameBoard ChildBoard2 : ChildrenList2){
        if (ChildBoard2.getPrevMove()!= Global.worst){
          int value = ChildBoard2.evaluatefunc();
          if (value>best){
            best = value;
            best_move = ChildBoard2.getPrevMove();
          }
        }
      }
    }
    return best_move;
  }
  public static GameBoard select(ArrayList<GameBoard> children){
    double min = Integer.MIN_VALUE;
    GameBoard next_board = new GameBoard('_');
    for(GameBoard ChildBoard : children){
      double value = ChildBoard.getValue();

      //outra melhor jogada
      if (value>min){
        min = value;
        next_board = ChildBoard.copyBoard();
      }
    }
    return next_board;
  }
  public static GameBoard rollOut(GameBoard node){
    LinkedList<Integer> actions = new LinkedList<Integer>();
    for (int i=1; i<8;i++){
      if (node.validMove(i)){
        actions.add(i);
      }
    }

    int max = actions.size();
    if (max == 0) return node;
    Random generator = new Random();
    int rand = generator.nextInt(max)+1;

    GameBoard new_child = node.copyBoard();
    new_child.MoveGameBoard(rand,2);
    double value = new_child.evaluateUCB();
    //definir funçao de valor
    new_child.setValue(value);

    return new_child;

  }

  public static int Stop_OP(GameBoard father, int move){
    //simular jogada anterior pai no sitio onde poderia tapar vitoria
    father.MoveGameBoard(move,1);

    //simular jogada de oponente no sitio onde poderia ganhar
    if (father.validMove(move)) { //caso coluna nao esteja cheia
      father.MoveGameBoard(move,2);
      if (father.evaluatefunc()==-512){ //nao impede v
        return -1; //pode acontecer se for unico move possivel
      }
      else{ //coluna nao está cheia mas ja impediu vitoria
        return move;
      }
    }
    else { //está cheia -> ja impediu vitoria
      return move;
    }

  }
}

class Global {
  public static int best; // best move for the computer
  public static int worst; //this move gives the victory to the Human
  public static int OP; //move that stops the Human from winning
  public static int max_depth; //max depth search
  public static int action; //used to break the algorithm
  public static int plays; //current play
  public static int best_plays; //quicker play to the best move
  public static int maxEval;
  public static int minEval;
  public static int a;
  public static int b;
  public static int total_nodes;
}

  _____     ______     ______     _____     __    __     ______    
 /\  == \   /\  ___\   /\  __ \   /\  __-.  /\ "-./  \   /\  ___\   
 \ \  __<   \ \  __\   \ \  __ \  \ \ \/\ \ \ \ \-./\ \  \ \  __\   
  \ \_\ \_\  \ \_____\  \ \_\ \_\  \ \____-  \ \_\ \ \_\  \ \_____\ 
   \/_/ /_/   \/_____/   \/_/\/_/   \/____/   \/_/  \/_/   \/_____/ 
                                                                   
//=================================================================//
Faculdade de Ciências da Universidade do Porto - Departamento de Ciências e Computadores

João Aguiar - up201606361
Pedro Sobral - up201305467

//=================================================================//

Connect4 is an implementation of several adversial search algorithms, with the goal of experimentally find a solution with a certain algorithm and being able to make a comparison to the level of time and memory used.

//=================================================================//

Types of Search: 
                 -MiniMax;
                 -Alpha-Beta;
                 -Monte Carlo Tree Search.

//=================================================================//

Classes: -Connect4 -> Requests input, creates the game and chooses the algorithm (if playing against computer);
         -GameBoard -> Creating and changing the board;
         -Player -> Create the players (symbols and names);
	 -TypesOfSearch -> Class with search types;
	 -Global -> Class that helps with some global variables.

//=================================================================//

Execute:     -Open the terminal
             -Go to the work folder and compile with the following: $javac *.java
             -To execute it just write to the command line: $java Connect4
	     -Then you may choose:
		 1) Play against Human/Computer;
		 2) Who plays first;
		 3) Symbol you want to use;
		 4) Algorithm;
		 5) Depth;

//=================================================================//

The program was compiled and run in Ubuntu 18.04.2 LTS with openjdk 1.8.0_191.

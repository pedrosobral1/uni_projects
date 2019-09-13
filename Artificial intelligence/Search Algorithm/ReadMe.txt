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

Puzzle15 is an implementation of several search algorithms, with the goal of experimentally find a solution with a certain algorithm and being able to make a comparison to the level of time and memory used.

//=================================================================//

Types of Search: 
                 -BFS -> performs search in width;
                 -DFS -> performs search in depth;
                 -IDFS -> depth search, where the maximum depth is your limit.
		 -A* -> performs A* search;
                 -Greedy -> performs Greedy search;

//=================================================================//

Classes: -Puzzle15 -> Requests input and calls the search functions. It also has a CheckValidation function that allows us to immeditely know if its possible to get to the final board.
         -GameBoard -> Creating and changing the board;
         -Heuristic -> Class to calculate the cost of the Nodes.
	 -TypesOfSearch -> Class with search types;
	 -BoardComparator -> Class that helps Priority Queue to compare boards;

//=================================================================//

Execute:     -Open the terminal
             -Go to the work folder and compile with the following: $javac *.java
             -To execute it just write to the command line: $java Puzzle15
         
	     There are two examples (test1.txt and test2.txt) you can use to run it: $java Puzzle15 < test1.txt
//=================================================================//

The program was compiled and run in Ubuntu 18.04.2 LTS with openjdk 1.8.0_191.

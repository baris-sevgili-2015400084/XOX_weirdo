package assignment3;
import java.io.*;
import java.util.*;

public class BES2015400084 {

	public static void main(String[] args) throws FileNotFoundException {

		Random rand = new Random();
		Scanner console = new Scanner(System.in);
		String we = "X"; 		//our mark is X
		String computer = "O";		//computers mark is O

		String[][] board = new String[6][6]; //I defined an array to keep and change our board

		System.out.println("Do you want to start a new game or load from a previous game(load/new)?");

		if(console.next().equalsIgnoreCase("load")){
			Scanner input = new Scanner(new File("input.txt"));
			String loadgame="";				//we take the data from input as 6 lines then keep it in loadgame string
			for(int p=0; p<=5; p++)
				loadgame+=input.nextLine();
			for(int i=0; i<=5; i++) { 			//these two for loops are used to print loadgame string into our array
				for(int j=0; j<=5; j++) {
					int n=6*i+j;					//to re-order as a table
					if(loadgame.charAt(n)==' ')			//to prevent possible reading mistakes
						n+=1;
					if(loadgame.charAt(n)=='X'){
						board[i][j] = "X";	
					}else if(loadgame.charAt(n)=='O'){
						board[i][j] = "O";
					}else board[i][j] = "B";
				}
			}
		}

		else {
			for (int i = 0; i <=5; i++) {		//if user asks for a new board these for loops create that
				for (int j = 0; j <= 5; j++) {
					board[i][j] = "B";			//prints "B" to each box in our array
				}
			}
		}

		int targetcolumn = 0;			//the target position of the column
		int targetrow = 0;				//the target position of the row
		int basecolumn = 0;				//the initial position of the column
		int baserow = 0;				//the initial position of the row
		int turns = 0;					//the counter that finds whose turn
		String actionType = "";			//the string that defines the action type (move or change)
		printBoard(board);				//method to print our board

		while(checkFinished(board)==3){		//instructions below keep go until finding a "finished" board

			boolean playability = false;   	//boolean to check whether the action is proper

			if(turns%2==0){					// it asks us to enter an action if turn%2 equals 0; if not computer takes an action

				System.out.println("Enter an action");



				//keeps go if the action is not valid
				while (!playability) {
					actionType = console.next();
					while(!actionType.equalsIgnoreCase("C")&&!actionType.equalsIgnoreCase("M")){ //checks action types validity
						console.nextLine(); //to consume the possible wrong line.
						System.out.println("Please define your action as \"M\" or \"C\"");
						actionType = console.next(); 		//re-takes the action type
					}


					if (actionType.equalsIgnoreCase("C")) { 	//it means a change action
						targetrow = console.nextInt();			//first coordinate
						targetcolumn = console.nextInt();		//second coordinate
						playability = change(board, we, targetrow, targetcolumn); //checks validity then takes action as in method

					} else if (actionType.equalsIgnoreCase("M")) { //it means a move action
						baserow = console.nextInt();	//the row of the chosen point that will be moved
						basecolumn = console.nextInt();	//the column of the chosen point that will be moved

						targetrow = console.nextInt();		//the row of the chosen point that we will go
						targetcolumn = console.nextInt();	//the column of the chosen point that we will go

						playability = move(board, baserow, basecolumn, targetrow, targetcolumn, we);
						//checks validity then takes action as in method
					}

					if(playability){ //checks playability to increment "turns" counter in order to pass her turn to computer
						turns++;
					}
				}
			}else{			//if turns%2==1 it means it's computer's turn
				while(!playability){

					int action = rand.nextInt(2);	// it decides its action with a random  ( "0" for change, "1" for move )

					if(action == 0){ //it means a change
						targetrow = rand.nextInt(6);	//the row of the chosen point; from [0-5]
						targetcolumn = rand.nextInt(6);	//the column of the chosen point; from [0-5]

						playability = change(board, computer, targetrow, targetcolumn);//checks validity then takes action as in method

					}else{
						baserow = rand.nextInt(6);		//the row of the chosen point that will be moved; from [0-5]
						basecolumn = rand.nextInt(6);	//the column of the chosen point that will be moved; from [0-5]

						targetrow = rand.nextInt(6);	//the row of the chosen point that we will go to; from [0-5]
						targetcolumn = rand.nextInt(6);	//the column of the chosen point that we will go to; from [0-5]

						playability = move(board, baserow, basecolumn, targetrow, targetcolumn, computer); //checks validity then takes action as in method
					}

					if(playability){	//checks playability to increment "turns" counter in order to pass her turn to computer
						turns++;
					}
				}
				System.out.println("Then computer played..");
			}
			printBoard(board);  //prints the board after each move
		}
		System.out.println();
		if(checkFinished(board) == 1){		//checks whether the game is finished and takes action according to winner
			System.out.println("yeyy! ne\"X\"t please ;)");
		}else {
			System.out.println("You're defeated.. You have a l\"O\"t to learn :(");
		}



	}
	public static void printBoard(String[][] board){  	//a method to print our board
		System.out.println("-BOARD  -\n");
		for(int i = 0; i< 6; i++){				//prints each characters in our array as a 6x6 table
			for (int j = 0; j < 6; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	public static int checkFinished(String[][] board){	/*checks whether the game is finished and takes 
														returns the integer which corresponds to winner */
		int counter = 1;		// a counter to define the winner
	
		for(int i = 0; i < 6; i++){			//these for loops check indexes next to each other
			for(int j = 1; j < 6; j++){
				if(!board[i][j-1].equals("B") && board[i][j-1].equals(board[i][j])){ //if it finds same 2 characters (x or o) increments counter
					counter++;
					if(counter == 6){			//when counter hits 6; the game is over
						if(board[i][j].equals("X")){	//if that index includes an X when counter hits 6; returns 1
							return 1;}
						else return 2;			//if that index not includes an X when counter hits 6; returns 2
					}
				}else{
					counter = 1;			// if characters following each other are not same; counter becomes 1
	
				}
			}
			counter=1;			//when skipping the next line counter becomes 1
		}
		counter=1;			//after checking rows counter becomes 1
	
		for(int j = 0; j < 6; j++){		//these for loops checks indexes one under the other
			for(int i = 1; i < 6; i++){
				if( !board[i-1][j].equals("B") && board[i-1][j].equals(board[i][j])){ //if it finds same 2 characters (x or o) increments counter
					counter++;
					if(counter == 6){			//when counter hits 6; the game is over
						if(board[i][j].equals("X")){		//if that index includes an X when counter hits 6; returns 1
							return 1;
						}else return 2;			//if that index not includes an X when counter hits 6; returns 2
					}
				}else{
					counter = 1;			// if characters one under the other are not same; counter becomes 1
				}
	
			}
			counter=1;			//when skipping the next column counter becomes 1
		}
	
	
	
	
	
		return 3;			//if counter never hits 6; method returns 3, which means game is not over
	
	}
	public static boolean isInside(int row, int column){	// a boolean method to check coordinates interval as [0-6]
		return row < 6 && row >=0 && column < 6 && column >=0 ;
	}
	public static boolean outerCheck(int row , int column, String player){	// a boolean method to check whether the coordinates on the outer shell
		return  isInside(row, column) && ( row==0 || row == 5 || column == 0 || column == 5 );	//it includes isInside method to check intervals
	
	
	}
	public static boolean change(String[][] board, String player,int targetrow, int targetcolumn ){ /*the boolean method that makes change action,
	takes board array, player's mark(x or o) as strings, target row and column's coordinates as integers*/
		if(outerCheck(targetrow, targetcolumn,player) && board[targetrow][targetcolumn].equals("B")){ //checks validity of the move
			if(player.equalsIgnoreCase("X"))	// if we are the one taking that action informs us about validity of the action
				System.out.println("Valid change");
	
			board[targetrow][targetcolumn] = player; // the target box becomes our chosen character
			return true;					//returns true after making the move
		}
		if(player.equalsIgnoreCase("X"))	// if we are the one taking that action informs us about validity of the action
			System.out.println("Not a valid change!");
	
		return false;				//returns false if action is not valid
	}
	public static boolean notMovable(int baserow, int basecolumn, int targetrow, int targetcolumn){	/* a boolean method to check movability 
	it takes base row and column's coordinates; and target row and column's coordinates as integers and returns true if move is NOT VALÝD */


		if (targetrow == baserow){ //if target and base rows are the same; one of them's column must be 0 and the other's column must be 5

			return !((basecolumn==5 && targetcolumn==0) || (basecolumn == 0 && targetcolumn ==5)); 

		} else if (targetcolumn == basecolumn) {	/*if target and base columns are the same;
		 one of them's row must be 0 and the other's row must be 5	*/
			return !((baserow==5 && targetrow==0) || (baserow == 0 && targetrow ==5));
		} else {	return true;		//if all needs are not met; returns true which means our move is not valid
		}



	}

	public static boolean move(String[][] board, int baserow, int basecolumn, int targetrow , int targetcolumn, String player ){/* the boolean
	 method	which takes board array and player's mark (x or o) as strings, base row and column's coordinates and target row and column's
	 coordinates as integers.then checks movability and does our shifting. if given coordinates are not proper for move action returns false; 
	 otherwise returns true at the end of the method*/


		if( !(outerCheck(baserow,basecolumn,player) && board[baserow][basecolumn] == player //if instructions are not valid returns false
				&&(outerCheck(targetrow,targetcolumn,player)))){
			if(player.equalsIgnoreCase("x")){		//if we are the one taking invalid action; warns us then returns false
				System.out.println("Check your coordinates");}
			return false;
		}



		if( notMovable(baserow,basecolumn,targetrow,targetcolumn)  ) {	//if instructions are not valid returns false
			if(player.equalsIgnoreCase("x"))				//if we are the one taking invalid action; warns us then returns false
				System.out.println("Not a valid shift");		
			return false;
		}

		String segment = board[baserow][basecolumn];		//if action is valid, defines the character in that part of the array
		//into our string "segment".

		if(baserow == targetrow){  //if base and target rows are the same that means each 
			//character in the that row will move one box left or right

			if(targetcolumn > basecolumn){		//Shifts the segment to the right end; shift others to one box left
				for(int i = 0; i <=4; i++ ){
					board[baserow][i] = board[baserow][i+1];
				}
				board[targetrow][targetcolumn] = segment;
			} else { 							//Shifts the segment to the left end ; shift others to one box right
				for(int i = 4; i >=0; i-- ){
					board[baserow][i+1] = board[baserow][i];
				}
				board[targetrow][targetcolumn] = segment;
			}


		} else if(basecolumn == targetcolumn){ //if base and target columns are the same that means each 
			//characters in the that column  will move one box up or down

			if(targetrow > baserow){		//Shifts the segment to the bottom end; shift others to one box up
				for(int i = 0; i <=4; i++ ){
					board[i][basecolumn] = board[i+1][basecolumn];
				}
				board[targetrow][targetcolumn] = segment;
			} else { 						//Shifts the segment to the top end; shift others to one box down
				for(int i = 4; i >=0; i-- ){
					board[i+1][basecolumn] = board[i][basecolumn];
				}
				board[targetrow][targetcolumn] = segment;
			}
		}  else {

			return false;		//if the coordinates are not proper for shifting returns false
		}
		return true;			//after taking one of the actions above returns true
	}


}


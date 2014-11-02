import java.util.Scanner;


public class Game {
	public int[][][] board;
	public int inARow;
	public int players;
	public int currentPlayer;
	
	public Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		Game g = new Game();
	}
	
	public Game() {
		System.out.println("x dimension:");
		int x = scan.nextInt();
		System.out.println("y dimension:");
		int y = scan.nextInt();
		System.out.println("z dimension:");
		int z = scan.nextInt();
		System.out.println("plays in a row:");
		inARow = scan.nextInt();
		System.out.println("players:");
		players = scan.nextInt();
		
		board = new int[z][y][x];
		currentPlayer = 1;
		
		scan.nextLine();
		
		int winner = 0;
		while (winner == 0) {
			printBoard();
			
			boolean picked = false;
			while (!picked) {
				System.out.println("Player " + currentPlayer + "'s turn:");
				
				String s = scan.nextLine();
				String[] array = s.split(",");
				if (array.length == 3) picked = input(currentPlayer,Integer.parseInt(array[0]),Integer.parseInt(array[1]),Integer.parseInt(array[2]));
			}
			
			if (currentPlayer == players) {
				currentPlayer = 1;
			} else {
				currentPlayer++;
			}
			
			winner = checkWins();
		}
		
		System.out.println("Player " + winner + " wins.");
	}
	
	public int checkWins() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				for (int z = 0; z < board[x][y].length; z++) {
					for (int xp = -1; xp <= 1; xp++) {
						for (int yp = -1; yp <= 1; yp++) {
							for (int zp = -1; zp <= 1; zp++) {
								if (xp!=0||yp!=0||zp!=0) {
									int check = checkNext(x,y,z,xp,yp,zp,0,0);
									if (check != 0) return check;
								}
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	public int checkNext(int x, int y, int z, int xp, int yp, int zp, int flavor, int hits) {
		if (board[x][y][z]==0) {
			//if (hits>1) System.out.println("[checkNext] dead end!");
			return 0;
		} else if (hits == inARow-1) {
			//System.out.println("[checkNext] hits!");
			return (board[x][y][z]);
		} else if (!(inside(0,x+xp,board.length-1)&&inside(0,y+yp,board[x].length-1)&&inside(0,z+zp,board[x][y].length-1))) {
			//System.out.println("[checkNext] outside board!");
			return 0;
		} else if (board[x][y][z]==flavor||flavor==0) {
			//System.out.println("[checkNext] checking next! "+x+"+"+xp+", "+y+"+"+yp+", "+z+"+"+zp+", "+hits);
			return checkNext(x+xp,y+yp,z+zp,xp,yp,zp,board[x][y][z],hits+1);
		} else {
			//System.out.println("[checkNext] unaccounted condition!");
			return 0;
		}
	}
	
	public boolean input(int player, int x, int y, int z) {
		if (inside(0,x,board.length-1)&&inside(0,y,board[x].length-1)&&inside(0,z,board[x][y].length-1)&&(board[x][y][z]==0)) {
			board[x][y][z] = player;
			return true;
		} else {
			return false;
		}
	}
	
	public void printBoard() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				for (int z = 0; z < board[x][y].length; z++) {
					if (board[x][y][z]!=0) System.out.print(board[x][y][z]);
					else System.out.print(" ");
					
					if (z!=board[x][y].length-1) System.out.print("|");
				}
				System.out.println();
				if (y!=board[x].length-1) {
					for (int z = 0; z < board[x][y].length*2-1; z++) {
						if (z%2==0) System.out.print("-");
						else System.out.print("+");
					}
					System.out.println();
				}
			}
			System.out.println();
		}
	}
	
	public boolean inside(int min, int cur, int max) {
		return (cur>=min)&&(cur<=max);
	}
}

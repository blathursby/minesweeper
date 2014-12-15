package minesweeper;

import java.util.Random;

public class Board {
	/*
		board - the current state of the board, where:
			-1 - denotes a mine
			0  - an empty place
			1, 2, 3, ... - how many mines are around
			
		minesLeft - mines that haven't been marked on the board
	*/
	Integer[][] board;
	Integer minesLeft, rows, cols;
	
	public Board(int _rows, int _cols, int mines){
		rows = _rows;
		cols = _cols;
		minesLeft = mines;
		
		board = new Integer[rows][cols];
		
		populate();
	}
	
	public int getPosition(int row, int col){
		return board[row][col];
	}
	
	private void populate(){
		/*
			mines - count of mines to be placed on the board during creation
		*/
		
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++)
				board[i][j] = 0;
		
		/*
			Placing mines in random places
		*/
		int mines = minesLeft;
		Random ran = new Random();
		
		while(mines > 0){
			int y = -1, x = -1;
			do{
				y = ran.nextInt(rows);
				x = ran.nextInt(cols);
			}while( board[y][x] != 0 );
			
			board[y][x] = -1;
			mines--;
		}
		
		/*
			Set tips around mines
		*/
		for(int x=0; x < cols; x++)
			for(int y=0; y < rows; y++){
				if( board[y][x] == 0 ){
					int m = 0; // mines around
					
					for( int _x = (x-1); _x < (x+2); _x++ )
						for( int _y = (y-1); _y < (y+2); _y++ ){
							
							if( _x > -1 && _y > -1 && _x < cols && _y < rows ){
								if( board[_y][_x] == -1 )
									m++;
							}
							
						}
					
					board[y][x] = m;
				}
			}
	}
}

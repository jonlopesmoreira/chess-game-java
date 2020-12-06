package chess;

import boardgame.Board;

public class ChessMatch 
{
	private Board board;
	
	public ChessMatch()
	{
		this.board = new Board(8,8);
	}
	
	public ChessPiece[][] getPieces()
	{
		ChessPiece[][] matriz = new ChessPiece[board.getRows()] [board.getCols()];
		for(int i=0; i<board.getRows(); i++)
		{
			for(int j=0; j<board.getCols(); j++)
			{
				matriz[i][j] = (ChessPiece)board.piece(i,j);
			}
		}
		return matriz;
	}
	
}

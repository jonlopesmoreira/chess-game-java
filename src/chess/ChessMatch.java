package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch 
{
	private Board board;
	
	public ChessMatch()
	{
		this.board = new Board(8,8);
		this.initialSetup();
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
	
	private void placeNewPiece(char col, int row, ChessPiece piece)
	{
		board.placePiece(piece, new ChessPosition(col, row).toPosition());
	}
	private void initialSetup()
	{
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));

	}
}

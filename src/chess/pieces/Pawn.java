package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[][] possibleMoves() 
	{
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];
		Position p = new Position(0, 0);
		
		if(getColor() == Color.WHITE)
		{
			p.setValues(position.getRow()-1, position.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow()-2, position.getCol());
			Position p2 = new Position(position.getRow()-1, position.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0 && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow()-1, position.getCol()-1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow()-1, position.getCol()+1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
		}
		else
		{
			p.setValues(position.getRow()+1, position.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow()+2, position.getCol());
			Position p2 = new Position(position.getRow()+1, position.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0 && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow()+1, position.getCol()-1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow()+1, position.getCol()+1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p))
			{
				mat[p.getRow()][p.getCol()] = true;
			}
		}
			
		return mat;
	}
	@Override
	public String toString() {
		return "P";
	}

}

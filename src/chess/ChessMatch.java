package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch 
{
	private Board board;
	private int turn;
	private Color currentPlayer;
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	private boolean check;

	public ChessMatch()
	{
		this.turn=1;
		this.currentPlayer=Color.WHITE;
		this.board = new Board(8,8);
		this.initialSetup();
		this.check=false;
	}
	
	public int getTurn()
	{
		return this.turn;
	}
	
	public void nextTurn()
	{
		this.turn++;
		this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color)
	{
		return (color==Color.WHITE ? Color.BLACK : Color.WHITE);			
	}
	
	private ChessPiece king(Color color)
	{
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor()==color).collect(Collectors.toList());
		for(Piece p: list)
		{
			if(p instanceof King)
			{
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king of the board");
	}
	
	private boolean testCheck(Color color)
	{
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor()== opponent(color)).collect(Collectors.toList());
		for(Piece p: opponentPieces)
		{
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getCol()])
			{
				return true;
			}
		}
		return false;
	}
	
	public Color getCurrentPlayer()
	{
		return this.currentPlayer;
	}
	
	public boolean getCheck()
	{
		return check;
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

	public boolean[][] possibleMoves(ChessPosition sourcePosition)
	{
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition)
	{
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if(testCheck(currentPlayer))
		{
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cant put yourself in check");
		}
		check = (testCheck(opponent(currentPlayer))) ? true: false;
		this.nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private void validateSourcePosition(Position position)
	{
		if(!board.thereIsAPiece(position))
		{
			throw new ChessException("There is no piece on source position");
		}
		
		if(this.currentPlayer != ((ChessPiece) board.piece(position)).getColor())
		{
			throw new ChessException("Wrong piece, this piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove())
		{
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target)
	{
		if(!board.piece(source).possibleMove(target))
		{
			throw new ChessException("The chosen piece cant move to target position");
		}
	}
	private Piece makeMove(Position source, Position target)
	{
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if(capturedPiece!=null)
		{
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece captured)
	{
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		if(captured !=null)
		{
			board.placePiece(captured, target);
			capturedPieces.remove(captured);
			piecesOnTheBoard.add(captured);
		}
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece)
	{
		board.placePiece(piece, new ChessPosition(col, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	private void initialSetup()
	{
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}

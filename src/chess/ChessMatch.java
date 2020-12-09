package chess;

import java.util.ArrayList;
import java.util.List;

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

	public ChessMatch()
	{
		this.turn=1;
		this.currentPlayer=Color.WHITE;
		this.board = new Board(8,8);
		this.initialSetup();
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
	
	public Color getCurrentPlayer()
	{
		return this.currentPlayer;
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

package chess.chess960;

import chess.ChessMatch;
import chess.Color;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Random;

import boardgame.Board;

// Chess 960 Rules
// randomize the back row of the board
// King has to be between the two rooks no matter what
// Bishops MUST be on opposite colors
// https://en.wikipedia.org/wiki/Chess960

//LOOP
// Maybe start with a list of the pieces, let it randomly pick one - DONE
// We'll need to remove the already picked items from the array so we don't pick the same ones or miss any -DONE
// Make sure the king is between the rooks by keeping track of where the rooks are (case statement) - DONE
// so, if we place first rook down, we need to check if random piece is a king - DONE
// if it is, proceed like normal. If it's not, make a check to make sure that before the last rook is placed, the king is placed before it - DONE

//ASSIGN
//Then, we loop through a-h files and assign them - DONE
// We'll probably need to add a case statement for what piece it picks - DONE

// This project is set up for the standard board set up, so we'll have to make sure to assign the King within the same number of spaces
// As the original (3 spaces from the first rook and 2 spaces from the second)

public class BoardSetup {
    private Board board;
    private ChessMatch chessMatch;


    public BoardSetup(Board gameBoard, ChessMatch chessMatch) {
        this.board = gameBoard;
        this.chessMatch = chessMatch;
    }

    private ArrayList<String> piecesList = new ArrayList<String>();
    private char[] files = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private boolean isFirstRookPlaced = false;
    private char currentFile = files[0];
    private int indexTracker = 0;
    private boolean isKingPlaced = false;  //track if king has been placed so we don't try to place to twice


    public void chess960SetUp() {
        Random rand = new Random();
        int randomPiece = 0;

        piecesList.add("Rook");
        piecesList.add("Knight");
        piecesList.add("Bishop");
        piecesList.add("Queen");
        piecesList.add("King");
        piecesList.add("Bishop");
        piecesList.add("Knight");
        piecesList.add("Rook");


        for (; indexTracker <= 7;) {
            // assign the file as we loop through this array since it's the same size as the piecesList
            // and we dont assign a piece to a file that already has one on it
            currentFile = files[indexTracker];

            // if size is one and we try to subtract it, it will cause an error
            if (piecesList.size() == 1) {
                randomPiece = 0;
            } else {
                // size - 1 because the array is 0 indexed and it shrinks at every iteration
                randomPiece = rand.nextInt(piecesList.size() - 1);
            }
            // if we get the king, but we haven't placed the first rook down, skip it
            if (piecesList.get(randomPiece) == "King" && isFirstRookPlaced == false) {
                continue;
            }
            // call method to set the pieces on the board. Takes in a piece, a file, and the rook/bishop counter
            // for proper setup
            placeWhitePieceOnBoard(piecesList.get(randomPiece));
            
            //remove piece, increment
            piecesList.remove(randomPiece);
            indexTracker++;
        } 
    }

    public void placeWhitePieceOnBoard (String piece) {
        // switch statement to place the pieces on the board
        switch (piece) {
            case "Rook":
            // if more than one rook has been picked, we want to make sure the king gets placed before the last rook
            // then we place the rook after 
            // otherwise, procced normally
                if (isFirstRookPlaced == true && isKingPlaced == false) {
                    placePawnPiece(Color.WHITE);
                    placeBlackPiece(piece);
                    isKingPlaced = true;
                    indexTracker++;
                }
                placePawnPiece(Color.WHITE);
                chessMatch.placeNewPiece(currentFile, 1, new Rook(board, Color.WHITE));
                placeBlackPiece(piece);
                isFirstRookPlaced = true;
                break;

            case "Knight":
                placePawnPiece(Color.WHITE);
                chessMatch.placeNewPiece(currentFile, 1, new Knight(board, Color.WHITE));
                placeBlackPiece(piece);
                break;
            case "Bishop":
                placePawnPiece(Color.WHITE);
                chessMatch.placeNewPiece(currentFile, 1, new Bishop(board, Color.WHITE));
                placeBlackPiece(piece);
                break;
            case "Queen":
                placePawnPiece(Color.WHITE);
                chessMatch.placeNewPiece(currentFile, 1, new Queen(board, Color.WHITE));
                placeBlackPiece(piece);
                break;
            case "King":
                placePawnPiece(Color.WHITE);
                chessMatch.placeNewPiece(currentFile, 1, new King(board, Color.WHITE, this.chessMatch));
                placeBlackPiece(piece);
                isKingPlaced = true;
                break;
        }
    }

    public void placePawnPiece(Color color) {
        if (color == Color.WHITE) {
            chessMatch.placeNewPiece(currentFile, 2, new Pawn(board, color, this.chessMatch));
        } else
        chessMatch.placeNewPiece(currentFile, 7, new Pawn(board, color, this.chessMatch));
    }

    public void placeBlackPiece (String piece) {
        // switch statement to place the pieces on the board

        switch (piece) {
            case "Rook":
                placePawnPiece(Color.BLACK);
                chessMatch.placeNewPiece(currentFile, 8, new Rook(board, Color.BLACK));
                break;

            case "Knight":
                placePawnPiece(Color.BLACK);
                chessMatch.placeNewPiece(currentFile, 8, new Knight(board, Color.BLACK));
                break;
            case "Bishop":
                placePawnPiece(Color.BLACK);
                chessMatch.placeNewPiece(currentFile, 8, new Bishop(board, Color.BLACK));
                break;
            case "Queen":
                placePawnPiece(Color.BLACK);
                chessMatch.placeNewPiece(currentFile, 8, new Queen(board, Color.BLACK));
                break;
            case "King":
                placePawnPiece(Color.BLACK);
                chessMatch.placeNewPiece(currentFile, 8, new King(board, Color.BLACK, this.chessMatch));
                isKingPlaced = true;
                break;
}
    }
}



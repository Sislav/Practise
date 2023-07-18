package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import model.ModelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Board;
import model.Board.GameResult;
import MachineIntellegence.MachineIntellegence;

class MachineIntellegenceTest {
    final String fileDirectory = System.getProperty("user.dir") + "/src/Tests/";

    Board playingBoard;
    ArrayList<String> possibleMoves;

    @BeforeEach
    void setUp(){
        try {
            playingBoard = new Board(fileDirectory + "PlayingPosition.txt");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testMove() throws ModelException {
        possibleMoves = playingBoard.getPossibleMoves();
        while(!possibleMoves.isEmpty()) {
            try { possibleMoves = playingBoard.move(MachineIntellegence.move(playingBoard)); }
            catch(Exception ignored){}
        }
        assertNotEquals(playingBoard.isWin(), GameResult.Continue);
    }

    @Test
    void testPlay() throws IOException, ModelException {
        int BlackWinCount = 0;
        int WhiteWinCount = 0;
        MachineIntellegence.DEPTH = 5;
        for(int i = 0; i < 100; i++) {
            playingBoard.startNewGame(System.getProperty("user.dir") + "/src/Tests/" + "StartPosition.txt");
            possibleMoves = playingBoard.getPossibleMoves();
            while (!possibleMoves.isEmpty()) {
                try {
                    possibleMoves = playingBoard.move(MachineIntellegence.move(playingBoard));
                } catch (Exception ignored) {
                }
            }
            playingBoard.endGame();
            if(playingBoard.isWin() == GameResult.BlackWin)
                BlackWinCount++;
            else
                WhiteWinCount++;
        }
        System.out.println("Black Win result: " + BlackWinCount + "\n" + "White Win result: " + WhiteWinCount);
    }
}
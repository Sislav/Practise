package MachineIntellegence;

import model.Cell;
import model.CheckerColor;

import model.*;



public class MachineIntellegence {
    private static CheckerColor friendsColor;
    public static int DEPTH = 10;

    public static String move(Board board) throws ModelException {
        friendsColor = board.getNextMoveColor();
        CheckerColor enemyColor = Cell.enemyColor(friendsColor);

        String bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        Board gameTreeRoot = new Board(board);
        for(Board child = gameTreeRoot.getChildren(); child != null; child = gameTreeRoot.getChildren()) {
            int alpha = miniMax(child, DEPTH - 1, bestScore, Integer.MAX_VALUE);
            if (alpha >= bestScore || bestMove == null) {
                bestMove = gameTreeRoot.getCurrentPath();
                bestScore = alpha;
            }
        }
        return bestMove;
    }

    private static int miniMax(Board currentNode, int depth, int alpha, int beta) throws ModelException {
        Board tmp = new Board(currentNode);
        if (depth <= 0 || tmp.isWin() != Board.GameResult.Continue) {
            return currentNode.getTotalWeight(friendsColor, depth);
        }
        if (currentNode.getNextMoveColor() == friendsColor) {
            int currentAlpha = Integer.MIN_VALUE;
            for(Board child = currentNode.getChildren(); child != null; child = currentNode.getChildren()) {
                currentAlpha = Math.max(currentAlpha, miniMax(child, depth - 1, alpha, beta));
                alpha = Math.max(alpha, currentAlpha);
                if (alpha >= beta || currentAlpha == Integer.MIN_VALUE) {
                    return alpha;
                }
            }

            return currentAlpha;
        }
        int currentBeta = Integer.MAX_VALUE;

        for(Board child = currentNode.getChildren(); child != null; child = currentNode.getChildren()) {
            currentBeta = Math.min(currentBeta, miniMax(child, depth - 1, alpha, beta));
            beta = Math.min(beta, currentBeta);
            if (beta <= alpha || currentBeta == Integer.MAX_VALUE) {
                return beta;
            }
        }
        return currentBeta;
    }
}
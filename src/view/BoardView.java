package view;

import model.*;
import model.CheckerColor;
import model.CheckerType;
import MachineIntellegence.MachineIntellegence;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;



public class BoardView extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final String fontName = "Times New Roman";

    private MoveStory history = new MoveStory();

    Board board;
    MyButton[][] cells = new MyButton[BOARD_SIZE][BOARD_SIZE];
    JPanel cellsPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JLabel endGame;

    ImageIcon whitePawnImg;
    ImageIcon whiteKingImg;
    ImageIcon blackPawnImg;
    ImageIcon blackKingImg;

    CheckerColor firstPlayerColor;
    CheckerColor firstMove;
    ArrayList<String> possibleMoves = null;

    boolean thisGameWithComputer = false;

    public MoveStory getMoveStory() {
        return history;
    }

    public BoardView(String player1, String player2, CheckerColor firstMove,
                     CheckerColor firstPlayerColor, boolean thisGameWithComputer) throws ViewException{
        try {
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            boardPanel.setLayout(new BorderLayout());
            this.thisGameWithComputer = thisGameWithComputer;
            this.firstMove = firstMove;
            this.firstPlayerColor = firstPlayerColor;

            board = new Board(firstMove, player1, player2);

            Font font = new Font(fontName, Font.BOLD, 14);

            StringBuffer title = new StringBuffer(player1);
            title.append(" против ");
            title.append(player2);
            endGame = new JLabel("игра идет");
            endGame.setOpaque(true);
            endGame.setForeground(new Color(92, 71, 13));
            endGame.setBackground(new Color(245, 229, 186));
            endGame.setAlignmentX(Component.CENTER_ALIGNMENT);
            endGame.setFont(font);

            JLabel label = new JLabel(title.toString());
            label.setOpaque(true);
            label.setForeground(new Color(92, 71, 13));
            label.setBackground(new Color(245, 229, 186));

            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setFont(font);

            this.add(Box.createRigidArea(new Dimension(1, 1)));
            this.add(label);
            this.add(Box.createRigidArea(new Dimension(1, 1)));

            initializeIcons();
            initializeBoard();

            JButton[] verticalCells = new JButton[BOARD_SIZE];
            JPanel verticalNumbers = new JPanel();
            verticalNumbers.setLayout(new GridLayout(9, 1));

            char number = '8';

            for (int i = 1; i <= BOARD_SIZE; i++) {
                verticalCells[i - 1] = new JButton(Character.toString(number));
                number--;
                verticalCells[i - 1].setOpaque(true);
                verticalCells[i - 1].setForeground(new Color(92, 71, 13));
                verticalCells[i - 1].setBackground(new Color(245, 229, 186));
                verticalCells[i - 1].setFont(font);
                verticalCells[i - 1].setBorderPainted(false);
                verticalCells[i - 1].setEnabled(false);
                verticalNumbers.add(verticalCells[i - 1]);
            }
            verticalNumbers.add(new JButton());

            JButton[] horizontalLabels = new JButton[BOARD_SIZE];

            char letter = 'a';
            for (int i = 0; i < BOARD_SIZE; i++) {
                horizontalLabels[i] = new JButton(" " + Character.toString(letter) + " ");
                letter++;
                horizontalLabels[i].setOpaque(true);
                horizontalLabels[i].setForeground(new Color(92, 71, 13));
                horizontalLabels[i].setBackground(new Color(245, 229, 186));
                horizontalLabels[i].setFont(font);
                horizontalLabels[i].setEnabled(false);

                cellsPanel.add(horizontalLabels[i]);

            }

            this.setBackground(new Color(245, 229, 186));

            this.add(boardPanel);

            this.add(endGame);

            boardPanel.add(verticalNumbers, BorderLayout.WEST);

            showBoard(false);

            startGame();
        }
        catch(Exception e){
            Exception BoardViewException = new ViewException("Error in BoardView method");
            System.exit(-1);
        }
    }

    public Board getBoard() {
        return board;
    }

    private void getPossibleMoves(){
        if(possibleMoves == null) {
            possibleMoves = board.getPossibleMoves();
        }
        for(int i = 0; i < possibleMoves.size(); i++) {
            int index = 0;

            int vertical = (int)(possibleMoves.get(i).charAt(index++) - '0');
            int horizontal = (int)(possibleMoves.get(i).charAt(index++) - '0');

            cells[horizontal][vertical].isPossibleChecker = true;
        }
    }

    private void startGame() throws ViewException{
        try {
            if(firstMove != firstPlayerColor) {
                board.initializePossibleMoves();
                possibleMoves = board.move(MachineIntellegence.move(board));
            }
            getPossibleMoves();
            showBoard(false);
        }
        catch(Exception e) {
            Exception cannotStartGame = new ViewException("Cannot start game");
            System.exit(-1);
        }
    }



    private void initializeBoard(){
        Font font = new Font(fontName, Font.BOLD, 14);
        boardPanel.add(cellsPanel, BorderLayout.CENTER);


        cellsPanel.setLayout(new GridLayout(9, 8));

        initializeCells();

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(firstPlayerColor == CheckerColor.White) cellsPanel.add(cells[reverse(i)][j]);
                else cellsPanel.add(cells[i][j]);
            }
        }


    }

    private void initializeCells() {
        for(int horizontal = 0; horizontal < BOARD_SIZE; horizontal++) {
            for(int vertical = 0; vertical < BOARD_SIZE; vertical++) {
                cells[horizontal][vertical] = new MyButton();
                cells[horizontal][vertical].horizontal = horizontal;
                cells[horizontal][vertical].vertical = vertical;
                cells[horizontal][vertical].setBorderPainted(false);
                cells[horizontal][vertical].setOpaque(true);
            }
        }
    }

    private void initializeIcons() throws ViewException{
        try {
            whitePawnImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/view/Icons/WhitePawn.png")));
            Image image = whitePawnImg.getImage();
            Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
            whitePawnImg = new ImageIcon(newimg);

            whiteKingImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/view/Icons/WhiteKing.png")));
            image = whiteKingImg.getImage();
            newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
            whiteKingImg = new ImageIcon(newimg);

            blackPawnImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/view/Icons/BlackPawn.png")));
            image = blackPawnImg.getImage();
            newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
            blackPawnImg = new ImageIcon(newimg);

            blackKingImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/view/Icons/BlackKing.png")));
            image = blackKingImg.getImage();
            newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
            blackKingImg = new ImageIcon(newimg);
        }
        catch (Exception e){
            Exception InitializeIconsException = new ViewException("Cannot initialize Icons");
            System.exit(-1);
        }
    }

    public void deleteLastMove() throws ModelException, IOException, ViewException {
        possibleMoves = board.deleteLastMove();
        if(possibleMoves == null) {
            possibleMoves = board.getPossibleMoves();
        }

        for(int horizontal = 0; horizontal < BOARD_SIZE; horizontal++) {
            for(int vertical = 0; vertical < BOARD_SIZE; vertical++) {
                cells[horizontal][vertical].isPossibleChecker = false;
                cells[horizontal][vertical].isPossibleMove = false;
            }
        }
        getPossibleMoves();
        history.deleteMove();
        showBoard(false);
    }


    public void showBoard(boolean possibleMove) throws ViewException{
        String currentBoard = board.toString();
        int horizontal = 0;
        int vertical = 0;

        try {

            for(int index = 0; index < currentBoard.length(); index++) {
                switch (currentBoard.charAt(index)) {
                    case (' ') -> {
                        cells[horizontal][vertical].type = CheckerType.NoChecker;
                        cells[horizontal][vertical].color = CheckerColor.NoColor;
                        cells[horizontal][vertical].setBackground(new Color(253, 245, 223));
                        vertical++;
                    }
                    case ('#') -> {
                        cells[horizontal][vertical].type = CheckerType.NoChecker;
                        cells[horizontal][vertical].color = CheckerColor.NoColor;
                        cells[horizontal][vertical].setIcon(null);
                        if (cells[horizontal][vertical].isPossibleMove)
                            cells[horizontal][vertical].setBackground(Color.YELLOW);
                        else
                            cells[horizontal][vertical].setBackground(new Color(201, 174, 101));
                        vertical++;
                    }
                    case ('w') -> {
                        cells[horizontal][vertical].type = CheckerType.Pawn;
                        cells[horizontal][vertical].color = CheckerColor.White;
                        cells[horizontal][vertical].setIcon(whitePawnImg);
                        if (cells[horizontal][vertical].isPossibleChecker && !possibleMove)
                            cells[horizontal][vertical].setBackground(Color.RED);
                        else
                            cells[horizontal][vertical].setBackground(new Color(201, 174, 101));
                        vertical++;
                    }
                    case ('W') -> {
                        cells[horizontal][vertical].type = CheckerType.King;
                        cells[horizontal][vertical].color = CheckerColor.White;
                        cells[horizontal][vertical].setIcon(whiteKingImg);
                        if (cells[horizontal][vertical].isPossibleChecker && !possibleMove)
                            cells[horizontal][vertical].setBackground(Color.RED);
                        else
                            cells[horizontal][vertical].setBackground(new Color(201, 174, 101));
                        vertical++;
                    }
                    case ('b') -> {
                        cells[horizontal][vertical].type = CheckerType.Pawn;
                        cells[horizontal][vertical].color = CheckerColor.Black;
                        cells[horizontal][vertical].setIcon(blackPawnImg);
                        if (cells[horizontal][vertical].isPossibleChecker && !possibleMove)
                            cells[horizontal][vertical].setBackground(Color.RED);
                        else
                            cells[horizontal][vertical].setBackground(new Color(201, 174, 101));
                        vertical++;
                    }
                    case ('B') -> {
                        cells[horizontal][vertical].type = CheckerType.King;
                        cells[horizontal][vertical].color = CheckerColor.Black;
                        cells[horizontal][vertical].setIcon(blackKingImg);
                        if (cells[horizontal][vertical].isPossibleChecker && !possibleMove)
                            cells[horizontal][vertical].setBackground(Color.RED);
                        else
                            cells[horizontal][vertical].setBackground(new Color(201, 174, 101));
                        vertical++;
                    }
                    default -> {
                    }
                }

                if(vertical >= BOARD_SIZE) {
                    vertical = 0;
                    horizontal++;
                }
                if(horizontal >= BOARD_SIZE) {
                    break;
                }

            }

        }
        catch(Exception ex) {
            Exception ShowBoardException = new ViewException("Cannot load show board method");
            System.exit(-1);
        }
    }
    private class MyButton extends JButton{
        int horizontal;
        int vertical;

        boolean isPossibleChecker = false;
        boolean isPossibleMove = false;

        String path;

        CheckerColor color;

        CheckerType type;

        public MyButton(){
            super();
            ActionListener action = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(isPossibleMove) {
                        try {
                            possibleMoves = board.move(path);

                            history.writeMove(path);
                            cellsDefaultOptions(path);
                            getPossibleMoves();
                            showBoard(false);
                            if(possibleMoves.isEmpty()) {
                                board.endGame();
                                endGame.setText("Игра окончена");
                                return;
                            }
                            if(thisGameWithComputer) {


                                String movePath = MachineIntellegence.move(board);
                                possibleMoves = board.move(movePath);

                                history.writeMove(movePath);
                                cellsDefaultOptions(movePath);
                                getPossibleMoves();
                                showBoard(false);
                                if(possibleMoves.isEmpty()) {
                                    board.endGame();
                                    endGame.setText("Игра окончена");
                                    return;
                                }

                            }
                        }
                        catch(Exception exp) {
                            Exception MyButtonException = new ViewException("Cannot initialize Board");
                            System.exit(-1);
                        }

                    }
                    else if(isPossibleChecker) {
                        try {
                            showPossibleMoves(horizontal, vertical);
                        } catch (ViewException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            };
            this.addActionListener(action);
        }

        public boolean isBlackCell() {
            if((horizontal % 2) == (vertical % 2)) {
                return true;
            }
            return false;
        }
    }

    private void cellsDefaultOptions(String path) {

        int index = 0;
        int startVertical = (int)(path.charAt(index++) - '0');
        int startHorizontal = (int)(path.charAt(index++) - '0');
        cells[startHorizontal][startVertical].setIcon(null);

        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j].isPossibleChecker = false;
                cells[i][j].isPossibleMove = false;
            }
        }
    }

    private static int reverse(int a) {
        return BOARD_SIZE - a - 1;
    }



    private void showPossibleMoves(int horizontal, int vertical) throws ViewException {
        MyButton button = cells[horizontal][vertical];
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j].isPossibleMove = false;
            }
        }
        for (String possibleMove : possibleMoves) {
            int index = 0;

            int startVertical = (int) (possibleMove.charAt(index++) - '0');
            int startHorizontal = (int) (possibleMove.charAt(index++) - '0');

            if (startVertical == button.vertical && startHorizontal == button.horizontal) {
                index = possibleMove.length() - 2;
                int finishVertical = (int) (possibleMove.charAt(index++) - '0');
                int finishHorizontal = (int) (possibleMove.charAt(index) - '0');
                cells[finishHorizontal][finishVertical].isPossibleMove = true;
                cells[finishHorizontal][finishVertical].path = possibleMove;

            }
        }

        showBoard(true);
    }


}
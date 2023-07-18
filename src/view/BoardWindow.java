package view;

import model.CheckerColor;
import java.awt.*;
import javax.swing.*;


public class BoardWindow extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;

    private static final String TITLE = "Шашки";

    private static final String fontName = "Times New Roman";

    BoardView board;

    OptionalPanel panel;

    MoveStory history;

    private String player1;
    private String player2;
    private CheckerColor firstMove;
    private CheckerColor firstPlayer;
    private boolean playingWithComputer = false;

    public BoardWindow(String player1, String player2, CheckerColor firstMove, CheckerColor firstPlayer) throws  ViewException {
        super(TITLE);
        try {
            this.setLayout(new BorderLayout());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setSize(WIDTH, HEIGHT);
            this.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            this.player1 = player1;
            this.player2 = player2;
            this.firstMove = firstMove;
            this.firstPlayer = firstPlayer;

            playingWithComputer = false;


            board = new BoardView(player1, player2, firstMove, firstPlayer, playingWithComputer);
            panel = new OptionalPanel(this, board);
            history = board.getMoveStory();


            JPanel ourPanel = new JPanel();
            ourPanel.setLayout(new BorderLayout());
            ourPanel.add(panel, BorderLayout.PAGE_END);
            ourPanel.add(history, BorderLayout.CENTER);

            this.add(board, BorderLayout.CENTER);
            this.add(ourPanel, BorderLayout.LINE_END);

            setVisible(true);
        }
        catch(Exception e){
            Exception BoardViewException = new ViewException("Cannot initialize BoardView method");
            System.exit(-1);
        }

    }

    public BoardWindow(String player1, CheckerColor firstMove, CheckerColor firstPlayer) throws ViewException {
        super(TITLE);
        try {
            this.setLayout(new BorderLayout());
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setSize(WIDTH, HEIGHT);
            this.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            this.player1 = player1;
            this.player2 = "Компьютер";
            this.firstMove = firstMove;
            this.firstPlayer = firstPlayer;

            playingWithComputer = true;

            board = new BoardView(player1, player2, firstMove, firstPlayer, playingWithComputer);
            panel = new OptionalPanel(this, board);
            history = board.getMoveStory();


            JPanel ourPanel = new JPanel();
            ourPanel.setLayout(new BorderLayout());
            ourPanel.add(panel, BorderLayout.PAGE_END);
            ourPanel.add(history, BorderLayout.CENTER);

            this.add(board, BorderLayout.CENTER);
            this.add(ourPanel, BorderLayout.LINE_END);

            setVisible(true);
        }
        catch(Exception e){
            Exception BoardViewException = new ViewException("Cannot initialize BoardView method");
            System.exit(-1);
        }
    }
}
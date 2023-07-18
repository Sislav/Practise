package view;


import java.awt.*;
import javax.swing.*;


public class MoveStory extends JPanel {
    JTextArea text;

    public MoveStory() {
        this.setLayout(new BorderLayout());
        this.add(new JButton("history"));

        JLabel label = new JLabel("История ходов:");

        Font font = new Font("Times New Roman", Font.BOLD, 14);
        label.setOpaque(true);
        label.setForeground(new Color(92, 71, 13));
        label.setBackground(new Color(245, 229, 186));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(font);

        this.add(label, BorderLayout.PAGE_START);
        this.text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setPreferredSize(new Dimension(1, 1));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setBackground(new Color(245, 229, 186));
        add(scroll, BorderLayout.CENTER);
    }

    public void deleteMove() {
        String[] moves = text.getText().split(" ");
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < moves.length - 1; i++) {
            buf.append(moves[i]);
            buf.append(' ');
        }
        text.setText(buf.toString());
    }

    public void writeMove(String path) {
        String[] movePath = path.split(":");
        StringBuffer finalMove = new StringBuffer();
        for (String s : movePath) {
            finalMove.append((char) ('a' + (s.charAt(0) - '0')));
            finalMove.append((char) (s.charAt(1) + 1));
            finalMove.append(':');
        }
        finalMove.deleteCharAt(finalMove.length() - 1);
        text.append(finalMove.toString());
        text.append(" ");
    }
}
package view;

import model.ModelException;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;



public class OptionalPanel extends JPanel{
    public OptionalPanel(JFrame frame, BoardView board){

        this.setLayout(new FlowLayout());
        JButton prevMove = new JButton("Прошлый ход");
        JButton mainMenu = new JButton("Главное меню");

        prevMove.setOpaque(true);
        prevMove.setBorderPainted(false);
        prevMove.setBackground(new Color(201, 174, 101));

        mainMenu.setOpaque(true);
        mainMenu.setBorderPainted(false);
        mainMenu.setBackground(new Color(201, 174, 101));

        ActionListener actionMainMenu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        };

        ActionListener actionPrevMove = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    board.deleteLastMove();
                    if(board.thisGameWithComputer) {
                        board.deleteLastMove();
                    }
                }
                catch(IOException | ModelException | ViewException ex) {
                    ViewException OptionalPanelException = new ViewException("Cannot start Optional Panel");
                    System.exit(-1);
                }
            }
        };

        mainMenu.addActionListener(actionMainMenu);
        prevMove.addActionListener(actionPrevMove);

        this.add(prevMove);
        this.add(mainMenu);
        this.setBackground(new Color(245, 229, 186));
    }
}
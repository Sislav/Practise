package view;

import javax.swing.*;
public class ViewException extends Exception{

    ViewException(String errorMessage){
        JFrame frame = getFrame();
        JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static JFrame getFrame(){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setBounds(750, 250, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }
}

package program;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LauchPage implements ActionListener {

    JFrame frame = new JFrame();
    JButton button = new JButton("New Game");


    LauchPage(){

        button.setBounds(100,160,200,40);
        button.setFocusable(false);
        button.addActionListener(this);

        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==button){
            //close out the frame before open a new one
            frame.dispose();
            NewWindow myWindow = new NewWindow();

        }
    }
}

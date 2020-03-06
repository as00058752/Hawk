/*
Author: Anh (Steven) Nguyen
Last update: 03/05/2020
CS 380 Project
Goal: 
    Auto-targeting system using image processing. This project will use white
    baloons on a dark background to simulate potential threats captured through
    a thermal scope. As such, the image processing scheme will recognize the 
    largest and brightest shape as the potential target; future versions will
    implement deep learning algorithms. Upon identifying the target, the UI will
    ask the user to verify if target is a threat. Once verified, a laser pointer
    mounted on two servos will track the target; future versions will have an
    airsoft gun (plastic pellets as projectiles) engaging the baloons.
 */
package hawk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
Author: Anh (Steven) Nguyen
 */

public class UserInterface extends JFrame implements ActionListener {
    boolean on;
    private static int winxpos=0,winypos=0; // place window here
    private JButton button1,exitButton, button2, button3,
            button4, button5, button6, button7;
    private JPanel northPanel;
    private JPanel southPanel;
    private MenuPanel centerPanel;
    private static JFrame myFrame = null;
    BufferedImage globalImg;
    
    //Constructor
    public UserInterface ()  {
        on = true; //Flag to turn off camera
        myFrame = this;
        
        //North panel 
        northPanel = new JPanel();
        northPanel.setBackground(Color.BLUE);
        //North buttons
        button1 = new JButton("Button 1");
        northPanel.add(button1);
        button1.addActionListener(this);
        exitButton = new JButton("Exit");
        northPanel.add(exitButton);
        exitButton.addActionListener(this);
        button2 = new JButton("Button 2");
        northPanel.add(button2);
        button2.addActionListener(this);
        button3 = new JButton("Button 3");
        northPanel.add(button3);
        button3.addActionListener(this);
        
        //South panel
        southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        //South buttons
        button4 = new JButton("Button 4");
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(button4);
        button4.addActionListener(this);
        button5 = new JButton("Button 5");
        button5.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(button5);
        button5.addActionListener(this);
        button6 = new JButton("Button 6");
        button6.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(button6);
        button6.addActionListener(this);
        button7 = new JButton("Button 7");
        button7.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(button7);
        button7.addActionListener(this);
        
        //Center panel
        centerPanel = new MenuPanel();
        
        //Add panels
        getContentPane().add("North",northPanel);
        getContentPane().add("South",southPanel);
        getContentPane().add("Center",centerPanel);
        
        //Set size
        setSize(800,800);
        setLocation(winxpos,winypos);
        setVisible(true);
    }

    //Button functions
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== exitButton) {
            on = false;
            dispose();
        }
        if (e.getSource()== button1) {
             System.out.println("Button 1");
        }
        if (e.getSource () == button2){           
            System.out.println("Button 2");
        }
        if (e.getSource() == button3){
            System.out.println("Button 3");
        }
        if (e.getSource() == button4){
            System.out.println("Button 4");
        }
        if (e.getSource() == button5){
            System.out.println("Button 5");
        }
        if (e.getSource() == button6){
            System.out.println("Button 6");
        }
        if (e.getSource() == button7){
            System.out.println("Button 7");
        }
    }
    
    //Refreshes center panel with selected image
    public void refreshFrame(BufferedImage img){
        globalImg = img;
        repaint();
    }
    
    //Center panel which contains the video feed
    class MenuPanel extends JPanel {
        public void paintComponent (Graphics g) {
            g.drawImage(globalImg, 0, 0, this);
        }
    }
}


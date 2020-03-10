/*
Author: Anh (Steven) Nguyen
Contributors: Anthony Lopez, Jacob Barron, Brandon Dahl
CS 380 Project
Goal: 
    Auto-targeting system using image processing. This project will use white
    baloons open a dark background to simulate potential threats captured through
    a thermal scope. As such, the image processing scheme will recognize the 
    largest and brightest shape as the potential target; future versions will
    implement deep learning algorithms. Upon identifying the target, the UI will
    ask the user to verify if target is a threat. Once verified, a laser pointer
    mounted open two servos will track the target; future versions will have an
    airsoft gun (plastic pellets as projectiles) engaging the baloons.
 */
package hawk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
Author: Anh (Steven) Nguyen
Last update: 03/09/2020 by Anh(Steven) Nguyen
 */

public class UserInterface extends JFrame implements ActionListener {
    boolean open, mark, target1, target2, target3, target4;
    private static final int winxpos=0,winypos=0; // place window here
    private final JButton button1,exitButton, button2, button3,
            button4, button5, button6, button7;
    private final JPanel northPanel;
    private final JPanel southPanel;
    private final MenuPanel centerPanel;
    BufferedImage globalImg;
    
    //Constructor
    public UserInterface (int xDim, int yDim)  {
        open = true; //Flag to turn off camera
        
        //North panel 
        northPanel = new JPanel();
        northPanel.setBackground(Color.BLUE);
        //North buttons
        button1 = new JButton("Show Tracking");
        northPanel.add(button1);
        button1.addActionListener(this);
        button2 = new JButton("Hide Tracking");
        northPanel.add(button2);
        button2.addActionListener(this);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        northPanel.add(exitButton);
        button3 = new JButton("Disengage All");
        northPanel.add(button3);
        button3.addActionListener(this);
        
        //South panel
        southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        //South buttons
        button4 = new JButton("Target 1");
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(button4);
        button4.addActionListener(this);
        button5 = new JButton("Target 2");
        button5.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(button5);
        button5.addActionListener(this);
        button6 = new JButton("Target 3");
        button6.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(button6);
        button6.addActionListener(this);
        button7 = new JButton("Target 4");
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
        setSize(xDim, yDim);
        setLocation(winxpos,winypos);
        setVisible(true);
    }

    //Button functions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== exitButton) {
            open = false;
        }
        if (e.getSource()== button1) {
             System.out.println("Show Tracking");
             mark = true;
        }
        if (e.getSource () == button2){           
            System.out.println("Hide Tracking");
            mark = false;
        }
        if (e.getSource() == button3){
            target1 = false;
            target2 = false;
            target3 = false;
            target4 = false;
            System.out.println("Disengage All");
        }
        if (e.getSource() == button4){
            target1 = !target1;
            System.out.println("Button 4");
        }
        if (e.getSource() == button5){
            target2 = !target2;
            System.out.println("Button 5");
        }
        if (e.getSource() == button6){
            target3 = !target3;
            System.out.println("Button 6");
        }
        if (e.getSource() == button7){
            target4 = !target4;
            System.out.println("Button 7");
        }
    }
    
    //Refreshes center panel with selected image
    public void refreshFrame(BufferedImage img){
        globalImg = img;
        repaint();
    }
    
    public void open() throws IOException, InterruptedException {
        this.refreshFrame(ImageIO.read(new File("StartLogo.jpg")));
        Thread.sleep(1000);
    }
    
    public void close() throws InterruptedException, IOException {
        this.refreshFrame(ImageIO.read(new File("ExitLogo.jpg")));
        Thread.sleep(1000);
        this.dispose();
    }
    
    //Center panel which contains the video feed
    class MenuPanel extends JPanel {
        @Override
        public void paintComponent (Graphics g) {
            g.drawImage(globalImg, 0, 0, this);
        }
    }
}


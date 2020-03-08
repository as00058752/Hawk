/*
Author: Anh (Steven) Nguyen
Contributors: Anthony Lopez, Jacob Barron, Brandon Dahl
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

import com.github.sarxos.webcam.Webcam;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
Author: Anh (Steven) Nguyen
Last update: 03/07/2020 by Anh(Steven) Nguyen
 */

public class Eyes {
    private final Webcam source = Webcam.getDefault();
    private BufferedImage snapShot = null;
    
    public void resolution(int w, int h){
        source.setViewSize(new Dimension(w, h));
    }
    
    public BufferedImage look() throws IOException{
        snapShot = source.getImage();
        return snapShot;
    }
    
    public void open(){
        source.open();
    }
    
    public void close(){
        source.close();
    }
    
    //Creates a JPG image
    public void imprint(int index) throws IOException{
        ImageIO.write(snapShot, "JPG", new File("Image" + index + ".jpg"));
    }
    
    //Draws a crosshair at the center of target
    public void mark(int xCenter, int yCenter) throws IOException{
        Graphics2D g = snapShot.createGraphics();
        g.drawImage(snapShot, 0, 0, null);
        g.setColor(Color.BLACK);
        g.drawString("Target 1", xCenter - 22, yCenter + 25);
        g.drawOval(xCenter - 10, yCenter -10, 20, 20);
        g.setColor(Color.BLUE);
        g.drawOval(xCenter - 11, yCenter -11, 22, 22);
        g.drawOval(xCenter - 9, yCenter -9, 18, 18);
        g.drawLine(xCenter - 7, yCenter, xCenter + 7, yCenter);
        g.drawLine(xCenter, yCenter - 7, xCenter, yCenter +7);
        g.dispose();
    }
}
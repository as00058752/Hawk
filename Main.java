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

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
Author: Anh (Steven) Nguyen
Last update: 03/07/2020 by Anh(Steven) Nguyen
 */

public class Main {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int xDim = 640, yDim =480;
        
        Hawk hawk = new Hawk();
        hawk.eyes.resolution(xDim, yDim);
        hawk.eyes.open();
        
        UserInterface display = new UserInterface(xDim, yDim + 110);
        display.refreshFrame(ImageIO.read(new File("StartLogo.jpg")));
        Thread.sleep(1000);
        
        while (display.on) {
            hawk = new Hawk();
            hawk.getImage();
            hawk.getRgbMax();
            hawk.findCenter();
            if (display.mark)
                hawk.eyes.mark(hawk.xCenter, hawk.yCenter);
            display.refreshFrame(hawk.img);
        }
        
        display.refreshFrame(ImageIO.read(new File("ExitLogo.jpg")));
        Thread.sleep(3000);
        display.dispose();
        hawk.eyes.close();
    }
}

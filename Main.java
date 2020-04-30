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

import java.io.File;
import java.io.IOException;

/*
Author: Anh (Steven) Nguyen
Last update: 04/29/2020 by Anh(Steven) Nguyen
 */

public class Main {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int xDim = 640, yDim =480, frames = 1;
        long timeFlag = System.currentTimeMillis(), currentTime;
        
        
        File panAngle = new File("panAngle.txt");
        File tiltAngle = new File("tiltAngle.txt");
        File panArrayX = new File("panArrayX.txt");
        File panArrayY = new File("panArrayY.txt");
        File tiltArrayX = new File("tiltArrayX.txt");
        File tiltArrayY = new File("tiltArrayY.txt");
        ///Environment object
        Environment field = new Environment();
        field.map(panArrayX, panArrayY, tiltArrayX, tiltArrayY, panAngle, tiltAngle);
        
        ////Messenger object used to send data to Servo-contro program
        Messenger msg = new Messenger();
        msg.configureRoute();
        msg.connect();
        
        ////Hawk object
        Hawk hawk = new Hawk();
        hawk.eyes.resolution(xDim, yDim); //set camera's resolution
        hawk.eyes.open(); //turn on camera
        
        ////UserInterface object used to display and interact with the GUI
        UserInterface display = new UserInterface(xDim, yDim + 110);
        display.open();
        
        
        
        ////This loop processes one frame per cycle
        while (display.open) {
            ////Optional if-statement to throtle the FPS
            //if (System.currentTimeMillis()/200 > timeFlag/200) {    
                
                ////Count the number of frames processed every second
                currentTime = System.currentTimeMillis();
                if (currentTime / 1000 > timeFlag / 1000) {
                    timeFlag = currentTime;
                    hawk.eyes.fps = frames;
                    frames = 0;
                } //End of if
                
                ////Get frame from camera, mark center or target, display frame
                hawk.getImage();
                hawk.findCenter();
                hawk.eyes.mark(display.mark, display.mapping, hawk.xCenter, hawk.yCenter, 
                        hawk.xI, hawk.xF, hawk.yI, hawk.yF, display.target1, 
                        field.frame[hawk.xCenter][hawk.yCenter], field);
                display.refreshFrame(hawk.img);
                
                frames++; //Keep track of the number of processed frames
                
                msg.send(hawk.xCenter, hawk.yCenter); //send info to servo-control program
            //} //End of if
        } //End of while
        
        msg.send(-1, -1); //flag to end connection to servo-control program
        display.close(); //close GUI window
        hawk.eyes.close(); //turn off camera
    }
}

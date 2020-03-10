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

import java.io.IOException;

/*
Author: Anh (Steven) Nguyen
Last update: 03/09/2020 by Anh(Steven) Nguyen
 */

public class Main {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int xDim = 640, yDim =480, frames = 1;
        long timeFlag = System.currentTimeMillis(), currentTime, flag = timeFlag;
        
        Hawk hawk = new Hawk();
        hawk.eyes.resolution(xDim, yDim);
        hawk.eyes.open();
        
        UserInterface display = new UserInterface(xDim, yDim + 110);
        display.open();
        
        while (display.open) {
        //if (System.currentTimeMillis()/200 > flag/200) {    
            currentTime = System.currentTimeMillis();
            if (currentTime / 1000 > timeFlag / 1000) {
                timeFlag = currentTime;
                hawk.eyes.fps = frames;
                frames = 0;
            }
            hawk.getImage();
            hawk.findCenter();
            hawk.eyes.mark(display.mark, hawk.xCenter, hawk.yCenter, hawk.xI, hawk.xF,
                    hawk.yI, hawk.yF, display.target1);
            display.refreshFrame(hawk.img);
            frames++;
            flag = currentTime;
        } //}
        
        display.close();
        hawk.eyes.close();
    }
}

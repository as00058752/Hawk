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

import java.io.IOException;

/*
Author: Anh (Steven) Nguyen
 */

public class Main {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Hawk hawk = new Hawk();
        hawk.eyes.resolution(640, 480);
        hawk.eyes.open();
        
        UserInterface display = new UserInterface();
        
        while (display.on) {
            hawk = new Hawk();
            hawk.getImage();
            hawk.getRgbMax();
            hawk.findCenter();
            hawk.eyes.mark(hawk.xCenter, hawk.yCenter);
            display.refreshFrame(hawk.img);
        }
        hawk.eyes.close();
    }
}

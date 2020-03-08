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

/*
Author: Anh (Steven) Nguyen
Last update: 03/05/2020 by Anh(Steven) Nguyen
 */

public class Claws {
    Environment field = new Environment();
    
    public void strike(){
        
    }
    
    public void aim(int xCenter, int yCenter){
        //field.map(xCenter, yCenter);
        //coordinatesToAngle(field.realX, field.realY);
    }
    
    public void servoControl(int panAngle, int tiltAngle){
        
        //move servos according to the given angles
        
    }
    
    public void coordinatesToAngle(int x, int y) {
        int panAngle = 0, tiltAngle = 0;
        
        //map (x,y) to pan and tilt angles
        
        servoControl(panAngle, tiltAngle);
    }
}

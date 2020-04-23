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
Last update: 04/23/2020 by Anh(Steven) Nguyen
 */

public class Pixel {
    int pan = 90;
    int tilt = 90;
    int[] xTilt = new int[641];
    int[] yTilt = new int[641];
    int[] xPan = new int[641];
    int[] yPan = new int[641];
    int nx = 0;
    int ny = 0;
    
}

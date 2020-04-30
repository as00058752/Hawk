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
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
Author: Anh (Steven) Nguyen
Last update: 04/29/2020 by Anh(Steven) Nguyen
 */

public class Environment {
    //2D array of Pixel objects
    Pixel[][] frame = new Pixel[641][481];
    int[] panArrayN = new int[131];
    Object[] panArrayX = new Object[131];
    Object[] panArrayY = new Object[131];
    int[] tiltArrayN = new int[131];
    Object[] tiltArrayX = new Object[131];
    Object[] tiltArrayY = new Object[131];

    public void map(File panArrayXFile, File panArrayYFile, File tiltArrayXFile, 
            File tiltArrayYFile, File panAngleFile, File tiltAngleFile) throws FileNotFoundException {
        
        int[][] panAngle = new int[641][481];
        Scanner panAngleReader = new Scanner(panAngleFile);
        
        int[][] tiltAngle = new int[641][481];
        Scanner tiltAngleReader = new Scanner(tiltAngleFile);
        
        this.panArrayX = new Object[131];
        Scanner panArrayXReader = new Scanner(panArrayXFile);
        
        this.panArrayY = new Object[131];
        Scanner panArrayYReader = new Scanner(panArrayYFile);
        
        this.tiltArrayX = new Object[131];
        Scanner tiltArrayXReader = new Scanner(tiltArrayXFile);
        
        this.tiltArrayY = new Object[131];
        Scanner tiltArrayYReader = new Scanner(tiltArrayYFile);
        
        for (int j = 0; j < 481; j++) {

            for (int i = 0; i < 641; i++) {
                panAngle[i][j] = panAngleReader.nextInt();
                tiltAngle[i][j] = tiltAngleReader.nextInt();
            }
        }
        panAngleReader.close();
        tiltAngleReader.close();
        
        for (int i = 80; i < 131; i++) {
            int[] tempPanArrayX = new int[panArrayXReader.nextInt()];
            int[] tempPanArrayY = new int[panArrayYReader.nextInt()];
            for (int j = 0; j < tempPanArrayX.length; j++) {
                tempPanArrayX[j] = panArrayXReader.nextInt();
                tempPanArrayY[j] = panArrayYReader.nextInt();
            }
            this.panArrayX[i] = tempPanArrayX;
            this.panArrayY[i] = tempPanArrayY;
            this.panArrayN[i] = tempPanArrayX.length;
        }
        panArrayXReader.close();
        panArrayYReader.close();
        
        for (int i = 50; i < 131; i++) {
            int[] tempTiltArrayX = new int[tiltArrayXReader.nextInt()];
            int[] tempTiltArrayY = new int[tiltArrayYReader.nextInt()];
            for (int j = 0; j < tempTiltArrayX.length; j++) {
                tempTiltArrayX[j] = tiltArrayXReader.nextInt();
                tempTiltArrayY[j] = tiltArrayYReader.nextInt();
            }
            this.tiltArrayX[i] = tempTiltArrayX;
            this.tiltArrayY[i] = tempTiltArrayY;
            this.tiltArrayN[i] = tempTiltArrayX.length;
        }
        tiltArrayXReader.close();
        tiltArrayYReader.close();
        
        //Initialize each Pixel object in the 2D array, frame.
        
        for (int y = 0; y < 481; y++) {
            for (int x = 0; x < 641; x++) {
                this.frame[x][y] = new Pixel();
                this.frame[x][y].pan = panAngle[x][y];
                this.frame[x][y].tilt = tiltAngle[x][y];
                this.frame[x][y].nPan = this.panArrayN[tiltAngle[x][y]];
                this.frame[x][y].xPan = (int[]) this.panArrayX[tiltAngle[x][y]];
                this.frame[x][y].yPan = (int[]) this.panArrayY[tiltAngle[x][y]];
                this.frame[x][y].nTilt = this.tiltArrayN[panAngle[x][y]];
                this.frame[x][y].xTilt = (int[]) this.tiltArrayX[panAngle[x][y]];
                this.frame[x][y].yTilt = (int[]) this.tiltArrayY[panAngle[x][y]];
            }
        }
    }
}

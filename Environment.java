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

public class Environment {
    int realX, realY;
    Pixel[][] frame = new Pixel[641][481];
    Pixel[] pan = new Pixel[641];
    Pixel[] tilt = new Pixel[481];
    
    public void map() {
        for (int x = 0; x <= 640; x++) {
            for (int y = 0; y <= 480; y++) {
                this.frame[x][y] = new Pixel();
            }
        }
        for (int x = 0; x <= 640; x++)
            this.pan[x] = new Pixel();
        
        for (int y = 0; y <= 480; y++)
            this.tilt[y] = new Pixel();
        
        int y;
        for (int tilt = 80; tilt <= 130; tilt++) {
            for (int x = 0; x <= 640; x++) {
                switch (tilt) {
                    case 80:
                        y = (int) -(-0.001 * Math.pow(x, 2) + 0.7787 * x - 199.42281);
                        break;
                    case 90:
                        y = (int) -(-0.00135 * Math.pow(x, 2) + 1.00876 * x - 321.02645);
                        break;
                    case 100:
                        y = (int) -(-0.00201 * Math.pow(x, 2) + 1.44737 * x - 466.06415);
                        break;
                    case 110:
                        y = (int) -(-0.00295 * Math.pow(x, 2) + 2.08113 * x - 641.29967);
                        break;
                    case 120:
                        y = (int) -(-0.00511 * Math.pow(x, 2) + 3.55183 * x - 959.81194);
                        break;
                    case 130:
                        y = (int) -(-0.00988 * Math.pow(x, 2) + 6.938 * x - 1639.8034);
                        break;
                    default:
                        y = -1;
                        x = 641;
                        break;
                }
                if (y >= 0 && y <= 480) {
                    this.frame[x][y].tilt = tilt;
                    int n = this.tilt[tilt].ny++;
                    this.tilt[tilt].xTilt[n] = x;
                    this.tilt[tilt].yTilt[n] = y;
                    //System.out.println("Tilt: (" + x + "," + y + ") ; " + tilt + " ; " + n);
                }
            }
        }
        for (int pan = 10; pan <= 170; pan++) {
            for (int x = 0; x <= 640; x++) {
                switch (pan) {
                    case 50:
                        y = (int) -(-1.285 * x - 33.554);
                        break;
                    case 70:
                        y = (int) -(-2.661 * x + 446.94);
                        break;
                    case 90:
                        y = (int) -(-13.15 * x + 4090.712);
                        break;
                    case 110:
                        y = (int) -(5.173 * x - 2282.425);
                        break;
                    case 130:
                        y = (int) -(1.794 * x - 1106.666);
                        break;
                    default:
                        y = -1;
                        x = 641;
                        break;
                }
                if (y >= 0 && y <= 480) {
                    this.frame[x][y].pan = pan;
                    int n = this.pan[pan].nx++;
                    this.pan[pan].xPan[n] = x;
                    this.pan[pan].yPan[n] = y;
                    //System.out.println("Pan: (" + x + "," + y + ") ; " + pan);
                }
            }
        }
        for (int x = 0; x <= 640; x++) {
            for (int i = 0; i <= 480; i++) {
                this.frame[x][i].xTilt = this.tilt[this.frame[x][i].tilt].xTilt;
                this.frame[x][i].yTilt = this.tilt[this.frame[x][i].tilt].yTilt;
                this.frame[x][i].ny = this.tilt[this.frame[x][i].tilt].ny;
                this.frame[x][i].xPan = this.pan[this.frame[x][i].pan].xPan;
                this.frame[x][i].yPan = this.pan[this.frame[x][i].pan].yPan;
                this.frame[x][i].nx = this.pan[this.frame[x][i].pan].nx;
            }
        }
        //for (int x = 0; x <= 640; x++) {
        //    for (int i = 0; i <= 480; i++) {
        //        if (this.frame[x][i].tilt != 90) {
        //            System.out.println("(" + x + "," + i + ")   tilt: " + this.frame[x][i].tilt);
        //        }
        //    }
        //}
    }
}

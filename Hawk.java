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

import java.awt.image.BufferedImage;
import java.io.IOException;

/*
Author: Anh (Steven) Nguyen
Last update: 04/23/2020 by Anh(Steven) Nguyen
 */

public class Hawk {
    BufferedImage img = null;
    int width, height, rgbMax, xCenter = 0, yCenter = 0, tolerance = 15, maxCrossSection = 0, xI, xF, yI, yF;
    Eyes eyes = new Eyes();
    Claws claws = new Claws();
    /**
     * @param x
     * @param y
     */
    public void getShape (int x, int y){
        int flag = 0, xi = x, xf = 0, horizontal, xMid, yi = 0, yf = 0, vertical, yMid;
        while (flag == 0){
            if (x >= this.width){
                xf = x;
                flag++;
            }
            else if (getPixelValue(x, y) > this.rgbMax - this.tolerance){
                x += 1;
            }
            else{
                if (y + 1 >= this.height){
                    if (getPixelValue(x, y - 1) > this.rgbMax - this.tolerance)
                        y -= 1;
                    else {
                        xf = x;
                        flag++;
                    }
                }
                else if (y - 1 <= 0){
                    if (getPixelValue(x, y + 1) > this.rgbMax - this.tolerance)
                        y += 1;
                    else {
                        xf = x;
                        flag++;
                    }
                }
                else if (getPixelValue(x, y + 1) > this.rgbMax - this.tolerance){
                    y += 1;
                }
                else if (getPixelValue(x, y - 1) > this.rgbMax - this.tolerance){
                    y -= 1;
                }
                else{
                    for(int temp = 0; temp < 15; temp++){
                        if (x + temp >= this.width || temp == 14){
                            xf = x;
                            flag++;
                            temp = 15;
                        }
                        else if (getPixelValue(x + temp, y) > this.rgbMax - this.tolerance){
                            x += temp;
                            temp = 15;
                        }
                    }
                }
            }
        }
        horizontal = xf - xi;
        xMid = xi + (horizontal / 2);
        
        yMid = y;
        x = xMid;
        while (flag == 1){
            if (y >= this.height){
                yf = y;
                flag++;
            }
            else if (getPixelValue(x, y) > this.rgbMax - this.tolerance){
                y += 1;
            }
            else{
                if (x + 1 >= this.width){
                    if (getPixelValue(x - 1, y) > rgbMax - tolerance)
                        x -= 1;
                    else {
                        yf = y;
                        flag++;
                    }
                }
                else if (x - 1 <= 0){
                    if (getPixelValue(x + 1, y) > rgbMax - tolerance)
                        x += 1;
                    else {
                        yf = y;
                        flag++;
                    }
                }
                else if (getPixelValue(x + 1, y) > rgbMax - tolerance)
                    x += 1;
                else if (getPixelValue(x - 1, y) > rgbMax - tolerance)
                    x -= 1;
                else{
                    for(int temp = 0; temp < 15; temp++){
                        if (y + temp >= this.height || temp == 14){
                            yf = y;
                            flag++;
                            temp = 15;
                        }
                        else if (getPixelValue(x, y + temp) > this.rgbMax - this.tolerance){
                            y += temp;
                            temp = 15;
                        }
                    }
                }
            }
        }
        
        y = yMid;
        x = xMid;
        while (flag == 2){
            if (y == 0){
                yi = 0;
                flag++;
            }
            else if (getPixelValue(x, y) > rgbMax - tolerance){
                y -= 1;
            }
            else{
                if (x + 1 >= width){
                    if (getPixelValue(x - 1, y) > rgbMax - tolerance)
                        x -= 1;
                    else {
                        yi = y;
                        flag++;
                    }
                }
                else if (x - 1 <= 0){
                    if (getPixelValue(x + 1, y) > rgbMax - tolerance)
                        x += 1;
                    else {
                        yi = y;
                        flag++;
                    }
                }
                else if (getPixelValue(x + 1, y) > rgbMax - tolerance)
                    x += 1;
                else if (getPixelValue(x - 1, y) > rgbMax - tolerance)
                    x -= 1;
                else{
                    for(int temp = 0; temp < 15; temp++){
                        if (y - temp <= 0 || temp == 14){
                            yi = y;
                            flag++;
                            temp = 15;
                        }
                        else if (getPixelValue(x, y - temp) > this.rgbMax - this.tolerance){
                            y -= temp;
                            temp = 15;
                        }
                    }
                }
            }
        }
        vertical = yf - yi;
        yMid = yi + vertical / 2;
        
        if (horizontal + vertical > this.maxCrossSection){
            xCenter = xMid; yCenter = yMid;
            xI = xi; xF = xf; yI = yi; yF = yf;
            this.maxCrossSection = horizontal + vertical;
        }
    }
    
    public void getImage(){
        try {
            this.img = this.eyes.look();
        }
        catch (IOException e){
            System.out.println(e);
        }
        this.resetValues();
    }
    
    public int getPixelValue(int x, int y){
        int r, g, b;
        //get pixel value
        int p = this.img.getRGB(x, y);

        //get red
        r = (p >> 16) & 0xff;

        //get green
        g = (p >> 8) & 0xff;

        //get blue
        b = p & 0xff;

        return r + g + b;
    }
    
    public void getRgbMax(){
        int temp;
        for (int x = 0; x < this.width; x += 1){
            for (int y = 0; y < this.height; y += 1){
                temp = this.getPixelValue(x, y);
                if (temp > this.rgbMax){
                    this.rgbMax = temp;
                }
            }
        }
    }
    
    public void findCenter(){
        for (int x = 0; x < this.width; x += 10){
            for (int y = 0; y < this.height; y += 10){
                if(this.getPixelValue(x, y) > this.rgbMax - this.tolerance)
                    this.getShape(x, y);
            }
        }

        for (int x = this.xI - 10; x < this.xI + 5; x += 1){
            for (int y = this.yI + 10; y < this.yF - 10; y += 1){
                try{
                    if(this.getPixelValue(x, y) > this.rgbMax - this.tolerance)
                    this.getShape(x, y);
                }
                catch (Exception e){}
            }
        }
    }
    
    public void resetValues(){
        this.width = this.img.getWidth();
        this.height = this.img.getHeight();
        this.getRgbMax();
        this.xCenter = 0;
        this.yCenter = 0;
        this.maxCrossSection = 0; 
        this.xI = 0;
        this.xF = 0;
        this.yI = 0;
        this.yF = 0;
    }
}

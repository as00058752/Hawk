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

import java.io.*;
import java.net.*;

/*
Author: Anthony Lopez
Contributors: Anh(Steven) Nguyen
Last update: 04/23/2020 by Anh(Steven) Nguyen
 */

class Messenger {
    int xPos, yPos;
    ServerSocket server;
    PrintWriter out;
    BufferedReader in;
    Socket client;
    
    ////Create socket
    public void configureRoute() throws IOException{
        this.server = new ServerSocket(8070);
        System.out.println("Standing by on port 8070");
    }
    
    ////Connect through port
    public void connect() throws IOException {
        try {
            this.client = server.accept();
            System.out.println("Connecting to Python program");
            this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.out = new PrintWriter(this.client.getOutputStream(), true);
            System.out.println("Connection sucessful!");
        }
        catch (Exception e){
            System.out.println("Connection failed");
        }
    }
    
    ////Send data through port
    public void send(int x, int y) throws IOException {
        this.out.println(x);
        this.out.println(y);
        System.out.println("(" + x + ", " + y + ")");
    }
}

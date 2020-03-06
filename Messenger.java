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

import java.io.*;
import java.net.*;

/*
Author: Anthony Lopez
Edited by Anh (Steven) Nguyen
 */

class Messenger {
    ServerSocket server;
    
    public void configureRoute() throws IOException{
        server = new ServerSocket(8080);
        System.out.println("Standing by on port 8080");
    }
    
    public void send() throws IOException {
        int x;
        int y;

        boolean run = true;
        while(run) {
            try (Socket client = server.accept()) {
                System.out.println("Connection started on port 8080");
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(),true);
                //String fromClient;
                for(y = 160; y <= 200; y+=2) {
                    for(x = 160; x <= 200; x+=2) {
                        out.println(x);
                        in.readLine();
                        
                        out.println(y);
                        in.readLine();
                        
                        //System.out.printf("X: %d Y: %d\n", x, y);
                    }
                }
            }
            run = false;
            System.out.println("Socket Closed");

        }
    }
}

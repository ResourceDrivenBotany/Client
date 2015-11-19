/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plantgameclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mishakanai
 */
public class PlantGameClient {
    static DataOutputStream toServer = null;
    static DataInputStream fromServer = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
          // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            //Socket socket = new Socket("192.168.0.9", 8000);
                      // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream()); // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream()); 
            
        } catch (IOException ex) {
        }
        
        if (toServer != null) {
            Scanner input = new Scanner(System.in);
            int i = 0;
            while(i < 2) {
                try{
                    toServer.writeUTF(input.nextLine());
                    toServer.flush();
                } catch (Exception e) {
                }
                i++;
            }
            try {
            toServer.writeInt(input.nextInt());
            toServer.flush();
            } catch (Exception e) {
                
            }
            boolean loopForInput = true;
            while (loopForInput)
            try {
                System.out.println(fromServer.readUTF());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PlantGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                loopForInput = false;
            } catch (IOException ex) {
                Logger.getLogger(PlantGameClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int j = 0; j < 4; j++) {
                try {
                    toServer.writeInt(input.nextInt());
                    toServer.flush();
                } catch (Exception e) {
                }
            }
        }
    }
    
}

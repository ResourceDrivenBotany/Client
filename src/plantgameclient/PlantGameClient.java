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
            Socket socketOut = new Socket("localhost", 8001);
            Socket socketIn = new Socket("localhost", 8000);
            //Socket socket = new Socket("192.168.0.9", 8000);
                      // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socketIn.getInputStream()); // Create an output stream to send data to the server
            toServer = new DataOutputStream(socketOut.getOutputStream()); 
            new Thread(() -> {
            while(true) {
                try {
                    System.out.println(fromServer.readUTF());
                } catch (IOException ex) {
                    Logger.getLogger(PlantGameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }).start();
            new Thread(() -> {
                if (toServer != null) {
                    Scanner input = new Scanner(System.in);
                    while(true) {
                        
                        String asString = input.nextLine();
                        try {
                            int asInt = Integer.parseInt(asString);
                            try {
                                toServer.writeInt(asInt);
                                toServer.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(PlantGameClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        catch(NumberFormatException nFE) {
                            try {
                                toServer.writeUTF(asString);
                                toServer.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(PlantGameClient.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }
            }).start();
            
        } catch (IOException ex) {
        }
        
        /*
        
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
            
            
            //^^instantiating game info
            for (int s = 0; s < 4; s++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 2; k++) {
                        try {
                            toServer.writeInt(input.nextInt());
                            toServer.flush();
                        } catch (Exception e) {
                        }
                    }
                    //NOW START ATTACK ROUND VVVVVV
                    int attackRoundMove = input.nextInt();
                    try {
                    toServer.writeInt(attackRoundMove);
                        if (attackRoundMove == 1) {
                            int playerToAttack = input.nextInt();
                            toServer.writeInt(playerToAttack);
                            int resourceToAttack = input.nextInt();
                            toServer.writeInt(resourceToAttack);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(PlantGameClient.class.getName()).log(Level.SEVERE, null, ex);
                    }

    //                try {
    //                    System.out.println(fromServer.readUTF());
    //                } catch (IOException ex) {
    //                    Logger.getLogger(PlantGameClient.class.getName()).log(Level.SEVERE, null, ex);
    //                }
                }
            }
        }*/
    }
    
}

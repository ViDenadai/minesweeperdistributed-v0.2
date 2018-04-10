/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mineSweeper.udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laser
 */
public class ClienteUdp implements Runnable {
    private static ClienteUdp clienteUdp;
    private DatagramSocket socketUdp;
    private DatagramPacket packetUdp;
    private int serverPort;
    private static final int BUFFERSIZE = 2048;
    private static final int MAXTRIES = 5;
    private byte[] buffer;
    
    
    public static void go(){
        Thread threadClienteUdp = new Thread(clienteUdp = new ClienteUdp());
        threadClienteUdp.start();
    }
    
    
    @Override
    public void run(){
        int tries = 0;       
        try {
            InetAddress broadCast = InetAddress.getByName("255.255.255.255");
            serverPort = 50000;
            socketUdp = new DatagramSocket();
            buffer = new byte[BUFFERSIZE];
            buffer = (new String("123")).getBytes();
            packetUdp = new DatagramPacket(buffer, buffer.length, broadCast, serverPort); 
            

            boolean receivedResponse = false;
            
            do{
                socketUdp.send(packetUdp);
                try{
                    socketUdp.receive(packetUdp);
                    receivedResponse = true;
                } catch (InterruptedIOException e){ 
                    tries += 1;
                    System.out.println("Timed out, " + (MAXTRIES - tries) + " more tries ...");
                }
            }while((!receivedResponse) && (tries < MAXTRIES));

            if (receivedResponse){
                System.out.println("Received: " + new String(packetUdp.getData()));
            }else{
                System.out.println("No response -- giving up.");
            }   
            socketUdp.close();
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(ClienteUdp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

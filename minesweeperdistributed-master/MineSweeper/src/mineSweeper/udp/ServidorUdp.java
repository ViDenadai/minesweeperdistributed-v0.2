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
public class ServidorUdp implements Runnable {
    private static ServidorUdp servidorUdp;
    private DatagramSocket socketUdp;
    private DatagramPacket packetUdp;
    private int serverPort;
    private static final int BUFFERSIZE = 2048;
    private byte[] buffer;
    
    
    public static void go(){
        Thread threadServidorUdp = new Thread(servidorUdp = new ServidorUdp());
        threadServidorUdp.start();
    }
    
    
    @Override
    public void run() {
        serverPort = 50000;
        try {
            buffer = new byte[BUFFERSIZE];
            socketUdp = new DatagramSocket(serverPort);
            packetUdp = new DatagramPacket(buffer, BUFFERSIZE); 
            for(;;){
                socketUdp.receive(packetUdp);
                System.out.println ("Handling client at " +
                    packetUdp.getAddress().getHostAddress() + " on port " +
                    packetUdp.getPort());
                
                buffer = (new String("abc")).getBytes();
    
                packetUdp.setData(buffer);
                socketUdp.send(packetUdp);

            }
        } catch (SocketException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServidorUdp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

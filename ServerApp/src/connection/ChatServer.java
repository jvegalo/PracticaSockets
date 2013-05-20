/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Connection
 * Create the server socket and allows it to
 * send and receive message.
 * Accepts connections of clients, create threads for each user and wait
 * for more connections
 * @author jose
 */
public class ChatServer {

    private static int PORT = 5000;
    
    public ChatServer() throws IOException{        
        ServerSocket socketServidor = new ServerSocket(PORT);
        while (true)
        {
            Socket client = socketServidor.accept();
            Runnable nuevoCliente = new ClientThread(client);
            Thread thread = new Thread(nuevoCliente);
            thread.start();
        }
    }
    
    
   

}

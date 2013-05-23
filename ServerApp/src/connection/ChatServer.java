/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import serverapp.ChatRoom;
import serverapp.User;

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
    public static ArrayList<User> userList = new ArrayList<User>();
    public static ArrayList<ChatRoom> chatRoomsList = new ArrayList<ChatRoom>();
    public static ArrayList<ClientThread> hilos = new ArrayList<ClientThread>();
    
    public ChatServer() throws IOException{        
        ServerSocket socketServidor = new ServerSocket(PORT);
        ChatRoom chatRoom = new ChatRoom("Sala 1");
        ChatRoom chatRoom2 = new ChatRoom("Sala 2");
        chatRoomsList.add(chatRoom);
        chatRoomsList.add(chatRoom2);
        while (true)
        {
            Socket client = socketServidor.accept();
            Runnable nuevoCliente = new ClientThread(client);
            Thread thread = new Thread(nuevoCliente);
            ClientThread nuevoClientesito = new ClientThread(client);
            hilos.add(nuevoClientesito);
            thread.start();
        }
    }
    
    
   

}

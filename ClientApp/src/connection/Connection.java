/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author jose
 */
public class Connection {
    private Socket serverSocket = null;
    private static Connection uniqueConnection = null;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    
    private Connection() throws UnknownHostException, IOException{
        serverSocket = new Socket("10.0.34.72",5000);
        dataInput = new DataInputStream(serverSocket.getInputStream());
        dataOutput = new DataOutputStream(serverSocket.getOutputStream());
        
    }
    //Singleton
    public static Connection getInstanceConnection() throws UnknownHostException, IOException{
        if(uniqueConnection==null){
            uniqueConnection = new Connection();
        }
        return uniqueConnection; 
    }
    
    public void sendMessage(String a) throws IOException{
        dataOutput.writeUTF(a);
        dataOutput.flush();
    }
    
    public String getMessage() throws IOException{
            String message = dataInput.readUTF();
            return message;
        
    }
    
    public String getChatroomList () throws IOException{
        this.sendMessage("getChatRoomsList&");
        String chatRooms = this.getMessage();
        System.out.println(chatRooms);
        return chatRooms;
        
    }
    
    public String requestChatRoom (String User_name, String ChatRoomDesired) throws IOException{
        this.sendMessage("addUserToChatRoom&"+User_name+"&"+ChatRoomDesired);
        String answer = this.getMessage();
        return answer;
    }
}

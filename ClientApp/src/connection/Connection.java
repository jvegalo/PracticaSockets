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
    
    private Connection() throws UnknownHostException, IOException{
        serverSocket = new Socket("localhost",5000);
        
    }
    //Singleton
    public static Connection getInstanceConnection() throws UnknownHostException, IOException{
        if(uniqueConnection==null){
            uniqueConnection = new Connection();
        }
        return uniqueConnection; 
    }
    
    public void sendMessage(String a) throws IOException{
        DataOutputStream os;
        os = new DataOutputStream( serverSocket.getOutputStream() );
        os.writeUTF(a);
    }
    
    public String getMessage() throws IOException{    
        DataInputStream dataInput;
        dataInput = new DataInputStream(serverSocket.getInputStream());
        String message = dataInput.readUTF();
        return message;
    }
}

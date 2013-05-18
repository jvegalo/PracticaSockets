/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author jose
 */
public class ClientThread implements Runnable{
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    
    
    //agregar la lista como parametro
    public ClientThread(Socket socket) {
        
        
        try {
            /* Set the channels of communication*/
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
            
            /* Add this chat to the default list model*/ 
            //chat.addListDataListener(this);
            
        } catch (Exception e) {
            e.getMessage();
        }
    }
     /**
     * To read a message from the client.
     * @return message
     * @throws IOException 
     */
    public String getMessage() throws IOException{        
        String message = dataInput.readUTF();
        return message;
    }
    
    /**
     * To send a message to the client.
     * @param message
     * @throws IOException 
     */
    public void sendMessage(String message) throws IOException{        
        dataOutput.writeUTF(message);
        dataOutput.flush();
    }
    @Override
    public void run() {
        //Hacer efectivo el protocolo.
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;
import connection.ChatServer;
import java.io.IOException;

/**
 *
 * @author jose
 */
public class ServerApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ChatServer con = new ChatServer();
        
        //con.sendMessage("Primer mensaje");
        //con.sendMessage("Segundo mensaje");
        //System.out.println(con.getMessage());
        
        
    }
}

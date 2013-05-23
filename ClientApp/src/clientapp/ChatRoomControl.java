/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import connection.Connection;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 *
 * @author jose
 */
public class ChatRoomControl {
    private ChatRoom ch;
    private Connection co;
    
    public ChatRoomControl(ChatRoom ch) throws UnknownHostException, IOException{
        this.ch = ch;
        co = Connection.getInstanceConnection();
    }
    
    public void listenServer(){
        try{
            while (true){
                String text = co.getMessage();
                String[] splitText = text.split("&");
                String command = splitText[0];
                if (command.equals("userAddedToChatRoom")){
                    Vector users = new Vector();
                    for (int i = 1;i<splitText.length;i++){
                        users.add(splitText[i]);
                        
                    }
                    ch.setUsers(users);
                    ch.notifyObservers();
                }
            }
        }catch (Exception e) {
            e.getMessage();
        }
    }
    
    public Vector getUsersFromMyChatroom(){
        Vector UsersFromMyChatRoom = new Vector();
        UsersFromMyChatRoom = ch.getUsers();
        return UsersFromMyChatRoom;
    }
}

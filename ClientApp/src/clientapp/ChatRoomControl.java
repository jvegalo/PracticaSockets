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
public class ChatRoomControl implements Runnable{
    private ChatRoom ch;
    private Connection co;
    
    public ChatRoomControl(ChatRoom ch) throws UnknownHostException, IOException{
        this.ch = ch;
        Thread hilo = new Thread(this);
        hilo.start();
        co = Connection.getInstanceConnection();
    }
    
    
    
    public Vector getUsersFromMyChatroom(){
        Vector UsersFromMyChatRoom = new Vector();
        UsersFromMyChatRoom = ch.getUsers();
        return UsersFromMyChatRoom;
    }
    
    public Vector getMessagesFromMyChatroom(){
        Vector messagesFromMyChatRoom = new Vector();
        messagesFromMyChatRoom = ch.getMessages();
        return messagesFromMyChatRoom;
    }
    
    public void getUsersFromServer() throws IOException{
        co.sendMessage("getUsersFromChatRoom&" + ch.getName());
        
    }
    
    public void sendUserMessageToServer(String msg, String userName) throws IOException{
        co.sendMessage("addUserMessageToChatRoom"+"&"+ch.getName()+"&"+userName+"&"+msg);
    }
    
    

    @Override
    public void run() {
        try{
            while (true){
                String text = co.getMessage();
                String[] splitText = text.split("&");
                String command = splitText[0];
                // if the users of the chat room changed
                if (command.equals("usersFromChatRoom")){
                    Vector users = new Vector();
                    for (int i = 1;i<splitText.length;i++){
                        users.add(splitText[i]);
                    }
                    ch.setUsers(users);
                }
                else if(command.equals("lastMessage")){
                    String userName = splitText[1];
                    String message = splitText[2];
                    String whoAndWhat = userName+" says: "+message;
                    ch.addLastMessage(whoAndWhat);
                    
                }
            }
        }catch (Exception e) {
            e.getMessage();
        }
    }
}

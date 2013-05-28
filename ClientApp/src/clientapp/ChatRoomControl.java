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
    boolean seguir = true;
    
    public ChatRoomControl(ChatRoom ch) throws UnknownHostException, IOException{
        this.ch = ch;
        Thread hilo = new Thread(this);
        hilo.start();
        co = Connection.getInstanceConnection();
    }
    
    public void stopCHC(){
        seguir = false;
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
    
    public void quitUserFromChatRoom(String userName) throws IOException{
        co.sendMessage("removeUserFromChatRoom"+"&"+userName+"&"+ch.getName());
    }
    
    public String getChatRoomName(){
        String name = ch.getName();
        return name;
    }

    @Override
    public void run() {
        try{
            while (seguir){
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
                else if(command.equals("userRemovedFromChatRoom")){
                    String chatRoom = splitText[1];
                    String userName = splitText[2];
                    if (chatRoom.equals(ch.getName())){
                        ch.removeUser(userName);
                    }
                }
            }
        }catch (Exception e) {
            e.getMessage();
        }
    }
}

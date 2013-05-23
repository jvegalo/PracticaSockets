/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import serverapp.ChatRoom;
import serverapp.User;

/**
 *
 * @author jose
 */
public class ClientThread implements Runnable{
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String response;
    
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
        System.out.println("*********Hilo nuevo*******");
        System.out.println("Soy tu nuevo cliente");
        boolean isNewUser = false;
        boolean isNewMessage = false;
        try{
            while(true){
                String text = dataInput.readUTF();
                String[] splitText = text.split("&");
                String command = splitText[0];
                if (command.equals("confirmUser")){
                    String username = splitText[1];
                    String password = splitText[2];
                    User user = new User(username, password);
                    ChatServer.userList.add(user);
                    response = "userAccepted";

                } else if (command.equals("getChatRoomsList")){
                    response = "chatRoomsList";
                    for (int i = 0; i < ChatServer.chatRoomsList.size(); i++){                        
                        response += "&"+ ChatServer.chatRoomsList.get(i).getName();
                    }
                } else if (command.equals("addChatRoom")){
                    String chatRoomName = splitText[1];
                    ChatRoom chatRoom = new ChatRoom(chatRoomName);
                    ChatServer.chatRoomsList.add(chatRoom);
                    response = "ChatRoom added&" + chatRoom.getName();
                } else if (command.equals("pushMessage")){

                } else if (command.equals("addUserToChatRoom")){
                    String userName = splitText[1];
                    String chatRoomName = splitText[2];
                    for (int i = 0; i < ChatServer.userList.size(); i++){
                        if (userName.equals(ChatServer.userList.get(i).getUsername())){
                            User newUser = ChatServer.userList.get(i);
                            for (int j = 0; j < ChatServer.chatRoomsList.size(); j++){
                                if (chatRoomName.equals(ChatServer.chatRoomsList.get(j).getName())){
                                    ChatServer.chatRoomsList.get(j).addUser(newUser);
                                    response = "youAreAccepted";
                                    
                                    isNewUser = true;
                                    
                                    break;
                                } 
                            }
                            break;
                        } 
                    }
                } else if (command.equals("removeUserFromChatRoom")){
                    String userName = splitText[1];
                    String chatRoomName = splitText[2];
                    for (int i = 0; i < ChatServer.userList.size(); i++){
                        if (userName.equals(ChatServer.userList.get(i).getUsername())){
                            User userToRemove = ChatServer.userList.get(i);
                            for (int j = 0; j < ChatServer.chatRoomsList.size(); j++){
                                if (chatRoomName.equals(ChatServer.chatRoomsList.get(j).getName())){
                                    ChatServer.chatRoomsList.remove(userToRemove);
                                    response = "userRemovedFromChatRoom&" + userToRemove.getUsername();
                                    break;
                                } 
                            }
                            break;
                        } 
                    }
                    
                } else if (command.equals("getUsersFromChatRoom")){
                    sendUsers(splitText);
                } else if(command.equals("addUserMessageToChatRoom")){
                    addMessageToChatRoom(splitText);
                }

                sendMessage(response);
                if (isNewUser){                    
                    sendUsers(splitText);
                }
            }
            
            
        } catch (Exception e) {
             e.getMessage();
        }
        
    }
    
    
    public void sendUsers(String[] splitText) throws IOException{
        String chatRoomName = splitText[1];
        String answer = "";
        ChatRoom chatRoom;
        for (int i = 0; i < ChatServer.chatRoomsList.size(); i++){
            if (chatRoomName.equals(ChatServer.chatRoomsList.get(i).getName())){
                chatRoom = ChatServer.chatRoomsList.get(i);
                answer = "usersFromChatRoom";
                for (int j = 0; j < chatRoom.getUsers().size(); j++){
                    answer += "&" + chatRoom.getUsers().get(j).getUsername();
                }
                break;
            }
        }
        for (int p = 0; p < ChatServer.hilos.size(); p++){
            ClientThread nuevoCliente=ChatServer.hilos.get(p);
            nuevoCliente.dataOutput.writeUTF(answer);
            
        }
        
    }

    private void addMessageToChatRoom(String[] splitText) throws IOException {
        String chatRoomName = splitText[1];
        String message = splitText[2];
        String lastMessage ="No hay mensaje";
        for (int i = 0; i < ChatServer.chatRoomsList.size(); i++){
            if (chatRoomName.equals(ChatServer.chatRoomsList.get(i).getName())){
                ChatServer.chatRoomsList.get(i).addMessage(message);
                lastMessage = message;
                break;
            }
        }
        for (int p = 0; p < ChatServer.hilos.size(); p++){
            ClientThread nuevoCliente=ChatServer.hilos.get(p);
            nuevoCliente.dataOutput.writeUTF(lastMessage);
            
        }
    }

}

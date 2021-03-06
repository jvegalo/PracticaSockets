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
    
    public void sendMessageToAll(String message) throws IOException{
        for (int p = 0; p < ChatServer.hilos.size(); p++){
            ClientThread nuevoCliente=ChatServer.hilos.get(p);
            nuevoCliente.dataOutput.writeUTF(message);
            
        }
    }
    
    @Override
    public void run() {
        //Hacer efectivo el protocolo.
        try{
            while(true){
                String text = dataInput.readUTF();
                String[] splitText = text.split("&");
                String command = splitText[0];
                System.out.println(text);
                if (command.equals("confirmUser")){
                    boolean userExist = false;
                    String username = splitText[1];
                    String password = splitText[2];
                    User user = new User(username, password);
                    for (int i = 0; i < ChatServer.userList.size(); i++){
                        if (username.equals(ChatServer.userList.get(i).getUsername())){
                            userExist = true;
                        }                        
                    }
                    if (userExist){
                        response = "userRefused";
                    } else {
                        ChatServer.userList.add(user);
                        response = "userAccepted";
                    }
                    
                    sendMessage(response);

                } else if (command.equals("getChatRoomsList")){
                    response = "chatRoomsList";
                    for (int i = 0; i < ChatServer.chatRoomsList.size(); i++){                        
                        response += "&"+ ChatServer.chatRoomsList.get(i).getName();
                    }
                    sendMessage(response);
                } else if (command.equals("addChatRoom")){
                    //piya aca es donde el server recibe ese mensaje a ver pillo y volveme a mostrar porfa el q le devuelve los chats al cliebte
                    String chatRoomName = splitText[1];
                    ChatRoom chatRoom = new ChatRoom(chatRoomName);
                    ChatServer.chatRoomsList.add(chatRoom);
                    response = "chatRoomAdded&" + chatRoom.getName();
                    //sendMessage(response);
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
                                    break;
                                } 
                            }
                            break;
                        } 
                    }
                    sendMessage(response);
                } else if (command.equals("removeUserFromChatRoom")){
                    String userName = splitText[1];
                    String chatRoomName = splitText[2];
                    for (int i = 0; i < ChatServer.userList.size(); i++){
                        if (userName.equals(ChatServer.userList.get(i).getUsername())){
                            User userToRemove = ChatServer.userList.get(i);
                            for (int j = 0; j < ChatServer.chatRoomsList.size(); j++){
                                if (chatRoomName.equals(ChatServer.chatRoomsList.get(j).getName())){
                                    ChatServer.chatRoomsList.get(j).removeUser(userToRemove);
                                    if (ChatServer.chatRoomsList.get(j).getUsers().isEmpty()){
                                        ChatServer.chatRoomsList.remove(j);
                                    }                                    
                                    response = "userRemovedFromChatRoom&" + chatRoomName + "&" + userToRemove.getUsername();
                                    break;
                                } 
                            }
                            break;
                        } 
                    }
                    sendMessageToAll(response);
                } else if (command.equals("getUsersFromChatRoom")){
                    sendUsers(splitText);
                    sendMessageToAll(response);
                } else if(command.equals("addUserMessageToChatRoom")){
                    
                    addMessageToChatRoom(splitText);
                    sendMessageToAll(response);
                } else if (command.equals("getAllUsers")){
                    response = "allUsers";
                    for (int i = 0; i < ChatServer.userList.size(); i++){
                        response += "&"+ ChatServer.userList.get(i).getUsername();
                    }
                } else if (command.equals("logoutUser")){
                    String username = splitText[1];
                    for (int i = 0; i < ChatServer.userList.size(); i++){
                        if (username.equals(ChatServer.userList.get(i).getUsername())){
                            ChatServer.userList.remove(i);
                            response = "userRevomed";
                            break;
                        }                        
                    }
                    //sendMessage(response);
                }
                 
                System.out.println(response);
                
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
        response = answer;
//        for (int p = 0; p < ChatServer.hilos.size(); p++){
//            ClientThread nuevoCliente=ChatServer.hilos.get(p);
//            nuevoCliente.dataOutput.writeUTF(answer);
//            
//        }
        
    }

    private void addMessageToChatRoom(String[] splitText) throws IOException {
        String chatRoomName = splitText[1];
        String userName = splitText[2];
        String message = splitText[3];
        String lastMessage ="No hay mensaje";
        for (int i = 0; i < ChatServer.chatRoomsList.size(); i++){
            if (chatRoomName.equals(ChatServer.chatRoomsList.get(i).getName())){
                ChatServer.chatRoomsList.get(i).addMessage(message);
                lastMessage = message;
                break;
            }
        }
        response = "lastMessage&"+userName+"&"+lastMessage;
//        for (int p = 0; p < ChatServer.hilos.size(); p++){
//            ClientThread nuevoCliente=ChatServer.hilos.get(p);
//            response = "lastMessage&"+userName+"&"+lastMessage;
//            nuevoCliente.dataOutput.writeUTF(response);
//            System.out.println(lastMessage);
//        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import java.util.Observable;
import java.util.Vector;

/**
 *
 * @author jose
 */
public class ChatRoom extends Observable {
    private String name;
    private Vector users;
    private Vector messages = new Vector();
    
    public ChatRoom(String name){
        this.name = name;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector getUsers() {
        return users;
    }

    public void setUsers(Vector users) {
        this.users = users;
        setChanged();
        notifyObservers();
    }

    public Vector getMessages() {
        return messages;
    }

    public void setMessages(Vector messages) {
        this.messages = messages;
    }
    
    public void addLastMessage(String message){
        this.messages.add(message);
        setChanged();
        notifyObservers();
    }
    
    public void removeUser(String user){
        for (int i =0;i<users.size();i++){
            User u = (User) users.elementAt(i);
            if (u.equals(user)){
                users.removeElementAt(i);
            }
        }
        setChanged();
        notifyObservers();
    }
}

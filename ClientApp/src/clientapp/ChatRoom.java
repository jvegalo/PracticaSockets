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
    private Vector messages;
    
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
    }

    public Vector getMessages() {
        return messages;
    }

    public void setMessages(Vector messages) {
        this.messages = messages;
    }
    
    
    
    
}

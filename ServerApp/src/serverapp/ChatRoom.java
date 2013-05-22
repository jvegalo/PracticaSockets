/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose
 */
public class ChatRoom {
    private String name;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<User> messages = new ArrayList<User>();

    public ChatRoom(String name) {
        this.name = name;
        messages = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
    public void addUser(User u){
        this.users.add(u);
    }
    
}

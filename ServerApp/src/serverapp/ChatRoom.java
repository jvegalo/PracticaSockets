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
    private User [] users;
    private List messages = new ArrayList();

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

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
    
    
}

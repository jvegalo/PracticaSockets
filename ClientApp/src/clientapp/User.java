/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;


/**
 *
 * @author jose
 */


public class User {
    private String user_name;
    private String password;
    private ChatRoom chat_room;
    
    
    public User(String username, String password) {
        this.user_name = username;
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}

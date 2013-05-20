/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

/**
 *
 * @author jose
 */
public class User {
    private String username;
    private String password;
    private ChatRoom chatRoom;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
        
}

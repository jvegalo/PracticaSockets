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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
        
}

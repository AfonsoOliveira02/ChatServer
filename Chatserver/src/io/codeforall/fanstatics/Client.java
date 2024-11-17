package io.codeforall.fanstatics;

import java.net.Socket;

public class Client {
    private Socket socket;
    private String nick;
    public Client(Socket socket,String nick){
        this.socket = socket;
        this.nick = nick;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

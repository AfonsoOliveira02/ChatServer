package io.codeforall.fanstatics;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ServerWorker implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private OutputStream bufferedWriter;
    private Server server;
    private Client client;

    public ServerWorker(Socket clientSocket, Server server,Client client) throws IOException {
        this.socket = clientSocket;
        this.server = server;
        this.client = client;

    }

    @Override
    public void run() {
        try {
            handler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void handler() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = socket.getOutputStream();
        bufferedWriter.write("Your name?".getBytes());
        client.setNick(bufferedReader.readLine().trim());
        while(true){
        bufferedWriter.write("What do u want to do ".getBytes());
        String line = bufferedReader.readLine();
        switch (line){
            case "/help":
                bufferedWriter.write("/whisper\n /quit\n /changename\n /changecolor".getBytes());
            break;
            case "/quit":
               server.removeClient(client);
               socket.close();
            case "/list":


            default:
                if(line.startsWith("/whisper")){
                    whisper(line.substring(9));}
                    else if(line.startsWith("/changecolor ")){
            }
                    else {Broadcast(line);
                }

}}}

    public void Broadcast(String line) throws IOException {
        for (Client client : server.getClients()) {
            if (client == this.client) {
                OutputStream outputStream = client.getSocket().getOutputStream();
                outputStream.write(line.getBytes());
            }

        }
    }
    private void whisper(String message) throws IOException {
        String[] parts = message.split(" ", 2);
        if (parts.length < 2) {
            bufferedWriter.write("Usage: /whisper <name> <message>".getBytes());
            return;
        }
        String targetNick = parts[0];
        String msg = parts[1];

        for (Client client : server.getClients()) {
            if (client.getNick().equalsIgnoreCase(targetNick)) {
                OutputStream out = client.getSocket().getOutputStream();
                out.write((this.client.getNick() + " whispers: " + msg + "\n").getBytes());
                out.flush();
                return;
            }
        }
        bufferedWriter.write(("User " + targetNick + " not found.".getBytes()).getBytes());


}
private void listClient() throws IOException { StringBuilder list = new StringBuilder("Connected users:\n");
    for (Client client : server.getClients()) {
        list.append(client.getNick()).append("\n");
    }
    bufferedWriter.write((list.toString()).getBytes());
}

}

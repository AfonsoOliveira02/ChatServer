package io.codeforall.fanstatics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    int port = 8080;
        ServerSocket serverSocket;
        private Socket clientSocket;
        private BufferedReader bufferedReader;
        private LinkedList<Client> clients;
        ExecutorService executorService = Executors.newCachedThreadPool();



    public Server() throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new LinkedList<>();
        System.out.println("Server started : " + serverSocket);
        while(true){
            clientSocket= serverSocket.accept();
            Client client = new Client(clientSocket,"gajo");
                    clients.add(client);
            System.out.println();
          executorService.submit(new ServerWorker(clientSocket,this,client));



    }}

    public LinkedList<Client> getClients() {
        return clients;
    }
    public void removeClient(Client client){
        clients.remove(client);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();

    }
}


package com.gmail.at.kotamadeo.app.server;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Data
public class Server implements Runnable {
    private int port;
    private String name;
    private int age;
    private String welcomeMessage;
    private String clientMessage;

    private void setConnection(int port) {
        try (ServerSocket socket = new ServerSocket(port);
             Socket client = socket.accept();
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            System.out.printf("Новое соединение подтверждено! Хост: %s, порт: %d%n",
                    client.getInetAddress(), client.getPort());

            out.println("Привет! Введи, пожалуйста, свое имя!");
            name = bufferedReader.readLine();

            out.println(String.format("%s, сколько тебе лет?", name));
            age = Integer.parseInt(bufferedReader.readLine());
            welcomeMessage = name + ", тебе открыт доступ к " + ((age < 18) ?
                    "детскому контенту" : "взрослому контенту") + "!";
            out.println(welcomeMessage);

            out.println(String.format("%s, введи какое-нибудь сообщение", name));
            clientMessage = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        setConnection(port);
    }
}

package com.gmail.at.kotamadeo.app.client;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

@Data
public class Client implements Runnable {
    private int port;
    private String name;
    private String age;
    private String welcomeMessage;
    private String clientMessage;

    private void getResponse(int port) {
        try (Socket client = new Socket(InetAddress.getLocalHost(), port);
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            System.out.println("Подключились к хосту: " + client.getRemoteSocketAddress());
            name = bufferedReader.readLine();
            System.out.println(name);
            out.println(new Scanner(System.in).next());

            age = bufferedReader.readLine();
            System.out.println(age);
            out.println(new Scanner(System.in).nextInt());

            welcomeMessage = bufferedReader.readLine();
            System.out.println(welcomeMessage);

            clientMessage = bufferedReader.readLine();
            System.out.println(clientMessage);
            out.println(new Scanner(System.in).nextLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        getResponse(port);
    }
}

package com.devlucasnascimento.ratelimiter.server;

import com.devlucasnascimento.ratelimiter.service.RateLimiter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RateLimiterServer {
    private final RateLimiter rateLimiter;

    public RateLimiterServer(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public void start() throws IOException {
        int porta = 8080;
        ServerSocket serverSocket = new ServerSocket(porta);
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String key;
            while ((key = reader.readLine()) != null) {
                boolean allow = rateLimiter.allowRequest(key);

                if (allow) {
                    writer.println("ALLOWED");
                } else {
                    writer.println("BLOCKED");
                }
            }
        }
    }
}

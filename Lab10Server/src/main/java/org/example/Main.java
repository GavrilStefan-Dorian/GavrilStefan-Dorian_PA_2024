package org.example;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main ( String [] args ) throws IOException {
        GameServer server = new GameServer(8000);
        server.start();
    }
}

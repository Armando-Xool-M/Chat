/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author CHARL
 */
public class ChatClientHandler implements Runnable {

    private Socket socket;
    private Servidor server;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean running = true;

    public ChatClientHandler(Socket socket, Servidor server) throws IOException {
        this.socket = socket;
        this.server = server;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public void send(byte[] audioData) throws IOException {
        dos.write(audioData);
        dos.flush();
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] audioData = new byte[1024];
                int count = dis.read(audioData, 0, audioData.length);
                if (count > 0) {
                    server.broadcast(audioData, this);
                }
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
                server.removeClient(this);
                try {
                    dis.close();
                    dos.close();
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

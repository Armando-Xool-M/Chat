/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author CHARL
 */
public class ChatClient {

    private AudioFormat audioFormat = new AudioFormat(8000, 8, 1, true, true);
    private TargetDataLine targetDataLine;
    private SourceDataLine sourceDataLine;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private boolean running = true;
    boolean Microo = true;
    AudioSender micro;
    AudioReceiver bocina;

    public ChatClient(Socket st) throws IOException, LineUnavailableException {
        socket = st;
        System.out.println("Connected to server: " + socket.getInetAddress());

        targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());

        micro = new AudioSender();
        micro.start();

        bocina = new AudioReceiver();
        bocina.start();
    }

    public void stop() {
        running = false;
        targetDataLine.stop();
        targetDataLine.close();
        sourceDataLine.stop();
        sourceDataLine.close();

        try {
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AudioSender extends Thread {

        byte[] buffer = new byte[1024];

        @Override
        public void run() {
            while (Microo) {
                
                int count = targetDataLine.read(buffer, 0, buffer.length);
                if (count > 0) {
                    try {
                        dos.write(buffer, 0, count);
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class AudioReceiver extends Thread {

        byte[] buffer = new byte[1024];

        @Override
        public void run() {
            while (running) {
                
                try {
                    int count = dis.read(buffer, 0, buffer.length);
                    if (count > 0) {
                        sourceDataLine.write(buffer, 0, count);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    void pausaMicro(){
        micro.suspend();
    }
    void reanudarMicro(){
        micro.resume();
    }
}

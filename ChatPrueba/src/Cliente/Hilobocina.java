/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author CHARL
 */
public class Hilobocina extends Thread{

    boolean playing = true;
    byte[] buffer = new byte[1024];
    InputStream entrada;
    SourceDataLine speaker;
    int count;
    Socket socket;

    public Hilobocina(Socket socket, SourceDataLine speaker, AudioFormat format) {
        this.speaker = speaker;
        this.socket = socket;
        try {
            speaker.open(format);
            speaker.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Hilobocina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (playing) {
            try {
                entrada = socket.getInputStream();
                count = entrada.read(buffer, 0, buffer.length);
                if (count > 0) {
                    //Aqui
                    speaker.write(buffer, 0, count);
                }
            } catch (IOException ex) {
                Logger.getLogger(Hilobocina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

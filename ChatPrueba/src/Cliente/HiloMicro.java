/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author CHARL
 */
public class HiloMicro extends Thread {

    boolean executing = false;
    TargetDataLine microphone;
    OutputStream outputStream;
    byte[] buffer = new byte[1024];

    public HiloMicro(TargetDataLine microphone, AudioFormat format, Socket socket) {
        this.microphone = microphone;
        System.out.println(microphone.toString());
        try {
            microphone.open(format);
            microphone.start();
            outputStream = socket.getOutputStream();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(HiloMicro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloMicro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void run() {
        while (executing) {
            int count = microphone.read(buffer, 0, buffer.length);
            if (count > 0) {
                try {
                    outputStream.write(buffer, 0, count);
                } catch (IOException ex) {
                    Logger.getLogger(HiloMicro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println(executing);
    }
    
    public void parar (){
        executing = false;
    }
    
    public void continuar (){
        executing = true;
    }
}


package Servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHARL
 */
public class Servidor {

    private ServerSocket servidor;
     private ServerSocket audio;
    private final int Puerto = 3000;
    private List<ChatClientHandler> clients = new ArrayList<>();

    public Servidor() throws IOException {
        servidor = new ServerSocket(Puerto);
        audio = new ServerSocket(8000);
        System.out.println("Servidor con conexion");

        while (true) {

            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado desde la direccion: " + cliente.getInetAddress().getHostAddress());
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            HiloServidor hilo = new HiloServidor(cliente, entrada.readUTF());
            hilo.start();
            Socket audioo = audio.accept();
            ChatClientHandler clientHandler = new ChatClientHandler(audioo, this);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }

    }
    
        public void broadcast(byte[] audioData, ChatClientHandler sender) throws IOException {
        for (ChatClientHandler client : clients) {
            if (client != sender) {
                client.send(audioData);
            }
        }
    }

    public void removeClient(ChatClientHandler client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        try {
            Servidor s = new Servidor();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

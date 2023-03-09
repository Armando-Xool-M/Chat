/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHARL
 */
public class HiloCliente extends Thread {

    private DataInputStream entrada;
    private Socket Socketcliente;
    private ChatGUI cliente;

    public HiloCliente(Socket Scliente, ChatGUI cliente) {
        this.Socketcliente = Scliente;
        this.cliente = cliente;
     
    }

    @Override
    public void run() {
        while (true) {
            try {
                entrada = new DataInputStream(Socketcliente.getInputStream());
                String ms = entrada.readUTF();
                System.out.println("Hilo:"  +ms);
                cliente.mensaje(ms);
                //cliente.conectados(entrada.readInt());
            } catch (IOException ex) {
                Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

   
    
}

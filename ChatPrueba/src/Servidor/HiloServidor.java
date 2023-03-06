/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHARL
 */
public class HiloServidor extends Thread {

    private DataInputStream entrada;
    private DataOutputStream salida;
    boolean salio = false;
    private Socket cliente;
    public static Vector<HiloServidor> usuarioActivo = new Vector();
    public String nombre;
    InputStream entradaAudio;
    OutputStream salidaAudio;
    private List<ChatClientHandler> clients = new ArrayList<>();

    HiloServidor(Socket cliente, String nombre) {
        this.cliente = cliente;
        this.nombre = nombre;
        usuarioActivo.add(this);
    }

    @Override
    public void run() {
        String mensaje = "";
        String[] result;

        while (true) {
            try {
                entrada = new DataInputStream(cliente.getInputStream());
                entradaAudio = cliente.getInputStream();
                mensaje = entrada.readUTF();
                result = mensaje.split(":");

                switch (result[0]) {
                    case "join":
                        System.out.println("El cliente " + result[1] + " " + result[2]);
                        System.out.println(usuarioActivo.size());
                        for (int i = 0; i < usuarioActivo.size(); i++) {
                            usuarioActivo.get(i).mensaje("El usuario " + result[1] + " " + result[2], usuarioActivo.size());
                        }
                        break;
                    case "exti":
                        usuarioActivo.removeElement(this);
                        break;
                    case "MSJ":
                        System.out.println("El cliente " + result[1] + " mando el mensaje: " + result[2]);
                        for (int i = 0; i < usuarioActivo.size(); i++) {
                            usuarioActivo.get(i).mensaje(result[1] + ": " + result[2], usuarioActivo.size());
                        }
                        break;
                    case "aud":
                         
                        break;

                }
            } catch (IOException ex) {
                System.out.println("El cliente: " + nombre + " se fue");
                usuarioActivo.removeElement(this);
                for (int i = 0; i < usuarioActivo.size(); i++) {
                    usuarioActivo.get(i).mensaje("El usuario: " + nombre + " se fue", usuarioActivo.size());
                }
                break;
            }
        }
    }

    public void mensaje(String mensaje, int i) {
        try {
            salida = new DataOutputStream(cliente.getOutputStream());
            salida.writeUTF(mensaje);
            salida.writeInt(i);
        } catch (IOException ex) {
            System.out.println("Error al mandar el mensaje");
        }
    }


    public void removeClient(ChatClientHandler client) {
        clients.remove(client);
    }

}

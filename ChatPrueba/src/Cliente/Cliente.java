/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Cliente;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

/**
 *
 * @author CHARL
 */
public class Cliente extends javax.swing.JFrame {

    private Socket cliente;
    public String nombre;
    private final int Puerto = 3000;
    public String host = "localhost";//10.182.2.206
    public DataOutputStream salida;
    private String texto;
    HiloCliente hilocliente;
    private String mensaje;
    private Socket audio;
    boolean micro = false;
    ChatClient client1;

    public Cliente() {
        initComponents();
        nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre");
        try {
            cliente = new Socket(host, Puerto);
            hilocliente = new HiloCliente(cliente, this);
            hilocliente.start();
            audio = new Socket(host, 8000);
            salida = new DataOutputStream(cliente.getOutputStream());
            salida.writeUTF(nombre);
            salida.writeUTF("join:" + nombre + ":se ha conectado");
            client1 = new ChatClient(audio);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);

        } catch (LineUnavailableException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtChat = new javax.swing.JTextArea();
        txtConectados = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        TxtMandarMsj = new javax.swing.JTextField();
        Microo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        TxtChat.setEditable(false);
        TxtChat.setColumns(20);
        TxtChat.setRows(5);
        jScrollPane1.setViewportView(TxtChat);

        txtConectados.setText("Conectados : 0");

        jButton2.setText("Enviar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        TxtMandarMsj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtMandarMsjActionPerformed(evt);
            }
        });
        TxtMandarMsj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtMandarMsjKeyPressed(evt);
            }
        });

        Microo.setText("Audio");
        Microo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MicrooActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(TxtMandarMsj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(3, 3, 3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtConectados)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton1))
                    .addComponent(Microo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(txtConectados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(TxtMandarMsj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Microo))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            salida.writeUTF("exti");
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TxtMandarMsjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtMandarMsjActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtMandarMsjActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        mensaje = TxtMandarMsj.getText();
        try {
            salida.writeUTF("MSJ:" + nombre + ":" + mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        TxtMandarMsj.setText(null);
        TxtMandarMsj.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void MicrooActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MicrooActionPerformed
        try {
            salida.writeUTF("aud:" + nombre);
            if (micro) {
                client1.pausaMicro();
                Microo.setBackground(Color.red); 
                micro = false;
            } else {
                client1.reanudarMicro();
                Microo.setBackground(Color.GREEN);
                micro = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MicrooActionPerformed

    private void TxtMandarMsjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtMandarMsjKeyPressed
        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            mensaje = TxtMandarMsj.getText();
            try {
                salida.writeUTF("MSJ:" + nombre + ":" + mensaje);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            TxtMandarMsj.setText(null);
            TxtMandarMsj.requestFocus();
        }
    }//GEN-LAST:event_TxtMandarMsjKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }

    void mensaje(String mensaje) {
        TxtChat.append(mensaje + "\n");
        System.out.println(mensaje);
    }

    void conectados(int i) {
        txtConectados.setText("Conectados: " + i);
        System.out.println("Conectados: " + i);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Microo;
    private javax.swing.JTextArea TxtChat;
    private javax.swing.JTextField TxtMandarMsj;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txtConectados;
    // End of variables declaration//GEN-END:variables
}

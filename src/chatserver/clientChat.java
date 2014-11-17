/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Riby
 */
public class clientChat implements Runnable {

    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;
    private volatile boolean isRunning = true;

    public clientChat(Socket X) {

        this.SOCK = X;
    }

    public void run() {

        try {
            try {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStream();

            } finally {
                SOCK.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(clientChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void DISCONNECT() throws IOError, IOException {
        System.out.println("coekt:  " + SOCK);
        System.out.println("list of users before  " + chatServerA.CurrentUsers);
        System.out.println("list of users  " + chatServerA.CurrentUsers);
        OUT.println(clientGUI.UserName + " has Disconnected");
        OUT.flush();
        JOptionPane.showMessageDialog(null, "You disconnected");
        System.exit(0);
    }

    public void CheckStream() {

        while (isRunning) {
            RECEIVE();
        }
    }

    public void kill() {

        isRunning = false;
    }

    public void RECEIVE() {
        if (INPUT.hasNext()) {
            String MESSAGE = INPUT.nextLine();

            if (MESSAGE.contains("#?!")) {
                System.out.println(MESSAGE);
                String tmp = MESSAGE.substring(3);
                tmp = tmp.replace("[", "");
                tmp = tmp.replace("]", "");

                String[] CurrentUsers = tmp.split(", ");
                System.out.println(CurrentUsers);

                clientGUI.JL_Online.setListData(CurrentUsers);

            } else {
                clientGUI.TA_Converation.append(MESSAGE + "\n");
            }

        }
    }

    public void SEND(String X) {

        OUT.println(clientGUI.UserName + " : " + X);
        OUT.flush();
        clientGUI.TF_Message.setText("");

    }

}

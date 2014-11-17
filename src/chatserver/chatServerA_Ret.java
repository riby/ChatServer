/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import static chatserver.chatServerA.CurrentUsers;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riby
 */
public class chatServerA_Ret implements Runnable {

    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";

    public chatServerA_Ret(Socket X) {
        this.SOCK = X;
    }

    public void CheckConnection() throws IOException {

        System.out.println("Checking " + chatServerA.CurrentUsers);

        Iterator itr = chatServerA.ConnectionArray.iterator();

        if (!SOCK.isConnected()) {

            while (itr.hasNext()) //for (int i = 1; i <= chatserver.chatServerA.ConnectionArray.size(); i++) {
            {
                if (itr == SOCK) {
                    chatServerA.ConnectionArray.remove(itr);
                } else {
                    itr.next();
                }
            }

            itr = chatServerA.ConnectionArray.iterator();
            while (itr.hasNext()) {
                System.out.println(itr);

                Socket TEMP_SOCK = (Socket) itr.next();
                PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + "disconnected");
                TEMP_OUT.flush();

                System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + "disconnected");

            }
        }
    }

    public void Disconnect(Socket S, String Cname) throws IOException {
        System.out.println("Before" + chatServerA.CurrentUsers);
        chatServerA.CurrentUsers.remove(Cname);

        System.out.println("After" + chatServerA.CurrentUsers);
        System.out.println("Before" + chatServerA.ConnectionArray.size());
        chatServerA.ConnectionArray.remove(S);

        System.out.println("After" + chatServerA.ConnectionArray.size());

        Iterator itr = chatServerA.ConnectionArray.iterator();
        while (itr.hasNext()) {
            Socket TEMP_SOCK = (Socket) itr.next();
            PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
            OUT.println("#?!" + CurrentUsers);
            OUT.flush();
        }
    }

    public void run() {

        try {
            INPUT = new Scanner(SOCK.getInputStream());
            OUT = new PrintWriter(SOCK.getOutputStream());
            while (true) {
                //       CheckConnection();
                if (!INPUT.hasNext()) {
                    return;
                }

                MESSAGE = INPUT.nextLine();

                if (MESSAGE.contains("Disconnected")) {
                    String[] tmp = MESSAGE.split(" ");

                    Disconnect(SOCK, tmp[0]);
                }
                System.out.println("Client Said:" + MESSAGE);

                System.out.println(chatServerA.ConnectionArray.size());
                System.out.println(chatServerA.ConnectionArray);
                CheckConnection();
                Iterator itr = chatServerA.ConnectionArray.iterator();
                while (itr.hasNext()) {
                    Socket TEMP_SOCK = (Socket) itr.next();
                    PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(MESSAGE);
                    TEMP_OUT.flush();

                    System.out.println("Sent to:`" + TEMP_SOCK.getLocalAddress().getHostName());

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(chatServerA_Ret.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                SOCK.close();
            } catch (IOException ex) {
                Logger.getLogger(chatServerA_Ret.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Riby
 */
public class chatServerA implements Runnable {

    public static HashSet<Socket> ConnectionArray = new HashSet();//can resize them array
    public static HashSet<String> CurrentUsers=new HashSet();
    public static int PORT;
    
   public chatServerA(int PORT)
    {
            this.PORT=PORT;
    }
    public void run(){
       
{    
try {

            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Waiting for clients..");
            while (true) {
                Socket SOCK = SERVER.accept();
                ConnectionArray.add(SOCK);
                System.out.println("Client Connected from " + SOCK.getLocalAddress().getHostName());
                AddUserName(SOCK);
                chatServerA_Ret chat = new chatServerA_Ret(SOCK);
                chat.CheckConnection();
                Thread x = new Thread(chat);
                x.start();
            }

        } catch (Exception x) {
            System.out.print(x);
        }
    }
    }
    public static void AddUserName(Socket X) throws IOException {
        Scanner INPUT = new Scanner(X.getInputStream());
        String UserName = INPUT.nextLine();
        CurrentUsers.add(UserName);

        
         Iterator itr = chatServerA.ConnectionArray.iterator();
        while(itr.hasNext())
        {
            System.out.println(itr);
            
             Socket TEMP_SOCK = (Socket) itr.next();
            PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
            System.out.println("add user"+CurrentUsers);
            OUT.println("#?!"+CurrentUsers);
            OUT.flush();
        }
        

    }

    public static void RemoveUserName(String UserName,Socket SOCK) throws IOException {

        System.out.println(chatServerA.ConnectionArray+"before");
         System.out.println(chatServerA.CurrentUsers+"Before");
        chatServerA.ConnectionArray.remove(SOCK);
        chatServerA.CurrentUsers.remove(UserName);
        
        System.out.println("Afer"+chatServerA.CurrentUsers);
        System.out.println("asdasd"+chatServerA.ConnectionArray);

          Iterator itr = chatServerA.ConnectionArray.iterator();
        while(itr.hasNext())
        {
            System.out.println(itr);
            Socket TEMP_SOCK = (Socket) itr.next();
            PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
            OUT.println(CurrentUsers);
            OUT.flush();
        }
       
       
    }

}

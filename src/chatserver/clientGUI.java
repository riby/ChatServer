
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.io.PrintWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Riby
 */
public class clientGUI implements Runnable {

    private static int PORT;

    public static String UserName = "Anonymous";
    private static clientChat ChatClient;
    private static JFrame MainWindow = new JFrame();
    private static JButton B_About = new JButton();
    private static JButton B_Connect = new JButton();
    private static JButton B_Disconnect = new JButton();
    private static JButton B_Help = new JButton();
    private static JButton B_Send = new JButton();
    private static JLabel L_Message = new JLabel();
    public static JTextField TF_Message = new JTextField(20);

    private static JLabel L_Conversation = new JLabel();
    public static JTextArea TA_Converation = new JTextArea();
    //  public static JTextArea TA_Converation = new JTextArea();
    private static JScrollPane SP_Conversation = new JScrollPane();
    private static JLabel L_Online = new JLabel();
    public static JList JL_Online = new JList();
    private static JScrollPane SP_Online = new JScrollPane();
    private static JLabel L_LoggedInAs = new JLabel();
    private static JLabel L_LoggedInAsBox = new JLabel();

    public static JFrame LogInInWindow = new JFrame();
    public static JTextField TF_UserNameBox = new JTextField(10);

    private static JButton B_Enter = new JButton("Enter");
    private static JLabel L_EnterUserName = new JLabel("Enter Name");
    private static JPanel P_LogIn = new JPanel();

    public clientGUI(int PORT, String UserName) {

        this.PORT = PORT;

        this.UserName = UserName;

    }

    public void run() {
        BuildMainWindow();
        Initialize();
    }

    public static void Connect() {
        try {

            final String HOST = "localhost";
            Socket SOCK = new Socket(HOST, PORT);
            System.out.println("You Connected to " + HOST);
            ChatClient = new clientChat(SOCK);
            PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(UserName);
            OUT.flush();
            Thread X = new Thread(ChatClient);
            X.start();
        } catch (Exception X) {
            System.out.println(X);
            JOptionPane.showMessageDialog(null, "Server not responing");
            System.exit(0);
        }
    }

    public static void Initialize() {
        B_Send.setEnabled(false);
        B_Disconnect.setEnabled(false);
        B_Connect.setEnabled(true);
    }

    public static void BuildLogInWindow() {
        LogInInWindow.setTitle("What's your name");
        LogInInWindow.setSize(400, 100);
        LogInInWindow.setLocation(250, 200);
        LogInInWindow.setResizable(true);
        P_LogIn = new JPanel();
        TF_UserNameBox.setBounds(70, 4, 260, 30);
        P_LogIn.add(L_EnterUserName);
        P_LogIn.add(TF_UserNameBox);
        P_LogIn.add(B_Enter);
        LogInInWindow.add(P_LogIn);
        Login_Action();
        LogInInWindow.setVisible(true);
    }

    public static void BuildMainWindow() {
        MainWindow.setTitle(UserName + "'s Chat Box");
        MainWindow.setSize(450, 500);
        MainWindow.setLocation(220, 180);
        MainWindow.setResizable(false);
        ConfigureMainWindow();
        MainWindow_Action();
        MainWindow.setVisible(true);
    }

    public static void ConfigureMainWindow() {
        MainWindow.setBackground(new java.awt.Color(255, 255, 255));
        MainWindow.setSize(500, 320);
        MainWindow.getContentPane().setLayout(null);
        B_Send.setBackground(Color.GRAY);
        B_Send.setForeground(Color.WHITE);
        B_Send.setText("Send");
        MainWindow.getContentPane().add(B_Send);
        B_Send.setBounds(250, 40, 81, 25);
        B_Disconnect.setBackground(Color.GRAY);
        B_Disconnect.setForeground(Color.WHITE);
        B_Disconnect.setText("Disconnect");
        MainWindow.getContentPane().add(B_Disconnect);
        B_Disconnect.setBounds(10, 40, 110, 25);
        B_Connect.setBackground(Color.GRAY);
        B_Connect.setForeground(Color.WHITE);
        B_Connect.setText("Connect");
        MainWindow.getContentPane().add(B_Connect);
        B_Connect.setBounds(130, 40, 110, 25);

        MainWindow.getContentPane().add(B_Help);

        B_About.setBackground(Color.GRAY);
        B_About.setForeground(Color.WHITE);
        B_About.setText("Copy Chat to File");
        MainWindow.getContentPane().add(B_About);
        B_About.setBounds(340, 40, 140, 25);
        L_Message.setText("Message");
        MainWindow.getContentPane().add(L_Message);
        L_Message.setBounds(10, 10, 60, 20);

        TF_Message.setForeground(Color.black);
        TF_Message.requestFocus();
        MainWindow.getContentPane().add(TF_Message);
        TF_Message.setBounds(70, 4, 260, 30);

        L_Conversation.setHorizontalAlignment(SwingConstants.CENTER);
        L_Conversation.setText("Conversation");
        MainWindow.getContentPane().add(L_Conversation);
        L_Conversation.setBounds(100, 70, 140, 16);

        TA_Converation.setColumns(20);
        TA_Converation.setFont(new java.awt.Font("Tahoma", 0, 12));
        TA_Converation.setForeground(Color.black);
        TA_Converation.setLineWrap(true);
        TA_Converation.setRows(5);
        TA_Converation.setEditable(false);

        SP_Conversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_Conversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_Conversation.setViewportView(TA_Converation);
        MainWindow.getContentPane().add(SP_Conversation);
        SP_Conversation.setBounds(10, 90, 330, 180);

        L_Online.setHorizontalAlignment(SwingConstants.CENTER);
        L_Online.setText("Currently Online");
        L_Online.setToolTipText("");
        MainWindow.getContentPane().add(L_Online);
        L_Online.setBounds(350, 70, 130, 16);
        JL_Online.setForeground(Color.black);
        SP_Online.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_Online.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_Online.setViewportView(JL_Online);
        MainWindow.getContentPane().add(SP_Online);
        SP_Online.setBounds(350, 90, 130, 180);

        L_LoggedInAs.setFont(new java.awt.Font("Tahoma", 0, 12));
        L_LoggedInAs.setText("Currently logged in as");
        MainWindow.getContentPane().add(L_LoggedInAs);
        L_LoggedInAs.setBounds(348, 0, 140, 15);

        L_LoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        L_LoggedInAsBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        L_LoggedInAsBox.setForeground(Color.black);
        L_LoggedInAsBox.setBorder(BorderFactory.createLineBorder(Color.black));
        L_LoggedInAsBox.setText("Currently logged in as");
        MainWindow.getContentPane().add(L_LoggedInAsBox);
        L_LoggedInAsBox.setBounds(340, 17, 150, 15);

    }

    public static void Login_Action() {
        B_Enter.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {

                ACTION_B_ENTER();
            }
        });
    }

    public static void addUserToServer() {
        L_LoggedInAsBox.setText(UserName);
        chatServerA.CurrentUsers.add(UserName);
        MainWindow.setTitle(UserName + "'s Chat Box");
        System.out.println(chatServerA.CurrentUsers);
        B_Send.setEnabled(true);
        B_Disconnect.setEnabled(true);
        B_Connect.setEnabled(false);
        Connect();

    }

    public static void ACTION_B_ENTER() {
        if (!TF_UserNameBox.getText().equals("")) {
            UserName = TF_UserNameBox.getText().trim();
            L_LoggedInAsBox.setText(UserName);
            chatServerA.CurrentUsers.add(UserName);
            MainWindow.setTitle(UserName + "'s Chat Box");
            System.out.println(chatServerA.CurrentUsers);
            LogInInWindow.setVisible(false);
            B_Send.setEnabled(true);
            B_Disconnect.setEnabled(true);
            B_Connect.setEnabled(false);
            Connect();

        } else {
            JOptionPane.showMessageDialog(null, "Please Enter a Name");
        }
    }

    public static void MainWindow_Action() {
        B_Send.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACTION_B_SEND();
            }
        });

        B_Disconnect.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACTION_B_DISCONNECT();
            }
        });

        B_Connect.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (UserName.equals("default")) {
                    TF_UserNameBox.setText("");
                    BuildLogInWindow();
                    //ACTION_B_ENTER();
                } else {
                    addUserToServer();
                }

            }
        });

        B_Help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ACTION_B_HELP();
            }
        });

        B_About.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    COPY_TO_FILE();
                } catch (IOException ex) {
                    Logger.getLogger(clientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public static void ACTION_B_SEND() {
        if (!TF_Message.getText().equals("")) {
            ChatClient.SEND(TF_Message.getText());
            TF_Message.requestFocus();
        }
    }

    public static void ACTION_B_DISCONNECT() {
        try {

            ChatClient.DISCONNECT();
        } catch (Exception X) {
            X.printStackTrace();
        }
    }

    public static void ACTION_B_HELP() {
        JOptionPane.showMessageDialog(null, "Multi-client Program");

    }

    public static void COPY_TO_FILE() throws IOException {

        String str = TA_Converation.getText();
        JFrame parentFrame = new JFrame();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("./"));
        int actionDialog = chooser.showSaveDialog(parentFrame);
        if (actionDialog == JFileChooser.APPROVE_OPTION) {
            BufferedWriter out = null;
            File fileName = new File(chooser.getSelectedFile() + "");
            if (fileName == null) {
                return;
            }
            if (fileName.exists()) {
                actionDialog = JOptionPane.showConfirmDialog(parentFrame,
                        "Replace existing file?");
                if (actionDialog == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            TA_Converation.write(writer);
            writer.close();

        }

    }

}

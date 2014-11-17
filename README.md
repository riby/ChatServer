ChatServer
==========

A Chat application using Multi Client Server model

The system developed is a chat room which allows users to chat in real time with other users in
the same Chat room.

Initially run login.java which in turn invokes the DBconnect.java. This displays the
login and registration page of the user.

To participate in a Chat Room one needs to register first. The registered users
information is stored in the MySQL in database login in the table login_info for authentication.

Once registered ,user can login to the chat room which displays the Chat room Server page.

After logging in it invokes ServerHandler.java, ServerDBconnect.java.It displays the list of available port numbers i.e; chat rooms. List of available ports are
stored in a database server_list in the table server_info. If needed one can create a chat room.

Select the chat room needed from the drop down menu and click JOIN button which takes you to
the chat room. This inturn invokes ClientGUI.java, ClientChat.java, ChatServerA.java and
ChatServerA-ret.java.

Similarly any number of users can register and login to the Chat room application. Any number
of users can chat in the same room. One can see the list of available users in the right side of the
chat room. If any user leaves the room it notifies, other users that a particular user has left the
chat room. The same person can participate in different chat rooms.

Finally ,user can leave from chat room or logout from the application whenever needed.

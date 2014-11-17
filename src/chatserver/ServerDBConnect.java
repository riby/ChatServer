package chatserver;

/**
 *
 * @author Riby
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Riby
 */
public class ServerDBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public ServerDBConnect() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/server_list", "root", "");
            st = (Statement) con.createStatement();

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public Map<Integer, Boolean> getServerList() {
        Map<Integer, Boolean> PORT = new HashMap<Integer, Boolean>();
        try {

            String query = "select * from `server_info`";
            rs = st.executeQuery(query);
            System.out.println("Records from database");

            while (rs.next()) {
                Integer server_port = rs.getInt("server_port");
                Boolean status = rs.getBoolean("status");
                System.out.println("Port" + server_port + " Status" + status);

                PORT.put(server_port, status);

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return PORT;
    }

    public void updateServerStatus(Integer server_port, int status) throws SQLException {

        String q = "UPDATE `server_list`.`server_info` SET `status` = '" + status + "' WHERE `server_info`.`server_port` =" + server_port;
        st.executeUpdate(q);
        System.out.println("updated records");

    }

}

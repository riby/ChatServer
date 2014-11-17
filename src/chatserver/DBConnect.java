package chatserver;

import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnect() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");
            st = (Statement) con.createStatement();

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void registerUser(String uname, String pass) throws SQLException {

        String query = "INSERT INTO `login`.`login_info` (`username`, `password`) VALUES ('" + uname + "', '" + pass + "')";
        st.executeUpdate(query);
        System.out.println("updated records");

    }

    public boolean checkUser(String uname) throws SQLException {
        String q = "SELECT * FROM `login_info` WHERE username='" + uname + "'";

        rs = st.executeQuery(q);

        if (rs.next() == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean loginCheck(String name, String password) throws SQLException {

        String name1 = "riby";
        String password2 = "12345";
        rs = null;
        String q = "SELECT * FROM `login_info` WHERE username='" + name + "' and password='" + password + "'";
        rs = st.executeQuery(q);

        if (rs.next() == true) {
            return true;
        } else {
            return false;
        }
    }

    public void getData() {
        try {

            String query = "select * from login_info";
            rs = st.executeQuery(query);
            System.out.println("Records from database");

            while (rs.next()) {
                String name = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("Name" + name + " Password" + password);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

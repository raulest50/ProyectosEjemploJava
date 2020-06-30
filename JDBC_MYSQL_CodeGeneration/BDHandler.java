import com.sun.jdi.connect.Connector;
import java.lang.ClassNotFoundException;
import java.lang.String;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BDHandler {
  public String user;

  public String url;

  public String pass;

  public String cname = "com.mysql.jdbc.Driver";

  public Connector link;

  public PreparedStatement pst;

  public ResultSet rs;

  void SetConnection() throws SQLException, ClassNotFoundException {
    Class.forName(this.cname);
    this.link = DriverManager.getConnection(url, user, pass);
  }

  void SetConnection() throws SQLException, ClassNotFoundException {
    this.rs.close();
    this.pst.close();
    this.link.close();
  }

  void SetConnection() throws SQLException, ClassNotFoundException {
    this.pst.close();
    this.link.close();
  }
}

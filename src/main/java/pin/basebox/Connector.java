package pin.basebox;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import pin.jarbox.Pass;

public class Connector implements Serializable {

  private static final long serialVersionUID = -6792394590059279917L;

  public String name;
  public Base base;
  public String path;
  public Integer port;
  public String data;
  public String user;
  public Pass pass;

  public String getFormed() {
    String result = base.getFormation();
    if (result.contains("$path") && path != null) {
      result = result.replace("$path", path);
    }
    if (result.contains("$port")) {
      if (port != null) {
        result = result.replace("$port", port.toString());
      } else if (base != null) {
        result = result.replace("$port", base.getDefaultPort().toString());
      }
    }
    if (result.contains("$data") && data != null) {
      result = result.replace("$data", data);
    }
    return result;
  }

  public synchronized Connection connect() throws Exception {
    Class.forName(base.getClasse());
    if ((user != null && !user.isEmpty() && pass != null)) {
      return DriverManager.getConnection(getFormed(), user, pass.getPassword());
    } else {
      return DriverManager.getConnection(getFormed());
    }
  }

  @Override
  public String toString() {
    return "{"
        + " name='" + name + "'"
        + ", base='" + base + "'"
        + ", path='" + path + "'"
        + ", port='" + port + "'"
        + ", data='" + data + "'"
        + ", user='" + user + "'"
        + ", pass='" + pass + "'"
        + "}";
  }
}

package pin.basebox;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseHelper {

  public List<TableHead> getTables(Connection connection) throws Exception {
    DatabaseMetaData meta = connection.getMetaData();
    ResultSet set = meta.getTables(null, null, "%", new String[] {"TABLE"});
    List<TableHead> result = new ArrayList<>();
    while (set.next()) {
      result.add(
          new TableHead(set.getString(1), set.getString(2), set.getString(3)));
    }
    return result;
  }

  public abstract boolean isErrorPrimaryKey(Exception error);

  public abstract void createTable(Connection connection, Table table,
      boolean onlyIfNotExists) throws Exception;

}

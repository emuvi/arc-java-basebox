package pin.basebox;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pin.jarbox.dat.Table;
import pin.jarbox.dat.TableField;
import pin.jarbox.dat.TableHead;


public abstract class BaseHelper {

  public abstract boolean isErrorPrimaryKey(Exception error);

  public abstract void createTable(Connection connection, Table table,
      boolean onlyIfNotExists) throws Exception;

  public List<TableHead> getTables(Connection connection) throws Exception {
    DatabaseMetaData meta = connection.getMetaData();
    ResultSet set = meta.getTables(null, null, "%", new String[] {"TABLE"});
    List<TableHead> result = new ArrayList<>();
    while (set.next()) {
      result.add(new TableHead(set.getString(1), set.getString(2), set.getString(3)));
    }
    return result;
  }

  public ResultSet selectFields(Table fromTable, Connection onConnection)
      throws Exception {
    StringBuilder select = new StringBuilder("SELECT ");
    boolean first = true;
    for (TableField field : fromTable.fields) {
      if (first) {
        first = false;
      } else {
        select.append(", ");
      }
      select.append(field.name);
    }
    select.append(" FROM ");
    select.append(fromTable.getSchemaName());
    return onConnection.createStatement().executeQuery(select.toString());
  }

}

package pin.basebox;

import java.sql.Connection;
import pin.jarbox.dat.Table;

public class BaseHelperPostgres extends BaseHelper {

  @Override
  public boolean isErrorPrimaryKey(Exception error) {
    return error.getMessage().contains("duplicate key value violates unique constraint");
  }

  @Override
  public void createTable(Connection connection, Table table, boolean ifNotExists) throws Exception {
    var builder = new StringBuilder("CREATE TABLE ");
    if (ifNotExists) {
      builder.append("IF NOT EXISTS ");
    }
    builder.append(table.getSchemaName());
    builder.append(" ( ");
    boolean first = true;
    for (var  field : table.fields) {
      if (!first) {
        builder.append(", ");
      } else {
        first = false;
      }
      builder.append(field.getSQLDescription().replace(" BLOB", " BYTEA"));
    }
    if (table.keys != null && !table.keys.isEmpty()) {
      builder.append(", PRIMARY KEY (");
      for (int ic = 0; ic < table.keys.size(); ic++) {
        if (ic > 0) {
          builder.append(", ");
        }
        builder.append(table.keys.get(ic));
      }
      builder.append(")");
    }
    builder.append(" )");
    connection.createStatement().execute(builder.toString());
  }

}

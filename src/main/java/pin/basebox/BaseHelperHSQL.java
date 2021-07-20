package pin.basebox;

import java.sql.Connection;
import pin.jarbox.dat.Table;
import pin.jarbox.dat.TableField;

public class BaseHelperHSQL extends BaseHelper {

  @Override
  public boolean isErrorPrimaryKey(Exception error) {
    return error.getMessage().contains(
        "integrity constraint violation: unique constraint or index violation;");
  }

  @Override
  public void createTable(Connection connection, Table table, boolean ifNotExists)
    throws Exception {
    StringBuilder builder = new StringBuilder();
    builder.append("CREATE TABLE ");
    if (ifNotExists) {
      builder.append("IF NOT EXISTS ");
    }
    builder.append(table.getSchemaName());
    builder.append(" ( ");
    for (int ic = 0; ic < table.fields.size(); ic++) {
      if (ic > 0) {
        builder.append(", ");
      }
      TableField field = table.fields.get(ic);
      builder.append(field.getSQLDescription());
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

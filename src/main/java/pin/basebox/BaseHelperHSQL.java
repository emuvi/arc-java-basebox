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
  public void createTable(Connection connection, Table table, boolean onlyIfNotExists)
    throws Exception {
    StringBuilder builder = new StringBuilder();
    builder.append("CREATE TABLE ");
    if (onlyIfNotExists) {
      builder.append("IF NOT EXISTS ");
    }
    builder.append(table.getSchemaName());
    builder.append(" (");
    for (int ic = 0; ic < table.fields.size(); ic++) {
      if (ic > 0) {
        builder.append(", ");
      }
      TableField campo = table.fields.get(ic);
      builder.append(campo.name);
      switch (campo.nature) {
        case Bool:
          builder.append(" BOOLEAN");
          break;
        case Char:
          builder.append(" CHAR(1)");
          break;
        case Chars:
        case Pass:
        case Color:
        case Enumeration:
        case Sugestion:
          builder.append(" VARCHAR");
          if (campo.size != null) {
            builder.append("(");
            builder.append(campo.size);
            builder.append(")");
          }
          break;
        case Int:
        case Serial:
          builder.append(" INTEGER");
          break;
        case Long:
        case SerialLong:
          builder.append(" BIGINT");
          break;
        case Float:
        case Double:
          builder.append(" NUMERIC");
          if (campo.size != null) {
            builder.append("(");
            builder.append(campo.size);
            if (campo.precision != null) {
              builder.append(",");
              builder.append(campo.precision);
            }
            builder.append(")");
          }
          break;
        case Date:
          builder.append(" DATE");
          break;
        case Time:
          builder.append(" TIME");
          break;
        case DateTime:
        case Timestamp:
          builder.append(" TIMESTAMP");
          break;
        case Bytes:
        case Undefined:
          builder.append(" BLOB");
          if (campo.size != null) {
            builder.append("(");
            builder.append(campo.size);
            builder.append(")");
          }
          break;
        default:
          throw new UnsupportedOperationException();
      }
      if (campo.notNull == true) {
        builder.append(" NOT NULL");
      }
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
    builder.append(")");
    connection.createStatement().execute(builder.toString());
  }

}

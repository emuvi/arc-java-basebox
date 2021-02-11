package pin.basebox;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import pin.jarbox.WzdChars;

public class TableHead {

  public String catalog;
  public String schema;
  public String name;

  public TableHead() { this(null, null, null); }

  public TableHead(String catalog) { this(catalog, null, null); }

  public TableHead(String catalog, String schema) {
    this(catalog, schema, null);
  }

  public TableHead(String catalog, String schema, String name) {
    this.catalog = catalog;
    this.schema = schema;
    this.name = name;
  }

  public String getSchemaAndName() { return WzdChars.sum(".", schema, name); }

  public String getNameForFile() {
    return WzdChars.sum(".", catalog, WzdChars.firstNonEmpty(schema, "public"),
                        name);
  }

  public Table getTable(Connection connection) throws Exception {
    Table result = new Table(this, new ArrayList<>(), new ArrayList<>());
    DatabaseMetaData meta = connection.getMetaData();
    ResultSet set = meta.getPrimaryKeys(this.catalog, this.schema, this.name);
    while (set.next()) {
      result.keys.add(set.getString(4));
    }
    ResultSet rst = meta.getColumns(this.catalog, this.schema, this.name, "%");
    while (rst.next()) {
      TableField campo = new TableField();
      campo.name = rst.getString(4);
      campo.nature = Nature.get(rst.getInt(5));
      campo.size = rst.getInt(7);
      campo.precision = rst.getInt(9);
      campo.notNull = "NO".equals(rst.getString(18));
      campo.key = false;
      if (result.keys.contains(campo.name)) {
        campo.key = true;
      }
      result.fields.add(campo);
    }
    return result;
  }

  @Override
  public String toString() {
    return WzdChars.sum(".", catalog, schema, name);
  }
}

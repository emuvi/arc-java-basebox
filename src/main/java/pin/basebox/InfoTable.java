package pin.basebox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoTable {

  private final String catalog;
  private final String schema;
  private final String name;

  private final List<InfoColumn> columns;

  public InfoTable(String catalog, String schema, String name) {
    this.catalog = catalog;
    this.schema = schema;
    this.name = name;
    this.columns = new ArrayList<>();
  }

  public String getCatalog() { return this.catalog; }

  public String getSchema() { return this.schema; }

  public String getName() { return this.name; }

  public String getFileName() {
    return ("public".equals(this.schema) ? "" : this.schema + ".") + this.name;
  }

  public List<InfoColumn> getColumns() { return columns; }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof InfoTable)) {
      return false;
    }
    InfoTable tableHead = (InfoTable)o;
    return Objects.equals(catalog, tableHead.catalog) &&
      Objects.equals(schema, tableHead.schema) &&
      Objects.equals(name, tableHead.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalog, schema, name);
  }

  @Override
  public String toString() {
    return "{"
      + " catalog='" + getCatalog() + "'"
      + ", schema='" + getSchema() + "'"
      + ", name='" + getName() + "'"
      + "}";
  }
}

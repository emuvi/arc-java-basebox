package pin.basebox;

import java.util.List;
import pin.jarbox.WzdChars;

public class Table {

  public TableHead head;
  public List<TableField> fields;
  public List<String> keys;

  public Table() {
    this(null, null, null);
  }

  public Table(TableHead head) {
    this(head, null, null);
  }

  public Table(TableHead head, List<TableField> fields) {
    this(head, fields, null);
  }

  public Table(TableHead head, List<TableField> fields, List<String> keys) {
    this.head = head;
    this.fields = fields;
    this.keys = keys;
  }

  public String getSchemaAndName() {
    return WzdChars.sum(".", this.head.schema, this.head.name);
  }

}

package pin.basebox;

public class TableField {

  public String name;
  public Nature nature;
  public Integer size;
  public Integer precision;
  public Boolean notNull;
  public Boolean key;

  @Override
  public String toString() {
    return "{"
        + " name='" + name + "'"
        + ", nature='" + nature + "'"
        + ", size='" + size + "'"
        + ", precision='" + precision + "'"
        + ", notNull='" + notNull + "'"
        + ", key='" + key + "'"
        + "}";
  }
}

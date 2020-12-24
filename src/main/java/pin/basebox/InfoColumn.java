package pin.basebox;

import java.util.Objects;

public class InfoColumn {

  private final String name;
  private final String clazz;
  private final Integer size;
  private final Integer precision;
  private final Boolean nullable;

  public InfoColumn(String name, String clazz, Integer size, Integer precision,
      Boolean nullable) {
    this.name = name;
    this.clazz = clazz;
    this.size = size;
    this.precision = precision;
    this.nullable = nullable;
  }

  public String getName() { return this.name; }

  public String getClazz() { return this.clazz; }

  public Integer getSize() { return this.size; }

  public Integer getPrecision() { return this.precision; }

  public Boolean isNullable() { return this.nullable; }

  public Boolean getNullable() { return this.nullable; }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof InfoColumn)) {
      return false;
    }
    InfoColumn infoColumn = (InfoColumn)o;
    return Objects.equals(name, infoColumn.name) &&
      Objects.equals(clazz, infoColumn.clazz) &&
      Objects.equals(size, infoColumn.size) &&
      Objects.equals(precision, infoColumn.precision) &&
      Objects.equals(nullable, infoColumn.nullable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, clazz, size, precision, nullable);
  }

  @Override
  public String toString() {
    return "{"
      + " name='" + getName() + "'"
      + ", clazz='" + getClazz() + "'"
      + ", size='" + getSize() + "'"
      + ", precision='" + getPrecision() + "'"
      + ", nullable='" + isNullable() + "'"
      + "}";
  }
}

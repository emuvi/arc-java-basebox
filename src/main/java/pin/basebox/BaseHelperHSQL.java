package pin.basebox;

import java.sql.Connection;

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
    builder.append(table.getEsquemaAndNome());
    builder.append(" (");
    for (int ic = 0; ic < table.getCampos().size(); ic++) {
      if (ic > 0) {
        builder.append(", ");
      }
      TableField campo = table.getCampos().get(ic);
      builder.append(campo.name);
      switch (campo.nature) {
        case Logico:
          builder.append(" BOOLEAN");
          break;
        case Caracter:
          builder.append(" CHAR(1)");
          break;
        case Caracteres:
        case Senha:
        case Cor:
        case Enumeracao:
        case Sugestao:
          builder.append(" VARCHAR");
          if (campo.size != null) {
            builder.append("(");
            builder.append(campo.size);
            builder.append(")");
          }
          break;
        case Inteiro:
        case Serial:
          builder.append(" INTEGER");
          break;
        case InteiroLongo:
        case SerialLongo:
          builder.append(" BIGINT");
          break;
        case Numero:
        case NumeroLongo:
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
        case Data:
          builder.append(" DATE");
          break;
        case Hora:
          builder.append(" TIME");
          break;
        case DataHora:
        case Momento:
          builder.append(" TIMESTAMP");
          break;
        case Arquivo:
        case Indefinido:
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
    if (table.hasChaves() && !table.getChaves().isEmpty()) {
      builder.append(", PRIMARY KEY (");
      for (int ic = 0; ic < table.getChaves().size(); ic++) {
        if (ic > 0) {
          builder.append(", ");
        }
        builder.append(table.getChaves().get(ic));
      }
      builder.append(")");
    }
    builder.append(")");
    connection.createStatement().execute(builder.toString());
  }

}

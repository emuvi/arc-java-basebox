package pin.basebox;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Objects;

public class TableHead {

  private String catalogo;
  private String esquema;
  private String nome;

  public TableHead() {
    this(null, null, null);
  }

  public TableHead(String catalogo) {
    this(catalogo, null, null);
  }

  public TableHead(String catalogo, String esquema) {
    this(catalogo, esquema, null);
  }

  public TableHead(String catalogo, String esquema, String nome) {
    this.catalogo = catalogo;
    this.esquema = esquema;
    this.nome = nome;
  }

  public Boolean hasCatalogo() {
    return this.catalogo != null;
  }

  public String getCatalogo() {
    return this.catalogo;
  }

  public Boolean isCatalogo(String catalogo) {
    return Objects.equals(this.catalogo, catalogo);
  }

  public void setCatalogo(String catalogo) {
    this.catalogo = catalogo;
  }

  public TableHead defCatalogo(String catalogo) {
    this.catalogo = catalogo;
    return this;
  }

  public Boolean hasEsquema() {
    return this.esquema != null;
  }

  public String getEsquema() {
    return this.esquema;
  }

  public Boolean isEsquema(String esquema) {
    return Objects.equals(this.esquema, esquema);
  }

  public void setEsquema(String esquema) {
    this.esquema = esquema;
  }

  public TableHead defEsquema(String esquema) {
    this.esquema = esquema;
    return this;
  }

  public Boolean hasNome() {
    return this.nome != null;
  }

  public String getNome() {
    return this.nome;
  }

  public Boolean isNome(String nome) {
    return Objects.equals(this.nome, nome);
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public TableHead defNome(String nome) {
    this.nome = nome;
    return this;
  }

  public String getEsquemaAndNome() {
    return String.join(".", getEsquema(), getNome());
  }

  public Table getTable(Connection connection) throws Exception {
    Table result = new Table(this);
    DatabaseMetaData meta = connection.getMetaData();
    ResultSet set = meta.getPrimaryKeys(this.catalogo, this.esquema, this.nome);
    while (set.next()) {
      result.putChaves(set.getString(4));
    }
    ResultSet rst = meta.getColumns(this.catalogo, this.esquema, this.nome, "%");
    while (rst.next()) {
      TableField campo = new TableField();
      campo.name = rst.getString(4);
      campo.nature = Natureza.get(rst.getInt(5));
      campo.size = rst.getInt(7);
      campo.precision = rst.getInt(9);
      campo.notNull = "NO".equals(rst.getString(18));
      campo.key = false;
      if (result.hasChaves()) {
        if (result.getChaves().contains(campo.name)) {
          campo.key = true;
        }
      }
      result.putCampos(campo);
    }
    return result;
  }

}

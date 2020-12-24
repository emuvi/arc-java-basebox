package pin.basebox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table {

  private TableHead cabeca;
  private List<TableField> campos;
  private List<String> chaves;

  public Table() {
    this(null, null, null);
  }

  public Table(TableHead cabeca) {
    this(cabeca, null, null);
  }

  public Table(TableHead cabeca, List<TableField> campos) {
    this(cabeca, campos, null);
  }

  public Table(TableHead cabeca, List<TableField> campos, List<String> chaves) {
    this.cabeca = cabeca;
    this.campos = campos;
    this.chaves = chaves;
  }

  public Boolean hasCabeca() {
    return this.cabeca != null;
  }

  public TableHead getCabeca() {
    return this.cabeca;
  }

  public Boolean isCabeca(TableHead cabeca) {
    return Objects.equals(this.cabeca, cabeca);
  }

  public void setCabeca(TableHead cabeca) {
    this.cabeca = cabeca;
  }

  public Table defCabeca(TableHead cabeca) {
    this.cabeca = cabeca;
    return this;
  }

  public Boolean hasCampos() {
    return this.campos != null;
  }

  public List<TableField> getCampos() {
    return this.campos;
  }

  public Boolean isCampos(List<TableField> campos) {
    return Objects.equals(this.campos, campos);
  }

  public void setCampos(List<TableField> campos) {
    this.campos = campos;
  }

  public Table defCampos(List<TableField> campos) {
    this.campos = campos;
    return this;
  }

  public Table putCampos(TableField... campos) {
    if (campos != null) {
      if (this.campos == null) {
        this.campos = new ArrayList<>();
      }
      for (TableField variavel : campos) {
        this.campos.add(variavel);
      }
    }
    return this;
  }

  public Table delCampos(TableField... campos) {
    if (campos != null) {
      if (this.campos != null) {
        for (TableField variavel : campos) {
          this.campos.remove(variavel);
        }
      }
    }
    return this;
  }

  public Table clearCampos() {
    if (this.campos != null) {
      this.campos.clear();
    }
    return this;
  }

  public Boolean hasChaves() {
    return this.chaves != null;
  }

  public List<String> getChaves() {
    return this.chaves;
  }

  public Boolean isChaves(List<String> chaves) {
    return Objects.equals(this.chaves, chaves);
  }

  public void setChaves(List<String> chaves) {
    this.chaves = chaves;
  }

  public Table defChaves(List<String> chaves) {
    this.chaves = chaves;
    return this;
  }

  public Table putChaves(String... chaves) {
    if (chaves != null) {
      if (this.chaves == null) {
        this.chaves = new ArrayList<>();
      }
      for (String variavel : chaves) {
        this.chaves.add(variavel);
      }
    }
    return this;
  }

  public Table delChaves(String... chaves) {
    if (chaves != null) {
      if (this.chaves != null) {
        for (String variavel : chaves) {
          this.chaves.remove(variavel);
        }
      }
    }
    return this;
  }

  public Table clearChaves() {
    if (this.chaves != null) {
      this.chaves.clear();
    }
    return this;
  }

  public String getEsquemaAndNome() {
    return String.join(".", this.cabeca.getEsquema(), this.cabeca.getNome());
  }

}

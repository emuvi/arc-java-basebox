package pin.basebox;

public enum Natureza {

  Logico, Caracter, Caracteres, Senha, Cor, Enumeracao, Sugestao, Inteiro, InteiroLongo, Serial, SerialLongo, Numero, NumeroLongo, Data, Hora, DataHora, Momento,

  Arquivo,

  Indefinido;

  public static Natureza get(int doSQLType) {
    switch (doSQLType) {
      case -7:
      case -6:
      case 5:
      case 4:
        return Natureza.Inteiro;
      case -5:
        return Natureza.InteiroLongo;
      case 6:
      case 7:
        return Natureza.Numero;
      case 8:
      case 2:
      case 3:
        return Natureza.NumeroLongo;
      case 1:
      case -15:
        return Natureza.Caracter;
      case 12:
      case -1:
      case -9:
      case -16:
        return Natureza.Caracteres;
      case 91:
        return Natureza.Data;
      case 92:
      case 2013:
        return Natureza.Hora;
      case 93:
      case 2014:
        return Natureza.DataHora;
      case -2:
      case -3:
      case -4:
      case 2004:
      case 2005:
      case 2011:
      case 2009:
        return Natureza.Arquivo;
      case 16:
        return Natureza.Logico;
      default:
        return Natureza.Indefinido;
    }
  }

}

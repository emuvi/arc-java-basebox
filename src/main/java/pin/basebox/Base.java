package pin.basebox;

public enum Base {

  HSQLMemory("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:$path", 9000,
      new BaseHelperHSQL()),

  HSQLLocal("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:$path;hsqldb.lock_file=true",
      9000, new BaseHelperHSQL()),

  HSQLClient("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://$path:$port/$data", 9000,
      new BaseHelperHSQL()),

  DerbyInner("", "jdbc:derby:$path;create=true", 1527, new BaseHelperDerby()),

  DerbyClient("", "jdbc:derby://$path:$port/$data;create=true", 1527,
      new BaseHelperDerby()),

  FirebirdInner("", "jdbc:firebirdsql:embedded:$path", 3050, new BaseHelperFirebird()),

  FirebirdLocal("", "jdbc:firebirdsql:local:$path", 3050, new BaseHelperFirebird()),

  FirebirdClient("", "jdbc:firebirdsql:$path/$port:$data", 3050,
      new BaseHelperFirebird()),

  MySQL("", "jdbc:mysql://$path:$port/$data", 3306, new BaseHelperMySQL()),

  Postgres("org.postgresql.Driver", "jdbc:postgresql://$path:$port/$data", 5432,
      new BaseHelperPostgres());

  private final String clazz;
  private final String formation;
  private final Integer defaultPort;
  private final BaseHelper helper;

  private Base(String classe, String formation, Integer defaultPort,
      BaseHelper auxiliar) {
    this.clazz = classe;
    this.formation = formation;
    this.defaultPort = defaultPort;
    this.helper = auxiliar;
  }

  public String getClasse() {
    return this.clazz;
  }

  public String getFormation() {
    return this.formation;
  }

  public Integer getDefaultPort() {
    return defaultPort;
  }

  public BaseHelper getHelper() {
    return this.helper;
  }
}

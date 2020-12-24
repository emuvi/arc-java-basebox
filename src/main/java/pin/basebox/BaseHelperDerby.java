package pin.basebox;

import java.sql.Connection;

public class BaseHelperDerby extends BaseHelper {

  @Override
  public boolean isErrorPrimaryKey(Exception error) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createTable(Connection connection, Table table, boolean onlyIfNotExists) throws Exception {
    throw new UnsupportedOperationException();
  }

}

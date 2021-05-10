package pin.basebox;

import java.sql.Connection;
import pin.jarbox.dat.Table;

public class BaseHelperPostgres extends BaseHelper {

  @Override
  public boolean isErrorPrimaryKey(Exception error) {
    return error.getMessage().contains("duplicate key value violates unique constraint");
  }

  @Override
  public void createTable(Connection connection, Table table, boolean onlyIfExists) throws Exception {
    throw new UnsupportedOperationException();
  }

}

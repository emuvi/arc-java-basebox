package pin;

import pin.basebox.HelmMain;
import pin.jarbox.Console;
import pin.jarbox.Utils;

public class BaseBox {

  public static void main(String[] args) {
    try {
      Console.start(args);
      Utils.startSystemLook();
      Utils.startMain(new HelmMain());
    } catch (Exception e) {
      Utils.treat(e);
    }
  }
  
}

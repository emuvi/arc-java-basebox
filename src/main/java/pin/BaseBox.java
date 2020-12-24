package pin;

import pin.basebox.HelmMain;
import pin.jarbox.Console;
import pin.jarbox.Utils;

public class BaseBox {

  public static void main(String[] args) {
    Console.start(args);
    Utils.startSystemLook();
    Utils.startMain(new HelmMain());
  }
}

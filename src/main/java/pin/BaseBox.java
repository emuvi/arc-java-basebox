package pin;

import pin.basebox.HelmMain;
import pin.jarbox.bin.Console;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;


public class BaseBox {

  public static void main(String[] args) {
    try {
      Console.start("BaseBox", "0.1.0", args,
          "A small app with a series of database utilities.");
      WzdDesk.startSystemLook();
      WzdDesk.startMain(new HelmMain());
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

}

package pin;

import pin.basebox.HelmMain;
import pin.jarbox.Console;
import pin.jarbox.WzdDesk;
import pin.jarbox.WzdLog;


public class BaseBox {

  public static void main(String[] args) {
    try {
      Console.start(args);
      WzdDesk.startSystemLook();
      WzdDesk.startMain(new HelmMain());
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }
  
}

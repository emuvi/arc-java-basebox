package pin.basebox;

import java.io.File;
import pin.jarbox.Messenger;

public class ImportFromCSV extends Thread {

  private final Messenger messenger;
  private final File origin;
  private final Connector destiny;

  public ImportFromCSV(Messenger messenger, File origin, Connector destiny) {
    super("ImportFromCSV");
    this.messenger = messenger;
    this.origin = origin;
    this.destiny = destiny;
  }

  @Override
  public void run() {
    try {
      
    } catch (Exception e) {
      messenger.handle(e);
    }
  }
}

package pin.basebox;

import java.io.File;
import pin.jarbox.Progress;

public class ImportFromCSV extends Thread {

  private final Progress progress;
  private final File origin;
  private final Connector destiny;

  public ImportFromCSV(Progress progress, File origin, Connector destiny) {
    super("ImportFromCSV");
    this.progress = progress;
    this.origin = origin;
    this.destiny = destiny;
  }

  @Override
  public void run() {
    try {
      
    } catch (Exception e) {
      progress.log(e);
    }
  }
}

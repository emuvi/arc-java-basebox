package pin.basebox;

import java.io.File;
import pin.jarbox.Progress;
import pin.jarbox.WzdFile;

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

  private void importArchive(File archive) {
    String tableName = WzdFile.getBaseName(archive.getName());
    
  }

  @Override
  public void run() {
    try {
      if (!origin.exists()) {
        throw new Exception("The origin must exist.");
      }
      if (origin.isFile()) {
        importArchive(origin);
      } else {
        for (File inside : origin.listFiles()) {
          if (inside.isFile()) {
            importArchive(inside);
          }
        }
      }
    } catch (Exception e) {
      progress.log(e);
    }
  }
}

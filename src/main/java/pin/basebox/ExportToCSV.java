package pin.basebox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import pin.jarbox.bin.Progress;
import pin.jarbox.dat.CSVFile;
import pin.jarbox.dat.FileMode;
import pin.jarbox.dat.Table;
import pin.jarbox.dat.TableHead;
import pin.jarbox.wzd.WzdBytes;
import pin.jarbox.wzd.WzdDate;

public class ExportToCSV extends Thread {

  private final Connector origin;
  private final File destiny;
  private final Progress progress;

  public ExportToCSV(Connector origin, File destiny, Progress progress) {
    super("ExportToCSV");
    this.origin = origin;
    this.destiny = destiny;
    this.progress = progress;
  }

  @Override
  public void run() {
    try {
      progress.log("Origin: " + origin);
      progress.log("Establishing destiny: " + destiny);
      if (!destiny.exists()) {
        Files.createDirectories(destiny.toPath());
      }
      if (!destiny.exists()) {
        throw new Exception("Could not create the destination folder.");
      }
      if (!destiny.isDirectory()) {
        throw new Exception("The destination must be a directory.");
      }
      progress.waitIfPausedAndThrowIfStopped();
      progress.log("Connecting to Origin...");
      try (Connection originConn = origin.connect()) {
        progress.log("Connected.");
        progress.waitIfPausedAndThrowIfStopped();
        progress.log("Getting tables...");
        List<TableHead> tables = origin.base.getHelper().getTables(originConn);
        for (TableHead tableHead : tables) {
          progress.log("Processing: " + tableHead.toString());
          Table table = tableHead.getTable(originConn);
          try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(destiny,
              tableHead.getNameForFile() + ".tab"), false), true)) {
            writer.write(table.toString());
          }
          final var tableDestiny = new File(destiny, tableHead.getNameForFile() + ".csv");
          try (CSVFile csvFile = new CSVFile(tableDestiny, FileMode.WRITE)) {
            final var row = new String[table.fields.size()];
            for (int i = 0; i < table.fields.size(); i++) {
              row[i] = table.fields.get(i).name;
            }
            csvFile.writeLine(row);
            ResultSet rstOrigin = origin.base.getHelper().selectAll(table, originConn);
            long recordCount = 0;
            while (rstOrigin.next()) {
              recordCount++;
              progress.log("Writing record " + recordCount + " of " + tableHead.name);
              for (int i = 0; i < table.fields.size(); i++) {
                row[i] = table.fields.get(i).formatValue(rstOrigin.getObject(i + 1));
              }
              csvFile.writeLine(row);
            }
          }
        }
      }
      progress.log("ExportToCSV Finished!");
    } catch (Exception error) {
      progress.log(error);
    }
  }
}

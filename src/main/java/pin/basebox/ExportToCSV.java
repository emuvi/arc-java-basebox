package pin.basebox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import pin.jarbox.bin.Progress;
import pin.jarbox.dat.Table;
import pin.jarbox.dat.TableField;
import pin.jarbox.dat.TableHead;
import pin.jarbox.wzd.WzdChars;
import pin.jarbox.wzd.WzdDate;
import pin.jarbox.wzd.WzdFile;

public class ExportToCSV extends Thread {

  private final Progress progress;
  private final Connector origin;
  private final File destiny;

  public ExportToCSV(Progress progress, Connector origin, File destiny) {
    super("ExportToCSV");
    this.progress = progress;
    this.origin = origin;
    this.destiny = destiny;
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
      try (Connection conOrigin = origin.connect()) {
        progress.log("Connected.");
        progress.waitIfPausedAndThrowIfStopped();
        progress.log("Getting tables...");
        List<TableHead> tables = origin.base.getHelper().getTables(conOrigin);
        for (TableHead tableHead : tables) {
          progress.log("Processing: " + tableHead.toString());
          Table table = tableHead.getTable(conOrigin);
          StringBuilder metadata = new StringBuilder();
          metadata.append(tableHead.toString());
          metadata.append(System.lineSeparator());
          StringBuilder select = new StringBuilder("SELECT ");
          boolean first = true;
          for (TableField field : table.fields) {
            if (first)
              first = false;
            else
              select.append(", ");
            progress.log(field.toString());
            metadata.append(field.toString());
            metadata.append(System.lineSeparator());
            select.append(field.name);
          }
          try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(destiny,
              tableHead.getNameForFile() + ".tab"), false), true)) {
            writer.write(metadata.toString());
          }
          select.append(" FROM ");
          select.append(tableHead.getSchemaAndName());
          try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(destiny,
              tableHead.getNameForFile() + ".csv"), false), true)) {
            for (int i = 0; i < table.fields.size(); i++) {
              if (i > 0) {
                writer.print(";");
              }
              writer.print("\"");
              writer.print(table.fields.get(i).name);
              if (i < table.fields.size() - 1) {
                writer.print("\"");
              } else {
                writer.println("\"");
              }
            }
            ResultSet rstDe = conOrigin.createStatement().executeQuery(select.toString());
            long recordCount = 0;
            while (rstDe.next()) {
              recordCount++;
              progress.log("Writing record " + recordCount + " of " + tableHead.name);
              for (int i = 0; i < table.fields.size(); i++) {
                if (i > 0) {
                  writer.print(";");
                }
                String ending = "";
                switch (table.fields.get(i).nature) {
                  case Bool:
                    writer.print(rstDe.getBoolean(i + 1));
                    break;
                  case Int:
                    writer.print(rstDe.getInt(i + 1));
                    break;
                  case Long:
                    writer.print(rstDe.getLong(i + 1));
                    break;
                  case Float:
                    writer.print(rstDe.getFloat(i + 1));
                    break;
                  case Double:
                    writer.print(rstDe.getDouble(i + 1));
                    break;
                  case Char:
                  case Chars:
                    writer.print("\"");
                    writer.print(WzdChars.replaceControlFlow(rstDe.getString(i + 1)
                        .replace("\"", "\"\"")));
                    ending = "\"";
                    break;
                  case Date:
                    writer.print(WzdDate.formatDate(rstDe.getDate(i + 1)));
                    break;
                  case Time:
                    writer.print(WzdDate.formatTime(rstDe.getTime(i + 1)));
                    break;
                  case Timestamp:
                    writer.print(WzdDate.formatTimestamp(rstDe.getTimestamp(i + 1)));
                    break;
                  case Bytes:
                    writer.print("\"");
                    writer.print(WzdFile.encodeToBase64(rstDe.getBytes(i + 1)));
                    ending = "\"";
                    break;
                  default:
                    throw new Exception("DataType Not Supported.");
                }
                if (i < table.fields.size() - 1) {
                  writer.print(ending);
                } else {
                  writer.println(ending);
                }
              }
            }
          }
        }
      }
      progress.log("DumpToCSV Finished!");
    } catch (Exception error) {
      progress.log(error);
    }
  }
}

package pin.basebox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pin.jarbox.Progress;
import pin.jarbox.Utils;

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
        List<InfoTable> tables = new ArrayList<>();
        ResultSet rst =
            conOrigin.getMetaData().getTables(null, null, "%", new String[] {"TABLE"});
        while (rst.next()) {
          var inf = new InfoTable(rst.getString("TABLE_CAT"),
              rst.getString("TABLE_SCHEM"), rst.getString("TABLE_NAME"));
          tables.add(inf);
          progress.log("Got head of: " + inf.getSchemaAndName());
          progress.waitIfPausedAndThrowIfStopped();
        }
        for (InfoTable infoTable : tables) {
          StringBuilder metadata = new StringBuilder();
          progress.log(infoTable.toString());
          metadata.append(infoTable.toString());
          metadata.append(System.lineSeparator());
          ResultSet resultColumns = conOrigin.getMetaData().getColumns(
              infoTable.getCatalog(), infoTable.getSchema(), infoTable.getName(), "%");
          StringBuilder select = new StringBuilder("SELECT ");
          boolean primeiro = true;
          while (resultColumns.next()) {
            if (primeiro)
              primeiro = false;
            else
              select.append(", ");
            InfoColumn infoColumn = new InfoColumn(resultColumns.getString("COLUMN_NAME"),
                Utils.getClassOfSQL(resultColumns.getInt("DATA_TYPE")),
                resultColumns.getInt("COLUMN_SIZE"),
                resultColumns.getInt("DECIMAL_DIGITS"),
                "YES".equals(resultColumns.getString("IS_NULLABLE")));
            progress.log(infoColumn.toString());
            metadata.append(infoColumn.toString());
            metadata.append(System.lineSeparator());
            infoTable.getColumns().add(infoColumn);
            select.append(infoColumn.getName());
          }
          try (
              PrintWriter writer = new PrintWriter(
                  new FileOutputStream(
                      new File(destiny, infoTable.getFileName() + ".tab"), false),
                  true)) {
            writer.write(metadata.toString());
          }
          select.append(" FROM ");
          select.append(infoTable.getName());
          try (
              PrintWriter writer = new PrintWriter(
                  new FileOutputStream(
                      new File(destiny, infoTable.getFileName() + ".csv"), false),
                  true)) {
            for (int i = 0; i < infoTable.getColumns().size(); i++) {
              if (i > 0) {
                writer.print(";");
              }
              writer.print("\"");
              writer.print(infoTable.getColumns().get(i).getName());
              if (i < infoTable.getColumns().size() - 1) {
                writer.print("\"");
              } else {
                writer.println("\"");
              }
            }
            ResultSet rstDe = conOrigin.createStatement().executeQuery(select.toString());
            long recordCount = 0;
            while (rstDe.next()) {
              recordCount++;
              progress
                  .log("Writing record " + recordCount + " of " + infoTable.getName());
              for (int i = 0; i < infoTable.getColumns().size(); i++) {
                if (i > 0) {
                  writer.print(";");
                }
                String ending = "";
                switch (infoTable.getColumns().get(i).getClazz()) {
                  case "Boolean":
                    writer.print(rstDe.getBoolean(i + 1));
                    break;
                  case "Integer":
                    writer.print(rstDe.getInt(i + 1));
                    break;
                  case "Long":
                    writer.print(rstDe.getLong(i + 1));
                    break;
                  case "Float":
                    writer.print(rstDe.getFloat(i + 1));
                    break;
                  case "Double":
                    writer.print(rstDe.getDouble(i + 1));
                    break;
                  case "Character":
                  case "String":
                    writer.print("\"");
                    writer.print(Utils.clean(rstDe.getString(i + 1)));
                    ending = "\"";
                    break;
                  case "Date":
                    writer.print(Utils.formatDate(rstDe.getDate(i + 1)));
                    break;
                  case "Time":
                    writer.print(Utils.formatTime(rstDe.getTime(i + 1)));
                    break;
                  case "Timestamp":
                    writer.print(Utils.formatTimestamp(rstDe.getTimestamp(i + 1)));
                    break;
                  case "Byte":
                    writer.print("\"");
                    writer.print(Utils.clean(Utils.encodeBase64(rstDe.getBytes(i + 1))));
                    ending = "\"";
                    break;
                  default:
                    throw new Exception("DataType Not Supported.");
                }
                if (i < infoTable.getColumns().size() - 1) {
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

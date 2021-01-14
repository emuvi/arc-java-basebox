package pin.basebox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class HeadFile implements Serializable {

  private static final long serialVersionUID = -3732566122753921328L;

  public ListConnector connectors;

  public static HeadFile bring(File fromFile) throws Exception {
    try (ObjectInputStream ois =
         new ObjectInputStream(new FileInputStream(fromFile))) {
      Object read = ois.readObject();
      if (read instanceof HeadFile) {
        return (HeadFile) read;
      } else {
        throw new Exception("The file does not contains the right type.");
      }
    }
  }

  public static void carry(HeadFile head, File toFile) throws Exception {
    try (ObjectOutputStream oos =
         new ObjectOutputStream(new FileOutputStream(toFile))) {
      oos.writeObject(head);
    }
  }
}

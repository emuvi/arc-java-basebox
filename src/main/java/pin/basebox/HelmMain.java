package pin.basebox;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import pin.jarbox.ColPanel;
import pin.jarbox.Helm;
import pin.jarbox.HelmEdit;
import pin.jarbox.Icons;
import pin.jarbox.Panel;
import pin.jarbox.PopMenu;
import pin.jarbox.RowPanel;
import pin.jarbox.Utils;

public class HelmMain extends Helm {

  private final JButton newButton = Icons.buttonNew(e -> menuNew());
  private final JButton openButton = Icons.buttonOpen(e -> menuOpen());
  private final JButton saveButton = Icons.buttonSave(e -> menuSave());
  private final RowPanel toolsPanel = new RowPanel(newButton, openButton, saveButton);
  private final DefaultListModel<Connector> connectorsModel = new DefaultListModel<>();
  private final JList<Connector> connectorsList = new JList<>(connectorsModel);
  private final JScrollPane connectorsScroll = new JScrollPane(connectorsList);
  private final JButton insertButton = Icons.buttonInsert(e -> menuInsert());
  private final JButton editButton = Icons.buttonEdit(e -> menuEdit());
  private final JButton deleteButton = Icons.buttonDelete(e -> menuDelete());
  private final JButton cogButton = Icons.buttonCog(e -> menuCog());
  private final PopMenu cogMenu = new PopMenu();
  private final ColPanel connectorsEdits =
      new ColPanel(insertButton, editButton, deleteButton, cogButton);
  private final Panel connectorsPanel =
      new RowPanel().addMax(connectorsScroll).add(connectorsEdits);

  public HelmMain() {
    super(new JFrame("BaseBox"), new ColPanel());
    setExitOnClose();
    setIcon(Icons.get(HelmMain.class, "basebox.png"));
    container.add(toolsPanel).addMax(connectorsPanel);
    initCogMenu();
    pack();
  }

  private void initCogMenu() {
    cogMenu.put("Export to CSV", e -> exportToCSV());
  }

  private void menuNew() {
    if (Utils.question("Do you wanna clear the connectors.")) {
      connectorsModel.clear();
    }
  }

  private void menuOpen() {
  }

  private void menuSave() {
  }

  private void menuInsert() {
    try {
      new HelmEdit<>(Connector.class, null, true, connector -> makeInsert(connector))
          .show();
    } catch (Exception e) {
      Utils.treat(e);
    }
  }

  private Boolean makeInsert(Connector connector) {
    connectorsModel.add(connectorsList.getSelectedIndex() + 1, connector);
    return true;
  }

  private void menuEdit() {
    try {
      Connector selected = connectorsList.getSelectedValue();
      if (selected != null) {
        new HelmEdit<>(Connector.class, selected, false, connector -> makeEdit(connector))
            .show();
      }
    } catch (Exception e) {
      Utils.treat(e);
    }
  }

  private Boolean makeEdit(Connector connector) {
    connectorsList.revalidate();
    return true;
  }

  private void menuDelete() {
    int selected = connectorsList.getSelectedIndex();
    if (selected > -1) {
      connectorsModel.remove(selected);
    }
  }

  private void menuCog() {
    cogMenu.show(cogButton);
  }

  private void exportToCSV() {
    System.out.println("export");
  }

  private void importFromCSV() {
    System.out.println("import");
  }
}

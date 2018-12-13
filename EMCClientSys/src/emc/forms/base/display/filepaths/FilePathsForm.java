/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.filepaths;

import emc.app.components.base.EMCModuleDropDown;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author riaan
 */
public class FilePathsForm extends BaseInternalFrame {

    private emcJPanel pnlFilePaths = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    public FilePathsForm(EMCUserData userData) {
        super("File Paths", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Base/SetupFilePaths.html"));
        try {

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.BaseFilePaths(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("operatingSystem");
            dataRelation.setFormTextId2("filePath");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabFilePaths() {
        List keys = new ArrayList();
//        keys.add("operatingSystem");
        keys.add("formModule");
        keys.add("filePath");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);

//        toptable.setComboBoxLookupToColumn("operatingSystem", new emcJComboBox(OperatingSystems.values()));
        toptable.setComboBoxLookupToColumn("formModule", new EMCModuleDropDown());
        EMCFilePicker filePicker = new EMCFilePicker();
        filePicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        toptable.setFilePickerToColumn("filePath", filePicker);

        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlFilePaths.setLayout(new GridLayout(1, 1));
        pnlFilePaths.add(topscroll);
        dataRelation.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabFilePaths();
        tabbedPanetop.add("File Paths", this.pnlFilePaths);
        this.add(tabbedPanetop);
    }
}

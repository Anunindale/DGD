/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.fileassociations;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.fileassociations.BaseFileAssociations;
import emc.enums.base.filepaths.OperatingSystems;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class FileAssociationsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate drm;

    public FileAssociationsForm(EMCUserData userData) {
        super("File Associations", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        drm = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseFileAssociations(), userData), userData);
        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("operatingSystem");
        drm.setFormTextId2("fileExtension");

        initFrame();
    }

    private emcJPanel createFileAssociationsPanel() {
        emcJPanel pnlFileAssocations = new emcJPanel(new GridLayout(1, 1));

        List keys = new ArrayList();
        keys.add("operatingSystem");
        keys.add("fileExtension");
        keys.add("openWith");

        emcTableModelUpdate m = new emcTableModelUpdate(this.drm, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);

        toptable.setComboBoxLookupToColumn("operatingSystem", new emcJComboBox(OperatingSystems.values()));

        drm.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);

        drm.setTablePanel(topscroll);
        
        pnlFileAssocations.add(topscroll);

        return pnlFileAssocations;
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("File Associations", createFileAssociationsPanel());
        this.add(tabbedPanetop);
    }
}

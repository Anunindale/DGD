/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.versioncontrol;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.developertools.versioningcontrol.DevVersioningControl;
import emc.entity.sop.SOPCustomers;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DevVersioningControlForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;

    public DevVersioningControlForm(EMCUserData userData) {
        super("Versioning Control", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.userData = userData.copyUserDataAndDataList();
        this.dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new DevVersioningControl(), this.userData), this.userData);
        this.dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        this.dataManager.setFormTextId1("customerId");
        this.dataManager.setFormTextId2("repositoryName");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Version Control", versionControlPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);

        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate versionControlPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("customerId");
        keys.add("repositoryName");
        keys.add("repositoryDescription");
        keys.add("repositoryURL");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);


        EMCLookupJTableComponent lkpCutomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCutomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", dataManager.getUserData()));
        table.setLookupToColumn("customerId", lkpCutomer);


        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }
}

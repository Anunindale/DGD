/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.classification2;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.sop.SOPCustomerClassification2;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPCustomerClassification2Form extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public SOPCustomerClassification2Form(EMCUserData userData) {
        super("Customer Classification 2", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.SOP.getId(), new SOPCustomerClassification2(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("classification2");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        contentPane.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("classification2");
        keys.add("description");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }
}

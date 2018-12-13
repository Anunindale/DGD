/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.entitylog;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.developertools.DevEntityLog;
import emc.entity.developertools.DevProjectMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.developertools.DevQueryType;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.developertools.DevProjectsMenu;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class DevEntityLogForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public DevEntityLogForm(EMCUserData userData) {
        super("Entity Log", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 600);
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(), new DevEntityLog(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("customerId");
        dataManager.setFormTextId2("projectName");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane topTabbed = new emcJTabbedPane();
        topTabbed.add("Entity Log", tablePane());
        emcJTabbedPane bottomTabbed = new emcJTabbedPane();
        bottomTabbed.add("Queries", queryPane());
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, topTabbed, bottomTabbed);
        topBottomSplit.setDividerLocation(275);
        contentPane.add(topBottomSplit, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("customerId");
        keys.add("projectName");
        keys.add("logDate");
        keys.add("runBeforeDeploy");
        keys.add("queryType");
        keys.add("ranQuery");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setuptableLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setuptableLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpCutomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCutomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", dataManager.getUserData()));
        table.setLookupToColumn("customerId", lkpCutomer);
        
        EMCLookupJTableComponent lkpProject = new EMCLookupJTableComponent(new DevProjectsMenu());
        lkpProject.setPopup(new EMCLookupPopup(new DevProjectMaster(), "projectName", dataManager.getUserData()));
        table.setLookupToColumn("projectName", lkpProject);

        emcJComboBox cbQueryType = new emcJComboBox(DevQueryType.values());
        table.setComboBoxLookupToColumn("queryType", cbQueryType);
    }

    private emcJScrollPane queryPane() {
        emcJTextArea txaQuery = new emcJTextArea(new EMCStringFormDocument(dataManager, "queryLog"));
        emcJScrollPane textScroll = new emcJScrollPane(txaQuery);
        return textScroll;
    }
}

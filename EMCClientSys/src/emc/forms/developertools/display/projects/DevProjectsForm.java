/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.projects;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.developertools.DevProjectLines;
import emc.entity.developertools.DevProjectMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.developertools.DevLayerType;
import emc.enums.developertools.DevProjectType;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class DevProjectsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate masterDRM;
    private emcDataRelationManagerUpdate linesDRM;

    public DevProjectsForm(EMCUserData userData) {
        super("Projects", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 600);
        //Master
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(), new DevProjectMaster(), userData), userData);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("projectName");
        masterDRM.setFormTextId2("projectDescription");
        //Lines
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(), new DevProjectLines(), userData), userData);
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("projectType");
        linesDRM.setFormTextId2("projectLayer");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("recordID");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("masterRecordID");
        //Form
        initFrame();
    }

    private void initFrame() {
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(275);
        this.setContentPane(topBottomSplit);
    }

    private emcJPanel masterPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Project", masterTablePane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        return thePanel;
    }

    private emcTablePanelUpdate masterTablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("customerId");
        keys.add("projectName");
        keys.add("projectDescription");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        EMCLookupJTableComponent lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", masterDRM.getUserData()));
        table.setLookupToColumn("customerId", lkpCustomer);

        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel linesPane() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", linesTablePane());
        thePanel.add(tabbed, BorderLayout.CENTER);
        return thePanel;
    }

    private emcTablePanelUpdate linesTablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("classpath");
        keys.add("projectType");
        keys.add("projectLayer");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLinesTableLookups(table);
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLinesTableLookups(emcJTableUpdate table) {
        emcJComboBox cbType = new emcJComboBox(DevProjectType.values());
        table.setComboBoxLookupToColumn("projectType", cbType);

        emcJComboBox cbLayer = new emcJComboBox(DevLayerType.values());
        table.setComboBoxLookupToColumn("projectLayer", cbLayer);
    }
}

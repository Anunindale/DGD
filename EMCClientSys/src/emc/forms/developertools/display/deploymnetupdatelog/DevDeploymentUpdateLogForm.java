/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.deploymnetupdatelog;

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
import emc.entity.developertools.DevDeploymentUpdateLogLines;
import emc.entity.developertools.datasources.DevDeploymentUpdateLogDS;
import emc.entity.developertools.installations.DevInstallationMaster;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.developertools.menuitems.display.DevInstallationMI;
import java.awt.BorderLayout;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class DevDeploymentUpdateLogForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate linesDRM;
    private EMCUserData linesUD;

    public DevDeploymentUpdateLogForm(EMCUserData userData) {
        super("Deployment Log", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DEVELOPERTOOLS.getId(), new DevDeploymentUpdateLogDS(), masterUD), masterUD);
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("installationName");
        masterDRM.setFormTextId2("dateUpdated");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new DevDeploymentUpdateLogLines(), linesUD), linesUD);
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("project");
        linesDRM.setFormTextId2("projectDescription");
        //Link'
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("recordID");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("masterRecordID");
        //Form
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(300);
        this.setContentPane(topBottomSplit);
    }

    private emcJPanel masterPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", masterTablePane());

        emcJPanel thePane = new emcJPanel(new BorderLayout());
        thePane.add(tabbed, BorderLayout.CENTER);

        return thePane;
    }

    private emcTablePanelUpdate masterTablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("installationName");
        keys.add("dateUpdated");
        keys.add("subversionHeader");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpInstallation = new EMCLookupJTableComponent(new DevInstallationMI());
        lkpInstallation.setPopup(new EMCLookupPopup(new DevInstallationMaster(), "installationName", masterUD));
        table.setLookupToColumn("installationName", lkpInstallation);
        //Lookups
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel linesPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Projects", linesTablePane());

        emcJPanel thePane = new emcJPanel(new BorderLayout());
        thePane.add(tabbed, BorderLayout.CENTER);

        return thePane;
    }

    private emcTablePanelUpdate linesTablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("project");
        keys.add("projectDescription");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }
}

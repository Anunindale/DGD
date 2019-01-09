/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.installations;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.developertools.installations.DevInstallationLines;
import emc.entity.developertools.installations.DevInstallationMaster;
import emc.enums.developertools.DevInstallationProperties;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author wikus
 */
public class DevInstallationForm extends BaseInternalFrame {

    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData linesUD;
    private emcDataRelationManagerUpdate linesDRM;

    public DevInstallationForm(EMCUserData userData) {
        super("Installations", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 580);

        this.masterUD = userData.copyUserDataAndDataList();
        this.masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new DevInstallationMaster(), masterUD), masterUD);
        this.masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        this.masterDRM.setFormTextId1("installationName");
        this.masterDRM.setFormTextId2("installationDescription");

        this.linesUD = userData.copyUserData();
        this.linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new DevInstallationLines(), linesUD), linesUD);
        this.linesDRM.setTheForm(this);
        this.linesDRM.setFormTextId1("propertyName");
        this.linesDRM.setFormTextId2("propertyValue");

        this.masterDRM.setLinesTable(linesDRM);
        this.masterDRM.setHeaderColumnID("installationName");
        this.linesDRM.setHeaderTable(masterDRM);
        this.linesDRM.setRelationColumnToHeader("installationName");

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterPane(), linesPane());
        topBottomSplit.setDividerLocation(250);
        this.setContentPane(topBottomSplit);
    }

    private emcJPanel masterPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Installations", installationPane());

        emcJPanel thePane = new emcJPanel(new BorderLayout());
        thePane.add(tabbed, BorderLayout.CENTER);

        return thePane;
    }

    private emcTablePanelUpdate installationPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("installationName");
        keys.add("installationDescription");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel linesPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Properties", propertiesPane());

        emcJPanel thePane = new emcJPanel(new BorderLayout());
        thePane.add(tabbed, BorderLayout.CENTER);

        return thePane;
    }

    private emcTablePanelUpdate propertiesPane() {
        List<String> keys = new ArrayList<String>();
        keys.add("propertyName");
        keys.add("propertyValue");
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setComboBoxLookupToColumn("propertyName", new emcJComboBox(DevInstallationProperties.values()));
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }
}

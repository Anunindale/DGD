/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.allocationimportfaillog;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.EMCDataType;
import emc.entity.debtors.DebtorsTransactions;
import emc.entity.debtors.allocationimport.DebtorsAllocationImportFailLog;
import emc.forms.debtors.display.allocationimportfaillog.resources.ImportButton;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsTransactionsMenu;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class DebtorsAllocationImportFailLogForm extends BaseInternalFrame {

    private emcDRMViewOnly drm;

    /** Creates a new instance of DebtorsAllocationImportForm. */
    public DebtorsAllocationImportFailLogForm(EMCUserData userData) {
        super("Allocation Import Fail Log", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 290);

        DebtorsAllocationImportFailLog entity = new DebtorsAllocationImportFailLog();
        EMCDataType dt = entity.getDataType("transReference", userData);
        //There should not be a relation on the server, but we want to allow go to main table on the client.
        dt.setRelatedTable(DebtorsTransactions.class.getName());
        dt.setRelatedField("referenceNumber");

        this.drm = new emcDRMViewOnly(new emcGenericDataSourceUpdate(entity, userData), userData);
        this.drm.setTheForm(this);
        this.setDataManager(drm);
        this.drm.setFormTextId1("importCode");
        this.drm.setFormTextId2("transReference");

        this.setupForm();
    }

    /** Sets up the form. */
    private void setupForm() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Fail Log", createFailLogTab());

        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the fail log tab. */
    private emcJPanel createFailLogTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("importCode");
        keys.add("transReference");
        keys.add("debit");
        keys.add("credit");
        keys.add("balance");
        keys.add("line");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        table.setColumnCellEditor("transReference", new EMCGoToMainTableEditor(new EMCStringDocument(), new DebtorsTransactionsMenu()));
        
        panel.add(tableScroll);

        return panel;
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new ImportButton(drm));

        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }
}

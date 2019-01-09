/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.allocationimport;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.debtors.allocationimport.DebtorsAllocationImport;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.allocationimport.DebtorsAllocationImportPaymentOrder;
import emc.forms.debtors.display.allocationimport.resources.AllocationImportDRM;
import emc.forms.debtors.display.allocationimport.resources.ClearLogButton;
import emc.forms.debtors.display.allocationimport.resources.ImportButton;
import emc.forms.debtors.display.allocationimport.resources.ValidateButton;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsAllocationImportFailLog;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class DebtorsAllocationImportForm extends BaseInternalFrame {

    private AllocationImportDRM drm;
    private EMCLookupJTableComponent lkpCustomer;
    private EMCFilePicker filePicker;

    /** Creates a new instance of DebtorsAllocationImportForm. */
    public DebtorsAllocationImportForm(EMCUserData userData) {
        super("Allocation Import", true, true, true, true, userData);
        this.setBounds(20, 20, 800, 290);

        this.drm = new AllocationImportDRM(new emcGenericDataSourceUpdate(new DebtorsAllocationImport(), userData), userData);
        this.drm.setTheForm(this);
        this.setDataManager(drm);
        this.drm.setFormTextId1("importCode");
        this.drm.setFormTextId2("description");

        this.initComponents(userData);
        this.setupForm();
    }

    /** Initializes components. */
    private void initComponents(EMCUserData userData) {
        this.lkpCustomer = new EMCLookupJTableComponent(new SOPCustomersMenu());
        this.lkpCustomer.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", userData));

        this.filePicker = new EMCFilePicker();
    }

    /** Sets up the form. */
    private void setupForm() {
        emcJTabbedPane tabs = new emcJTabbedPane();

        tabs.add("Import", createImportTab());
        tabs.add("Parameters", createParameterTab());

        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the import tab. */
    private emcJPanel createImportTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();
        keys.add("customerId");
        keys.add("importCode");
        keys.add("description");
        keys.add("importFile");
        keys.add("importDate");
        keys.add("importStatus");

        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);

        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);

        table.setLookupToColumn("customerId", lkpCustomer);
        table.setFilePickerToColumn("importFile", filePicker);

        panel.add(tableScroll);

        return panel;
    }

    /** Creates parameter tab. */
    private emcJPanel createParameterTab() {
        emcYesNoComponent ysnAllowPartial = new emcYesNoComponent(drm, "allowPartialAllocation");
        EMCFormComboBox cmbPaymentOrder = new EMCFormComboBox(DebtorsAllocationImportPaymentOrder.values(), drm, "autoAllocationPaymentOrder");

        Component[][] components = new Component[][]{
            {new emcJLabel(drm.getColumnName("allowPartialAllocation")), ysnAllowPartial},
            {new emcJLabel(drm.getColumnName("autoAllocationPaymentOrder")), cmbPaymentOrder}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    /** Creates buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(new ImportButton(this.drm));
        buttons.add(new ValidateButton(drm));
        buttons.add(new emcMenuButton("View Log", new DebtorsAllocationImportFailLog(), this, 0, false));
        buttons.add(new ClearLogButton(drm));

        return emcSetGridBagConstraints.createButtonPanel(buttons, false);
    }

    /** Reads data from file. */
    public List<String> readData() {
        BufferedReader reader = null;
        try {
            List<String> lines = new ArrayList<String>();

            reader = new BufferedReader(new FileReader((String) drm.getLastFieldValueAt("importFile")));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;
        } catch (Exception ex) {
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex1) {
            }
        }

        return null;
    }
}

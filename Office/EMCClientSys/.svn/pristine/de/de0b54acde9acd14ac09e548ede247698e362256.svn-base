/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.dblog;

import emc.app.components.tables.EMCFieldsMapComboBox;
import emc.app.components.tables.EMCMapComboBoxFactory;
import emc.app.components.tables.EMCTablesMapComboBox;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.dblog.BaseDBLogSetup;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.dblog.resources.RefressButton;
import emc.forms.base.display.dblog.resources.SetupTypesDropDown;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.workflow.menuitems.display.workflowMaster;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DBLogSetupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData userData;
    private EMCFieldsMapComboBox fields;

    public DBLogSetupForm(EMCUserData userData) {
        super("DB Log and Action Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 290);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseDBLogSetup(), userData), userData) {

                @Override
                public void tableRowChanged(int rowIndex) {
                    super.tableRowChanged(rowIndex);
                    if (fields != null) {
                        fields.updateFields((String) super.getFieldValueAt(rowIndex, "tableName"), DBLogSetupForm.this.userData);
                    }
                }

                @Override
                public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                    //Empty field (" ") should be set to null.  checkBlank will trim the String.
                    if ("fieldName".equals(columnIndex) && Functions.checkBlank(aValue)) {
                        aValue = null;
                    }

                    if ("tableName".equals(columnIndex)) {
                        //Reload table.
                        if (fields != null) {
                            fields.updateFields((String)aValue, DBLogSetupForm.this.userData);
                        }
                    }

                    super.setFieldValueAt(rowIndex, columnIndex, aValue);
                }
            };
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("tableName");
            dataRelation.setFormTextId2("type");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        List<emcJButton> buttList = new ArrayList<emcJButton>();
        buttList.add(new RefressButton(userData));
        emcJPanel buttonPanel = emcSetGridBagConstraints.createButtonPanel(buttList);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Setup", setupTab());
        thePanel.add(tabbed, BorderLayout.CENTER);
        thePanel.add(buttonPanel, BorderLayout.EAST);
        this.add(thePanel);
    }

    private emcJPanel setupTab() {
        List keys = new ArrayList();
        keys.add("tableName");
        keys.add("type");
        keys.add("fieldName");
        keys.add("actionWorkFlow");
        keys.add("actionValue");
        
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        //Lookups
        fields = new EMCFieldsMapComboBox(true);
        EMCTablesMapComboBox tables = EMCMapComboBoxFactory.getTablesMapComboBox(userData);
        table.setComboBoxLookupToColumn(m.getColumnByFieldName("fieldName"), fields);
        table.setComboBoxLookupToColumn(m.getColumnByFieldName("tableName"), tables);
        table.setComboBoxLookupToColumn("type", new SetupTypesDropDown());

        EMCLookupJTableComponent lkpWorkFlow = new EMCLookupJTableComponent(new workflowMaster());
        lkpWorkFlow.setPopup(new EMCLookupPopup(new WorkFlowMaster(), "workFlowId", userData));
        table.setLookupToColumn("actionWorkFlow", lkpWorkFlow);
        //Lookups
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }
}

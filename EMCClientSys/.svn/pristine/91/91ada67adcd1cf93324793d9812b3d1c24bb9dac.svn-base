/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.dblog;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.dblog.BaseDBLog;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.dblog.resources.DBLogViewInfoDialog;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class DBLogViewForm extends BaseInternalFrame {

    private emcDRMViewOnly dataRelation;

    public DBLogViewForm(EMCUserData userData) {
        super("DB Log", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 400);
        try {
            dataRelation = new emcDRMViewOnly(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseDBLog(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("tableLabel");
            dataRelation.setFormTextId2("type");
        } catch (Exception e) {

        }
        initFrame();
    }

    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Log", logPane());
        thePanel.add(tabbed);
        this.add(thePanel, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    private emcJPanel logPane() {
        List keys = new ArrayList();
        keys.add("tableLabel");
        keys.add("uniqueIdentifier");
        keys.add("type");
        keys.add("userName");
        keys.add("createdDate");
        keys.add("createdTime");
        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

        };
        emcJTableUpdate table = new emcJTableUpdate(m);
        //Lookups
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        emcJPanel thePanel = new emcJPanel(new GridLayout());
        thePanel.add(tableScroll);
        return thePanel;
    }

    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttons = new ArrayList<emcJButton>();

        buttons.add(new emcJButton("View Detail") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);

                new DBLogViewInfoDialog((String)dataRelation.getLastFieldValueAt("tableLabel"),
                        (String)dataRelation.getLastFieldValueAt("tableName"),
                        (String)dataRelation.getLastFieldValueAt("uniqueIdentifier"),
                        (String)dataRelation.getLastFieldValueAt("oldValue"),
                        (String)dataRelation.getLastFieldValueAt("newValue"), getUserData());
            }
        });

        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
}

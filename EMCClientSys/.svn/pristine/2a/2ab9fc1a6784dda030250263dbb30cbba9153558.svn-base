/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.terminationlog;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJPanel;
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
import emc.entity.base.BaseEmployeeTable;
import emc.entity.hr.HRTerminationType;
import emc.entity.hr.datasource.HRTerminationLogDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import emc.menus.hr.menuitems.display.HRTerminationTypeMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRTerminationLogForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData userData;

    public HRTerminationLogForm(EMCUserData userData) {
        super("Termination Log", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRTerminationLogDS(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeName");
            dataRelation.setFormTextId2("terminationType");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Termination Log", tablePane());
        tabbed.add("Comments", commentPane());
        this.add(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("employeeNumber");
        keys.add("employeeName");
        keys.add("terminationType");
        keys.add("terminationDate");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        table.setColumnEditable("employeeName", false);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupPopup empPop = new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData);
        EMCLookupJTableComponent lkpEmp = new EMCLookupJTableComponent(new employees());
        lkpEmp.setPopup(empPop);
        table.setLookupToColumn("employeeNumber", lkpEmp);

        EMCLookupPopup typePop = new EMCLookupPopup(new HRTerminationType(), "terminationType", userData);
        EMCLookupJTableComponent lkpType = new EMCLookupJTableComponent(new HRTerminationTypeMenu());
        lkpType.setPopup(typePop);
        table.setLookupToColumn("terminationType", lkpType);
    }

    private emcJPanel commentPane() {
        emcJTextArea txaComment = new emcJTextArea(new EMCStringFormDocument(dataRelation, "comment"));
        emcJPanel thePanel = new emcJPanel(new BorderLayout());
        thePanel.add(txaComment, BorderLayout.CENTER);
        return thePanel;
    }
}

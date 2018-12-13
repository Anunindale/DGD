/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.hr.display.previousemploymenthistory;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.hr.HREmployeeControllLookupPanel;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.hr.HRPreviousEmploymentHistory;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class HRPreviousEmploymentHistoryForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HRPreviousEmploymentHistoryForm(EMCUserData userData) {
        super("Previous Employment History", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 320);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.HR.getId(), new HRPreviousEmploymentHistory(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("company");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(new HREmployeeControllLookupPanel(HRPreviousEmploymentHistory.class.getName(), "employeeNumber", dataRelation, userData), BorderLayout.NORTH);
        contentPane.add(tabbedPane(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJTabbedPane tabbedPane() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Previous Employment History", tablePane());
        return tabbed;
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("company");
        keys.add("position");
        keys.add("fromDate");
        keys.add("toDate");
        keys.add("reasonForLeaving");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.crm.display.interestgroup;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.crm.CRMInterestGroup;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.workflow.menuitems.display.workflowMaster;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class CRMInterestGroupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public CRMInterestGroupForm(EMCUserData userData) {
        super("Interest Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.CRM.getId(), new CRMInterestGroup(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("interestGroupId");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Interest Groups", tablePane());
        this.add(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("interestGroupId");
        keys.add("description");
        keys.add("workFlowId");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //lookups
        EMCLookupJTableComponent lkpWorkFlow = new EMCLookupJTableComponent(new workflowMaster());
        lkpWorkFlow.setPopup(new EMCLookupPopup(new WorkFlowMaster(), "workFlowId", dataRelation.getUserData()));
        table.setLookupToColumn("workFlowId", lkpWorkFlow);

        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}


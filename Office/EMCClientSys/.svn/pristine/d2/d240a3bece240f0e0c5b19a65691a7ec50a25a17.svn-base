/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.crm.display.interest;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.crm.CRMInterest;
import emc.entity.crm.CRMInterestGroup;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.crm.menuitems.display.CRMInterestGroupMI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CRMInterestForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public CRMInterestForm(EMCUserData userData) {
        super("Interest", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.CRM.getId(), new CRMInterest(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("interest");
            dataRelation.setFormTextId2("description");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Interest", tablePane());
        this.add(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("interest");
        keys.add("description");
        keys.add("interestGroup");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //lookups
        EMCLookupJTableComponent lkpInterestGroup = new EMCLookupJTableComponent(new CRMInterestGroupMI());
        lkpInterestGroup.setPopup(new EMCLookupPopup(new CRMInterestGroup(), "interestGroupId", dataRelation.getUserData()));
        table.setLookupToColumn("interestGroup", lkpInterestGroup);

        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}

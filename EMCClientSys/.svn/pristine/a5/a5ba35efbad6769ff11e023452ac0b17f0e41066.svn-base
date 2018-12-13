/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.crm.display.parameters;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.editors.EMCComboBoxCellEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.crm.CRMParameters;
import emc.entity.workflow.WorkFlowMaster;
import emc.enums.crm.CRMProspectActRule;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.workflow.menuitems.display.workflowMaster;
import java.awt.Component;
import java.awt.GridBagConstraints;

/**
 *
 * @author wikus
 */
public class CRMParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public CRMParametersForm(EMCUserData userData) {
        super("Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 350);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.CRM.getId(), new CRMParameters(), userData), userData);
            dataManager.setDummyForm(this);
            this.setDataManager(dataManager);
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Parameters", formPathPane());
        this.setContentPane(tabbed);
    }

    private emcJPanel formPathPane() {
        EMCLookupFormComponent lkpWorkFlow = new EMCLookupFormComponent(new workflowMaster(), dataManager, "prospectsWorkFlowId");
        lkpWorkFlow.setPopup(new EMCLookupPopup(new WorkFlowMaster(), "workFlowId", userData));
        EMCFormComboBox cmbProspectActRule = new EMCFormComboBox(CRMProspectActRule.values(), dataManager, "prospectActRule");
        cmbProspectActRule.addBlank();
        Component[][] comp = {{new emcJLabel("Prospect Work Flow"), lkpWorkFlow},
        {new emcJLabel("Assign Prospect to Employee Rule"),cmbProspectActRule}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Parameters");
    }
}



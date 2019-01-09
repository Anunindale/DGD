/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.creditors.display.creditorsparameters;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.creditors.CreditorsParameters;
import emc.enums.modules.*;
import emc.framework.EMCUserData;

/**
 *
 * @author claudette
 */
public class CreditorsParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public CreditorsParametersForm(EMCUserData userData) {

        super("Parameters", true, true, true, true, userData);
        //this.setHelpFile(new emcHelpFile("PMM/CreditorsParametersForm.html"));
        this.setBounds(20, 20, 550, 350);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(
                new emcGenericDataSourceUpdate(enumEMCModules.CREDITORS.getId(), new CreditorsParameters(), userData), userData);
        dataManager.setDummyForm(this);
        this.setDataManager(dataManager);

        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        this.setContentPane(tabbed);
    }
}

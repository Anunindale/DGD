/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.resources;

import emc.app.components.emctable.DataRelationManagerInterface;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class ApprovalButton extends emcMenuButtonList {

    private DataRelationManagerInterface DRMInt;
    private EMCUserData userData;

    public ApprovalButton(BaseInternalFrame form) {
        super("Approval", form);
        DRMInt = (DataRelationManagerInterface) form.getDataManager();
        userData = form.getUserData();
        this.addMenuItem("Approve", null, -1005, false);
        this.addMenuItem("Unapprove", null, -1006, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        int formIndex = 0;
        if (theCmd.equals("Approve")) {
            formIndex = -1005;
        } else if (theCmd.equals("Unapprove")) {
            formIndex = -1006;
        }
        DRMInt.generateRelatedFormUserData(userData, formIndex);
    }
}

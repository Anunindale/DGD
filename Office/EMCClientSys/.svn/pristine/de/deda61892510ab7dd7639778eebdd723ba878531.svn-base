/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactions.resources;

import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.action.DebtorsTransactionSettlement;
import java.awt.event.ActionEvent;

/**
 * @description : This button is used to open the Transaction Settlement form.
 *
 * @date        : 08 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class TransSettlementButton extends emcMenuButton {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of TransSettlementButton */
    public TransSettlementButton(BaseInternalFrame frame, emcDataRelationManagerUpdate drm) {
        super("Allocation", null, frame, 0, false);
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        EMCUserData userData = drm.getUserData().copyUserData();

        //Used in settlement form constructor.
        userData.setUserData(2, drm.getLastFieldValueAt("customerId"));

        if (drm.getTheForm() != null) {
            drm.getTheForm().getDeskTop().createAndAdd(new DebtorsTransactionSettlement(), -1, -1, userData, null, 1);
        }
    }
}

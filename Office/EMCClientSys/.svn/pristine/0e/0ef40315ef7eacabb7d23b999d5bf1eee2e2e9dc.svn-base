/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.customers.resources;

import emc.app.components.emcMenuButton;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.action.DebtorsTransactionSettlement;
import java.awt.event.ActionEvent;

/**
 * @description : This button is used to open the Debtors transaction settlement form.
 *
 * @date        : 11 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class TransSettlementButton extends emcMenuButton {

    private CustomerDRM customerDRM;

    /** Creates a new instance of TransSettlementButton */
    public TransSettlementButton(BaseInternalFrame frame, CustomerDRM customerDRM, int relatedFormIndex) {
        super("Allocate", null, frame, relatedFormIndex, false);
        this.customerDRM = customerDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        EMCUserData userData = customerDRM.getUserData().copyUserData();

        //Used in settlement form constructor.
        userData.setUserData(2, customerDRM.getLastFieldValueAt("customerId"));

        if (customerDRM.getTheForm() != null) {
            customerDRM.getTheForm().getDeskTop().createAndAdd(new DebtorsTransactionSettlement(), -1, -1, userData, null, 1);
        }
    }
}

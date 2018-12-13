/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.sop.display.customers.resources;

import emc.app.components.emcJButton;
import emc.framework.EMCUserData;
import emc.menus.sop.menuitems.display.CustomerActivityMenu;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description : This button is used to open up the Customer Activity Form.
 *
 * @date        : 07 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ActivityButton extends emcJButton {

    private CustomerDRM customerDRM;

    /** Creates a new instance of ActivityButton */
    public ActivityButton(CustomerDRM customerDRM) {
        super("Activity");
        this.customerDRM = customerDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        long recordID = (Long)this.customerDRM.getLastFieldValueAt("recordID");
        
        if (recordID == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please save customer before trying to view customer activity.");
            return;
        }

        String customerId = (String)this.customerDRM.getLastFieldValueAt("customerId");

        EMCUserData activityUserData = customerDRM.getUserData().copyUserData();
        activityUserData.setUserData(3, customerId);

        CustomerActivityMenu activityMenu = new CustomerActivityMenu();
        activityMenu.setDoNotOpenForm(false);

        this.customerDRM.getTheForm().getDeskTop().createAndAdd(activityMenu, -1, -1, activityUserData, null, 1);
    }
}

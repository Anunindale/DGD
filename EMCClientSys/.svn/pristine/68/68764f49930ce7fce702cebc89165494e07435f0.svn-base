/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.transactionsettlement.resources;

import emc.app.components.emcJButton;
import emc.app.wsmanager.EMCWSManager;
import emc.forms.debtors.display.transactionsettlement.TransactionSettlementForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description : This button is used to allocate settlements.
 *
 * @date        : 15 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class AllocateButton extends emcJButton {

    private TransactionSettlementForm form;

    /** Creates a new instance of AllocateButton */
    public AllocateButton(TransactionSettlementForm form) {
        super("Allocate");
        this.form = form;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        EMCUserData userData = form.getUserData();
        
        EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.ALLOCATE_SETTLEMENT);

        List toSend = new ArrayList();
        toSend.add(form.getSessionId());
        toSend.add(form.getCustomerId());

        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);

        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean)toSend.get(1)) {
            Logger.getLogger("emc").log(Level.INFO, "Successfully allocated.", userData);

            form.loadData(form.getCustomerId(), null, userData);
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to allocate settlement.", userData);
        }
    }
}

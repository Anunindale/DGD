/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.debtors.menuitems.display.DebtorsCreditNotes;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class CreditNoteButton extends emcJButton {

    private CustomerInvoiceMasterDRM masterDRM;

    /** Creates a new instance of CreditNoteButton. */
    public CreditNoteButton(CustomerInvoiceMasterDRM masterDRM) {
        super("Credit Note");
        this.masterDRM = masterDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent e) {
        super.doActionPerformed(e);

        if (EMCDialogFactory.createQuestionDialog(masterDRM.getTheForm(), "Create Credit Note", "Are you sure that you wish to create a Credit Note against the selected Invoice?") == JOptionPane.YES_OPTION) {
            boolean useStock = false;

            //Only allow stock to be returned if the original invoice used stock.
            if ((Boolean) masterDRM.getLastFieldValueAt("invoiceStock")) {
                int option = EMCDialogFactory.createQuestionDialog(masterDRM.getTheForm(), "Use Stock?", "Do you want to create a stock Credit Note?");

                if (option == JOptionPane.CLOSED_OPTION) {
                    Logger.getLogger("emc").log(Level.WARNING, "Credit Note not created.");
                    return;
                } else if (option == JOptionPane.YES_OPTION) {
                    useStock = true;
                }
            }

            EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.CREATE_CREDIT_NOTE);

            List toSend = new ArrayList();
            toSend.add(masterDRM.getLastFieldValueAt("invCNNumber"));
            toSend.add(useStock);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterDRM.getUserData());

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof DebtorsCreditNoteMaster) {
                DebtorsCreditNoteMaster creditNote = (DebtorsCreditNoteMaster) toSend.get(1);
                
                Logger.getLogger("emc").log(Level.INFO, "Succesfully created Credit Note.");
                masterDRM.refreshRecordIgnoreFocus(masterDRM.getLastRowAccessed());

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
                query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), EMCQueryConditions.NOT);
                query.addAnd("status", DebtorsInvoiceStatus.CANCELED.toString(), EMCQueryConditions.NOT);
                query.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION.toString(), EMCQueryConditions.NOT);
                query.addAnd("invCNNumber", creditNote.getInvCNNumber());
                
                EMCUserData relatedUD = masterDRM.getUserData().copyUserData();
                relatedUD.setUserData(0, query);

                masterDRM.getTheForm().getDeskTop().createAndAdd(new DebtorsCreditNotes(), -1, -1, relatedUD, masterDRM, 1);
            } else {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Credit Note.");
                //Do not open related form, exit here.
                return;
            }
        }
    }
}

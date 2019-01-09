/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.event.ActionEvent;

/**
 *
 * @author riaan
 */
public class TotalsButton extends emcJButton {

    private emcDataRelationManagerUpdate drm;
    private boolean invoice;

    /** Creates a new instance of TotalsButton. */
    public TotalsButton(emcDataRelationManagerUpdate drm, boolean invoice) {
        super("Totals");
        this.drm = drm;
        this.invoice = invoice;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        new InvoiceCreditNoteTotalDialog((String) drm.getLastFieldValueAt("invCNNumber"), invoice, drm.getUserData());
    }
}

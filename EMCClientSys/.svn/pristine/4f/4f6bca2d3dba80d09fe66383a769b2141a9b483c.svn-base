/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.postdatedpayment.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.event.ActionEvent;

/**
 * @description : This button is used to process payments in a given date range.
 *
 * @date        : 19 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ProcessButton extends emcJButton {

    private emcDataRelationManagerUpdate drm;

    /** Creates a new instance of ProcessButton */
    public ProcessButton(emcDataRelationManagerUpdate drm) {
        super("Process");

        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);

        new ProcessPaymentDialog(drm).setVisible(true);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.gl.display.journal.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.event.ActionEvent;

/**
 *
 * @author riaan
 */
public class TotalsButton extends emcJButton {

    private emcDataRelationManagerUpdate drm;

    /**
     * Creates a new instance of TotalsButton.
     */
    public TotalsButton(emcDataRelationManagerUpdate drm) {
        super("Totals");
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        new TotalsDialog((String)drm.getLastFieldValueAt("journalNumber"), drm.getUserData()).setVisible(true);
    }
}

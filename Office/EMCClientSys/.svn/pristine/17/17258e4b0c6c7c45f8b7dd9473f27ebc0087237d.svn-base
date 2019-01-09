/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.resources;

import emc.app.components.emcMenuButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import java.awt.event.ActionEvent;

/**
 *
 * @author rico
 */
public class ActiveJRDisplay extends emcMenuButton {

    private boolean all = true;
    private String label;
    private emcDataRelationManagerUpdate drm;

    public ActiveJRDisplay(String label, emcDataRelationManagerUpdate drm) {
        super(label);
        this.label = label;
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        if (all) {
            all = false;
            this.setText("All Journals");
            //create query
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.journals.InventoryJournalMaster.class.getName());
            qu.addAnd("journalStatus", emc.enums.base.journals.JournalStatus.POSTED, EMCQueryConditions.NOT);
            qu.addAnd("companyId", drm.getUserData().getCompanyId());
            qu.addOrderBy("journalNumber");
            //set query
            drm.getUserData().setUserData(0, qu.toString());
            drm.getUserData().setUserData(1, qu.getCountQuery());
            drm.setUserData(drm.getUserData());

        } else {
            all = true;
            this.setText(label);
            //create query
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.journals.InventoryJournalMaster.class.getName());
            qu.addAnd("companyId", drm.getUserData().getCompanyId());
            qu.addOrderBy("journalNumber");
            //set query
            drm.getUserData().setUserData(0, qu.toString());
            drm.getUserData().setUserData(1, qu.getCountQuery());
            drm.setUserData(drm.getUserData());
        }
    }
}

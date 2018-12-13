/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders.resources;

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
public class ActivePODisplay extends emcMenuButton {

    private boolean all = true;
    private String label;
    private emcDataRelationManagerUpdate drm;

    public ActivePODisplay(String label, emcDataRelationManagerUpdate drm) {
        super(label);
        this.label = label;
        this.drm = drm;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        if (all) {
            all = false;
            this.setText("All PO's");
            //create query
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addAnd("status", emc.enums.pop.purchaseorders.PurchaseOrderStatus.RECEIVED, EMCQueryConditions.NOT);
            qu.addAnd("companyId", drm.getUserData().getCompanyId());
            qu.addOrderBy("purchaseOrderId");
            //set query
            drm.getUserData().setUserData(0, qu.toString());
            drm.getUserData().setUserData(1, qu.getCountQuery());
            drm.setUserData(drm.getUserData());

        } else {
            all = true;
            this.setText(label);
            //create query
            EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, emc.entity.pop.POPPurchaseOrderMaster.class.getName());
            qu.addAnd("companyId", drm.getUserData().getCompanyId());
            qu.addOrderBy("purchaseOrderId");
            //set query
            drm.getUserData().setUserData(0, qu.toString());
            drm.getUserData().setUserData(1, qu.getCountQuery());
            drm.setUserData(drm.getUserData());
        }
    }
}

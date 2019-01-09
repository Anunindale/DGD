/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricearangement.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.queryswitchbutton.EMCQuerySwitchButton;
import emc.entity.sop.SOPPriceArangements;
import emc.enums.emcquery.EMCQueryConditions;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class PriceArangementQuerySwitchButton extends EMCQuerySwitchButton {

    public PriceArangementQuerySwitchButton(emcDataRelationManagerUpdate drm) {
        super("Active", drm);
        this.addQuery("Active", SOPPriceArangements.class.getName(), "toDate", EMCQueryConditions.GREATER_THAN_EQ, Functions.nowDate());
        this.addQuery("All", SOPPriceArangements.class.getName(), "toDate", EMCQueryConditions.GREATER_THAN_EQ, null);
    }
}

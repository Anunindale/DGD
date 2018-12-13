/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.pop.posting;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.pop.posting.PostingQuantities;


/**
 *
 * @author riaan
 */
public class QuantitySelectionDropdown extends EMCFormComboBox {

    /** Creates a new instance of QuantitySelectionDropdown. */
    public QuantitySelectionDropdown(emcDataRelationManagerUpdate dataRelation, String columnIndex) {
        super(new String[] {PostingQuantities.ALL.toString(), /*PostingQuantities.DELIVERY_NOTE.toString(),*/ PostingQuantities.RECEIVED.toString()}, dataRelation, columnIndex);
    }
}

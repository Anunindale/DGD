/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.numbersequences.foreignkeys;

import emc.datatypes.base.numbersequences.NumberSequenceId;
import emc.entity.base.numbersequences.BaseNumberSequence;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author riaan
 */
public class NumberSequenceIdFK extends NumberSequenceId {

    /** Creates a new instance of NumberSequenceIdFK */
    public NumberSequenceIdFK() {
        this.setRelatedField("numberSequenceId");
        this.setRelatedTable(BaseNumberSequence.class.getName());
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
        this.setUpdateAction(enumDeleteUpdateOptions.CASCADE);
    }
}

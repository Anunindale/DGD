/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.trec.phrases;

import emc.datatypes.EMCInt;

/**
 *
 * @author wikus
 */
public class SortNumber extends EMCInt {

    public SortNumber() {
        this.setEmcLabel("Sort");
        this.setMandatory(true);
        this.setColumnWidth(15);
    }

}

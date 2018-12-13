/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.creditrating;

import emc.datatypes.EMCDouble;

/**
 * @description : Data type for tolerancePercentage on DebtorsCreditRating.
 *
 * @date        : 28 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class TolerancePercentage extends EMCDouble {

    /** Creates a new instance of TolerancePercentage */
    public TolerancePercentage() {
        this.setEmcLabel("Tolerance %");
    }
}

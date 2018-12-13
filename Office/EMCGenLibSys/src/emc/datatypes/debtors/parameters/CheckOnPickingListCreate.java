/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.debtors.parameters;

import emc.datatypes.EMCBoolean;

/**
 * @description : Data type for checkOnPickingListCreate on DebtorsParameters.
 *
 * @date        : 28 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CheckOnPickingListCreate extends EMCBoolean {

    /** Creates a new instance of CheckOnPickingListCreate */
    public CheckOnPickingListCreate() {
        this.setEmcLabel("Check on Picking List Create");
    }
}

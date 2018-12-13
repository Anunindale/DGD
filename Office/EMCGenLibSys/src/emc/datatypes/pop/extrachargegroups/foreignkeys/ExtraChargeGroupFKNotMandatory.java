/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.pop.extrachargegroups.foreignkeys;

/**
 * @description : Not mandatory data type.
 *
 * @date        : 28 Apr 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class ExtraChargeGroupFKNotMandatory extends ExtraChargeGroupFK {

    /** Creates a new instance of ExtraChargeGroupFKNotMandatory. */
    public ExtraChargeGroupFKNotMandatory() {
        this.setMandatory(false);
    }
}

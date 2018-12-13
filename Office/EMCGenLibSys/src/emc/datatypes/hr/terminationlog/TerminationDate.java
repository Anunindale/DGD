/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.hr.terminationlog;

import emc.datatypes.EMCDate;

/**
 *
 * @author wikus
 */
public class TerminationDate extends EMCDate{

    public TerminationDate() {
         this.setEmcLabel("Termination Date");
         this.setMandatory(true);
    }



}

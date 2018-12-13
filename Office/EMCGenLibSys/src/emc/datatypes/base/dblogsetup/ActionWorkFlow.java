/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.dblogsetup;

import emc.datatypes.workflow.master.foreignkeys.WorkFlowIdFKNM;

/**
 *
 * @author wikus
 */
public class ActionWorkFlow extends WorkFlowIdFKNM {

    public ActionWorkFlow() {
        this.setEmcLabel("Work Flow");
    }
}

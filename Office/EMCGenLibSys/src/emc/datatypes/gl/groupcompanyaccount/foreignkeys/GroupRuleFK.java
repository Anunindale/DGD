/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.groupcompanyaccount.foreignkeys;

import emc.datatypes.gl.groupcompanyaccount.GroupRule;
import emc.entity.gl.rules.GLGroupCompanyAccount;

/**
 *
 * @author claudette
 */
public class GroupRuleFK extends GroupRule {

    public GroupRuleFK() {
        this.setRelatedTable(GLGroupCompanyAccount.class.getName());
        this.setRelatedField("groupRule");
    }
}

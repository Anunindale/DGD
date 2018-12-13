/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.grouprules.foreignkeys;

import emc.datatypes.gl.grouprules.GroupRules;
import emc.entity.gl.rules.GLGroupRules;

/**
 *
 * @author claudette
 */
public class GroupRulesFK extends GroupRules {

    public GroupRulesFK() {
        this.setRelatedTable(GLGroupRules.class.getName());
        this.setRelatedField("groupRules");
    }
}

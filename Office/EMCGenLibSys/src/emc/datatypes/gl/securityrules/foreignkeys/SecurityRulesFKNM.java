/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.securityrules.foreignkeys;

import emc.datatypes.gl.securityrules.SecurityRules;
import emc.entity.gl.rules.GLSecurityRules;

/**
 *
 * @author wikus
 */
public class SecurityRulesFKNM extends SecurityRules {

    public SecurityRulesFKNM() {
        this.setRelatedTable(GLSecurityRules.class.getName());
        this.setRelatedField("ruleId");
        this.setMandatory(false);
    }
}

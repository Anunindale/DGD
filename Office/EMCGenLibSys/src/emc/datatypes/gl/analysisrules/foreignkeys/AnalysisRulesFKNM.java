/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.analysisrules.foreignkeys;

import emc.datatypes.gl.securityrules.SecurityRules;
import emc.entity.gl.rules.GLAnalysisRules;

/**
 *
 * @author wikus
 */
public class AnalysisRulesFKNM extends SecurityRules {

    public AnalysisRulesFKNM() {
        this.setRelatedTable(GLAnalysisRules.class.getName());
        this.setRelatedField("ruleId");
        this.setMandatory(false);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.budgetlines;

import emc.datatypes.datasources.DSRelation;
import emc.entity.gl.GLBudgetLines;
import emc.entity.gl.GLChartOfAccounts;

/**
 *
 * @author claudette
 */
public class DescriptionDS extends emc.datatypes.systemwide.Description {

    public DescriptionDS() {
        DSRelation relation = new DSRelation();
        relation.setSourceTable(GLChartOfAccounts.class.getName());
        relation.setRelatedTable(GLBudgetLines.class.getName());
        relation.setRelatedField("description");
        relation.setPKField("accountNumber");
        relation.setFKField("account");
        this.setEditable(false);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.budgetlines.foreignkeys;

import emc.datatypes.gl.budgetlines.LineNumber;
import emc.entity.gl.GLBudgetLines;

/**
 *
 * @author claudette
 */
public class LineNumberFK extends LineNumber {

    public LineNumberFK() {
        this.setRelatedTable(GLBudgetLines.class.getName());
        this.setRelatedField("lineNumber");
    }
}

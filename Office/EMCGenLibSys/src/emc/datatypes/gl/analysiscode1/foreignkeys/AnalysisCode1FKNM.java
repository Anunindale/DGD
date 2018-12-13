/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.analysiscode1.foreignkeys;

import emc.datatypes.gl.analysiscode1.AnalysisCode1;
import emc.entity.gl.analysiscodes.GLAnalysisCode1;

/**
 *
 * @author wikus
 */
public class AnalysisCode1FKNM extends AnalysisCode1 {

    public AnalysisCode1FKNM() {
        this.setRelatedTable(GLAnalysisCode1.class.getName());
        this.setRelatedField("analysisCode");
        this.setMandatory(false);
    }
}

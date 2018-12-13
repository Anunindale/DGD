/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.analysiscode4.foreignkeys;

import emc.datatypes.gl.analysiscode4.AnalysisCode4;
import emc.entity.gl.analysiscodes.GLAnalysisCode4;

/**
 *
 * @author wikus
 */
public class AnalysisCode4FKNM extends AnalysisCode4 {

    public AnalysisCode4FKNM() {
        this.setRelatedTable(GLAnalysisCode4.class.getName());
        this.setRelatedField("analysisCode");
        this.setMandatory(false);
    }
}

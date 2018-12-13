/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.gl;

import emc.entity.gl.analysiscodes.GLAnalysisCode1;
import emc.entity.gl.analysiscodes.GLAnalysisCode2;
import emc.entity.gl.analysiscodes.GLAnalysisCode3;
import emc.entity.gl.analysiscodes.GLAnalysisCode4;
import emc.entity.gl.analysiscodes.GLAnalysisCode5;
import emc.entity.gl.analysiscodes.GLAnalysisCode6;

/**
 *
 * @author claudette
 */
public enum GLAnalysisCode {

    ANALYSIS_CODE1(0, "ANALYSIS CODE 1", GLAnalysisCode1.class.getName()),
    ANALYSIS_CODE2(0, "ANALYSIS CODE 2", GLAnalysisCode2.class.getName()),
    ANALYSIS_CODE3(0, "ANALYSIS CODE 3", GLAnalysisCode3.class.getName()),
    ANALYSIS_CODE4(0, "ANALYSIS CODE 4", GLAnalysisCode4.class.getName()),
    ANALYSIS_CODE5(0, "ANALYSIS CODE 5", GLAnalysisCode5.class.getName()),
    ANALYSIS_CODE6(0, "ANALYSIS CODE 6", GLAnalysisCode6.class.getName());
    
    //Enum variables
    final int id;
    final String label;
    final String entityClassName;

    /** Creates a new instance of ItemClassifications */
    GLAnalysisCode(final int id, final String label, final String entityClassName) {
        this.id = id;
        this.label = label;
        this.entityClassName = entityClassName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getEntityClassName() {
        return this.entityClassName;
    }

    public static GLAnalysisCode fromString(final String str) {
        for (GLAnalysisCode e : GLAnalysisCode.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static GLAnalysisCode fromId(final int id) {
        for (GLAnalysisCode e : GLAnalysisCode.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

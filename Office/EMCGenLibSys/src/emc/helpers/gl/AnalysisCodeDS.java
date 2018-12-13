/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.gl;

import emc.functions.Functions;

/**
 *
 * @author claudette
 */
public class AnalysisCodeDS {

    private long recordId;
    private String analysisEntityClassName;
    private String analysis;
    private String description;

    public AnalysisCodeDS() {
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getAnalysisEntityClassName() {
        return analysisEntityClassName;
    }

    public void setAnalysisEntityClassName(String analysisEntityClassName) {
        this.analysisEntityClassName = analysisEntityClassName;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** Overridden for convenience, return classification + description. */
    @Override
    public String toString() {
        return getAnalysis() + (Functions.checkBlank(getDescription()) || getDescription().equals("null") ? "" : " : " + getDescription());
    }
}

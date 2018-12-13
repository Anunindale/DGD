/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.wf.common;

/**
 *
 * @author rico
 */
public class EvaluationTree {

    private String description;
    private Double lineNo;
    private Double nextLineNo;
    private String primaryIndicator;
    private Double duration;

    public EvaluationTree() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLineNo() {
        return lineNo;
    }

    public void setLineNo(Double lineNo) {
        this.lineNo = lineNo;
    }

    public Double getNextLineNo() {
        return nextLineNo;
    }

    public void setNextLineNo(Double nextLineNo) {
        this.nextLineNo = nextLineNo;
    }

    @Override
    public String toString() {
        if (this.lineNo > 0) {
            return getPrimaryIndicator() + ": (" + getLineNo().toString() + ") " + this.getDescription() + " (" + this.getNextLineNo().toString() + ")";
        } else {
            return this.getDescription();
        }
    }

    public String getPrimaryIndicator() {
        return primaryIndicator;
    }

    public void setPrimaryIndicator(String primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }
}

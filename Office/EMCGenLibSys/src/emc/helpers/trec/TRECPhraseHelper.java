/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.trec;

/**
 *
 * @author stu
 */
public class TRECPhraseHelper {

    private String phraseId;
    private String phraseDescription;
    private boolean ticked;
    private boolean mandatory;
    
    public TRECPhraseHelper(){
        
    }

    public String getPhraseDescription() {
        return phraseDescription;
    }

    public void setPhraseDescription(String phraseDescription) {
        this.phraseDescription = phraseDescription;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(String phraseId) {
        this.phraseId = phraseId;
    }   
}

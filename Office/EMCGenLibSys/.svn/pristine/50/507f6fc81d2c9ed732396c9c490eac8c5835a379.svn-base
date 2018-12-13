/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.trec.phrases.ERGNumber;
import emc.datatypes.trec.phrases.ParentClass;
import emc.datatypes.trec.phrases.Phrase;
import emc.datatypes.trec.phrases.PhraseId;
import emc.datatypes.trec.phrases.SortNumber;
import emc.datatypes.trec.phrases.StandardPhrases;
import emc.datatypes.trec.phrases.AddedPhrases;
import emc.datatypes.trec.phrases.ClassSpecific;
import emc.datatypes.trec.phrasetypes.foreignkey.PhraseTypeFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECPhrases", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"phraseId", "companyId"})})
public class TRECPhrases extends EMCEntityClass {

    private String phraseId;
    private String parentClass;
    private int sortNumber;
    @Column(length = 1000)
    private String phrase;
    private String typeId;
    @Column(length = 1000)
    private String ergNumber;
    private boolean standardPhrases;
    private boolean addedPhrases;
    private boolean classSpecific;

    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(String phraseId) {
        this.phraseId = phraseId;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getErgNumber() {
        return ergNumber;
    }

    public void setErgNumber(String ergNumber) {
        this.ergNumber = ergNumber;
    }

    public boolean isStandardPhrases() {
        return standardPhrases;
    }

    public void setStandardPhrases(boolean standardPhrases) {
        this.standardPhrases = standardPhrases;
    }

    public boolean isAddedPhrases() {
        return addedPhrases;
    }

    public void setAddedPhrases(boolean addedPhrases) {
        this.addedPhrases = addedPhrases;
    }

    public boolean isClassSpecific() {
        return classSpecific;
    }

    public void setClassSpecific(boolean classSpecific) {
        this.classSpecific = classSpecific;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("phraseId", new PhraseId());
        toBuild.put("parentClass", new ParentClass());
        toBuild.put("sortNumber", new SortNumber());
        toBuild.put("phrase", new Phrase());
        toBuild.put("typeId", new PhraseTypeFK());
        toBuild.put("ergNumber", new ERGNumber());
        toBuild.put("standardPhrases", new StandardPhrases());
        toBuild.put("addedPhrases", new AddedPhrases());
        toBuild.put("classSpecific", new ClassSpecific());
        return toBuild;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Description;
import emc.datatypes.trec.classes.ShortDescription;
import emc.datatypes.trec.phrasetypes.PhraseType;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECPhraseTypes", uniqueConstraints = {@UniqueConstraint(columnNames = {"typeId", "companyId"})})
public class TRECPhraseTypes extends EMCEntityClass {

    private String typeId;
    private String description;
    private String shortDescription;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("typeId", new PhraseType());
        toBuild.put("description", new Description());
        toBuild.put("shortDescription", new ShortDescription());
        return toBuild;
    }
}

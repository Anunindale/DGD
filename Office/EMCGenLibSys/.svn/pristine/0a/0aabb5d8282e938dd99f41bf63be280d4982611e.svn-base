/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Description;
import emc.datatypes.trec.trectype.TrecType;
import emc.datatypes.trec.trectype.Type;
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
@Table(name = "TRECTrecTypes", uniqueConstraints = {@UniqueConstraint(columnNames = {"trecTypeId", "companyId"})})
public class TRECTrecTypes extends EMCEntityClass {

    private String trecTypeId;
    private String description;
    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrecTypeId() {
        return trecTypeId;
    }

    public void setTrecTypeId(String trecTypeId) {
        this.trecTypeId = trecTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("trecTypeId", new TrecType());
        toBuild.put("description", new Description());
        toBuild.put("type", new Type());
        return toBuild;
    }
}

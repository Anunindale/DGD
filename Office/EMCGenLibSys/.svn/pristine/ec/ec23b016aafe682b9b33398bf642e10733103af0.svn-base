/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.hr;

import emc.datatypes.EMCDataType;
import emc.datatypes.hr.medicalaid.MedicalAid;
import emc.datatypes.systemwide.Description;
import emc.datatypes.systemwide.Telephone;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "HRMedicalAid", uniqueConstraints = {@UniqueConstraint(columnNames = {"medicalAid", "companyId"})})
public class HRMedicalAid extends EMCEntityClass {

    private String medicalAid;
    private String description;
    private String contactNum;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getMedicalAid() {
        return medicalAid;
    }

    public void setMedicalAid(String medicalAid) {
        this.medicalAid = medicalAid;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("medicalAid", new MedicalAid());
        toBuild.put("description", new Description());
        toBuild.put("contactNum", new Telephone());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("medicalAid");
        toBuild.add("description");
        return toBuild;
    }
}

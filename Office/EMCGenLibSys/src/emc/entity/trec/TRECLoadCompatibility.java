/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.trec.classes.foreignkey.ClassIdFKMandatory;
import emc.datatypes.trec.loadcompatibility.LoadAllowed;
import emc.datatypes.trec.loadcompatibility.LoadNotes;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "TRECLoadCompatibility", uniqueConstraints = {@UniqueConstraint(columnNames = {"classId", "otherClassId", "companyId"})})
public class TRECLoadCompatibility extends EMCEntityClass {

    private String classId;
    private String otherClassId;
    @Column(length = 1000)
    private String note;
    private boolean allowed;

    /**
     * @return the classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * @param classId the classId to set
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * @return the otherClassId
     */
    public String getOtherClassId() {
        return otherClassId;
    }

    /**
     * @param otherClassId the otherClassId to set
     */
    public void setOtherClassId(String otherClassId) {
        this.otherClassId = otherClassId;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the allowed
     */
    public boolean isAllowed() {
        return allowed;
    }

    /**
     * @param allowed the allowed to set
     */
    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap map = super.buildFieldList();
        map.put("classId", new ClassIdFKMandatory());
        map.put("otherClassId", new ClassIdFKMandatory());
        map.put("note", new LoadNotes());
        map.put("allowed", new LoadAllowed());
        return map;
    }
}

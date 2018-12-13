/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.trec;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.docuref.Note;
import emc.datatypes.trec.classes.ClassDescription;
import emc.datatypes.trec.classes.ClassId;
import emc.datatypes.trec.classes.HasChildren;
import emc.datatypes.trec.classes.IsParent;
import emc.datatypes.trec.classes.PackGrpThreshold1;
import emc.datatypes.trec.classes.PackGrpThreshold2;
import emc.datatypes.trec.classes.PackGrpThreshold3;
import emc.datatypes.trec.classes.ShortDescription;
import emc.datatypes.trec.classes.SubRiskPackGrpThreshold1;
import emc.datatypes.trec.classes.SubRiskPackGrpThreshold2;
import emc.datatypes.trec.classes.SubRiskPackGrpThreshold3;
import emc.datatypes.trec.classes.foreignkey.ClassIdFKNM;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "TRECClasses", uniqueConstraints = {@UniqueConstraint(columnNames = {"classId", "companyId"})})
public class TRECClasses extends EMCEntityClass {

    private String classId;
    private String description;
    private String parentClass;
    private boolean isParent;
    private boolean hasChildren;
    private double packGrp1Threshold;
    private double packGrp2Threshold;
    private double packGrp3Threshold;
    private double srPackGrp1Threshold;
    private double srPackGrp2Threshold;
    private double srPackGrp3Threshold;
    @Column(length = 1000)
    private String notes;
    @Column(length = 500)
    private String shortDescription;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * @return the packGrp1Threshold
     */
    public double getPackGrp1Threshold() {
        return packGrp1Threshold;
    }

    /**
     * @param packGrp1Threshold the packGrp1Threshold to set
     */
    public void setPackGrp1Threshold(double packGrp1Threshold) {
        this.packGrp1Threshold = packGrp1Threshold;
    }

    /**
     * @return the packGrp2Threshold
     */
    public double getPackGrp2Threshold() {
        return packGrp2Threshold;
    }

    /**
     * @param packGrp2Threshold the packGrp2Threshold to set
     */
    public void setPackGrp2Threshold(double packGrp2Threshold) {
        this.packGrp2Threshold = packGrp2Threshold;
    }

    /**
     * @return the packGrp3Threshold
     */
    public double getPackGrp3Threshold() {
        return packGrp3Threshold;
    }

    /**
     * @param packGrp3Threshold the packGrp3Threshold to set
     */
    public void setPackGrp3Threshold(double packGrp3Threshold) {
        this.packGrp3Threshold = packGrp3Threshold;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the srPackGrp1Threshold
     */
    public double getSrPackGrp1Threshold() {
        return srPackGrp1Threshold;
    }

    /**
     * @param srPackGrp1Threshold the srPackGrp1Threshold to set
     */
    public void setSrPackGrp1Threshold(double srPackGrp1Threshold) {
        this.srPackGrp1Threshold = srPackGrp1Threshold;
    }

    /**
     * @return the srPackGrp2Threshold
     */
    public double getSrPackGrp2Threshold() {
        return srPackGrp2Threshold;
    }

    /**
     * @param srPackGrp2Threshold the srPackGrp2Threshold to set
     */
    public void setSrPackGrp2Threshold(double srPackGrp2Threshold) {
        this.srPackGrp2Threshold = srPackGrp2Threshold;
    }

    /**
     * @return the srPackGrp3Threshold
     */
    public double getSrPackGrp3Threshold() {
        return srPackGrp3Threshold;
    }

    /**
     * @param srPackGrp3Threshold the srPackGrp3Threshold to set
     */
    public void setSrPackGrp3Threshold(double srPackGrp3Threshold) {
        this.srPackGrp3Threshold = srPackGrp3Threshold;
    }

    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("classId", new ClassId());
        toBuild.put("description", new ClassDescription());
        toBuild.put("isParent", new IsParent());
        toBuild.put("hasChildren", new HasChildren());
        toBuild.put("shortDescription", new ShortDescription());
        toBuild.put("parentClass", new ClassIdFKNM());
        toBuild.put("packGrp1Threshold", new PackGrpThreshold1());
        toBuild.put("packGrp2Threshold", new PackGrpThreshold2());
        toBuild.put("packGrp3Threshold", new PackGrpThreshold3());
        toBuild.put("srPackGrp1Threshold", new SubRiskPackGrpThreshold1());
        toBuild.put("srPackGrp2Threshold", new SubRiskPackGrpThreshold2());
        toBuild.put("srPackGrp3Threshold", new SubRiskPackGrpThreshold3());
        toBuild.put("notes", new Note());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List<String> toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("classId");
        toBuild.add("description");
        toBuild.add("parentClass");
        return toBuild;
    }
}

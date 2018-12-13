/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.numbersequences;

import emc.datatypes.base.numbersequences.AllowManualEntry;
import emc.datatypes.systemwide.Description;
import emc.datatypes.base.numbersequences.MaxNumber;
import emc.datatypes.base.numbersequences.MinNumber;
import emc.datatypes.base.numbersequences.ModuleId;
import emc.datatypes.base.numbersequences.NextAvailableNumber;
import emc.datatypes.base.numbersequences.NumberSequenceId;
import emc.datatypes.base.numbersequences.PerField;
import emc.datatypes.base.numbersequences.PerValue;
import emc.datatypes.base.numbersequences.PrefixString;
import emc.datatypes.base.numbersequences.RefField;
import emc.datatypes.base.numbersequences.RefTable;
import emc.datatypes.base.numbersequences.SuffixString;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "BaseNumberSequence", uniqueConstraints = {@UniqueConstraint(columnNames = {"numberSequenceId", "uniqueDescription", "companyId"})})
public class BaseNumberSequence extends EMCEntityClass {

    private String moduleId;
    private String numberSequenceId;
    private String description;
    private long maxNumber = 1;
    private long minNumber;
    private String prefixString;
    private String suffixString;
    private String refTable;
    private String refField;
    private long nextAvailableNumber;
    private boolean allowManualEntry;
    private boolean forceSequence;
    private String perField; //this
    private String perValue;
    private String uniqueDescription; //to get around many unique contraints
    private boolean random;
    @Column(columnDefinition="MEDIUMBLOB")
    private byte[] randomNumbers;

    /** Creates a new instance of BaseNumberSequence */
    public BaseNumberSequence() {
    }

    public String getNumberSequenceId() {
        return numberSequenceId;
    }

    public void setNumberSequenceId(String numberSequenceId) {
        this.numberSequenceId = numberSequenceId;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public String getRefField() {
        return refField;
    }

    public void setRefField(String refField) {
        this.refField = refField;
    }

    public boolean getAllowManualEntry() {
        return allowManualEntry;
    }

    public void setAllowManualEntry(boolean allowManualEntry) {
        this.allowManualEntry = allowManualEntry;
    }

    public String getPrefixString() {
        return prefixString;
    }

    public void setPrefixString(String prefixString) {
        this.prefixString = prefixString;
    }

    public String getSuffixString() {
        return suffixString;
    }

    public void setSuffixString(String suffixString) {
        this.suffixString = suffixString;
    }

    public long getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(long minNumber) {
        this.minNumber = minNumber;
    }

    public long getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(long maxNumber) {
        this.maxNumber = maxNumber;
    }

    public long getNextAvailableNumber() {
        return nextAvailableNumber;
    }

    public void setNextAvailableNumber(long nextAvailableNumber) {
        this.nextAvailableNumber = nextAvailableNumber;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public byte[] getRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(byte[] randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("numberSequenceId", new NumberSequenceId());
        toBuild.put("minNumber", new MinNumber());
        toBuild.put("maxNumber", new MaxNumber());
        toBuild.put("description", new Description());
        toBuild.put("refTable", new RefTable());
        toBuild.put("refField", new RefField());
        toBuild.put("prefixString", new PrefixString());
        toBuild.put("suffixString", new SuffixString());
        toBuild.put("nextAvailableNumber", new NextAvailableNumber());
        toBuild.put("allowManualEntry", new AllowManualEntry());
        toBuild.put("perField", new PerField());
        toBuild.put("perValue", new PerValue());
        toBuild.put("moduleId", new ModuleId());

        return toBuild;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerField() {
        return perField;
    }

    public void setPerField(String perField) {
        this.perField = perField;
    }

    public String getPerValue() {
        return perValue;
    }

    public void setPerValue(String perValue) {
        this.perValue = perValue;
    }

    public String getUniqueDescription() {
        return uniqueDescription;
    }

    public void setUniqueDescription(String uniqueDescription) {
        this.uniqueDescription = uniqueDescription;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return the forceSequence
     */
    public boolean isForceSequence() {
        return forceSequence;
    }

    /**
     * @param forceSequence the forceSequence to set
     */
    public void setForceSequence(boolean forceSequence) {
        this.forceSequence = forceSequence;
    }
}

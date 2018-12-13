/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.entity.debtors.allocationimport;

import emc.datatypes.EMCDataType;
import emc.datatypes.debtors.allocationimportsetup.MapConditionField;
import emc.datatypes.debtors.allocationimportsetup.MapConditionValue;
import emc.datatypes.debtors.allocationimportsetup.SpreadsheetMapping;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author riaan
 */
@Entity
@Table(name = "DebtorsAllocationImportSetupLines", uniqueConstraints = {@UniqueConstraint(columnNames = {"spreadsheetMapping", "mapCondition", "mapConditionValue"})})
public class DebtorsAllocationImportSetupLines extends EMCEntityClass {

    private long masterID;
    private String spreadsheetMapping;
    private String mapCondition;
    private String mapConditionValue;
    private String mapConditionField;
    
    /** Creates a new instance of DebtorsAllocationImportSetupLines. */
    public DebtorsAllocationImportSetupLines() {
        
    }

    public String getSpreadsheetMapping() {
        return spreadsheetMapping;
    }

    public void setSpreadsheetMapping(String spreadsheetMapping) {
        this.spreadsheetMapping = spreadsheetMapping;
    }

    public String getMapCondition() {
        return mapCondition;
    }

    public void setMapCondition(String mapCondition) {
        this.mapCondition = mapCondition;
    }

    public String getMapConditionValue() {
        return mapConditionValue;
    }

    public void setMapConditionValue(String mapConditionValue) {
        this.mapConditionValue = mapConditionValue;
    }

    public long getMasterID() {
        return masterID;
    }

    public void setMasterID(long masterID) {
        this.masterID = masterID;
    }

    public String getMapConditionField() {
        return mapConditionField;
    }

    public void setMapConditionField(String mapConditionField) {
        this.mapConditionField = mapConditionField;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("spreadsheetMapping", new SpreadsheetMapping());
        toBuild.put("mapConditionValue", new MapConditionValue());
        toBuild.put("mapConditionField", new MapConditionField());

        return toBuild;
    }

}

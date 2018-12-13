/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.action.emcimportwizard.resources;

import java.io.Serializable;

/**
 * Helper class for the import wizzard.
 * 
 * @author wikus
 */
public class ColumnInfo implements Serializable{
    
    private String columnName;
    private boolean active;
    private String defaultValue;
    private boolean override;
    private boolean mandatory;
    private Object type;
    
    public ColumnInfo(String columnName, boolean active, String defaultValue, boolean override, boolean mandatory, Object type){
        this.columnName = columnName;
        this.active = active;
        this.defaultValue = defaultValue;
        this.override = override;
        this.mandatory = mandatory;
        this.type = type;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public boolean isActive() {
        return active;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public boolean isOverride() {
        return override;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }
    
    
}

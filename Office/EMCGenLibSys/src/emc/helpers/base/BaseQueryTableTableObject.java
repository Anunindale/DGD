/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.base;

import emc.framework.EMCDebug;
import emc.tables.EMCTable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class BaseQueryTableTableObject {

    private String entityClass;
    private String entityClassField;
    private String joinTable;
    private String joinField;
    private boolean leftOuterJoin;
    //2 = Entity; 3 = Join
    private int recordType;
    private String emcLabel;

    /** Creates a new instance if ReportTableObject */
    public BaseQueryTableTableObject() {
    }

    /** Creates a new instance if ReportTableObject */
    public BaseQueryTableTableObject(String entityClass, String entityClassField, String joinTable, String joinField, int recordType, boolean leftOuterJoin) {
        this.entityClass = entityClass;
        this.entityClassField = entityClassField;
        this.joinTable = joinTable;
        this.joinField = joinField;
        this.recordType = recordType;
        this.leftOuterJoin = leftOuterJoin;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public String getEntityClassField() {
        return entityClassField;
    }

    public void setEntityClassField(String entityClassField) {
        this.entityClassField = entityClassField;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }

    public String getJoinField() {
        return joinField;
    }

    public void setJoinField(String joinField) {
        this.joinField = joinField;
    }

    public boolean isLeftOuterJoin() {
        return leftOuterJoin;
    }

    public void setLeftOuterJoin(boolean leftOuterJoin) {
        this.leftOuterJoin = leftOuterJoin;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        if (this.emcLabel == null) {
            String classToDisplay = null;
            switch (recordType) {
                case 2:
                    classToDisplay = this.entityClass;
                    break;
                case 3:
                    classToDisplay = this.joinTable;
                    break;
            }
            if (classToDisplay != null) {
                try {
                    this.emcLabel = ((EMCTable) Class.forName(classToDisplay).newInstance()).getEmcLabel();
                } catch (Exception ex) {
                    if (EMCDebug.getDebug()) {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to create EMCTable to get emc label.", ex);
                    }
                }
            }
        }

        return this.emcLabel;
    }

    /** Clears the emc label.  Call this method before serializing object. */
    public void clearNotSerializeFields() {
        this.emcLabel = null;
    }
}

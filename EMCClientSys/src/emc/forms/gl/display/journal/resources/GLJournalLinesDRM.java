/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.gl.display.journal.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.util.NumFieldCalculator;
import emc.enums.base.journals.JournalStatus;
import emc.framework.EMCUserData;
import emc.functions.Functions;

/**
 *
 * @author riaan
 */
public class GLJournalLinesDRM extends emcDataRelationManagerUpdate {

    private NumFieldCalculator numFieldCalculator = new NumFieldCalculator(this, "lineNumber");

    /** Creates a new instance of GLJournalLinesDRM. */
    public GLJournalLinesDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void updatePersist(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = getLastRowAccessed();
        }
        if ((Long) this.getFieldValueAt(rowIndex, "recordID") == 0) {
            this.setFieldValueAt(rowIndex, "lineNumber", numFieldCalculator.calcBigDecimalLineNo(rowIndex));
        }
        super.updatePersist(rowIndex);

        GLJournalMasterDRM masterDRM = (GLJournalMasterDRM) getHeaderTable();
        if (JournalStatus.fromString((String) masterDRM.getLastFieldValueAt("journalStatus")) == JournalStatus.APPROVED) {
            masterDRM.refreshRecordNoDoRelation(masterDRM.getLastRowAccessed());
        }
    }

    @Override
    public Object getFieldValueAt(int rowIndex, String columnIndex) {
        Object ret = super.getFieldValueAt(rowIndex, columnIndex);
        if ((Long) this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "recordID") != 0) {
            boolean eFlag = this.isRowUpdated();

            if (columnIndex.equals("contraAccount") && Functions.checkBlank(ret)) {
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "journalContraAccount");
                if (masterCA != null) {
                    this.setFieldValueAtRegardRelation(rowIndex, columnIndex, masterCA, false);
                }
                if (!eFlag) {
                    this.setEditFlag(false);
                }
            } else if (columnIndex.equals("contraType") && Functions.checkBlank(ret)) {
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "journalContraType");
                if (masterCA != null) {
                    this.setFieldValueAtRegardRelation(rowIndex, columnIndex, masterCA, false);
                }
                if (!eFlag) {
                    this.setEditFlag(false);
                }

            } else if (columnIndex.equals("lineDate") && ret == null) {     //This field is a date, check for null only.
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "journalDate");
                if (masterCA != null) {
                    this.setFieldValueAtRegardRelation(rowIndex, columnIndex, masterCA, false);
                }
                if (!eFlag) {
                    this.setEditFlag(false);
                }
            } else if (columnIndex.equals("vatIncluded") && !eFlag && (Long)getLastFieldValueAt("recordID") == 0) { //Only set boolean the very first time it's requested.
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "vatIncluded");
                if (masterCA != null) {
                    this.setFieldValueAtRegardRelation(rowIndex, columnIndex, masterCA, false);
                }
                if (!eFlag) {
                    this.setEditFlag(false);
                }
            } else if (columnIndex.equals("vatCode") && Functions.checkBlank(ret)) {
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "vatCode");
                if (masterCA != null) {
                    this.setFieldValueAtRegardRelation(rowIndex, columnIndex, masterCA, false);
                }
                if (!eFlag) {
                    this.setEditFlag(false);
                }
            } else if (columnIndex.equals("vatInputOutput") && Functions.checkBlank(ret)) {
                Object masterCA = this.getHeaderTable().getFieldValueAt(this.getHeaderTable().getLastRowAccessed(), "vatInputOutput");
                if (masterCA != null) {
                    this.setFieldValueAtRegardRelation(rowIndex, columnIndex, masterCA, false);
                }
                if (!eFlag) {
                    this.setEditFlag(false);
                }
            }
        }
        return ret;
    }
}

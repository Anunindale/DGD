/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.creditors.display.creditnote.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.enums.creditors.CreditorsCreditNoteInvoiceType;
import emc.framework.EMCUserData;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class CreditorsCreditNoteMasterDRM extends emcDataRelationManagerUpdate {

    public CreditorsCreditNoteMasterDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        super.insertRowCache(rowIndex, addRowAfter);
        super.setFieldValueAt(getLastRowAccessed(), "creditNoteInvoiceType", CreditorsCreditNoteInvoiceType.MANUAL_CREDIT_NOTE.toString());
        super.setFieldValueAt(getLastRowAccessed(), "creditNoteInvoiceDate", Functions.nowDate());
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        super.setFieldValueAt(rowIndex, columnIndex, aValue);
        if (getLastSetFieldValueStatus()) {
            if (Functions.checkBlank(getFieldValueAt(rowIndex, "creditNoteInvoiceType"))) {
                super.setFieldValueAt(rowIndex, "creditNoteInvoiceType", CreditorsCreditNoteInvoiceType.MANUAL_CREDIT_NOTE.toString());
            }
            if (Functions.checkBlank(getFieldValueAt(rowIndex, "creditNoteInvoiceDate"))) {
                super.setFieldValueAt(rowIndex, "creditNoteInvoiceDate", Functions.nowDate());
            }
        }
    }
}

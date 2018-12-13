/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals.stocktakecapture;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.forms.inventory.display.journals.JournalLinesDRM;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class CaptureDRM extends emcDataRelationManagerUpdate {

    private JournalLinesDRM journalLineDRM;

    public CaptureDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    public void setJournalLineDRM(JournalLinesDRM journalLineDRM) {
        this.journalLineDRM = journalLineDRM;
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        super.insertRowCache(rowIndex, addRowAfter);
        this.setFieldValueAt(getLastRowAccessed(), "journalNumber", journalLineDRM.getLastFieldValueAt("journalNumber"));
    }


    @Override
    public boolean persistOnClosing() {
        boolean b = super.persistOnClosing();
        if (b) {
            journalLineDRM.refreshDataIgnoreFocus();
        }
        return b;
    }
}

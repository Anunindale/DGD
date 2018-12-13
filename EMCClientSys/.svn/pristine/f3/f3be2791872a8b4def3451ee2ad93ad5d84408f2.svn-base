/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.journals;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.base.journals.JournalStatus;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class JournalsDRM extends emcDataRelationManagerUpdate {

    /** Creates a new instance of JournalsDRM */
    public JournalsDRM(emcGenericDataSourceUpdate dataSource, EMCUserData userData) {
        super(dataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData) {
        Object obj1 = super.getFieldValueAt(this.getLastRowAccessed(), this.getHeaderColumnID());
        if (obj1 == null) {
            obj1 = "";
        }
        //return linesUserData;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryJournalLines.class);
        query.addAnd("journalNumber", obj1);
        query.addOrderBy("lineNo");
        List x = new ArrayList();
        x.add(0, query);
        linesUserData.setUserData(x);
        return linesUserData;
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        switch (Index) {
            case 0:
                formUserData.setUserData(2, this);
                break;
        }
        return formUserData;
    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);

        updateComponents();
    }

    //Package protected method used to enable/disable tabs/buttons based on journal type.
    void updateComponents() {
        JournalsForm form = (JournalsForm) getTheForm();

        InventoryJournalTypes type = InventoryJournalTypes.fromString((String) this.getFieldValueAt(this.getLastRowAccessed(), "journalType"));
        JournalStatus status = JournalStatus.fromString((String) this.getFieldValueAt(this.getLastRowAccessed(), "journalStatus"));
        emcDataRelationManagerUpdate linsDRM = this.getLinesTable();

        if (type != null) {
            switch (type) {
                case MOVEMENT:
                    form.setLinesTabEnabled(2, false);
                    form.setLinesTabEnabled(3, false);
                    form.enableRegisterButton(true);
                    form.enableStockTakeButton(false);
                    form.enableStockCountButton(false);
                    if (linsDRM != null) {
                        ((JournalLinesDRM) linsDRM).setupStockTakeTable(false);
                    }
                    break;

                case STOCKTAKE:
                    form.setLinesTabEnabled(2, false);
                    form.setLinesTabEnabled(3, true);
                    form.enableRegisterButton(true);
                    switch (status) {
                        case POSTED:
                            form.enableStockTakeButton(false);
                            form.enableStockCountButtonPosted();
                            break;
                        default:
                            form.enableStockTakeButton(true);
                            form.enableStockCountButton(true);
                            break;

                    }
                    if (linsDRM != null) {
                        ((JournalLinesDRM) linsDRM).setupStockTakeTable(true);
                    }
                    break;

                case TRANSFER:
                    form.setLinesTabEnabled(2, true);
                    form.setLinesTabEnabled(3, false);
                    form.enableRegisterButton(false);
                    form.enableStockTakeButton(false);
                    form.enableStockCountButton(false);
                    if (linsDRM != null) {
                        ((JournalLinesDRM) linsDRM).setupStockTakeTable(false);
                    }
                    break;
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.journals.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.util.NumFieldCalculator;
import emc.entity.debtors.journals.DebtorsJournalLines;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;

/**
 * @description : Lines DRM for the Debtors journals form.
 *
 * @date        : 17 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class JournalLinesDRM extends emcDataRelationManagerUpdate {

    private NumFieldCalculator lineNumCalc;

    /** Creates a new instance of JournalLinesDRM */
    public JournalLinesDRM(EMCUserData userData) {
        super(new emcGenericDataSourceUpdate(
                enumEMCModules.DEBTORS.getId(),
                new DebtorsJournalLines(), userData), userData);

        lineNumCalc = new NumFieldCalculator(this, "lineNo");
    }

    @Override
    public void updatePersist(int rowIndex) {
        if (rowIndex == -1) {
            rowIndex = this.getLastRowAccessed();
        }

        if ((Long)this.getFieldValueAt(rowIndex, "recordID") == 0) {
            this.setFieldValueAt(rowIndex, "lineNo", lineNumCalc.calcLineNo(rowIndex));
        }

        super.updatePersist(rowIndex);

        if (getLastUpdateStatus()) {
            //Header status may have been changeds.
            this.getHeaderTable().refreshRecordIgnoreFocus(-1);
        }
    }
}

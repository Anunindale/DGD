/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.gl.financialperiod.foreignkeys;

import emc.datatypes.gl.financialperiod.PeriodId;
import emc.entity.gl.GLFinancialPeriods;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class PeriodIdFK extends PeriodId {

    public PeriodIdFK() {
        this.setRelatedTable(GLFinancialPeriods.class.getName());
        this.setRelatedField("periodId");
        this.setUpdateAction(enumDeleteUpdateOptions.RESTRICT);
        this.setDeleteAction(enumDeleteUpdateOptions.RESTRICT);
    }
}

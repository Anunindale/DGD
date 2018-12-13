/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.calendar.resources;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.enums.base.calendar.CalendarShiftType;
import emc.framework.EMCUserData;

/**
 *
 * @author wikus
 */
public class ShiftDRM extends EMCControlLookupComponentDRM {

    private CalendarShiftType formType;

    public ShiftDRM(emcGenericDataSourceUpdate tableDataSource, CalendarShiftType formType, EMCUserData userData) {
        super(tableDataSource, userData);
        this.formType = formType;
    }

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        Object theDate = getLastFieldValueAt("shiftDate");
        super.insertRowCache(rowIndex, addRowAfter);
        if (formType.equals(CalendarShiftType.EXCEPTION)) setFieldValueAt(getLastRowAccessed(), "shiftDate", theDate);
    }
}

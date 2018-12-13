/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.parameters;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.base.calendar.WeekDays;

/**
 *
 * @author wikus
 */
public class FirstDayOfWeekComboBox extends EMCFormComboBox {

    private boolean busyUpdate = false;

    public FirstDayOfWeekComboBox(emcDataRelationManagerUpdate dataRelation, String columnIndex) {
        super(dataRelation, columnIndex);
        for (Object o : WeekDays.values()) {
            this.addItem(o.toString());
        }
        this.updateDocumentContents();
    }

    @Override
    public void UpdateCache() {
        if (!busyUpdate)
            this.getDataRelationManager().setFieldValueAt(this.getRowIndex(), getFieldName(), WeekDays.fromString(this.getSelectedItem().toString()).getId());
    }

    @Override
    public void updateDocumentContents() {
        busyUpdate = true;
        Object obj = getDataRelationManager().getFieldValueAt(getRowIndex(), getFieldName());
        if (obj != null) {
            this.setSelectedItem(WeekDays.fromId((Integer) obj).toString());
        } else {
            this.setSelectedItem(null);
        }
        busyUpdate = false;
    }
}

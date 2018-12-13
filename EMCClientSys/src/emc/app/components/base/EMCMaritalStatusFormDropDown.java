/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.base.employees.BaseMaritalStatus;

/**
 *
 * @author wikus
 */
public class EMCMaritalStatusFormDropDown extends EMCFormComboBox {

    public EMCMaritalStatusFormDropDown(emcDataRelationManagerUpdate dataRelation, String columnIndex) {
        super(dataRelation, columnIndex);
        BaseMaritalStatus[] statuses = BaseMaritalStatus.values();
        for (BaseMaritalStatus status : statuses) {
            this.addItem(status.toString());
        }
    }
}

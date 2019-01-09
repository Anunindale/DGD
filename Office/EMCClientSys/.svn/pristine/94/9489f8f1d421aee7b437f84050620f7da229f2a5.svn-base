/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base;

import emc.app.components.EMCFormComboBox;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.base.employees.BaseGender;

/**
 *
 * @author wikus
 */
public class EMCGenderFormDropDown extends EMCFormComboBox {

    public EMCGenderFormDropDown(emcDataRelationManagerUpdate drm, String field) {
        super(drm, field);
        BaseGender[] genders = BaseGender.values();
        for (BaseGender gender : genders) {
            this.addItem(gender.toString());
        }
    }
}

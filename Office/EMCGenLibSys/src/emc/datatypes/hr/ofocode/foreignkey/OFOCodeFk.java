/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.ofocode.foreignkey;

import emc.datatypes.hr.ofocode.OFOCode;
import emc.entity.hr.HROFOCodes;

/**
 *
 * @author wikus
 */
public class OFOCodeFk extends OFOCode {

    public OFOCodeFk() {
        this.setRelatedTable(HROFOCodes.class.getName());
        this.setRelatedField("ofoCode");
        this.setMandatory(false);
    }
}

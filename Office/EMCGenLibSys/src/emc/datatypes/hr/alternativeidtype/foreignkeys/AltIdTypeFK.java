/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.alternativeidtype.foreignkeys;

import emc.datatypes.hr.alternativeidtype.AltIdType;
import emc.entity.hr.HRAlternativeIdType;

/**
 *
 * @author claudette
 */
public class AltIdTypeFK extends AltIdType {

    public AltIdTypeFK() {
        this.setRelatedTable(HRAlternativeIdType.class.getName());
        this.setRelatedField("altIdType");
    }
}

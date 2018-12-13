/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.equitycode.foreignkeys;

import emc.datatypes.hr.equitycode.EquityCode;
import emc.entity.hr.HREquityCode;

/**
 *
 * @author claudette
 */
public class EquityCodeFK extends EquityCode {

    public EquityCodeFK() {
        this.setRelatedTable(HREquityCode.class.getName());
        this.setRelatedField("equityCode");
    }
}

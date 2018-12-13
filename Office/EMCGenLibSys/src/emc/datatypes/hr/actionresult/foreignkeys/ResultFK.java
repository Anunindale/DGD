/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.hr.actionresult.foreignkeys;

import emc.datatypes.hr.actionresult.Result;
import emc.entity.hr.HRActionResults;

/**
 *
 * @author wikus
 */
public class ResultFK extends Result {

    public ResultFK() {
        this.setRelatedTable(HRActionResults.class.getName());
        this.setRelatedField("resultId");
    	this.setColumnWidth(77);
        this.setMandatory(false);
    }
}

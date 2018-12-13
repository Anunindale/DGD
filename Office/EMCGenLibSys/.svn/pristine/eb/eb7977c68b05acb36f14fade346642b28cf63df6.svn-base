/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.base.reporttools;

import emc.datatypes.EMCLong;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.enums.datatypes.enumDeleteUpdateOptions;

/**
 *
 * @author wikus
 */
public class QueryTableRecord extends EMCLong{

    public QueryTableRecord() {
        this.setRelatedTable(BaseReportUserQueryTable.class.getName());
        this.setRelatedField("recordID");
        this.setDeleteAction(enumDeleteUpdateOptions.CASCADE);
    }

}

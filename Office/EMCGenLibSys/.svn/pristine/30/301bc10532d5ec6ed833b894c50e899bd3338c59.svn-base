/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.sop.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.systemwide.Name;
import emc.entity.sop.*;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class SOPSalesRepGroupSetupDS extends SOPSalesRepGroupsSetup {

    private String repName;

    public SOPSalesRepGroupSetupDS() {
        this.setDataSource(true);
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("repName", new Name());
        return toBuild;
    }
}

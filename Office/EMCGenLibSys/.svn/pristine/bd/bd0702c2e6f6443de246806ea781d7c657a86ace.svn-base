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
public class SOPSalesRepGroupsDS extends SOPSalesRepGroups {

    private String groupManagerName;

    public SOPSalesRepGroupsDS() {
        this.setDataSource(true);
    }

    public String getGroupManagerName() {
        return groupManagerName;
    }

    public void setGroupManagerName(String groupManagerName) {
        this.groupManagerName = groupManagerName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("groupManagerName", new Name());
        return toBuild;
    }
}

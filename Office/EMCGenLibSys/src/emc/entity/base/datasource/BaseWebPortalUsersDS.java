/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.webportalusers.SourveRef;
import emc.entity.base.webportal.BaseWebPortalUsers;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class BaseWebPortalUsersDS extends BaseWebPortalUsers {

    private String sourceRef;
    private String sourceRefDesc;

    public BaseWebPortalUsersDS() {
        this.setDataSource(true);
    }

    public String getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }

    public String getSourceRefDesc() {
        return sourceRefDesc;
    }

    public void setSourceRefDesc(String sourceRefDesc) {
        this.sourceRefDesc = sourceRefDesc;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("sourceRef", new SourveRef());
        return toBuild;
    }
}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.developertools.sites.UserName;
import emc.entity.base.BaseMailReturnAddressSetup;
import java.util.HashMap;

/**
 *
 * @author wikus
 */
public class BaseMailReturnAddressSetupDS extends BaseMailReturnAddressSetup {

    private String userName;

    public BaseMailReturnAddressSetupDS() {
        this.setDataSource(true);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("userName", new UserName());
        return toBuild;
    }
}

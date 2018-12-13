/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.mailsetup;

import emc.bus.base.UsersLocal;
import emc.entity.base.Usertable;
import emc.entity.base.datasource.BaseMailReturnAddressSetupDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseMailReturnAddressSetupDSBean extends EMCDataSourceBean implements BaseMailReturnAddressSetupDSLocal {

    @EJB
    private UsersLocal usersBean;

    public BaseMailReturnAddressSetupDSBean() {
        this.setDataSourceClassName(BaseMailReturnAddressSetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        BaseMailReturnAddressSetupDS ds = (BaseMailReturnAddressSetupDS) dataSourceInstance;
        Usertable user = usersBean.findUser(ds.getUserId(), userData);
        ds.setUserName(user.getUserName());
        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            if (fieldNameToValidate.equals("userId")) {
                BaseMailReturnAddressSetupDS ds = (BaseMailReturnAddressSetupDS) theRecord;
                Usertable user = usersBean.findUser(ds.getUserId(), userData);
                ds.setUserName(user.getUserName());
                if (isBlank(ds.getEmailAddress())) {
                    ds.setEmailAddress(user.getUserEmail());
                }
                return ds;
            }
        }
        return valid;
    }
    
}

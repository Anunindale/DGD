/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.sites;

import emc.entity.developertools.sites.DevSiteMaster;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.server.AuthorizationKeyManager;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevSiteMasterBean extends EMCEntityBean implements DevSiteMasterLocal {

    /** Creates a new instance of DevSiteMasterBean. */
    public DevSiteMasterBean() {

    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        DevSiteMaster siteMaster = (DevSiteMaster)iobject;
        
        updateAuthorizationKey(siteMaster, userData);

        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        DevSiteMaster siteMaster = (DevSiteMaster)uobject;

        DevSiteMaster persisted = (DevSiteMaster)util.findDetachedPersisted(siteMaster, userData);
        
        if (!util.checkObjectsEqual(siteMaster.getServerSystemId(), persisted.getServerSystemId())) {
            updateAuthorizationKey(siteMaster, userData);
        }
        
        return super.update(uobject, userData);
    }

    /**  Updates authorization key. */
    private void updateAuthorizationKey(DevSiteMaster siteMaster, EMCUserData userData) {
        if (isBlank(siteMaster.getServerSystemId())) {
            siteMaster.setAuthorizationKey(null);
        } else {
            siteMaster.setAuthorizationKey(AuthorizationKeyManager.getAuthKey(siteMaster.getServerSystemId()));
        }
    }
}

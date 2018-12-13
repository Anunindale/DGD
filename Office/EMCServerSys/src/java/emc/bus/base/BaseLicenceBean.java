/* 
 * BaseLicenceBean.java
 *
 * Created on 24 October 2007, 09:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.bus.base.onlineusers.OnlineUsersLocal;
import emc.entity.base.BaseLicenceTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.server.AuthorizationKeyManager;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rico
 */
@Stateless
public class BaseLicenceBean extends EMCEntityBean implements BaseLicenceLocal {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private OnlineUsersLocal onlineUsersBean;

    /** Creates a new instance of BaseLicenceBean */
    public BaseLicenceBean() {
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.update(uobject, userData);
        if (ret instanceof EMCEntityClass) {
            onlineUsersBean.updateNumberOfUsers(userData);
        }
        return ret;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.insert(iobject, userData);
        onlineUsersBean.updateNumberOfUsers(userData);
        return ret;
    }
    ////////////////////////////////////////////////
    //Added Marius - 2007-12-05
    //To find the first record in the licence code 

    public BaseLicenceTable find(EMCUserData userData) {
        //Use EMC Query so that System Table Check on BaseLicense Table is honered
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseLicenceTable.class);
        BaseLicenceTable ret = (BaseLicenceTable) util.executeSingleResultQuery(query, userData);
        return ret;
    }

    public BaseLicenceTable findDefaultCompany() {
        String companyId = emc.constants.systemConstants.defaultCompanyId();
        Query qr = em.createQuery("SELECT u from BaseLicenceTable u WHERE u.companyId = :companyId");
        qr.setParameter("companyId", companyId);
        BaseLicenceTable ret = null;
        if (qr.getResultList().size() != 0) {
            ret = (BaseLicenceTable) qr.getResultList().get(0);
        }
        return ret;
    }
    ////////////////////////////////////////////////

    /**
     * Returns a unique identifier for the system executing this method.
     * @param userData User data.
     * @return A unique identifier for the system executing this method.
     */
    public String getSystemID(EMCUserData userData) {
        return AuthorizationKeyManager.getSystemIdentifier();
    }

    /**
     * Validates an authorization key.
     * @param key Key to validate.
     * @param userData User data.
     * @return A boolean indicating whether the given key is valid.
     */
    public boolean validateAuthKey(String key, EMCUserData userData) {
        return AuthorizationKeyManager.checkAuthKey(key);
    }
    
    public List getActiveModuleList(EMCUserData userData){
        List<String> moduleList= find(userData).moduleList();
        return moduleList;
    }
}

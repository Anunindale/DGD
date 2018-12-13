/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.webportal;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseWebPortalUsersLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.base.webportal.BaseWebPortalUsers loginWebPortalUser(java.lang.String userName, java.lang.String password, emc.framework.EMCUserData userData);

    public boolean activateWebUser(long webUserRecordID, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean deactivateWebUser(long webUserRecordID, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public boolean deactivateAllWebUsers(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
    /**
     * Attempts to find a student id number or passport number;
     * @param userName
     * @param userData
     * @return id or passport or null if none found
     */
    public String getStudentIdNumber(String userName,EMCUserData userData);
}

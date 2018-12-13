/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.dblog;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DBLogLocal extends EMCEntityBeanLocalInterface {
    /**
     * Deletes all log records up to the specified date.
     * @param toDate Date up to which records should be cleared.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean clearDBLog(java.util.Date toDate, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}

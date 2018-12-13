/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.dblog;

import emc.enums.enumPersistOptions;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DBLogLogicLocal {

    public void doDBLogging(EMCEntityClass theRecord, enumPersistOptions type, EMCUserData userData) throws EMCEntityBeanException;

    public boolean refresh(EMCUserData userData);
}

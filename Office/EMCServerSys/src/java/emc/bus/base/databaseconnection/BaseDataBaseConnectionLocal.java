/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.databaseconnection;

import emc.enums.base.databaseconnection.DBConnection;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseDataBaseConnectionLocal extends EMCEntityBeanLocalInterface {

    public BaseDataBaseConnectionBean findConnectionInfo(DBConnection connection, EMCUserData userData);
}

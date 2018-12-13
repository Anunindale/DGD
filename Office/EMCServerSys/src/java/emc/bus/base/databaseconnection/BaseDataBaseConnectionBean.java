/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.databaseconnection;

import emc.entity.base.BaseDBConnections;
import emc.enums.base.databaseconnection.DBConnection;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseDataBaseConnectionBean extends EMCEntityBean implements BaseDataBaseConnectionLocal {

    public BaseDataBaseConnectionBean findConnectionInfo(DBConnection connection, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDBConnections.class);
        query.addAnd("connectionId", connection.toString());
        return (BaseDataBaseConnectionBean) util.executeSingleResultQuery(query, userData);
    }
}

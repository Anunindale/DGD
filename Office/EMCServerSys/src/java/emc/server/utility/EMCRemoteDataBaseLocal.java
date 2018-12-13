/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.utility;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface EMCRemoteDataBaseLocal {

    public java.sql.Connection connectToDatabase(emc.entity.base.BaseDBConnections connInfo, emc.framework.EMCUserData userData);

    public void closeConnectionToDB(java.sql.Connection conn, emc.framework.EMCUserData userData);

    public java.util.List<java.lang.Object[]> exJDBCFieldReadQuery(java.sql.Connection conn, emc.framework.EMCQuery theQuery, emc.framework.EMCUserData userData);

    public int exJDBCUpdateQuery(java.sql.Connection conn, emc.framework.EMCQuery theQuery, emc.framework.EMCUserData userData);
}

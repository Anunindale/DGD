/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.dblog;

import emc.entity.base.dblog.BaseDBLog;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DBLogBean extends EMCEntityBean implements DBLogLocal {

    /**
     * Creates a new instance of DBLogBean.
     */
    public DBLogBean() {
    }

    /**
     * Deletes all log records up to the specified date. Cannot be rolled back.
     *
     * @param toDate Date up to which records should be cleared.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean clearDBLog(Date toDate, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, BaseDBLog.class);
        query.addAnd("createdDate", Functions.date2String(toDate, "yyyy-MM-dd"), EMCQueryConditions.LESS_THAN_EQ);

        util.executeUpdate(query, userData);

        return true;
    }
}
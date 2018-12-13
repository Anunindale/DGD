/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.reporttools;

import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseReportUserQueryTableLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.base.reporttools.BaseReportUserQueryTable saveDefaultQueryForUser(java.util.List queryInformation, emc.framework.EMCUserData userData);

    public java.util.List getUserSavedQueries(java.lang.String reportId, emc.framework.EMCUserData userData);

    public boolean saveUserQuery(java.lang.String currentQueryName, java.lang.String newQueryName, java.lang.String reportId, emc.framework.EMCUserData userData);

    public List getLastQueryForUser(String reportId, EMCUserData userData);

    /**
     * Delete a query
     * @param reportId
     * @param queryName
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean deleteQuery(String reportId, String queryName, EMCUserData userData) throws EMCEntityBeanException;

    public long createCustom(BaseReportUserQueryTable fromRecord, long recId, EMCUserData userData) throws EMCEntityBeanException;

    public boolean checkQueryExistance(String queryName, String reportId, EMCUserData userData);

    public emc.framework.EMCQuery constructAndReturnQuery(java.lang.String reportId, java.lang.String queryName, emc.framework.EMCUserData userData);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.entitylog;

import emc.entity.developertools.DevEntityLog;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevEntityLogBean extends EMCEntityBean implements DevEntityLogLocal {

    @Override
    public List<List<String>> getEntityLogQueries(String customerId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevEntityLog.class);
        query.addAnd("customerId", customerId);
        query.addAnd("ranQuery", false);
        List<DevEntityLog> entityLogList = util.executeGeneralSelectQuery(query, userData);

        List<String> beforeDeployment = new ArrayList<String>();
        List<String> afterDeployment = new ArrayList<String>();

        for (DevEntityLog entityLog : entityLogList) {
            if (!isBlank(entityLog.getQueryLog())) {
                if (entityLog.isRunBeforeDeploy()) {
                    beforeDeployment.add(entityLog.getQueryLog());
                } else {
                    afterDeployment.add(entityLog.getQueryLog());
                }
                entityLog.setRanQuery(true);
                this.update(entityLog, userData);
            }
        }

        List retList = new ArrayList();
        retList.add(beforeDeployment);
        retList.add(afterDeployment);

        return retList;
    }

    @Override
    public void executeEntityLogQueries(List<String> queries, EMCUserData userData) {
        for (String q : queries) {
            util.executeNativeQuery(q, userData);
        }
    }
}

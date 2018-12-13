/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.versioncontrol;

import emc.entity.developertools.versioningcontrol.DevVersioningControl;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
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
public class DevVersioningControlBean extends EMCEntityBean implements DevVersioningControlLocal {

    @Override
    public List<String> getVersionedCustomers(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevVersioningControl.class);
        query.addField("customerId");
        query.addField("repositoryName");
        List<Object[]> selectedData = util.executeGeneralSelectQuery(query, userData);

        List<String> retList = new ArrayList<String>();

        for (Object[] oa : selectedData) {
            retList.add(oa[0] + " - " + oa[1]);
        }
        return retList;
    }

    @Override
    public String getRepositoryURL(String repositoryKey, EMCUserData userData) {
        String[] custRepSplit = repositoryKey.split(" - ");

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevVersioningControl.class);
        query.addAnd("customerId", custRepSplit[0]);
        query.addAnd("repositoryName", custRepSplit[1]);
        query.addField("repositoryURL");

        return (String) util.executeSingleResultQuery(query, userData);
    }
}

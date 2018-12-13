/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.pop;

import emc.entity.pop.POPQualityTestType;
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
public class POPQualityTestTypeBean extends EMCEntityBean implements POPQualityTestTypeLocal{

    public POPQualityTestTypeBean() {
    }
    
    public String findDesc(String qualityTestId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPQualityTestType.class.getName());
        query.addAnd("testTypeId", qualityTestId);
        query.addField("description");
        return (String)util.executeSingleResultQuery(query, userData);
    }

}

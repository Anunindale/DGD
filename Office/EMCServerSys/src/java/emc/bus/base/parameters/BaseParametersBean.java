/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.parameters;

import emc.entity.base.BaseParameters;
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
public class BaseParametersBean extends EMCEntityBean implements BaseParametersLocal {

    @Override
    public BaseParameters getBaseParameters(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseParameters.class);
        return (BaseParameters) util.executeSingleResultQuery(query, userData);
    }
}

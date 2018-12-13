/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.parameters;

import emc.entity.developertools.DevParameters;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevParametersBean extends EMCEntityBean implements DevParametersLocal {

    public DevParameters getParameters(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevParameters.class.getName());
        DevParameters param = (DevParameters) util.executeSingleResultQuery(query, userData);
        if (param == null) {
            logMessage(Level.INFO, "No Parameters found for dev. Param will be Created", userData);
            param = new DevParameters();
            this.insert(param, userData);
        }
        return param;
    }
}

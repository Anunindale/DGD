/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.crm.parameters;

import emc.entity.crm.CRMParameters;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class CRMParametersBean extends EMCEntityBean implements CRMParametersLocal {

    public CRMParameters getParameter(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CRMParameters.class.getName());
        CRMParameters param = (CRMParameters) util.executeSingleResultQuery(query, userData);
        if (param == null) {
            param = new CRMParameters();
            this.insert(param, userData);
            Logger.getLogger("emc").log(Level.SEVERE, "No parameters found for CRM, new record created.", userData);
        }
        return param;
    }
}

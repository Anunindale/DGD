/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.parameters;

import emc.entity.creditors.CreditorsParameters;
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
 * @author claudette
 */
@Stateless
public class CreditorsParametersBean extends EMCEntityBean implements CreditorsParametersLocal {

    public CreditorsParameters getParameter(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsParameters.class.getName());
        CreditorsParameters param = (CreditorsParameters) util.executeSingleResultQuery(query, userData);
        if (param == null) {
            param = new CreditorsParameters();
            this.insert(param, userData);
            Logger.getLogger("emc").log(Level.SEVERE, "No parameters found for Creditors, new record created.", userData);
        }
        return param;
    }
}

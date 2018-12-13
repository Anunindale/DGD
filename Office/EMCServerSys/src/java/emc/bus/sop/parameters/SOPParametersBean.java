/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.parameters;

import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPSalesRepCommission;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
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
public class SOPParametersBean extends EMCEntityBean implements SOPParametersLocal {

    public SOPParameters getParameterRecord(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPParameters.class);
        return (SOPParameters) util.executeSingleResultQuery(query, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            if (fieldNameToValidate.contains("customerItemTable")) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
                if (util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Sales rep commission data already exists. You may not change the Commission fields.");
                    valid = false;
                }
            }
        }
        return valid;
    }

    public boolean deactivateCommissionFields(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesRepCommission.class);
        return util.exists(query, userData);
    }
}

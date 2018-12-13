/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop;

import emc.entity.pop.POPParameters;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class POPParametersBean extends EMCEntityBean implements POPParametersLocal {

    /** Creates a new instance of POPParametersBean */
    public POPParametersBean() {
    }

    /** Returns a boolean indicating whether supplier reference should be displayed. */
    public boolean getDisplaySupperRef(EMCUserData userData) {
        String className = POPParameters.class.getName();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, className);
        query.addField("displaySupplierItemRef");
        query.addAnd("companyId", util.getCompanyId(className, userData));

        Object result = util.executeSingleResultQuery(query, userData);
        return result == null ? false : (Boolean) result;
    }

    public boolean serialMoreThanOne(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPParameters.class.getName());
        query.addField("serialMoreThanOne");
        query.addAnd("companyId", userData.getCompanyId());
        Object o = util.executeSingleResultQuery(query, userData);
        return (o == null ? false : (Boolean) o);
    }

    /** Returns the parameters for a company.  If no parameters exist for the company a set of defualt parameters will be created. */
    public POPParameters getPOPParameters(EMCUserData userData) {
        String className = POPParameters.class.getName();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, className);
        query.addAnd("companyId", util.getCompanyId(className, userData));

        POPParameters params = (POPParameters) util.executeSingleResultQuery(query, userData);

        if (params == null) {
            params = new POPParameters();

            try {
                insert(params, userData);
            } catch (EMCEntityBeanException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to create POPParameters.", userData);
                }
            }

        }

        return params;
    }

    public List<String> getPlannedReleaseItemFields(EMCUserData userData) {
        POPParameters param = getPOPParameters(userData);
        List<String> retList = new ArrayList<String>();
        if (!isBlank(param.getItemField1ForPlannedRelease())) {
            retList.add(param.getItemField1ForPlannedRelease());
        }
        if (!isBlank(param.getItemField2ForPlannedRelease())) {
            retList.add(param.getItemField2ForPlannedRelease());
        }
        return retList;
    }
}

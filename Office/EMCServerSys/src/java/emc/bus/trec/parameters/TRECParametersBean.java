/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.parameters;

import emc.entity.trec.TRECParameters;
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
public class TRECParametersBean extends EMCEntityBean implements TRECParametersLocal {

    @Override
    public TRECParameters getParameterRecord(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECParameters.class);
        TRECParameters param = (TRECParameters) util.executeSingleResultQuery(query, userData);
        if (param == null) {
            param = new TRECParameters();
        }

        return param;
    }
}

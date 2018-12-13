/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.preferredshipname;

import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECPreferredShipName;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Chris
 */
@Stateless
public class TRECPreferredShipNameBean extends EMCEntityBean implements TRECPreferredShipNameLocal {

    @Override
    public List getPreferredShipNameList(String unNumber, String ShippingName, EMCUserData userData) {

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        query.addAnd("unNumber", unNumber);
        query.addAnd("shippingName", ShippingName);
        TRECChemicals chemical = (TRECChemicals) util.executeSingleResultQuery(query, userData);

        if (isBlank(chemical)) {
            return null;
        } else {
            EMCQuery query1 = new EMCQuery(enumQueryTypes.SELECT, TRECPreferredShipName.class);
            query1.addAnd("chemicalLink", chemical.getRecordID());
            query1.addField("properShipName");
            List<String> prefShipName = util.executeGeneralSelectQuery(query1, userData);
            if(prefShipName.isEmpty()){
                return null;
            }else{
                return prefShipName;
            }
        }

    }
    
}

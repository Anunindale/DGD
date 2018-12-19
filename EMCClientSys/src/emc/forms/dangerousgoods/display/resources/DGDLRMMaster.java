/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.dangerousgoods.DGDContacts;
import emc.enums.dangerousgoods.ContactType;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;

/**
 *
 * @author pj
 */
public class DGDLRMMaster extends EMCLookupRelationManager{

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) 
    {
            EMCQuery consignorQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            EMCQuery operatorQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);

                consignorQuery.addAnd("customer", drm.getLastFieldValueAt("customer"));
                consignorQuery.addAnd("type", ContactType.CONSIGNOR.toString());
                getLookupFromName("defconsignorlkp").get(0).setTheQuery(consignorQuery);

                operatorQuery.addAnd("customer", drm.getLastFieldValueAt("customer"));
                operatorQuery.addAnd("type", ContactType.OPERATOR.toString());
                getLookupFromName("defoperatorlkp").get(0).setTheQuery(operatorQuery);
    }

    @Override
    public void doRelation(EMCLookup lookup) 
    {

    }
    
    
}

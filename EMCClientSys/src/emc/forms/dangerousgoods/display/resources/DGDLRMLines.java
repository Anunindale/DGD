/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.enums.dangerousgoods.ContactType;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;

/**
 *
 * @author pj
 */
public class DGDLRMLines extends EMCLookupRelationManager{

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) 
    {
            EMCQuery consignorQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);

                consignorQuery.addTableAnd(DGDeclarationLines.class.getName(), "customer", DGDContacts.class.getName(), "customer");
                consignorQuery.addAnd("customer", drm.getLastFieldValueAt("customer"));
                consignorQuery.addAnd("consignor", ContactType.CONSIGNOR.toString());
                getLookupFromName("consignor").get(0).setTheQuery(consignorQuery);

    }

    @Override
    public void doRelation(EMCLookup lookup) 
    {

    }
    
}

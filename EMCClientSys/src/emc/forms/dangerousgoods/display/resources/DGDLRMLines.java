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
import emc.enums.emcquery.EMCQueryConditions;
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
                consignorQuery.addAnd("customer", drm.getHeaderTable().getLastFieldValueAt("customer"));
                consignorQuery.addAnd("type", ContactType.CONSIGNOR.toString());
                getLookupFromName("consignor").get(0).setTheQuery(consignorQuery);
            
            EMCQuery consigneeQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
                consigneeQuery.addAnd("customer", drm.getHeaderTable().getLastFieldValueAt("customer"));
                consigneeQuery.addAnd("type", ContactType.CONSIGNEE.toString());
                getLookupFromName("consignee").get(0).setTheQuery(consigneeQuery);
            
            EMCQuery operatorQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
                operatorQuery.addAnd("customer", drm.getHeaderTable().getLastFieldValueAt("customer"));
                operatorQuery.addAnd("type", ContactType.OPERATOR.toString());
                getLookupFromName("operator").get(0).setTheQuery(operatorQuery);
            
            EMCQuery custodianQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
                custodianQuery.addAnd("customer", drm.getHeaderTable().getLastFieldValueAt("customer"));
                custodianQuery.addAnd("type", ContactType.PRODUCT_CUSTODIAN.toString());
                getLookupFromName("productcustodian").get(0).setTheQuery(custodianQuery);
            
            EMCQuery ownerQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);                
                ownerQuery.addAnd("customer", drm.getHeaderTable().getLastFieldValueAt("customer"));
                ownerQuery.addAnd("type", ContactType.PRODUCT_OWNER.toString());
                getLookupFromName("productowner").get(0).setTheQuery(ownerQuery);
            
            EMCQuery manufacturerQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
                manufacturerQuery.addAnd("customer", drm.getHeaderTable().getLastFieldValueAt("customer"));
                manufacturerQuery.addAnd("type", ContactType.PRODUCT_MANUFACTURER.toString());
                getLookupFromName("productmanufacturer").get(0).setTheQuery(manufacturerQuery);
           
            EMCQuery partyQuery = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
                partyQuery.addAnd("customer", drm.getHeaderTable().getLastFieldValueAt("customer"));
                partyQuery.addAnd("type", ContactType.PARTY_CONTRACTING_THE_OPERATOR.toString());
                getLookupFromName("contractingparty").get(0).setTheQuery(partyQuery);

    }

    @Override
    public void doRelation(EMCLookup lookup) 
    {
        
    }
    
}

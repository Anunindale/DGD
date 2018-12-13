/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.inventory.register.forms.superclass;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.inventory.register.forms.remove.RemoveRegisterForm;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.app.inventory.register.RegisterFormInterface;
import emc.entity.inventory.InventoryLocation;
import emc.entity.inventory.InventoryWarehouse;
import emc.entity.inventory.transactions.InventorySummary;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.register.RegisterFormTypeEnum;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;

/**
 *
 * @author wikus
 */
public class RegisterLRM extends EMCLookupRelationManager {

    private RegisterFormTypeEnum type;
    private EMCUserData userData;
    private RegisterFormInterface theForm;

    public RegisterLRM(EMCUserData userData) {
        this.type = RegisterFormTypeEnum.FIRST_TIME;
        this.userData = userData;
    }

    public RegisterLRM(RegisterFormInterface theForm, EMCUserData userData) {
        this.type = RegisterFormTypeEnum.MUST_EXIST;
        this.userData = userData;
        this.theForm = theForm;
    }

    @Override
    public void doRelation(EMCLookup lookup) {
        setLookupQuery();
    }

    @Override
    public void doRowChanged(emcDataRelationManagerUpdate drm) {
        setLookupQuery();
    }

    private void setLookupQuery() {
        EMCQuery theQuery;
        EMCLookup theLookup;
        switch (type) {
            case FIRST_TIME:
                //Warehouse
                theLookup = getLookupFromName("warehouse").get(0);
                theQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryWarehouse.class.getName());
                theQuery.addAnd("companyId", userData.getCompanyId());
                if (!Functions.checkBlank(getLookupFromName("location").get(0).getValue())) {
                    theQuery.addTableAnd(InventoryLocation.class.getName(), "warehouseId", InventoryWarehouse.class.getName(), "warehouseId");
                    theQuery.addAnd("locationId", getLookupFromName("location").get(0).getValue(), InventoryLocation.class.getName());
                }
                theLookup.setTheQuery(theQuery);

                //Location
                theLookup = getLookupFromName("location").get(0);
                theQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryLocation.class.getName());
                theQuery.addAnd("companyId", userData.getCompanyId());
                if (!Functions.checkBlank(getLookupFromName("warehouse").get(0).getValue())) {
                    theQuery.addAnd("warehouseId", getLookupFromName("warehouse").get(0).getValue());
                }
                theLookup.setTheQuery(theQuery);
                
                //Pallet
                //NOT NEEDED JUST YET
                break;
            case PRODUCTION_BOM_JOURNAL: //fall through
            case MUST_EXIST:
                //Serial
                theLookup = getLookupFromName("serial").get(0);
                theQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                theQuery.addAnd("itemId", theForm.getItemId());
                theQuery.addAnd("dimension1", theForm.getDimension1());
                theQuery.addAnd("dimension2", theForm.getDimension2());
                theQuery.addAnd("dimension3", theForm.getDimension3());
                if (!Functions.checkBlank(getLookupFromName("batch").get(0).getValue())) theQuery.addAnd("batch", getLookupFromName("batch").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("warehouse").get(0).getValue())) theQuery.addAnd("warehouse", getLookupFromName("warehouse").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("location").get(0).getValue())) theQuery.addAnd("location", getLookupFromName("location").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("pallet").get(0).getValue())) theQuery.addAnd("pallet", getLookupFromName("pallet").get(0).getValue());
                theLookup.setTheQuery(theQuery);

                //Batch
                theLookup = getLookupFromName("batch").get(0);
                theQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                theQuery.addAnd("itemId", theForm.getItemId());
                theQuery.addAnd("dimension1", theForm.getDimension1());
                theQuery.addAnd("dimension2", theForm.getDimension2());
                theQuery.addAnd("dimension3", theForm.getDimension3());
                if (!Functions.checkBlank(getLookupFromName("serial").get(0).getValue())) theQuery.addAnd("serialNo", getLookupFromName("serial").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("warehouse").get(0).getValue())) theQuery.addAnd("warehouse", getLookupFromName("warehouse").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("location").get(0).getValue())) theQuery.addAnd("location", getLookupFromName("location").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("pallet").get(0).getValue())) theQuery.addAnd("pallet", getLookupFromName("pallet").get(0).getValue());
                theLookup.setTheQuery(theQuery);

                //Warehouse
                theLookup = getLookupFromName("warehouse").get(0);
                theQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                theQuery.addAnd("itemId", theForm.getItemId());
                theQuery.addAnd("dimension1", theForm.getDimension1());
                theQuery.addAnd("dimension2", theForm.getDimension2());
                theQuery.addAnd("dimension3", theForm.getDimension3());
                if (!Functions.checkBlank(getLookupFromName("serial").get(0).getValue())) theQuery.addAnd("serialNo", getLookupFromName("serial").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("batch").get(0).getValue())) theQuery.addAnd("batch", getLookupFromName("batch").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("location").get(0).getValue())) theQuery.addAnd("location", getLookupFromName("location").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("pallet").get(0).getValue())) theQuery.addAnd("pallet", getLookupFromName("pallet").get(0).getValue());
                theLookup.setTheQuery(theQuery);

                //Location
                theLookup = getLookupFromName("location").get(0);
                theQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                theQuery.addAnd("itemId", theForm.getItemId());
                theQuery.addAnd("dimension1", theForm.getDimension1());
                theQuery.addAnd("dimension2", theForm.getDimension2());
                theQuery.addAnd("dimension3", theForm.getDimension3());
                if (!Functions.checkBlank(getLookupFromName("serial").get(0).getValue())) theQuery.addAnd("serialNo", getLookupFromName("serial").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("batch").get(0).getValue())) theQuery.addAnd("batch", getLookupFromName("batch").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("warehouse").get(0).getValue())) theQuery.addAnd("warehouse", getLookupFromName("warehouse").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("pallet").get(0).getValue())) theQuery.addAnd("pallet", getLookupFromName("pallet").get(0).getValue());
                theLookup.setTheQuery(theQuery);

                //Pallet
                theLookup = getLookupFromName("pallet").get(0);
                theQuery = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class.getName());
                theQuery.addAnd("itemId", theForm.getItemId());
                theQuery.addAnd("dimension1", theForm.getDimension1());
                theQuery.addAnd("dimension2", theForm.getDimension2());
                theQuery.addAnd("dimension3", theForm.getDimension3());
                if (!Functions.checkBlank(getLookupFromName("serial").get(0).getValue())) theQuery.addAnd("serialNo", getLookupFromName("serial").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("batch").get(0).getValue())) theQuery.addAnd("batch", getLookupFromName("batch").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("warehouse").get(0).getValue())) theQuery.addAnd("warehouse", getLookupFromName("warehouse").get(0).getValue());
                if (!Functions.checkBlank(getLookupFromName("location").get(0).getValue())) theQuery.addAnd("location", getLookupFromName("location").get(0).getValue());
                theLookup.setTheQuery(theQuery);

                break;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.inventoryreference;

import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.entity.inventory.InventoryReference;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class InventoryReferenceDRM extends EMCControlLookupComponentDRM {

    public InventoryReferenceDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object itemId,itemReference;
        List x;
        EMCQuery query;

        itemId = super.getFieldValueAt(this.getLastRowAccessed(), "itemId");
        itemReference = super.getFieldValueAt(this.getLastRowAccessed(), "itemPrimaryReference");

        switch (Index) {

            case 0:
                if (itemId != null) {
                    x = new ArrayList();
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
                    query.addAnd("itemId", itemId);
                    query.addAnd("refType", InventoryReferenceTypes.SUPPLIER.toString());
                    query.addOrderBy("supplierNo");
                    x.add(0, query);
                    x.add(1, query.getCountQuery());
                    x.add(2, super.getFieldValueAt(this.getLastRowAccessed(), "itemDesc"));
                    x.add(3, itemReference!=null?itemReference:itemId);
                    x.add(4, super.getFieldValueAt(this.getLastRowAccessed(), "itemId"));
                    formUserData.setUserData(x);
                }
                break;
            case 1:
                if (itemId != null) {
                    x = new ArrayList();
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
                    query.addAnd("itemId", itemId);
                    query.addAnd("refType", InventoryReferenceTypes.CUSTOMER.toString());
                    query.addOrderBy("customerNo");
                    x.add(0, query);
                    x.add(1, query.getCountQuery());
                    x.add(2, super.getFieldValueAt(this.getLastRowAccessed(), "itemDesc"));
                    x.add(3, itemReference!=null?itemReference:itemId);
                    x.add(4, super.getFieldValueAt(this.getLastRowAccessed(), "itemId"));
                    formUserData.setUserData(x);
                }
                break;
        }
        return formUserData;
    }
}

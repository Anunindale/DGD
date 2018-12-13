/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.suppliers;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.inventory.InventoryReference;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SuppliersDRM extends emcDataRelationManagerUpdate {

    public SuppliersDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object suppId;
        List x;
        EMCQuery query;
        switch (Index) {
            case 0:
                suppId = super.getFieldValueAt(this.getLastRowAccessed(), "supplierId");
                if (suppId != null) {
                    x = new ArrayList();
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
                    query.addAnd("supplierNo", suppId);
                    query.addOrderBy("itemId");
                    x.add(0, query.toString());
                    x.add(1, query.getCountQuery());
                    x.add(2, suppId);
                    x.add(3, super.getFieldValueAt(this.getLastRowAccessed(), "supplierName"));
                    formUserData.setUserData(x);
                }
                break;
        }
        return formUserData;
    }
}

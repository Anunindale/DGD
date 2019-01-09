/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.dimension2groups;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension2GroupSetup;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class Dimension2GroupsDRM extends emcDataRelationManagerUpdate {

    public Dimension2GroupsDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Object dimGroupId;
        Object description;
        List x;
        switch (Index) {
            case 0:
                dimGroupId = super.getFieldValueAt(this.getLastRowAccessed(), "dimensionGroupId");
                description = super.getFieldValueAt(this.getLastRowAccessed(), "description");
                String recordID = super.getFieldValueAt(this.getLastRowAccessed(), "recordID").toString();
                if (!recordID.equals("0")) {
                    if (dimGroupId != null && description != null) {
                        x = new ArrayList();
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension2GroupSetup.class.getName());
                        query.addTableAnd(InventoryDimension2.class.getName(), "dimensionId", InventoryDimension2GroupSetup.class.getName(), "dimensionId");
                        query.addAnd("dimensionGroupId", dimGroupId.toString(), InventoryDimension2GroupSetup.class.getName());
                        query.addOrderBy("sortCode", InventoryDimension2.class.getName());
                        x.add(0, query);
                        x.add(1, "");
                        x.add(2, description.toString());
                        x.add(3, dimGroupId.toString());
                        formUserData.setUserData(x);
                    }
                }
                break;
            default:
                break;
        }
        return formUserData;
    }
}

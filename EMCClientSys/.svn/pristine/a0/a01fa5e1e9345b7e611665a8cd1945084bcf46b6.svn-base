/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.dimension1groups;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension1GroupSetup;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class Dimension1GroupsDRM extends emcDataRelationManagerUpdate {

    public Dimension1GroupsDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
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
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1GroupSetup.class.getName());
                        query.addTableAnd(InventoryDimension1.class.getName(), "dimensionId", InventoryDimension1GroupSetup.class.getName(), "dimensionId");
                        query.addAnd("dimensionGroupId", dimGroupId.toString(), InventoryDimension1GroupSetup.class.getName());
                        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
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

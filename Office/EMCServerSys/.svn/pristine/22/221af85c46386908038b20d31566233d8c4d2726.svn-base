/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.dimensions.InventoryItemDimension1SetupLocal;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimension1SetupDSBean extends EMCDataSourceBean implements InventoryItemDimension1SetupDSLocal {

    @EJB
    private InventoryItemDimension1SetupLocal itemDimensionSetupBean;

    /** Creates a new instance of InventoryItemDimension1SetupDSBean */
    public InventoryItemDimension1SetupDSBean() {
        setDataSourceClassName(InventoryItemDimension1SetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        return dataSourceInstance;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return itemDimensionSetupBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addTableAnd(InventoryDimension1.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addTableAsField(InventoryDimension1.class.getName());

        return super.getNumRows(theTable, userData);
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addTableAnd(InventoryDimension1.class.getName(), "dimensionId", InventoryItemDimension1Setup.class.getName(), "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension1.class.getName());
        query.addTableAsField(InventoryDimension1.class.getName());

        return super.getDataInRange(theTable, userData, start, end);
    }
}

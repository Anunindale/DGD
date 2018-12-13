/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.dimensions.InventoryItemDimension2SetupLocal;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryItemDimension2Setup;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension2SetupDS;
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
public class InventoryItemDimension2SetupDSBean extends EMCDataSourceBean implements InventoryItemDimension2SetupDSLocal {

    @EJB
    private InventoryItemDimension2SetupLocal itemDimensionSetupBean;

    /** Creates a new instance of InventoryItemDimension2SetupDSBean */
    public InventoryItemDimension2SetupDSBean() {
        setDataSourceClassName(InventoryItemDimension2SetupDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        return dataSourceInstance;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = itemDimensionSetupBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);

        if (ret instanceof InventoryItemDimension2Setup) {
            ret = convertSuperToDataSource(ret, userData);
        }

        return ret;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addTableAnd(InventoryDimension2.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension2.class.getName());
        query.addTableAsField(InventoryDimension2.class.getName());

        return super.getNumRows(theTable, userData);
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addTableAnd(InventoryDimension2.class.getName(), "dimensionId", InventoryItemDimension2Setup.class.getName(), "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension2.class.getName());
        query.addTableAsField(InventoryDimension2.class.getName());

        return super.getDataInRange(theTable, userData, start, end);
    }
}

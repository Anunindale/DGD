/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.dimensions.InventoryItemDimension3SetupLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.InventoryItemDimension3Setup;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension3SetupDS;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimension3SetupDSBean extends EMCDataSourceBean implements InventoryItemDimension3SetupDSLocal {

    @EJB
    private InventoryItemDimension3SetupLocal itemDimensionSetupBean;

    /** Creates a new instance of InventoryItemDimension3SetupDSBean */
    public InventoryItemDimension3SetupDSBean() {
        setDataSourceClassName(InventoryItemDimension3SetupDS.class.getName());
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
        query.addTableAnd(InventoryDimension3.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        query.addTableAsField(InventoryDimension3.class.getName());

        return super.getNumRows(theTable, userData);
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        query.addTableAnd(InventoryDimension3.class.getName(), "dimensionId", InventoryItemDimension3Setup.class.getName(), "dimensionId");
        query.addOrderBy("sortCode", InventoryDimension3.class.getName());
        query.addTableAsField(InventoryDimension3.class.getName());

        return super.getDataInRange(theTable, userData, start, end);
    }

}

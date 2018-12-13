/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryItemDimension1SetupLocal;
import emc.bus.inventory.dimensions.InventoryItemDimensionGroupLocal;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimension1SetupFormDS;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.dimensions.DimensionsEnum;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimension1SetupFormDSBean extends EMCDataSourceBean implements InventoryItemDimension1SetupFormDSLocal {

    @EJB
    private InventoryItemDimension1SetupLocal itemDimensionSetupBean;
    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryItemDimensionGroupLocal dimGroupBean;

    /** Creates a new instance of InventoryItemDimension1SetupFormDSBean */
    public InventoryItemDimension1SetupFormDSBean() {
        setDataSourceClassName(InventoryItemDimension1SetupFormDS.class.getName());
    }

    /** This method creates records when the table is empty */
    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return itemDimensionSetupBean.getNumRows(theTable, userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return itemDimensionSetupBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return itemDimensionSetupBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return itemDimensionSetupBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryItemDimension1SetupFormDS ds = (InventoryItemDimension1SetupFormDS) theRecord;
        if (fieldNameToValidate.equalsIgnoreCase("itemPrimaryReference")) {
            String[] sArray = referenceBean.checkItemReference(ds.getItemPrimaryReference(), userData);
            if (sArray != null) {
                if (!dimGroupBean.isDimensionActiveOnItem(sArray[0], DimensionsEnum.DIMENSION1, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Config is not active on the selected item.", userData);
                    return false;
                }
                ds.setItemId(sArray[0]);
                ds.setItemPrimaryReference(sArray[1]);
                return ds;
            } else {
                return false;
            }
        }
        
        Object ret = itemDimensionSetupBean.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    
        if (ret instanceof InventoryItemDimension1Setup) {
            ret = convertSuperToDataSource(ret, userData);
        }
        
        return ret;
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryItemDimension1SetupFormDS ds = (InventoryItemDimension1SetupFormDS) dataSourceInstance;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class.getName());
        query.addAnd("dimensionId", ds.getDimensionId());
        InventoryDimension1 dim1 = (InventoryDimension1) util.executeSingleResultQuery(query, userData);

        ds.setDescription(dim1.getDescription());
        ds.setSortCode(dim1.getSortCode());

        query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
        query.addField("reference");
        query.addField("refType");
        query.addAnd("itemId", ds.getItemId());
        query.addOrderBy("refType", InventoryReference.class.getName(), EMCQueryOrderByDirections.DESC);
        List<Object[]> list = util.executeGeneralSelectQuery(query, userData);
        for (Object[] s : list) {
            if (s[1].toString().equals(InventoryReferenceTypes.DEFAULT.toString()) || s[1].toString().equals(InventoryReferenceTypes.PRIMARY.toString())) {
                ds.setItemPrimaryReference(s[0].toString());
                break;
            }
        }

        return ds;
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        List<InventoryItemDimension1SetupFormDS> ret = (List<InventoryItemDimension1SetupFormDS>) super.getDataInRange(theTable, userData, start, end);

        Collections.sort(ret);

        return ret;
    }
}

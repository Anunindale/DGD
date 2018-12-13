/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.datasource;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryItemDimensionCombinationsLocal;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.entity.inventory.dimensions.datasource.InventoryItemDimensionCombinationsDS;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryItemDimensionCombinationsDSBean extends EMCDataSourceBean implements InventoryItemDimensionCombinationsDSLocal {

    @EJB
    private InventoryReferenceLocal referenceBean;
    @EJB
    private InventoryItemDimensionCombinationsLocal combBean;

    /** Creates a new instance of InventoryItemDimensionCombinationsDSBean. */
    public InventoryItemDimensionCombinationsDSBean() {
        this.setDataSourceClassName(InventoryItemDimensionCombinationsDS.class.getName());
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return combBean.getNumRows(InventoryItemDimensionCombinations.class, userData);
    }

    /** This method caters specifically for the lookups on the Purchase Order form. */
    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        Collection superData = combBean.getDataInRange(InventoryItemDimensionCombinations.class, userData, start, end);

        List thisData = convertSuperToDataSource(theTable, (List) superData, userData);

        for (Object instance : thisData) {
            populateDataSourceFields((EMCEntityClass) instance, userData);
        }

        return thisData;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        InventoryItemDimensionCombinationsDS ds = (InventoryItemDimensionCombinationsDS) theRecord;
        Object ret = combBean.validateField(fieldNameToValidate, convertDataSourceToSuper(ds, userData), userData);
        if (ret instanceof InventoryItemDimensionCombinations || (Boolean) ret) {
            ret = ret instanceof InventoryItemDimensionCombinations ? this.convertSuperToDataSource(ret, userData) : ds;
            if (fieldNameToValidate.equalsIgnoreCase("itemPrimaryReference")) {
                String[] sArray = referenceBean.checkItemReference(ds.getItemPrimaryReference(), userData);
                if (sArray != null) {
                    ds.setItemId(sArray[0]);
                    ds.setItemPrimaryReference(sArray[1]);
                    return ds;
                } else {
                    return false;
                }
            }
        }
        return ret;
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryItemDimensionCombinationsDS ds = (InventoryItemDimensionCombinationsDS) dataSourceInstance;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
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
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return combBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return combBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return combBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }
}

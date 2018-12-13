/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.inventory.dimensions.datasource.lines;

import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.inventory.dimensions.lines.InventoryDimension1LinesLocal;
import emc.entity.inventory.dimensions.datasource.InventoryDimension1LinesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryDimension1LinesDSBean extends EMCDataSourceBean implements InventoryDimension1LinesDSLocal {

    @EJB
    private InventoryDimension1LinesLocal dim1LinesBean;
    @EJB
    private InventoryDimension3Local dim3Bean;

    /** Creates a new instance of InventoryDimension1LinesDSBean */
    public InventoryDimension1LinesDSBean() {
        this.setDataSourceClassName(InventoryDimension1LinesDS.class.getName());
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return dim1LinesBean.delete(super.convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return dim1LinesBean.update(super.convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return dim1LinesBean.insert(super.convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        InventoryDimension1LinesDS ds = (InventoryDimension1LinesDS)dataSourceInstance;

        ds.setDim3Desc(dim3Bean.findDimensionDescription(ds.getDimension3Id(), userData));

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = dim1LinesBean.validateField(fieldNameToValidate, theRecord, userData);

        if (ret instanceof Boolean && (Boolean)ret) {
            InventoryDimension1LinesDS ds = (InventoryDimension1LinesDS)theRecord;
            if (fieldNameToValidate.equals("dimension3Id")) {
                ret = populateDataSourceFields(ds, userData);
            }
        }

        return ret;
    }


}

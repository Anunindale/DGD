/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions.lines;

import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.lines.InventoryDimension1Lines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class InventoryDimension1LinesBean extends EMCEntityBean implements InventoryDimension1LinesLocal {

    @EJB
    private InventoryDimension1Local dimension1Bean;

    /** Creates a new instance of InventoryDimension1LinesBean */
    public InventoryDimension1LinesBean() {
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryDimension1Lines record = (InventoryDimension1Lines) iobject;
        deactivateMaster(record.getDimension1Id(), userData);
        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryDimension1Lines record = (InventoryDimension1Lines) uobject;
        deactivateMaster(record.getDimension1Id(), userData);
        return super.update(uobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryDimension1Lines record = (InventoryDimension1Lines) dobject;
        deactivateMaster(record.getDimension1Id(), userData);
        return super.delete(dobject, userData);
    }

    private void deactivateMaster(String dimension1Id, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);
        query.addAnd("dimensionId", dimension1Id);
        InventoryDimension1 dim1 = (InventoryDimension1) util.executeSingleResultQuery(query, userData);
        if (dim1 == null) {
            throw new EMCEntityBeanException("Failed to find Dimension Record");
        }
        if (dim1.isActive()) {
            dim1.setActive(false);
            dimension1Bean.update(dim1, userData);
        }
    }
}

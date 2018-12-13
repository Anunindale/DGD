/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.dimensions;

import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
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
public class InventoryDimension3Bean extends EMCEntityBean implements InventoryDimension3Local {

    @EJB
    private InventoryDimension3Local thisBean;

    /** Creates a new instance of InventoryDimension3Bean */
    public InventoryDimension3Bean() {
    }

    private boolean checkColourDesign(InventoryDimension3 dim3, EMCUserData userData) {
        boolean res = true;

        if (isBlank(dim3.getSourceColRef()) && isBlank(dim3.getDesignNo()) && isBlank(dim3.getColourWay()) && isBlank(dim3.getBaseColour())) {
            return true;
        }

        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
        qu.addAnd("sourceColRef", dim3.getSourceColRef());
        qu.addAnd("designNo", dim3.getDesignNo());
        qu.addAnd("colourWay", dim3.getColourWay());
        qu.addAnd("baseColour", dim3.getBaseColour());
        qu.addAnd("recordID", dim3.getRecordID(), EMCQueryConditions.NOT);

        List<InventoryDimension3> qRes = util.executeGeneralSelectQuery(qu, userData);
        if (qRes != null && qRes.size() > 0) {
            res = false;
            Logger.getLogger("emc").log(Level.SEVERE, "The Source Colour, Design Number,Base Colour, Colour Way combination is in use.", userData);
        }
        return res;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        InventoryDimension3 dim3 = (InventoryDimension3) vobject;
        if (this.isBlank(dim3.getSourceColRef())) {
            Logger.getLogger("emc").log(Level.WARNING, "No Source Colour entered.", userData);
        }

        return super.doInsertValidation(vobject, userData) && checkColourDesign(dim3, userData);
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        InventoryDimension3 dim3 = (InventoryDimension3) vobject;
        return super.doUpdateValidation(vobject, userData) && checkColourDesign(dim3, userData) && checkSourceColRef(dim3, userData);
    }

    /** Checks that the SourceColRef field is not blank.  Included for old data. */
    private boolean checkSourceColRef(InventoryDimension3 dim3, EMCUserData userData) {
        if (isBlank(dim3.getSourceColRef())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Source Col Ref is mandatory.  Please enter data.", userData);
            return false;
        } else {
            return true;
        }
    }

    public String findDimensionDescription(String dimensionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
        query.addAnd("dimensionId", dimensionId);
        query.addField("description");
        Object ret = util.executeSingleResultQuery(query, userData);
        return isBlank(ret) ? "" : ret.toString();
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return super.getNumRows(theTable, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = super.insert(iobject, userData);
        thisBean.updateSortCodes(userData);
        return ret;
    }

    public void updateSortCodes(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class.getName());
        query.addOrderBy("dimensionId");
        List<InventoryDimension3> dimList = util.executeGeneralSelectQuery(query, userData);
        int count = 0;
        for (InventoryDimension3 dim : dimList) {
            if (dim.getSortCode() != count) {
                dim.setSortCode(count);
                this.doUpdate(dim, userData);
            }
            count++;
        }
    }

    public InventoryDimension3 getDimension3Record(String dimensionId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3.class);
        query.addAnd("dimensionId", dimensionId);
        return (InventoryDimension3) util.executeSingleResultQuery(query, userData);
    }
}

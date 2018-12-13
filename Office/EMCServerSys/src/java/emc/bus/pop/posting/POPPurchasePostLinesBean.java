/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.pop.posting;

import emc.bus.inventory.dimensions.InventoryDimensionTableLocal;
import emc.entity.inventory.dimensions.InventoryDimensionTable;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.pop.posting.POPPurchasePostLines;
import emc.enums.enumQueryTypes;
import emc.enums.pop.posting.DocumentTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.Collection;
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
public class POPPurchasePostLinesBean extends EMCEntityBean implements POPPurchasePostLinesLocal {

    @EJB
    private InventoryDimensionTableLocal dimBean;

    /** Creates a new instance of POPPurchasePostLinesBean */
    public POPPurchasePostLinesBean() {
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        return super.getDataInRange(theTable, userData, start, end);
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return super.getNumRows(theTable, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            if (DocumentTypes.RETURN_GOODS.toString().equals(userData.getUserData(2))) {
                POPPurchasePostLines line = (POPPurchasePostLines) theRecord;
                if (fieldNameToValidate.equals("quantity")) {
                    ret = validateQtyToReturn(line, userData);
                }
            }
        }
        return ret;
    }

    private boolean validateQtyToReturn(POPPurchasePostLines line, EMCUserData userData) {
        InventoryDimensionTable dimRec = dimBean.getDimensionRecord(line.getDimId(), userData);
        if (dimRec == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to get dimension record", userData);
            return false;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addAnd("itemId", line.getItemId());
        query.addAnd("dimension1", dimRec.getDimension1Id());
        query.addAnd("dimension2", dimRec.getDimension2Id());
        query.addAnd("dimension3", dimRec.getDimension3Id());
        query.addAnd("warehouse", dimRec.getWarehouseId());
        query.addFieldAggregateFunction("physicalAvailable", "SUM");
        query.addFieldAggregateFunction("qcAvailable", "SUM");
        query.addFieldAggregateFunction("recAvailable", "SUM");
        Object[] qtyArray = (Object[]) util.executeSingleResultQuery(query, userData);
        double totalQty = 0;
        for (Object o : qtyArray) {
            totalQty += (Double)o;
        }
        if (line.getQuantity() > totalQty) {
            Logger.getLogger("emc").log(Level.SEVERE, "There is only " + totalQty + " items available to return.", userData);
            return false;
        } else return true;
    }
}

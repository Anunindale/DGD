/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.itemdimensions;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.constants.systemConstants;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Name         : InventoryItemDimensionReportBean
 *
 * @Date         : 24 Aug 2009
 *
 * @Description  : Bean for the item dimension report.
 *
 * @author       : riaan
 */
@Stateless
public class InventoryItemDimensionReportBean extends EMCReportBean implements InventoryItemDimensionReportLocal {

    @EJB
    private InventoryDimension3Local dimension3Bean;
    @EJB
    private InventoryItemMasterLocal itemBean;

    /** Creates a new instance of InventoryItemDimensionReportBean. */
    public InventoryItemDimensionReportBean() {
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        InventoryItemDimensionCombinations combination;
        List retList = new ArrayList();

        Map<String, InventoryItemMaster> itemMap = new HashMap<String, InventoryItemMaster>();
        Map<String, String> dimension3Map = new HashMap<String, String>();

        String itemId;
        InventoryItemDimensionReportDS ds;
        String dimension1;
        String dimension2;
        String dimension3;
        String dimension3Description;

        for (Object result : queryResult) {
            combination = (InventoryItemDimensionCombinations) result;

            ds = new InventoryItemDimensionReportDS();

            itemId = combination.getItemId();
            InventoryItemMaster item = itemMap.get(itemId);

            if (item == null) {
                //Fall over if item not found
                item = itemBean.findItem(itemId, userData);
                itemMap.put(itemId, item);
            }
            
            ds.setItemReference(item.getItemReference());
            ds.setItemDescription(item.getDescription());
            ds.setItemGroup(item.getItemGroup());

            dimension1 = systemConstants.emcNA().equals(combination.getDimension1Id()) ? null : combination.getDimension1Id();
            dimension2 = systemConstants.emcNA().equals(combination.getDimension2Id()) ? null : combination.getDimension2Id();
            dimension3 = systemConstants.emcNA().equals(combination.getDimension3Id()) ? null : combination.getDimension3Id();

            ds.setDimension1(dimension1);
            ds.setDimension2(dimension2);
            ds.setDimension3(dimension3);

            if (!isBlank(dimension3)) {
                dimension3Description = dimension3Map.get(dimension3);

                if (isBlank(dimension3Description)) {
                    dimension3Description = dimension3Bean.findDimensionDescription(dimension3, userData);
                    dimension3Map.put(dimension3, dimension3Description);
                }
                ds.setDimension3Description(dimension3Description);
            }
            populateList(retList, ds, itemId, userData);
        }
        return retList;
    }

    /** Populates the given list with using the given datasource instance.  If supplier references are found, they will be printed individually.  If not, a "default" will be printed. */
    private void populateList(List retList, InventoryItemDimensionReportDS ds, String itemId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryReference.class.getName());
        query.addAnd("itemId", itemId);
        query.addAnd("dimension1", ds.getDimension1());
        query.addAnd("dimension2", ds.getDimension2());
        query.addAnd("dimension3", ds.getDimension3());
        List<InventoryReference> suppRef = util.executeGeneralSelectQuery(query, userData);

        if (suppRef.isEmpty()) {
            retList.add(ds);
        } else {
            InventoryItemDimensionReportDS secondDS;
            for (InventoryReference ref : suppRef) {
                secondDS = (InventoryItemDimensionReportDS) util.doClone(ds, InventoryItemDimensionReportDS.class, userData);
                secondDS.setSuppId(ref.getSupplierNo());
                secondDS.setSuppRef(ref.getReference());
                secondDS.setSuppSpec(ref.getAlternativeDescription());
                retList.add(secondDS);
                //Clear dimensions and dimension 3 description.  It's only important the first time.  Using a flag seems like overkill.
                ds.setDimension1(null);
                ds.setDimension2(null);
                ds.setDimension3(null);
                ds.setDimension3Description(null);
            }
        }
    }
}

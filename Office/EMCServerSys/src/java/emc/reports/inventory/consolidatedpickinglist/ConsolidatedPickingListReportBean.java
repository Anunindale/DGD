/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.inventory.consolidatedpickinglist;

import emc.bus.inventory.InventoryItemMasterLocal;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.bus.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensionsLocal;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.consolidatedpickinglist.InventoryConsolidatedPLLines;
import emc.entity.inventory.consolidatedpickinglist.InventoryConsolidatedPLMaster;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.inventory.dimensions.additionaldimensions.InventoryAdditionalDimensions;
import emc.entity.inventory.picking.InventoryPickingListLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class ConsolidatedPickingListReportBean extends EMCReportBean implements ConsolidatedPickingListReportLocal {

    @EJB
    private InventoryItemMasterLocal itemMasterBean;
    @EJB
    private InventoryDimension3Local dim3Bean;
    @EJB
    private InventoryAdditionalDimensionsLocal additionalDimsBean;

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        return manipulateQueryResult(util.executeGeneralSelectQuery(parameters.get(1).toString(), userData), null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        //Records should be ordered by the Picking List Master id
        List<Object> ret = new ArrayList<Object>();
        String davidLocationsRegex = "[a-zA-Z]{1}[0-9]{2,3}";

        InventoryConsolidatedPLLines consolPickListLine = null;
        InventoryConsolidatedPLMaster consolPickListMaster = null;
        String consolPickingListId = null;
        Date pickingDate = new Date();
        for (Object ob : queryResult) {
            consolPickListLine = (InventoryConsolidatedPLLines) ob;

            consolPickingListId = consolPickListLine.getConsolidatedPickingListId();

            if (consolPickListMaster == null || !consolPickListLine.getConsolidatedPickingListId().equals(consolPickingListId)) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryConsolidatedPLMaster.class);
                query.addAnd("consolidatedPickingListId", consolPickingListId);
                consolPickListMaster = (InventoryConsolidatedPLMaster) util.executeSingleResultQuery(query, userData);
                pickingDate = consolPickListMaster.getPickingDate();
            }

            InventoryItemMaster item = itemMasterBean.findItem(consolPickListLine.getItemId(), userData);

            InventoryDimension3 dim3 = dim3Bean.getDimension3Record(consolPickListLine.getDimension3(), userData);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryPickingListLines.class);
            query.addAnd("consolidatedPickingListLineId", consolPickListLine.getRecordID());
            query.addOrderBy("location");
            query.addOrderBy("batch");
            query.addOrderBy("serial");
            List<InventoryPickingListLines> pickPines = util.executeGeneralSelectQuery(query, userData);

            Map<String, ConsolidatedPickingListreportDS> dsMap = new HashMap<String, ConsolidatedPickingListreportDS>();

            for (InventoryPickingListLines pickListLine : pickPines) {
                //Lines with identical dimensions should be grouped together.
                StringBuilder key = new StringBuilder(pickListLine.getItemId());
                key.append(pickListLine.getDimension1());
                key.append(pickListLine.getDimension2());
                key.append(pickListLine.getDimension3());
                key.append(pickListLine.getBatch());
                key.append(pickListLine.getSerial());
                key.append(pickListLine.getWarehouse());
                key.append(pickListLine.getLocation());

                ConsolidatedPickingListreportDS ds = dsMap.get(key.toString());

                if (ds == null) {
                    ds = new ConsolidatedPickingListreportDS();
                    ds.setPickingListNo(consolPickingListId);
                    ds.setPickingDate(Functions.date2String(pickingDate));
                    ds.setComments(consolPickListMaster.getComments());

                    ds.setItemReference(item.getItemReference());
                    ds.setItemDesc(item.getDescription());

                    ds.setDimension1(consolPickListLine.getDimension1());
                    ds.setDimension2(consolPickListLine.getDimension2());
                    if (dim3 != null) {
                        ds.setDimension3(consolPickListLine.getDimension3());
                        ds.setColourDesc(dim3.getDescription());
                    }

                    ds.setUoM(pickListLine.getUom());
                    ds.setBatch(pickListLine.getBatch());
                    ds.setSerialNo(pickListLine.getSerial());
                    ds.setLocationId(pickListLine.getLocation());
                    ds.setPallet(pickListLine.getPallet());
                    ds.setCut(pickListLine.getCut());

                    InventoryAdditionalDimensions addDims = additionalDimsBean.getAdditionalDimensions(pickListLine.getItemId(), pickListLine.getDimension1(), pickListLine.getDimension2(), pickListLine.getDimension3(), pickListLine.getBatch(), pickListLine.getSerial(), userData);

                    ds.setWidthRefTitle("Width");
                    if (addDims != null) {
                        ds.setWidth(addDims.getWidth());
                    }

                    ds.setSubGroupId(consolPickingListId + pickListLine.getWarehouse() + ds.getLocationId() + ds.getItemReference());
//                    //N & L Specific mod.  ("David" warehouse)
//                    if ("DCS".equals(pickListLine.getWarehouse()) && (pickListLine.getLocation() != null && pickListLine.getLocation().matches(davidLocationsRegex))) {
//                        //Append '*' to the Picking List No. to ensure that all 'David' warehouse lines are printed separately
//                        ds.setSubGroupId(consolPickingListId + ds.getLocationId());
//                    } else {
//                        ds.setSubGroupId(consolPickingListId);
//                    }

                    ret.add(ds);

                    dsMap.put(key.toString(), ds);
                }
                ds.setQtyToPick(ds.getQtyToPick() + pickListLine.getOrderQty());
            }
        }

        return ret;
    }
}

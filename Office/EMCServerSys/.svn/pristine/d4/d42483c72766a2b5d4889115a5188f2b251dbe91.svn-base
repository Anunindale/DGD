/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorders.mill;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.entity.base.BaseCompanyTable;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.pop.purchaseorders.PurchaseOrderMillBoolean;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.math.EMCMath;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class POPMillPurchaseOrderReportBean extends EMCReportBean implements POPMillPurchaseOrderReportLocal {

    @EJB
    private InventoryDimension3Local dimensionBean;
    @EJB
    private InventoryReferenceLocal referenceBean;

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        POPPurchaseOrderMaster purchaseMaster;
        POPMillPurchaseOrderReportDS ds;
        List dsList = new ArrayList();
        List<POPPurchaseOrderLines> purchaseLinesList;
        for (Object o : queryResult) {
            try {
                purchaseMaster = (POPPurchaseOrderMaster) o;
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class);
                query.addAnd("purchaseOrderId", purchaseMaster.getPurchaseOrderId());
                purchaseLinesList = util.executeGeneralSelectQuery(query, userData);
                if (purchaseLinesList == null || purchaseLinesList.isEmpty()) {
                    Logger.getLogger("emc").log(Level.WARNING, "Purchase Order " + purchaseMaster.getPurchaseOrderId() + " has no lines.", userData);
                    continue;
                }
                ds = new POPMillPurchaseOrderReportDS();
                populateMasterFields(ds, purchaseMaster, userData);
                populateLinesFields(ds, purchaseLinesList, purchaseMaster.getSupplier(), userData);
                dsList.add(ds);
            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to populate report lines: " + ex.getMessage(), userData);
            }
        }
        return dsList;
    }

    private void populateMasterFields(POPMillPurchaseOrderReportDS ds, POPPurchaseOrderMaster purchaseMaster, EMCUserData userData) {
        ds.setOrderNo(purchaseMaster.getPurchaseOrderId());
        ds.setCompanyName(getCompanyName(purchaseMaster.getCompanyId(), userData));
        ds.setRequiredDate(Functions.date2String(purchaseMaster.getRequestedDeliveryDate()));
        ds.setCustomerOrderNo(purchaseMaster.getSupplierOrderNumber());
        ds.setAgentCode(purchaseMaster.getAgentCode());
        ds.setDeliveryAddress(purchaseMaster.getAddressLine1() + " " + purchaseMaster.getAddressLine2() + " " + purchaseMaster.getAddressLine3() + " " +
                purchaseMaster.getAddressLine4() + " " + purchaseMaster.getAddressLine5() + " " + purchaseMaster.getAddressPostalCode());
        ds.setSoftex(formatBooleanField(purchaseMaster.getSoftex()));
        ds.setSoftexOther(purchaseMaster.getOtherSoftex());
        ds.setExistingDesign(formatBooleanField(purchaseMaster.getExistingDesign()));
        ds.setBulkedDesign(formatBooleanField(purchaseMaster.getExistingDesign()));
        ds.setWidthPlain(purchaseMaster.getWidthPlain());
        ds.setWidthPrinted(purchaseMaster.getWidthPrint());
        ds.setWeightPlain(purchaseMaster.getWeightPlain());
        ds.setWeightPrinted(purchaseMaster.getWeightPrint());
        ds.setConsignment(purchaseMaster.getConsignment());
        ds.setTerms(purchaseMaster.getDeliveryTerms());
        ds.setWithOrder(purchaseMaster.getDeliverWithOrder());
        ds.setMatchOrder(purchaseMaster.getMatchOrder());
        ds.setPrintAfter(formatBooleanField(purchaseMaster.getPrintAfterDelivery()));
        ds.setPrintAfterType(purchaseMaster.getTypeOfPrint());
        ds.setProcessAfter(formatBooleanField(purchaseMaster.getProcessAfterDelivery()));
        ds.setAfterProcessType(purchaseMaster.getTypeOfProcess());
        ds.setReplacement(formatBooleanField(purchaseMaster.getReplacementOrder()));
        ds.setReplacementReason(purchaseMaster.getReplacementReason());
        ds.setCuttingReceived(formatBooleanField(purchaseMaster.getCuttingReceived()));
        ds.setProformaNo(purchaseMaster.getBlanketOrderRef());
        ds.setLabDye(formatBooleanField(purchaseMaster.getLabDyes()));
        ds.setStrikeOff(formatBooleanField(purchaseMaster.getStrikeOff()));
        ds.setRemarks(purchaseMaster.getComments());
        ds.setCorrespondence(purchaseMaster.getCorrespondenceReference());
        ds.setExStock(formatBooleanField(purchaseMaster.getEXStock()));
        ds.setLabInfo(purchaseMaster.getAdditionalLabInfo());
        ds.setRetailer(purchaseMaster.getRetailer());
        ds.setColourBlocked(formatBooleanField(purchaseMaster.getColourBlocked()));
        ds.setEndUser(purchaseMaster.getEndUser());
        ds.setPreviosOrder(purchaseMaster.getPreviousOrder());
        ds.setOrderDate(Functions.date2String(purchaseMaster.getOrderedDate()));
    }

    private String getCompanyName(String companyId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseCompanyTable.class);
        query.addAnd("companyId", companyId);
        BaseCompanyTable company = (BaseCompanyTable) util.executeSingleResultQuery(query, userData);
        return company.getCompanyName();
    }

    private String formatBooleanField(String value) {
        if (isBlank(value) || PurchaseOrderMillBoolean.fromString(value).equals(PurchaseOrderMillBoolean.UNKNOWN)) {
            return "YES / NO";
        } else {
            return value;
        }
    }

    private void populateLinesFields(POPMillPurchaseOrderReportDS ds, List<POPPurchaseOrderLines> purchaseLinesList, String supplier, EMCUserData userData) throws Exception {
        int count = 0;
        double totalQuantity = 0;
        Map<String, InventoryDimension3> dimensionMap = new HashMap<String, InventoryDimension3>();
        InventoryDimension3 dimension;
        EMCMath math = new EMCMath();
        boolean useBaseColour = false;
        for (POPPurchaseOrderLines purchaseLines : purchaseLinesList) {
            count++;
            if (isBlank(ds.getItemReference())) {
                List<String> referenceList = referenceBean.findReferenceAndDesc(purchaseLines.getItemId(), InventoryReferenceTypes.PRIMARY, userData);
                ds.setItemReference(referenceList.get(0));
                ds.setItemDescription(referenceList.get(1));
                ds.setUom(purchaseLines.getUom());
            }

            if (!isBlank(purchaseLines.getItemDimension3())) {
                dimension = dimensionMap.get(purchaseLines.getItemDimension3());
                if (dimension == null) {
                    dimension = dimensionBean.getDimension3Record(purchaseLines.getItemDimension3(), userData);
                    dimensionMap.put(purchaseLines.getItemDimension3(), dimension);
                    if (isBlank(ds.getDesignNo())) {
                        ds.setDesignNo(dimension.getDesignNo());
                        useBaseColour = true;
                    }
                }
                setFieldValue(ds, "fabricColour" + count, useBaseColour ? findBaseColour(dimension, dimensionMap, userData) : dimension.getDescription());
                setFieldValue(ds, "catagory" + count, findColourCatagory(supplier, purchaseLines, dimension.getCatagory(), userData));
                setFieldValue(ds, "designCode" + count, purchaseLines.getDesignCode());
                setFieldValue(ds, "printColourNumber" + count, dimension.getColourWay());
            }

            InventoryReference supplierRef = referenceBean.findSupplierReferenceRecord(purchaseLines.getItemId(), purchaseLines.getItemDimension1(),
                    purchaseLines.getItemDimension2(), purchaseLines.getItemDimension3(), supplier, true, userData);

            if (supplierRef != null) {
                ds.setItemReference(supplierRef.getReference());
                setFieldValue(ds, "fabricColourNumber" + count, supplierRef.getAlternativeDescription());
            }

            setFieldValue(ds, "printColour" + count, purchaseLines.getPrintColour());
            if (purchaseLines.getQuantity() != 0) {
                setFieldValue(ds, "quantity" + count, math.round(purchaseLines.getQuantity(), 2));
            }
            if (purchaseLines.getFabricPrice() != 0) {
                setFieldValue(ds, "fabricPrice" + count, math.round(purchaseLines.getFabricPrice(), 2));
            }
            if (purchaseLines.getPrintPrice() != 0) {
                setFieldValue(ds, "printPrice" + count, math.round(purchaseLines.getPrintPrice(), 2));
            }
            if (purchaseLines.getItemPrice() != 0) {
                setFieldValue(ds, "totalPrice" + count, math.round(purchaseLines.getItemPrice(), 2));
            }
            totalQuantity += purchaseLines.getQuantity();
        }
        if (totalQuantity != 0) {
            ds.setTotalquantity(String.valueOf(math.round(totalQuantity, 2)));
        }
    }

    private void setFieldValue(POPMillPurchaseOrderReportDS ds, String fieldName, Object value) throws Exception {
        if (value == null) {
            return;
        }
        Field f = POPMillPurchaseOrderReportDS.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(ds, String.valueOf(value));
    }

    private String findBaseColour(InventoryDimension3 colour, Map<String, InventoryDimension3> colourMap, EMCUserData userData) {
        if (isBlank(colour.getDesignNo()) || isBlank(colour.getBaseColour())) {
            return colour.getDescription();
        } else {
            InventoryDimension3 baseColour = colourMap.get(colour.getBaseColour());
            if (baseColour == null) {
                baseColour = dimensionBean.getDimension3Record(colour.getBaseColour(), userData);
                colourMap.put(colour.getBaseColour(), baseColour);
            }
            return baseColour.getDescription();
        }
    }

    private String findColourCatagory(String supplier, POPPurchaseOrderLines purchaseLines, String dimensionCatagory, EMCUserData userData) {
        InventoryReference supplierReference = referenceBean.findSupplierReferenceRecord(purchaseLines.getItemId(), purchaseLines.getItemDimension1(), purchaseLines.getItemDimension2(), purchaseLines.getItemDimension3(), supplier, false, userData);
        if (supplierReference == null || isBlank(supplierReference.getDimension3Shade())) {
            return dimensionCatagory;
        } else {
            return supplierReference.getDimension3Shade();
        }
    }
}

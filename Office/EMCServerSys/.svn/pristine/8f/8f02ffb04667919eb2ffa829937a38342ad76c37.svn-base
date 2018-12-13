/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.goodsreceive;

import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.pop.POPParametersLocal;
import emc.bus.pop.POPQualityTestTypeLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class POPGoodsReceiveReportBean extends EMCReportBean implements POPGoodsReceiveReportLocal {

    @EJB
    private POPQualityTestTypeLocal qualityBean;
    @EJB
    private POPParametersLocal parametersBean;
    @EJB
    private InventoryReferenceLocal itemRefBean;

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> reportData = new ArrayList<Object>();
        POPGoodsReceiveReportDS ds;
        Map<String, POPSuppliers> supplier = new HashMap();
        POPSuppliers supplierInfo;
        POPParameters param = parametersBean.getPOPParameters(userData);
        boolean post = false;

        if (userData.getUserData(7) != null && userData.getUserData(7) instanceof Boolean && (Boolean) userData.getUserData(7)) {
            //Position 7 is kept to keep track whether or not the note is copied.
            post = (Boolean) userData.getUserData(7);
        }

        for (Object result : queryResult) {
            Object[] ret = (Object[]) result;
            ds = new POPGoodsReceiveReportDS();
            ds.setPurchaseOrderId((String) ret[0]);
            ds.setSupplierId((String) ret[1]);
            ds.setDocumentNumber((String) ret[2]);
            ds.setPostDate(dateHandler.date2String((Date) ret[3]));
            ds.setLineNumber(ret[4] == null ? 0D : (Double) ret[4]);
            ds.setItemId((String) ret[45]);
            ds.setInventoryDimensionId((Long) ret[6]);
            ds.setQuantity(ret[7] == null ? 0D : (Double) ret[7]);
            ds.setUom((String) ret[8]);
            ds.setItemPrice(ret[9] == null ? 0D : (Double) ret[9]);
            ds.setNetAmount(ret[10] == null ? 0D : (Double) ret[10]);
            ds.setDeliveryDate(dateHandler.date2String((Date) ret[11]));
            ds.setGrn((String) ret[12]);
            ds.setItemDescription((String) ret[13]);
            ds.setDetailedDescription((String) ret[14]);

            //Get Supplier info based on supplier ID.
            if (supplier.get((String) ret[1]) == null) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class);
                query.addAnd("supplierId", (String) ret[1]);
                supplierInfo = (POPSuppliers) util.executeSingleResultQuery(query, userData);
                supplier.put((String) ret[1], supplierInfo);
            } else {
                supplierInfo = supplier.get((String) ret[1]);
            }

            ds.setAddressPhysicalLine1(supplierInfo.getAddressPhysicalLine1());
            ds.setAddressPhysicalLine2(supplierInfo.getAddressPhysicalLine2());
            ds.setAddressPhysicalLine3(supplierInfo.getAddressPhysicalLine3());
            ds.setAddressPhysicalLine4(supplierInfo.getAddressPhysicalLine4());
            ds.setAddressPhysicalLine5(supplierInfo.getAddressPhysicalLine5());
            ds.setAddressPhysPostalCode(supplierInfo.getAddressPhysPostalCode());
            ds.setTelephone(supplierInfo.getTelephone());
            ds.setFax(supplierInfo.getFax());

            ds.setCompanyName((String) ret[15]);
            ds.setCoPhysAdrs1((String) ret[16]);
            ds.setCoPhysAdrs2((String) ret[17]);
            ds.setCoPhysAdrs3((String) ret[18]);
            ds.setCoPhysAdrs4((String) ret[19]);
            ds.setCoPhysAdrs5((String) ret[20]);
            ds.setCoPhysPostCode((String) ret[21]);
            ds.setRegistration((String) ret[22]);
            ds.setVatNumber((String) ret[23]);
            ds.setCoPhoneNo((String) ret[24]);
            ds.setCoFaxNo((String) ret[25]);
            ds.setAddressLine1((String) ret[26]);
            ds.setAddressLine2((String) ret[27]);
            ds.setAddressLine3((String) ret[28]);
            ds.setAddressLine4((String) ret[29]);
            ds.setAddressLine5((String) ret[30]);
            ds.setAddressPostalCode((String) ret[31]);
            ds.setDeliveryTerms((String) ret[32]);
            ds.setDeliveryMode((String) ret[33]);

            //Calculate the discount and amount.
            if ((Double) ret[34] == null) {
                ds.setReqQuantity(0D);
                ds.setDiscount((ds.getItemPrice() * ds.getQuantity()) * 0D / 100);
                ds.setAmount(((ds.getItemPrice() * ds.getQuantity()) - ds.getDiscount()));
            } else {
                ds.setReqQuantity((Double) ret[34]);
                ds.setDiscount((ds.getItemPrice() * ds.getQuantity()) * (Double) ret[38] / 100);
                ds.setAmount(((ds.getItemPrice() * ds.getQuantity()) - ds.getDiscount()));
            }
            ds.setDimension1((String) ret[35]);
            ds.setDimension2((String) ret[36]);
            ds.setDimension3((String) ret[37]);

            //Check to see whether or not there is a quality check required.
            if (!isBlank((String) ret[39])) {
                ds.setQualityTest((String) ret[39]);
                ds.setQualityDesc(qualityBean.findDesc((String) ret[39], userData));
            }

            if (ret[41] != null) {
                ds.setQualityTestReq((Boolean) ret[41]);
            } else {
                ds.setQualityTestReq(Boolean.FALSE);
            }

            if (ret[40] != null) {
                ds.setQualityReportReq((Boolean) ret[40]);
            } else {
                ds.setQualityReportReq(Boolean.FALSE);
            }

            ds.setPrintPrice(param.getPriceReceivedReport());

            //Get the Supplier Item Information
            Object[] supRefInfo = itemRefBean.getSupplierReference((String) ret[5], (String) ret[35], (String) ret[36], (String) ret[37], (String) ret[42], userData);
            ds.setOrderedBy((String) ret[43]);

            ds.setAlternateItem((String) supRefInfo[0]);
            ds.setAlternateDescription((String) supRefInfo[1]);

            //Calculate the outstanding quantity
            ds.setQuantityOutstanding((Double) ret[34] - (Double) ret[7]);

            if (post) {
                ds.setCopy("PRINT - " + (Integer) ret[44] + " (COPY)");
            } else {
                ds.setCopy("PRINT - " + (Integer) ret[44]);
            }

           

            reportData.add(ds);
        }
        return reportData;
    }

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addAnd("transactionNumber", POPSupplierReceivedJournalLines.class.getName(), EMCQueryConditions.EQUALS, "transactionNumber", POPPurchaseOrderLines.class.getName());
        query.addField("purchaseOrderId", POPSupplierReceivedJournalMaster.class.getName());//0
        query.addField("supplierId", POPSupplierReceivedJournalMaster.class.getName());//1
        query.addField("documentNumber", POPSupplierReceivedJournalMaster.class.getName());//2
        query.addField("receivedDate", POPSupplierReceivedJournalMaster.class.getName());//3

        query.addField("lineNo", POPSupplierReceivedJournalLines.class.getName());//4
        query.addField("itemId", POPSupplierReceivedJournalLines.class.getName());//5
        query.addField("dimId", POPSupplierReceivedJournalLines.class.getName());//6
        query.addField("quantity", POPSupplierReceivedJournalLines.class.getName());//7
        query.addField("uom", POPSupplierReceivedJournalLines.class.getName());//8
        query.addField("price", POPSupplierReceivedJournalLines.class.getName());//9
        query.addField("lineAmount", POPSupplierReceivedJournalLines.class.getName());//10
        query.addField("receivedDate", POPSupplierReceivedJournalLines.class.getName());//11
        query.addField("receivedId", POPSupplierReceivedJournalLines.class.getName());//12

        query.addField("description", InventoryItemMaster.class.getName());//13
        query.addField("detailedDescription", InventoryItemMaster.class.getName());//14

        query.addField("companyName", BaseCompanyTable.class.getName()); //15
        query.addField("coPhysAdrs1", BaseCompanyTable.class.getName()); //16
        query.addField("coPhysAdrs2", BaseCompanyTable.class.getName()); //17
        query.addField("coPhysAdrs3", BaseCompanyTable.class.getName()); //18
        query.addField("coPhysAdrs4", BaseCompanyTable.class.getName()); //19
        query.addField("coPhysAdrs5", BaseCompanyTable.class.getName()); //20
        query.addField("coPhysPostCode", BaseCompanyTable.class.getName()); //21
        query.addField("coRegNr", BaseCompanyTable.class.getName()); //22
        query.addField("coVatRegNr", BaseCompanyTable.class.getName()); //23
        query.addField("coPhoneNr", BaseCompanyTable.class.getName()); //24
        query.addField("coFaxNr", BaseCompanyTable.class.getName()); //25

        query.addField("addressLine1", POPPurchaseOrderMaster.class.getName()); //26
        query.addField("addressLine2", POPPurchaseOrderMaster.class.getName()); //27
        query.addField("addressLine3", POPPurchaseOrderMaster.class.getName()); //28
        query.addField("addressLine4", POPPurchaseOrderMaster.class.getName()); //29
        query.addField("addressLine5", POPPurchaseOrderMaster.class.getName()); //30
        query.addField("addressPostalCode", POPPurchaseOrderMaster.class.getName()); //31
        query.addField("deliveryTerms", POPPurchaseOrderMaster.class.getName()); //32
        query.addField("modeOfDelivery", POPPurchaseOrderMaster.class.getName()); //33

        query.addField("quantity", POPPurchaseOrderLines.class.getName()); //34
        query.addField("itemDimension1", POPPurchaseOrderLines.class.getName()); //35
        query.addField("itemDimension2", POPPurchaseOrderLines.class.getName()); //36
        query.addField("itemDimension3", POPPurchaseOrderLines.class.getName()); //37
        query.addField("discountPercentage", POPPurchaseOrderLines.class.getName()); //38

        query.addField("qualityTest", POPPurchaseOrderMaster.class.getName()); //39
        query.addField("qualityReportReq", POPPurchaseOrderMaster.class.getName()); //40
        query.addField("qualityTestReq", POPPurchaseOrderMaster.class.getName()); //41
        query.addField("supplier", POPPurchaseOrderMaster.class.getName()); //42
        query.addField("orderedBy", POPPurchaseOrderMaster.class.getName()); //43

        query.addField("postVersion", POPPurchaseOrderMaster.class.getName()); //44

        query.addField("itemReference", InventoryItemMaster.class.getName());//45


        query.addField("comments", POPPurchaseOrderMaster.class.getName()); //46
        query.addField("comments", POPPurchaseOrderLines.class.getName()); //47

        return super.manipulateQuery(query, userData);
    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        return super.getReportResult(parameters, userData);
    }
}

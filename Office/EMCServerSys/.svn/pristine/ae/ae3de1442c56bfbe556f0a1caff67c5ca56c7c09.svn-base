/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorderposting;

import emc.bus.base.BaseEmployeeLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.pop.POPParametersLocal;
import emc.bus.pop.POPQualityTestTypeLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.transactions.InventoryTransactions;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.transactions.InventoryTransactionsRefType;
import emc.enums.pop.supplierreceivedjournals.ReceivedJournalType;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class POPGoodsReceivedReportBean extends EMCReportBean implements POPGoodsReceivedReportLocal {

    @EJB
    private InventoryReferenceLocal itemRefBean;
    @EJB
    private POPQualityTestTypeLocal qualityBean;
    @EJB
    private POPParametersLocal parametersBean;
    @EJB
    private BaseEmployeeLocal employeeBean;

    /** Creates a new instance of POPGoodsReceivedReportBean. */
    public POPGoodsReceivedReportBean() {

    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List ret = new ArrayList();
        if (queryResult.size() > 0) {

            String masterClass = POPPurchaseOrderMaster.class.getName();
            String linesClass = POPPurchaseOrderLines.class.getName();
            String supplierClass = POPSuppliers.class.getName();
            String companyClass = BaseCompanyTable.class.getName();
            String itemMasterClass = InventoryItemMaster.class.getName();
            String tranClass = InventoryTransactions.class.getName();

            String lastPurchaseOrderId = "";
            String purchaseOrderId = "";

            String lastTranId = "";
            String tranId = "";

            InventoryTransactions tran = null;
            POPPurchaseOrderMaster master = null;
            POPPurchaseOrderLines line = null;
            POPSuppliers supplier = null;
            InventoryItemMaster item = null;

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, companyClass);
            query.addAnd("companyId", util.getCompanyId(companyClass, userData));

            BaseCompanyTable company = (BaseCompanyTable) util.executeSingleResultQuery(query, userData);

            POPPurchaseOrderPostingReportDS ds = null;

            for (Object ob : queryResult) {
                ds = new POPPurchaseOrderPostingReportDS();

                tran = (InventoryTransactions) ob;

                purchaseOrderId = tran.getRefNumber();

                if (!purchaseOrderId.equals(lastPurchaseOrderId)) {
                    lastPurchaseOrderId = purchaseOrderId;
                    query = new EMCQuery(enumQueryTypes.SELECT, masterClass);
                    query.addAnd("purchaseOrderId", purchaseOrderId);

                    master = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

                    query = new EMCQuery(enumQueryTypes.SELECT, supplierClass);
                    query.addAnd("supplierId", master.getSupplier());

                    supplier = (POPSuppliers) util.executeSingleResultQuery(query, userData);
                }

                tranId = tran.getTransId();

                if (!tranId.equals(lastTranId)) {
                    lastTranId = tranId;
                    query = new EMCQuery(enumQueryTypes.SELECT, linesClass);
                    query.addAnd("transactionNumber", tranId);

                    line = (POPPurchaseOrderLines) util.executeSingleResultQuery(query, userData);

                    query = new EMCQuery(enumQueryTypes.SELECT, itemMasterClass);
                    query.addAnd("itemId", tran.getItemId());

                    item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);
                }

                //Header fields 
                ds.setOrderedBy(employeeBean.findEmployeeNameAndSurname(master.getOrderedBy(), userData));
                ds.setCompanyName(company.getCompanyName());
                ds.setCoPhoneNo(company.getCoPhoneNr());
                ds.setCoFaxNo(company.getCoFaxNr());
                ds.setRegistration(company.getCoRegNr());
                ds.setVatNumber(company.getCoVatRegNr());

                ds.setSupplierName(supplier.getSupplierName());

                if (!isBlank(master.getQualityTest())) {
                    ds.setQualityTest(master.getQualityTest());
                    ds.setQualityDesc(qualityBean.findDesc(master.getQualityTest(), userData));
                }

                ds.setQualityTestReq(master.getQualityTestReq());
                ds.setQuatityReportReq(master.getQualityReportReq());

                ds.setPurchaseOrderId(master.getPurchaseOrderId());

                Date masterDate = tran.getPhysicalDate();
                if (masterDate != null) {
                    ds.setPostDate(Functions.date2String(masterDate, "dd/MM/yyyy"));
                }

                List<String> addressFields = new ArrayList<String>();

                if (!isBlank(supplier.getAddressPhysicalLine1())) {
                    addressFields.add(supplier.getAddressPhysicalLine1());
                }

                if (!isBlank(supplier.getAddressPhysicalLine2())) {
                    addressFields.add(supplier.getAddressPhysicalLine2());
                }

                if (!isBlank(supplier.getAddressPhysicalLine3())) {
                    addressFields.add(supplier.getAddressPhysicalLine3());
                }

                if (!isBlank(supplier.getAddressPhysicalLine4())) {
                    addressFields.add(supplier.getAddressPhysicalLine4());
                }

                if (!isBlank(supplier.getAddressPhysicalLine5())) {
                    addressFields.add(supplier.getAddressPhysicalLine5());
                }

                if (!isBlank(supplier.getAddressPhysPostalCode())) {
                    addressFields.add(supplier.getAddressPhysPostalCode());
                }

                doSupplierAddress(ds, addressFields);
                addressFields.clear();

                if (!isBlank(company.getCoPhysAdrs1())) {
                    addressFields.add(company.getCoPhysAdrs1());
                }

                if (!isBlank(company.getCoPhysAdrs2())) {
                    addressFields.add(company.getCoPhysAdrs2());
                }

                if (!isBlank(company.getCoPhysAdrs3())) {
                    addressFields.add(company.getCoPhysAdrs3());
                }

                if (!isBlank(company.getCoPhysAdrs4())) {
                    addressFields.add(company.getCoPhysAdrs4());
                }

                if (!isBlank(company.getCoPhysAdrs5())) {
                    addressFields.add(company.getCoPhysAdrs5());
                }

                if (!isBlank(company.getCoPhysPostCode())) {
                    addressFields.add(company.getCoPhysPostCode());
                }

                doCompanyAddress(ds, addressFields);
                addressFields.clear();

                if (!isBlank(master.getAddressLine1())) {
                    addressFields.add(master.getAddressLine1());
                }

                if (!isBlank(master.getAddressLine2())) {
                    addressFields.add(master.getAddressLine2());
                }

                if (!isBlank(master.getAddressLine3())) {
                    addressFields.add(master.getAddressLine3());
                }

                if (!isBlank(master.getAddressLine4())) {
                    addressFields.add(master.getAddressLine4());
                }

                if (!isBlank(master.getAddressLine5())) {
                    addressFields.add(master.getAddressLine5());
                }

                if (!isBlank(master.getAddressPostalCode())) {
                    addressFields.add(master.getAddressPostalCode());
                }

                doDeliveryAddress(ds, addressFields);

                //Lines fields
                ds.setDimension1(line.getItemDimension1());
                ds.setDimension2(line.getItemDimension2());
                ds.setDimension3(line.getItemDimension3());

                //Position 9 in user data specifies whether this is a copy of a goods received note.
                Boolean copy = (Boolean) userData.getUserData(9);

                if (copy != null && copy) {
                    ds.setCopy("(Copy)");
                }

                ds.setDocumentNumber(tran.getDocumentNo());

                ds.setInternalRef(master.getInternalRef());
                ds.setExternalRef(master.getExternalRef());

                String itemRef = itemRefBean.checkItemReference(line.getItemId(), userData)[1];
                Object[] supRefInfo = itemRefBean.getSupplierReference(line.getItemId(), line.getItemDimension1(), line.getItemDimension2(), line.getItemDimension3(), master.getSupplier(), userData);

                String supRef = (String) supRefInfo[0];
                String supDesc = (String) supRefInfo[1];

                POPParameters parameters = parametersBean.getPOPParameters(userData);
                ds.setPrintPrice(parameters.getPriceReceivedReport());
                if (parameters.getDisplaySupplierItemRef() && !isBlank(supRef)) {
                    ds.setItemId(supRef);
                    ds.setDescription(supDesc);

                    ds.setAlternateItem(itemRef);
                    ds.setAlternateDescription(item.getDescription());
                } else {
                    ds.setItemId(itemRef);
                    ds.setDescription(item.getDescription());

                    ds.setAlternateItem(supRef);
                    ds.setAlternateDescription(supDesc);
                }

                ds.setItemPrice(line.getItemPrice());

                Date poDate = line.getDeliveryDate();
                if (poDate != null) {
                    ds.setDeliveryDate(Functions.date2String(line.getDeliveryDate(), "dd/MM/yyyy"));
                }

                ds.setQuantity(line.getQuantity());

                query = new EMCQuery(enumQueryTypes.SELECT, tranClass);
                query.addFieldAggregateFunction("quantity", tranClass, "SUM");
                query.openConditionBracket(EMCQueryBracketConditions.NONE);
                query.openConditionBracket(EMCQueryBracketConditions.NONE);
                query.addAnd("createdDate", tran.getCreatedDate(), EMCQueryConditions.EQUALS);
                query.addAnd("createdTime", tran.getCreatedTime(), EMCQueryConditions.LESS_THAN);
                query.closeConditionBracket();
                query.openConditionBracket(EMCQueryBracketConditions.OR);
                query.addAnd("createdDate", tran.getCreatedDate(), EMCQueryConditions.LESS_THAN);
                query.closeConditionBracket();
                query.closeConditionBracket();
                query.addAnd("refType", InventoryTransactionsRefType.Receipt.toString());
                query.addAnd("transId", tran.getTransId());

                Double receivedQty = (Double) util.executeSingleResultQuery(query, userData);

                if (receivedQty == null) {
                    receivedQty = 0d;
                }

                ds.setItemsReceived(Math.abs(tran.getQuantity()));

                ds.setDiscountPercentage(line.getDiscountPercentage());
                ds.setDiscount((ds.getItemPrice() * Math.abs(ds.getItemsReceived())) * ds.getDiscountPercentage() / 100);
                ds.setNetAmount(ds.getItemPrice() * ds.getItemsReceived() - ds.getDiscount());

                ds.setQuantityOutstanding(line.getQuantity() - receivedQty > 0 ? line.getQuantity() - receivedQty : line.getQuantity());

                double remaining = ds.getQuantity() - (ds.getItemsReceived() + receivedQty);
                ds.setRemainingQuantity(remaining > 0 ? remaining : 0);

                //Get goods received note number
                query = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalMaster.class.getName());
                query.addAnd("type", ReceivedJournalType.RECEIVED_NOTE.toString());
                query.addAnd("documentNumber", tran.getDocumentNo());

                POPSupplierReceivedJournalMaster supRecJournal = (POPSupplierReceivedJournalMaster) util.executeSingleResultQuery(query, userData);

                if (supRecJournal != null) {
                    ds.setGrn(supRecJournal.getReceivedId());
                }
                
                ret.add(ds);
            }
        }

        return ret;
    }

    /** This method formats the supplier address */
    private void doSupplierAddress(POPPurchaseOrderPostingReportDS ds, List<String> addressFields) {
        if (addressFields.size() > 0) {
            ds.setAddressPhysicalLine1(addressFields.get(0));
        }

        if (addressFields.size() > 1) {
            ds.setAddressPhysicalLine2(addressFields.get(1));
        }

        if (addressFields.size() > 2) {
            ds.setAddressPhysicalLine3(addressFields.get(2));
        }

        if (addressFields.size() > 3) {
            ds.setAddressPhysicalLine4(addressFields.get(3));
        }

        if (addressFields.size() > 4) {
            ds.setAddressPhysicalLine5(addressFields.get(4));
        }

        if (addressFields.size() > 5) {
            ds.setAddressPhysPostalCode(addressFields.get(5));
        }
    }

    /** This method formats the company address */
    private void doCompanyAddress(POPPurchaseOrderPostingReportDS ds, List<String> addressFields) {
        if (addressFields.size() > 0) {
            ds.setCoPhysAdrs1(addressFields.get(0));
        }

        if (addressFields.size() > 1) {
            ds.setCoPhysAdrs2(addressFields.get(1));
        }

        if (addressFields.size() > 2) {
            ds.setCoPhysAdrs3(addressFields.get(2));
        }

        if (addressFields.size() > 3) {
            ds.setCoPhysAdrs4(addressFields.get(3));
        }

        if (addressFields.size() > 4) {
            ds.setCoPhysAdrs5(addressFields.get(4));
        }

        if (addressFields.size() > 5) {
            ds.setCoPhysPostCode(addressFields.get(5));
        }
    }

    /** This method formats the delivery address */
    private void doDeliveryAddress(POPPurchaseOrderPostingReportDS ds, List<String> addressFields) {
        if (addressFields.size() > 0) {
            ds.setAddressLine1(addressFields.get(0));
        }

        if (addressFields.size() > 1) {
            ds.setAddressLine2(addressFields.get(1));
        }

        if (addressFields.size() > 2) {
            ds.setAddressLine3(addressFields.get(2));
        }

        if (addressFields.size() > 3) {
            ds.setAddressLine4(addressFields.get(3));
        }

        if (addressFields.size() > 4) {
            ds.setAddressLine5(addressFields.get(4));
        }

        if (addressFields.size() > 5) {
            ds.setAddressPostalCode(addressFields.get(5));
        }
    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        return super.getReportResult(parameters, userData);
    }
}

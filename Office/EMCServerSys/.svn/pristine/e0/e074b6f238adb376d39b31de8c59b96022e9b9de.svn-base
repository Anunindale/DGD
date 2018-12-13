/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorderposting;

import emc.bus.base.BaseCompanyLocal;
import emc.bus.base.BaseEmployeeLocal;
import emc.bus.pop.POPPurchaseOrderMasterLocal;
import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.pop.POPParametersLocal;
import emc.bus.pop.POPQualityTestTypeLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryReference;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.pop.POPDeliveryModes;
import emc.entity.pop.POPDeliveryTerms;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.entity.pop.posting.POPPurchasePostLines;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.supplierreceivedjournals.ReceivedJournalType;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.ArrayList;
import java.util.Date;
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
public class POPPurchaseOrderPostingReportBean extends EMCReportBean implements POPPurchaseOrderPostingReportLocal {

    @EJB
    private POPPurchaseOrderMasterLocal poMasterBean;
    @EJB
    private GLVATCodeLocal vatCodeBean;
    @EJB
    private InventoryReferenceLocal itemRefBean;
    @EJB
    private POPQualityTestTypeLocal qualityBean;
    @EJB
    private POPParametersLocal parametersBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private BaseCompanyLocal companyBean;

    /** Creates a new instance of POPPurchaseOrderPostingReportBean */
    public POPPurchaseOrderPostingReportBean() {
    }

    @Override
    public List<Object> getReportResult(List<Object> parameters, EMCUserData userData) {
        String query = userData.getUserData().get(0).toString();

        List data = util.executeGeneralSelectQuery(query, userData);

        return manipulateQueryResult(data, null, userData);
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List ret = new ArrayList();

        //Position 3 in user data holds document type.
        DocumentTypes docType = DocumentTypes.fromString((String) userData.getUserData(3));

        //Position 7 in user data keeps track of whether PO was posted.  Set on post button
        boolean post = (Boolean) userData.getUserData(7);

        POPPurchasePostMaster postMaster = null;
        List<POPPurchasePostLines> postLines = null;
        POPPurchaseOrderMaster purchaseOrderMaster = null;
        POPPurchaseOrderLines purchaseOrderLine = null;
        String faxNumber = null;

        POPSuppliers supplier = null;

        InventoryItemMaster item = null;

        BaseCompanyTable company = null;

        POPPurchaseOrderPostingReportDS ds = null;

        POPParameters param = parametersBean.getPOPParameters(userData);

        for (Object ob : queryResult) {
            postMaster = (POPPurchasePostMaster) ob;

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
            query.addAnd("purchaseOrderId", postMaster.getPurchaseOrderId());

            purchaseOrderMaster = (POPPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);
            purchaseOrderMaster.setPostVersion(purchaseOrderMaster.getPostVersion() + 1);
            try {
                poMasterBean.update(purchaseOrderMaster, userData);
            } catch (EMCEntityBeanException ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to set version on Purchase Order.", userData);
                }
            }
            
            faxNumber = companyBean.getFaxNumber(purchaseOrderMaster.getOrderedBy(), enumEMCModules.POP, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, BaseCompanyTable.class.getName());
            query.addAnd("companyId", userData.getCompanyId());

            company = (BaseCompanyTable) util.executeSingleResultQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class.getName());
            query.addAnd("supplierId", purchaseOrderMaster.getSupplier());

            supplier = (POPSuppliers) util.executeSingleResultQuery(query, userData);

            query = new EMCQuery(enumQueryTypes.SELECT, POPPurchasePostLines.class.getName());
            query.addAnd("postMasterId", postMaster.getPostMasterId());

            postLines = util.executeGeneralSelectQuery(query);

            for (POPPurchasePostLines postLine : postLines) {
                if (postLine.getQuantity() != 0) {
                    query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
                    query.addAnd("itemId", postLine.getItemId());

                    item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);

                    query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
                    query.addAnd("purchaseOrderId", purchaseOrderMaster.getPurchaseOrderId());
                    query.addAnd("lineNo", postLine.getLineNumber());

                    purchaseOrderLine = (POPPurchaseOrderLines) util.executeSingleResultQuery(query, userData);

                    ds = new POPPurchaseOrderPostingReportDS();

                    //Header fields 
                    ds.setOrderedBy(employeeBean.findEmployeeNameAndSurname(purchaseOrderMaster.getOrderedBy(), userData));
                    ds.setCompanyName(company.getCompanyName());
                    ds.setCoPhoneNo(company.getCoPhoneNr());
                    ds.setCoFaxNo(faxNumber);
                    ds.setRegistration(company.getCoRegNr());
                    ds.setVatNumber(company.getCoVatRegNr());

                    ds.setSupplierName(supplier.getSupplierName());

                    if (!isBlank(purchaseOrderMaster.getQualityTest())) {
                        ds.setQualityTest(purchaseOrderMaster.getQualityTest());
                        ds.setQualityDesc(qualityBean.findDesc(purchaseOrderMaster.getQualityTest(), userData));
                    }

                    ds.setQualityTestReq(purchaseOrderMaster.getQualityTestReq());
                    ds.setQuatityReportReq(purchaseOrderMaster.getQualityReportReq());

                    ds.setPurchaseOrderId(postMaster.getPurchaseOrderId());

                    Date masterDate = purchaseOrderMaster.getOrderedDate();
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

                    if (!isBlank(purchaseOrderMaster.getAddressLine1())) {
                        addressFields.add(purchaseOrderMaster.getAddressLine1());
                    }

                    if (!isBlank(purchaseOrderMaster.getAddressLine2())) {
                        addressFields.add(purchaseOrderMaster.getAddressLine2());
                    }

                    if (!isBlank(purchaseOrderMaster.getAddressLine3())) {
                        addressFields.add(purchaseOrderMaster.getAddressLine3());
                    }

                    if (!isBlank(purchaseOrderMaster.getAddressLine4())) {
                        addressFields.add(purchaseOrderMaster.getAddressLine4());
                    }

                    if (!isBlank(purchaseOrderMaster.getAddressLine5())) {
                        addressFields.add(purchaseOrderMaster.getAddressLine5());
                    }

                    if (!isBlank(purchaseOrderMaster.getAddressPostalCode())) {
                        addressFields.add(purchaseOrderMaster.getAddressPostalCode());
                    }

                    doDeliveryAddress(ds, addressFields);
                    doDeleveryTermsMode(ds, purchaseOrderMaster, userData);

                    ds.setInternalRef(purchaseOrderMaster.getInternalRef());
                    ds.setExternalRef(purchaseOrderMaster.getExternalRef());

                    //Lines fields
                    ds.setDimension1(purchaseOrderLine.getItemDimension1());
                    ds.setDimension2(purchaseOrderLine.getItemDimension2());
                    ds.setDimension3(purchaseOrderLine.getItemDimension3());

                    ds.setUom(purchaseOrderLine.getUom());
                    ds.setQuantity(postLine.getQuantity());

                    String itemRef = itemRefBean.checkItemReference(purchaseOrderLine.getItemId(), userData)[1];
                    Object[] supRefInfo = itemRefBean.getSupplierReference(purchaseOrderLine.getItemId(), purchaseOrderLine.getItemDimension1(), purchaseOrderLine.getItemDimension2(), purchaseOrderLine.getItemDimension3(), purchaseOrderMaster.getSupplier(), userData);

                    String supRef = (String) supRefInfo[0];
                    String supDesc = (String) supRefInfo[1];
                    if (supRef == null || supRef.equals("")) {
                        EMCQuery getSupRef = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.InventoryReference.class.getName());
                        getSupRef.addAnd("itemId", purchaseOrderLine.getItemId());
                        getSupRef.addAnd("supplierNo", purchaseOrderMaster.getSupplier());
                        getSupRef.addAnd("dimension1", purchaseOrderLine.getItemDimension1());
                        getSupRef.addAnd("dimension2", purchaseOrderLine.getItemDimension2());
                        getSupRef.addAnd("dimension3", purchaseOrderLine.getItemDimension3());
                        InventoryReference suppRef = (InventoryReference) util.executeSingleResultQuery(getSupRef, userData);
                        if (suppRef != null) {
                            supRef = suppRef.getReference();
                            supDesc = suppRef.getAlternativeDescription();
                        }
                    }
                    //ninian mod
                    String toAddtoSuppSpec = "";
                    if (param.getItemGrpToReadColMast() != null && param.getItemGrpToReadColMast().equals(item.getItemGroup())) {
                        //get Colour Master
                        if (purchaseOrderLine.getItemDimension3() != null) {
                            EMCQuery colMastQ = new EMCQuery(enumQueryTypes.SELECT, emc.entity.inventory.dimensions.InventoryDimension3.class.getName());
                            colMastQ.addAnd("dimensionId", purchaseOrderLine.getItemDimension3());
                            InventoryDimension3 colour = (InventoryDimension3) util.executeSingleResultQuery(colMastQ, userData);
                            if (!isBlank(colour.getSourceColRef())) {
                                toAddtoSuppSpec += !toAddtoSuppSpec.equals("") ? "-" + colour.getSourceColRef() : colour.getSourceColRef();
                            }
                            if (!isBlank(colour.getDesignNo())) {
                                toAddtoSuppSpec += !toAddtoSuppSpec.equals("") ? "-" + colour.getDesignNo() : colour.getDesignNo();
                            }
                            if (!isBlank(colour.getBaseColour())) {
                                toAddtoSuppSpec += !toAddtoSuppSpec.equals("") ? "-" + colour.getBaseColour() : colour.getBaseColour();
                            }
                            if (!isBlank(colour.getColourWay())) {
                                toAddtoSuppSpec += !toAddtoSuppSpec.equals("") ? "-" + colour.getColourWay() : colour.getColourWay();
                            }
                            supDesc += !toAddtoSuppSpec.equals("") ? "-" + toAddtoSuppSpec : toAddtoSuppSpec;
                        }
                    } //end ninian mod
                    if (param.getDisplaySupplierItemRef() && !isBlank(supRef)) {
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
                    if (param.isPrintDetailedItemDescription())
                        ds.setDetailedDescription(item.getDetailedDescription());

              
                        ds.setMasterComments(purchaseOrderMaster.getComments());
                        ds.setLineComments(purchaseOrderLine.getComments());

                    switch (docType) {
                        case BLANKET_ORDER:  //Fall through
                        case PURCHASE_ORDER:
                            doPurchaseOrderPostLine(ds, postMaster, postLine, purchaseOrderMaster, purchaseOrderLine, item, docType, post, userData);
                            break;
                        case RECEIVING_LIST:
                            doReceiptsListLine(ds, postMaster, postLine, purchaseOrderMaster, purchaseOrderLine, item, userData);
                            break;
                        case RETURN_GOODS:
                            doGoodsReturnedLine(ds, postMaster, postLine, purchaseOrderMaster, purchaseOrderLine, item, post, userData);
                            ds.setPrintPrice(param.getPriceReturnedReport());
                            break;
                        case GOODS_RECEIVED_NOTE:
                            doGoodsReceivedLine(ds, postMaster, postLine, purchaseOrderMaster, purchaseOrderLine, item, post, userData);
                            ds.setPrintPrice(param.getPriceReceivedReport());
                            break;

                        default:
                            break;
                    }

                    ret.add(ds);
                }
            }
        }

        return ret;
    }

    /** This method is used to handle purchase order post lines and blanket order lines. */
    private void doPurchaseOrderPostLine(POPPurchaseOrderPostingReportDS ds, POPPurchasePostMaster postMaster, POPPurchasePostLines postLine, POPPurchaseOrderMaster purchaseOrderMaster, POPPurchaseOrderLines purchaseOrderLine, InventoryItemMaster item, DocumentTypes docType, boolean post, EMCUserData userData) {
        Date poDate = purchaseOrderLine.getDeliveryDate();
        if (poDate != null) {
            ds.setDeliveryDate(Functions.date2String(purchaseOrderLine.getDeliveryDate(), "dd/MM/yyyy"));
        }

        ds.setUom(purchaseOrderLine.getUom());
        ds.setItemPrice(purchaseOrderLine.getItemPrice());
        ds.setDiscountPercentage(purchaseOrderLine.getDiscountPercentage());
        ds.setDiscount((ds.getItemPrice() * ds.getQuantity()) * ds.getDiscountPercentage() / 100);
        ds.setAmount(((ds.getItemPrice() * ds.getQuantity()) - ds.getDiscount()));

        if (purchaseOrderMaster.getVatApplicable()) {
            ds.setLineVAT(((vatCodeBean.getVatPercentage(purchaseOrderLine.getVatCode(), userData) / 100) * ds.getAmount()));
        }

        //Update post version.  (Only if this is a purchase order)
        if (!PurchaseOrderStatus.REQUISITION.toString().equals(purchaseOrderMaster.getStatus()) && !docType.equals(DocumentTypes.BLANKET_ORDER)) {
            if (post) {
                ds.setCopy("PRINT - " + purchaseOrderMaster.getPostVersion());
            } else {
                ds.setCopy("PRINT - " + purchaseOrderMaster.getPostVersion() + " (COPY)");
            }
        }

        String blanketOrderRef = purchaseOrderMaster.getBlanketOrderRef();

        if (!isBlank(blanketOrderRef)) {
            ds.setBlanketOrderRef(blanketOrderRef);
        }
    }

    /** This method is used to handle receipts list lines. */
    private void doReceiptsListLine(POPPurchaseOrderPostingReportDS ds, POPPurchasePostMaster postMaster, POPPurchasePostLines postLine, POPPurchaseOrderMaster purchaseOrderMaster, POPPurchaseOrderLines purchaseOrderLine, InventoryItemMaster item, EMCUserData userData) {
        ds.setItemPrice(purchaseOrderLine.getItemPrice());

        Date poDate = purchaseOrderLine.getDeliveryDate();
        if (poDate != null) {
            ds.setDeliveryDate(Functions.date2String(purchaseOrderLine.getDeliveryDate(), "dd/MM/yyyy"));
        }
    }

    /** This method is used to handle goods received lines. */
    private void doGoodsReceivedLine(POPPurchaseOrderPostingReportDS ds, POPPurchasePostMaster postMaster, POPPurchasePostLines postLine, POPPurchaseOrderMaster purchaseOrderMaster, POPPurchaseOrderLines purchaseOrderLine, InventoryItemMaster item, boolean post, EMCUserData userData) {
        //Position 9 in user data specifies whether this is a copy of a goods received note.
        Boolean copy = (Boolean) userData.getUserData(9);

        //Get goods received note number
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalMaster.class.getName());
        query.addAnd("type", ReceivedJournalType.RECEIVED_NOTE.toString());
        query.addAnd("documentNumber", postMaster.getDocumentNumber());

        POPSupplierReceivedJournalMaster supRecJournal = (POPSupplierReceivedJournalMaster) util.executeSingleResultQuery(query, userData);

        if (supRecJournal != null) {
            ds.setGrn(supRecJournal.getReceivedId());
        }

        if (copy != null && copy) {
            ds.setCopy("Copy");
        }

        ds.setDocumentNumber(postMaster.getDocumentNumber());

        ds.setItemPrice(purchaseOrderLine.getItemPrice());

        Date poDate = purchaseOrderLine.getDeliveryDate();
        if (poDate != null) {
            ds.setDeliveryDate(Functions.date2String(purchaseOrderLine.getDeliveryDate(), "dd/MM/yyyy"));
        }

        if (post) {
            if (postLine.getPoLineRemainingItems() == -1) {
                //Items received on purchase order already updated by transactions                    
                ds.setRemainingQuantity(purchaseOrderLine.getQuantity() - purchaseOrderLine.getItemsReceived());
            } else {
                ds.setRemainingQuantity(postLine.getPoLineRemainingItems());
            }
            ds.setItemsReceived(postLine.getQuantity());
        } else {
            ds.setItemsReceived(postLine.getQuantity());
            if (postLine.getPoLineRemainingItems() == -1) {
                //Not posted, thus purchase order not updated.
                ds.setRemainingQuantity(purchaseOrderLine.getQuantity() - purchaseOrderLine.getItemsReceived() - ds.getItemsReceived());
            } else {
                ds.setRemainingQuantity(postLine.getPoLineRemainingItems());
            }
        }

        ds.setDiscountPercentage(purchaseOrderLine.getDiscountPercentage());
        ds.setDiscount((ds.getItemPrice() * ds.getQuantity()) * ds.getDiscountPercentage() / 100);
        ds.setNetAmount(ds.getItemPrice() * ds.getItemsReceived() - ds.getDiscount());

        //Subtract over receive quantity to ensure that the correct outstanding quantity is displayed on the report after over receiving.
        ds.setQuantityOutstanding(ds.getQuantity() + ds.getRemainingQuantity() - purchaseOrderLine.getOverReceiveQty());
    }

    /** This method is used to handle goods returned lines. */
    private void doGoodsReturnedLine(POPPurchaseOrderPostingReportDS ds, POPPurchasePostMaster postMaster, POPPurchasePostLines postLine, POPPurchaseOrderMaster purchaseOrderMaster, POPPurchaseOrderLines purchaseOrderLine, InventoryItemMaster item, boolean post, EMCUserData userData) {
        //Position 9 in user data specifies whether this is a copy of a goods received note.
        Boolean copy = (Boolean) userData.getUserData(9);

        //Get goods returned note number
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalMaster.class.getName());
        query.addAnd("type", ReceivedJournalType.RETURN_GOODS.toString());
        query.addAnd("documentNumber", postMaster.getDocumentNumber());

        POPSupplierReceivedJournalMaster supRecJournal = (POPSupplierReceivedJournalMaster) util.executeSingleResultQuery(query, userData);

        if (supRecJournal != null) {
            ds.setRn(supRecJournal.getReceivedId());
        }

        if (copy != null && copy) {
            ds.setCopy("Copy");
        }

        ds.setDocumentNumber(postMaster.getDocumentNumber());

        ds.setItemPrice(purchaseOrderLine.getItemPrice());

        Date poDate = purchaseOrderLine.getDeliveryDate();
        if (poDate != null) {
            ds.setDeliveryDate(Functions.date2String(purchaseOrderLine.getDeliveryDate(), "dd/MM/yyyy"));
        }

        ds.setItemsReceived(postLine.getQuantity() * -1);

        ds.setDiscountPercentage(purchaseOrderLine.getDiscountPercentage());
        ds.setDiscount((ds.getItemPrice() * Math.abs(ds.getQuantity())) * ds.getDiscountPercentage() / 100);
        ds.setNetAmount(ds.getItemPrice() * ds.getItemsReceived() - ds.getDiscount());

        //Subtract over receive quantity to ensure that the correct outstanding quantity is displayed on the report after over receiving.
        if (post) {
            ds.setQuantityOutstanding(purchaseOrderLine.getItemsReceived());
        } else {
            ds.setQuantityOutstanding(purchaseOrderLine.getItemsReceived() - ds.getItemsReceived());
        }
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

    private void doDeleveryTermsMode(POPPurchaseOrderPostingReportDS ds, POPPurchaseOrderMaster purchaseOrderMaster, EMCUserData userData) {
        ds.setDeleveryTerms(purchaseOrderMaster.getDeliveryTerms());
        if (!isBlank(ds.getDeleveryTerms())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPDeliveryTerms.class.getName());
            query.addAnd("deliveryTermsId", ds.getDeleveryTerms());
            query.addField("description");
            ds.setDeleveryTermsDesc((String) util.executeSingleResultQuery(query, userData));
        }
        ds.setDeleveryMode(purchaseOrderMaster.getModeOfDelivery());
        if (!isBlank(ds.getDeleveryMode())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPDeliveryModes.class.getName());
            query.addAnd("deliveryModeId", ds.getDeleveryMode());
            query.addField("description");
            ds.setDeleveryModeDesc((String) util.executeSingleResultQuery(query, userData));
        }
    }
}

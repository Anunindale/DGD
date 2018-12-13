/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.cancelledpurchaseorders;

import emc.bus.gl.vatcodes.GLVATCodeLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.pop.POPParametersLocal;
import emc.bus.pop.POPQualityTestTypeLocal;
import emc.bus.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMasterLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderLines;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS;
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
public class POPCancelledPurchaseOrderReportBean extends EMCReportBean implements POPCancelledPurchaseOrderReportLocal {

    @EJB
    private POPCancelledPurchaseOrderMasterLocal poMasterBean;
    @EJB
    private GLVATCodeLocal vatCodeBean;
    @EJB
    private InventoryReferenceLocal itemRefBean;
    @EJB
    private POPQualityTestTypeLocal qualityBean;
    @EJB
    private POPParametersLocal parametersBean;

    /** Creates a new instance of POPPurchaseOrderPostingReportBean */
    public POPCancelledPurchaseOrderReportBean() {

    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List ret = new ArrayList();

        String purchaseOrderId = null;

        POPCancelledPurchaseOrderLines cancelledLine;
        POPCancelledPurchaseOrderMaster cancelledMaster = null;

        POPSuppliers supplier = null;

        InventoryItemMaster item = null;

        BaseCompanyTable company = null;

        POPPurchaseOrderPostingReportDS ds = null;

        POPParameters param = parametersBean.getPOPParameters(userData);

        EMCQuery query;

        for (Object ob : queryResult) {
            cancelledLine = (POPCancelledPurchaseOrderLines) ob;

            if (!cancelledLine.getPurchaseOrderId().equals(purchaseOrderId)) {
                purchaseOrderId = cancelledLine.getPurchaseOrderId();

                query = new EMCQuery(enumQueryTypes.SELECT, POPCancelledPurchaseOrderMaster.class.getName());
                query.addAnd("purchaseOrderId", purchaseOrderId);

                cancelledMaster = (POPCancelledPurchaseOrderMaster) util.executeSingleResultQuery(query, userData);

                query = new EMCQuery(enumQueryTypes.SELECT, BaseCompanyTable.class.getName());
                query.addAnd("companyId", userData.getCompanyId());

                company = (BaseCompanyTable) util.executeSingleResultQuery(query, userData);

                query = new EMCQuery(enumQueryTypes.SELECT, POPSuppliers.class.getName());
                query.addAnd("supplierId", cancelledMaster.getSupplier());

                supplier = (POPSuppliers) util.executeSingleResultQuery(query, userData);
            }

            query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemMaster.class.getName());
            query.addAnd("itemId", cancelledLine.getItemId());

            item = (InventoryItemMaster) util.executeSingleResultQuery(query, userData);

            ds = new POPPurchaseOrderPostingReportDS();

            //Header fields 
            ds.setOrderedBy(cancelledMaster.getOrderedBy());
            ds.setCompanyName(company.getCompanyName());
            ds.setCoPhoneNo(company.getCoPhoneNr());
            ds.setCoFaxNo(company.getCoFaxNr());
            ds.setRegistration(company.getCoRegNr());
            ds.setVatNumber(company.getCoVatRegNr());

            ds.setSupplierName(supplier.getSupplierName());

            if (!isBlank(cancelledMaster.getQualityTest())) {
                ds.setQualityTest(cancelledMaster.getQualityTest());
                ds.setQualityDesc(qualityBean.findDesc(cancelledMaster.getQualityTest(), userData));
            }

            ds.setQualityTestReq(cancelledMaster.getQualityTestReq());
            ds.setQuatityReportReq(cancelledMaster.getQualityReportReq());

            ds.setPurchaseOrderId(cancelledMaster.getPurchaseOrderId());

            Date masterDate = cancelledMaster.getCreatedDate();
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

            if (!isBlank(cancelledMaster.getAddressLine1())) {
                addressFields.add(cancelledMaster.getAddressLine1());
            }

            if (!isBlank(cancelledMaster.getAddressLine2())) {
                addressFields.add(cancelledMaster.getAddressLine2());
            }

            if (!isBlank(cancelledMaster.getAddressLine3())) {
                addressFields.add(cancelledMaster.getAddressLine3());
            }

            if (!isBlank(cancelledMaster.getAddressLine4())) {
                addressFields.add(cancelledMaster.getAddressLine4());
            }

            if (!isBlank(cancelledMaster.getAddressLine5())) {
                addressFields.add(cancelledMaster.getAddressLine5());
            }

            if (!isBlank(cancelledMaster.getAddressPostalCode())) {
                addressFields.add(cancelledMaster.getAddressPostalCode());
            }

            doDeliveryAddress(ds, addressFields);

            //Lines fields
            ds.setDimension1(cancelledLine.getItemDimension1());
            ds.setDimension2(cancelledLine.getItemDimension2());
            ds.setDimension3(cancelledLine.getItemDimension3());

            ds.setUom(cancelledLine.getUom());
            ds.setQuantity(cancelledLine.getQuantity());

            String itemRef = itemRefBean.checkItemReference(cancelledLine.getItemId(), userData)[1];
            Object[] supRefInfo = itemRefBean.getSupplierReference(cancelledLine.getItemId(), cancelledLine.getItemDimension1(), cancelledLine.getItemDimension2(), cancelledLine.getItemDimension3(), cancelledMaster.getSupplier(), userData);

            String supRef = (String) supRefInfo[0];
            String supDesc = (String) supRefInfo[1];

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

          

            Date poDate = cancelledLine.getDeliveryDate();
            if (poDate != null) {
                ds.setDeliveryDate(Functions.date2String(cancelledLine.getDeliveryDate(), "dd/MM/yyyy"));
            }

            ds.setItemPrice(cancelledLine.getItemPrice());
            ds.setDiscountPercentage(cancelledLine.getDiscountPercentage());
            ds.setDiscount((ds.getItemPrice() * ds.getQuantity()) * ds.getDiscountPercentage() / 100);
            ds.setAmount(((ds.getItemPrice() * ds.getQuantity()) - ds.getDiscount()));

            if (cancelledMaster.getVatApplicable()) {
                ds.setLineVAT(((vatCodeBean.getVatPercentage(cancelledLine.getVatCode(), userData) / 100) * ds.getAmount()));
            }

            String blanketOrderRef = cancelledMaster.getBlanketOrderRef();

            if (!isBlank(blanketOrderRef)) {
                ds.setBlanketOrderRef(blanketOrderRef);
            }

            ret.add(ds);
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
}

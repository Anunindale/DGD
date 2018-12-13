/*  
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.pop;

import emc.bus.pop.POPDeliveryModesLocal;
import emc.bus.pop.POPDeliveryTermsLocal;
import emc.bus.pop.POPDiscountGroupLocal;
import emc.bus.pop.POPExtraChargeGroupLocal;
import emc.bus.pop.POPParametersLocal;
import emc.bus.pop.POPPriceGroupLocal;
import emc.bus.pop.POPPurchaseOrderApprovalGroupsLocal;
import emc.bus.pop.POPPurchaseOrderLinesLocal;
import emc.bus.pop.POPPurchaseOrderMasterLocal;
import emc.bus.pop.POPQualityTestTypeLocal;
import emc.bus.pop.POPSupplierGroupLocal;
import emc.bus.pop.POPSupplierLocal;
import emc.bus.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMasterLocal;
import emc.bus.pop.cancelledpurchaseorders.datasource.POPCancelledPurchaseOrderLinesDSLocal;
import emc.bus.pop.costchange.CostChangeReasonLocal;
import emc.bus.pop.costchange.CostChangeSummaryDSLocal;
import emc.bus.pop.datasource.POPPurchaseOrderApprovalGroupEmployeesDSLocal;
import emc.bus.pop.datasource.POPPurchaseOrderLinesDSLocal;
import emc.bus.pop.datasource.pricearrangements.POPPriceArrangementDSLocal;
import emc.bus.pop.grnreprint.POPGRNReprintTempLocal;
import emc.bus.pop.grnreprint.datasource.POPGRNReprintTempDSLocal;
import emc.bus.pop.journals.POPSupplierReceivedJournalLinesLocal;
import emc.bus.pop.journals.POPSupplierReceivedJournalMasterLocal;
import emc.bus.pop.journals.datasource.POPSupplierReceivedJournalLinesLocalDS;
import emc.bus.pop.planning.POPPlannedPurchaseOrdersDSLocal;
import emc.bus.pop.planning.POPPlannedPurchaseOrdersLocal;
import emc.bus.pop.posting.POPPurchasePostLinesLocal;
import emc.bus.pop.posting.POPPurchasePostMasterLocal;
import emc.bus.pop.posting.POPPurchasePostSetupTableLocal;
import emc.bus.pop.posting.datasource.POPPurchasePostLinesDSLocal;
import emc.bus.pop.posting.datasource.POPPurchasePostMasterDSLocal;
import emc.bus.pop.pricearrangements.POPPriceArrangementsLocal;
import emc.bus.pop.returnreason.POPReturnGoodsReasonLocal;
import emc.commands.EMCCommands;
import emc.entity.pop.POPDeliveryModes;
import emc.entity.pop.POPDeliveryTerms;
import emc.entity.pop.POPDiscountGroup;
import emc.entity.pop.POPExtraChargeGroup;
import emc.entity.pop.POPGoodsReturnReason;
import emc.entity.pop.POPParameters;
import emc.entity.pop.POPPriceGroup;
import emc.entity.pop.POPPurchaseOrderApprovalGroupEmployees;
import emc.entity.pop.POPPurchaseOrderApprovalGroups;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.POPQualityTestType;
import emc.entity.pop.POPSuppliers;
import emc.entity.pop.POPSupplierGroup;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMaster;
import emc.entity.pop.cancelledpurchaseorders.datasource.POPCancelledPurchaseOrderLinesDS;
import emc.entity.pop.costchange.POPCostChangeReason;
import emc.entity.pop.costchange.CostChangeSummaryDS;
import emc.entity.pop.datasource.POPPurchaseOrderLinesDS;
import emc.entity.pop.datasource.pricearrangements.POPPriceArrangementDS;
import emc.entity.pop.grnreprint.POPGRNReprintTemp;
import emc.entity.pop.grnreprint.datasource.POPGRNReprintTempDS;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.entity.pop.journals.datasource.POPSupplierReceivedJournalLinesDS;
import emc.entity.pop.planning.POPPlannedPurchaseOrders;
import emc.entity.pop.planning.datasource.POPPlannedPurchaseOrdersDS;
import emc.entity.pop.posting.POPPurchasePostLines;
import emc.entity.pop.posting.POPPurchasePostMaster;
import emc.entity.pop.posting.POPPurchasePostSetupTable;
import emc.entity.pop.posting.datasource.POPPurchasePostLinesDS;
import emc.entity.pop.posting.datasource.POPPurchasePostMasterDS;
import emc.entity.pop.pricearrangements.POPPriceArrangements;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.pop.ClientPOPMethods;
import emc.methods.pop.ServerPOPMethods;
import emc.reports.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderReportLocal;
import emc.reports.pop.goodsreceive.POPGoodsReceiveReportLocal;
import emc.reports.pop.grnlabels.LabelsLocal;
import emc.reports.pop.pricevariance.POPPriceVarianceLocal;
import emc.reports.pop.purchaseorderposting.POPGoodsReceivedReportLocal;
import emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportLocal;
import emc.reports.pop.purchaseorders.POPGoodsReceivedLocal;
import emc.reports.pop.purchaseorders.POPGoodsReturnedReportLocal;
import emc.reports.pop.purchaseorders.POPOutstandingPOLocal;
import emc.reports.pop.purchaseorders.mill.POPMillPurchaseOrderReportLocal;
import emc.reports.pop.purchaseorders.servicesreceived.POPServicesReceivedLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class POPMethodMapperBean implements POPMethodMapperBeanLocal {

    @EJB
    private POPSupplierLocal creditorsSupplierBean;
    @EJB
    private POPSupplierGroupLocal creditorsSupplierGroupBean;
    @EJB
    private POPPriceGroupLocal creditorsPriceGroupBean;
    @EJB
    private POPDiscountGroupLocal creditorsDiscountGroupBean;
    @EJB
    private POPExtraChargeGroupLocal creditorsExtraChargeGroupBean;
    @EJB
    private POPPurchaseOrderMasterLocal creditorsPurchaseOrderMasterBean;
    @EJB
    private POPPurchaseOrderLinesLocal creditorsPurchaseOrderLinesBean;
    @EJB
    private POPSupplierReceivedJournalLinesLocal creditorsSupplierReceivedJournalLinesBean;
    @EJB
    private POPSupplierReceivedJournalMasterLocal creditorsSupplierReceivedJournalMasterBean;
    @EJB
    private POPPurchasePostMasterLocal creditorsPurchasePostMasterBean;
    @EJB
    private POPPurchasePostLinesLocal creditorsPurchasePostLinesBean;
    @EJB
    private POPPurchasePostSetupTableLocal creditorsPurchasePostSetupTableBean;
    @EJB
    private POPPurchasePostMasterDSLocal postMasterDSBean;
    @EJB
    private POPPurchaseOrderPostingReportLocal purchaseOrderPostingReportBean;
    @EJB
    private POPPurchaseOrderApprovalGroupsLocal purchaseOrderApprovalGroupBean;
    @EJB
    private POPPurchaseOrderApprovalGroupEmployeesDSLocal purchaseOrderApprovalGroupEmpDSBean;
    @EJB
    private POPDeliveryTermsLocal deliveryTermsBean;
    @EJB
    private POPDeliveryModesLocal deliveryModesBean;
    @EJB
    private POPPurchaseOrderLinesDSLocal purchaseOrderlinesDSBean;
    @EJB
    private POPPurchasePostLinesDSLocal purchasePostLinesDSBean;
    @EJB
    private POPSupplierReceivedJournalLinesLocalDS recievedJournalLinesDS;
    @EJB
    private POPQualityTestTypeLocal qualityBean;
    @EJB
    private POPParametersLocal parametersBean;
    @EJB
    private POPOutstandingPOLocal outstandingPOReportBean;
    @EJB
    private POPGoodsReceivedLocal goodsReceivedBean;
    @EJB
    private POPCancelledPurchaseOrderMasterLocal cancelledPOMaster;
    @EJB
    private CostChangeReasonLocal reasonBean;
    @EJB
    private CostChangeSummaryDSLocal costChangeBean;
    @EJB
    private LabelsLocal labelsBean;
    @EJB
    private POPGoodsReceivedReportLocal goodsReceivedReportBean;
    @EJB
    private POPPriceVarianceLocal varianceBean;
    @EJB
    private POPGoodsReturnedReportLocal returnReportBean;
    @EJB
    private POPCancelledPurchaseOrderReportLocal cancelledPOReportBean;
    @EJB
    private POPCancelledPurchaseOrderLinesDSLocal cancelDSBean;
    @EJB
    private POPGRNReprintTempLocal grnReprintBean;
    @EJB
    private POPGRNReprintTempDSLocal grnReprintDSBean;
    @EJB
    private POPReturnGoodsReasonLocal returnReasonBean;
    @EJB
    private POPMillPurchaseOrderReportLocal millPurchaseReportBean;
    @EJB
    private POPPlannedPurchaseOrdersLocal plannedPurchaseOrderBean;
    @EJB
    private POPPlannedPurchaseOrdersDSLocal plannedPurchaseOrderDSBean;
    @EJB
    private POPPriceArrangementsLocal priceArrangementsBean;
    @EJB
    private POPPriceArrangementDSLocal priceArrangementsDSBean;
    @EJB
    private POPServicesReceivedLocal servicesReceivedBean;
    @EJB
    private POPGoodsReceiveReportLocal goodsReceiveBean;

    public POPMethodMapperBean() {
    }

    public List mapMethodPOP(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.POP.getId());
        retCmd.setMethodId(ClientPOPMethods.GENERAL_METHOD.toString());

        switch (ServerPOPMethods.fromString(cmd.getMethodId())) {
            //POP suppliers
            case INSERT_POPSUPPLIERS:
                theDataList.add(creditorsSupplierBean.insert((POPSuppliers) dataList.get(1), userData));
                break;
            case UPDATE_POPSUPPLIERS:
                theDataList.add(creditorsSupplierBean.update((POPSuppliers) dataList.get(1), userData));
                break;
            case DELETE_POPSUPPLIERS:
                theDataList.add(creditorsSupplierBean.delete((POPSuppliers) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPSUPPLIERS:
                theDataList.add(creditorsSupplierBean.getNumRows(POPSuppliers.class, userData));
                break;
            case GETDATA_POPSUPPLIERS:
                theDataList = (List<Object>) creditorsSupplierBean.getDataInRange(POPSuppliers.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPSUPPLIERS:
                theDataList.add(creditorsSupplierBean.validateField(dataList.get(1).toString(), (POPSuppliers) dataList.get(2), userData));
                break;
            //POP supplier groups
            case INSERT_POPSUPPLIERGROUP:
                theDataList.add(creditorsSupplierGroupBean.insert((POPSupplierGroup) dataList.get(1), userData));
                break;
            case UPDATE_POPSUPPLIERGROUP:
                theDataList.add(creditorsSupplierGroupBean.update((POPSupplierGroup) dataList.get(1), userData));
                break;
            case DELETE_POPSUPPLIERGROUP:
                theDataList.add(creditorsSupplierGroupBean.delete((POPSupplierGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPSUPPLIERGROUP:
                theDataList.add(creditorsSupplierGroupBean.getNumRows(POPSupplierGroup.class, userData));
                break;
            case GETDATA_POPSUPPLIERGROUP:
                theDataList = (List<Object>) creditorsSupplierGroupBean.getDataInRange(POPSupplierGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPSUPPLIERGROUP:
                theDataList.add(creditorsSupplierGroupBean.validateField(dataList.get(1).toString(), (POPSupplierGroup) dataList.get(2), userData));
                break;
            //POP supplier groups
            case INSERT_POPPRICEGROUP:
                theDataList.add(creditorsPriceGroupBean.insert((POPPriceGroup) dataList.get(1), userData));
                break;
            case UPDATE_POPPRICEGROUP:
                theDataList.add(creditorsPriceGroupBean.update((POPPriceGroup) dataList.get(1), userData));
                break;
            case DELETE_POPPRICEGROUP:
                theDataList.add(creditorsPriceGroupBean.delete((POPPriceGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPRICEGROUP:
                theDataList.add(creditorsPriceGroupBean.getNumRows(POPPriceGroup.class, userData));
                break;
            case GETDATA_POPPRICEGROUP:
                theDataList = (List<Object>) creditorsPriceGroupBean.getDataInRange(POPPriceGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPRICEGROUP:
                theDataList.add(creditorsPriceGroupBean.validateField(dataList.get(1).toString(), (POPPriceGroup) dataList.get(2), userData));
                break;
            //POP discount groups
            case INSERT_POPDISCOUNTGROUP:
                theDataList.add(creditorsDiscountGroupBean.insert((POPDiscountGroup) dataList.get(1), userData));
                break;
            case UPDATE_POPDISCOUNTGROUP:
                theDataList.add(creditorsDiscountGroupBean.update((POPDiscountGroup) dataList.get(1), userData));
                break;
            case DELETE_POPDISCOUNTGROUP:
                theDataList.add(creditorsDiscountGroupBean.delete((POPDiscountGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPDISCOUNTGROUP:
                theDataList.add(creditorsDiscountGroupBean.getNumRows(POPDiscountGroup.class, userData));
                break;
            case GETDATA_POPDISCOUNTGROUP:
                theDataList = (List<Object>) creditorsDiscountGroupBean.getDataInRange(POPDiscountGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPDISCOUNTGROUP:
                theDataList.add(creditorsDiscountGroupBean.validateField(dataList.get(1).toString(), (POPDiscountGroup) dataList.get(2), userData));
                break;
            //POP extra charge groups
            case INSERT_POPEXTRACHARGEGROUP:
                theDataList.add(creditorsExtraChargeGroupBean.insert((POPExtraChargeGroup) dataList.get(1), userData));
                break;
            case UPDATE_POPEXTRACHARGEGROUP:
                theDataList.add(creditorsExtraChargeGroupBean.update((POPExtraChargeGroup) dataList.get(1), userData));
                break;
            case DELETE_POPEXTRACHARGEGROUP:
                theDataList.add(creditorsExtraChargeGroupBean.delete((POPExtraChargeGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPEXTRACHARGEGROUP:
                theDataList.add(creditorsExtraChargeGroupBean.getNumRows(POPExtraChargeGroup.class, userData));
                break;
            case GETDATA_POPEXTRACHARGEGROUP:
                theDataList = (List<Object>) creditorsExtraChargeGroupBean.getDataInRange(POPExtraChargeGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPEXTRACHARGEGROUP:
                theDataList.add(creditorsExtraChargeGroupBean.validateField(dataList.get(1).toString(), (POPExtraChargeGroup) dataList.get(2), userData));
                break;

            //POP purchase order master
            case INSERT_POPPURCHASEORDERMASTER:
                theDataList.add(creditorsPurchaseOrderMasterBean.insert((POPPurchaseOrderMaster) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEORDERMASTER:
                theDataList.add(creditorsPurchaseOrderMasterBean.update((POPPurchaseOrderMaster) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEORDERMASTER:
                theDataList.add(creditorsPurchaseOrderMasterBean.delete((POPPurchaseOrderMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEORDERMASTER:
                theDataList.add(creditorsPurchaseOrderMasterBean.getNumRows(POPPurchaseOrderMaster.class, userData));
                break;
            case GETDATA_POPPURCHASEORDERMASTER:
                theDataList = (List<Object>) creditorsPurchaseOrderMasterBean.getDataInRange(POPPurchaseOrderMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEORDERMASTER:
                theDataList.add(creditorsPurchaseOrderMasterBean.validateField(dataList.get(1).toString(), (POPPurchaseOrderMaster) dataList.get(2), userData));
                break;
            case APPROVE_PURCHASEORDER:
                creditorsPurchaseOrderMasterBean.approvePurchaseOrder(dataList.get(1).toString(), userData);
                break;
            case UNAPPROVE_PURCHASEORDER:
                creditorsPurchaseOrderMasterBean.unApprovePurchaseOrder(dataList.get(1).toString(), userData);
                break;
            case PRINT_PRICEVARIANCE:
                theDataList = varianceBean.getReportResult(dataList, userData);
                break;
            case UPDATE_LINES_DATE:
                creditorsPurchaseOrderMasterBean.updateLinesDate((String) dataList.get(1), (Date) dataList.get(2), userData);
                break;
            case CHECK_QC:
                theDataList.add(creditorsPurchaseOrderMasterBean.checkQCReturn(dataList.get(1).toString(), userData));
                break;
            //POP purchase order lines
            case INSERT_POPPURCHASEORDERLINES:
                theDataList.add(creditorsPurchaseOrderLinesBean.insert((POPPurchaseOrderLines) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEORDERLINES:
                theDataList.add(creditorsPurchaseOrderLinesBean.update((POPPurchaseOrderLines) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEORDERLINES:
                theDataList.add(creditorsPurchaseOrderLinesBean.delete((POPPurchaseOrderLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEORDERLINES:
                theDataList.add(creditorsPurchaseOrderLinesBean.getNumRows(POPPurchaseOrderLines.class, userData));
                break;
            case GETDATA_POPPURCHASEORDERLINES:
                theDataList = (List<Object>) creditorsPurchaseOrderLinesBean.getDataInRange(POPPurchaseOrderLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEORDERLINES:
                theDataList.add(creditorsPurchaseOrderLinesBean.validateField(dataList.get(1).toString(), (POPPurchaseOrderLines) dataList.get(2), userData));
                break;
            case SPLIT_PURCHASE_ORDER_LINE:
                theDataList.add(creditorsPurchaseOrderLinesBean.splitLine((POPPurchaseOrderLines) dataList.get(1), (Double) dataList.get(2), (Date) dataList.get(3), userData));
                break;
            //POPPurchaseOrderApprovalGroups
            case INSERT_POPPURCHASEORDERAPPROVALGROUPS:
                theDataList.add(purchaseOrderApprovalGroupBean.insert((POPPurchaseOrderApprovalGroups) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEORDERAPPROVALGROUPS:
                theDataList.add(purchaseOrderApprovalGroupBean.update((POPPurchaseOrderApprovalGroups) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEORDERAPPROVALGROUPS:
                theDataList.add(purchaseOrderApprovalGroupBean.delete((POPPurchaseOrderApprovalGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEORDERAPPROVALGROUPS:
                theDataList.add(purchaseOrderApprovalGroupBean.getNumRows(POPPurchaseOrderApprovalGroups.class, userData));
                break;
            case GETDATA_POPPURCHASEORDERAPPROVALGROUPS:
                theDataList = (List<Object>) purchaseOrderApprovalGroupBean.getDataInRange(POPPurchaseOrderApprovalGroups.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEORDERAPPROVALGROUPS:
                theDataList.add(purchaseOrderApprovalGroupBean.validateField(dataList.get(1).toString(), (POPPurchaseOrderApprovalGroups) dataList.get(2), userData));
                break;
            //POPPurchaseOrderApprovalGroupEmployees
            case INSERT_POPPURCHASEORDERAPPROVALGROUPEMPLOYEES:
                theDataList.add(purchaseOrderApprovalGroupEmpDSBean.insert((POPPurchaseOrderApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEORDERAPPROVALGROUPEMPLOYEES:
                theDataList.add(purchaseOrderApprovalGroupEmpDSBean.update((POPPurchaseOrderApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEORDERAPPROVALGROUPEMPLOYEES:
                theDataList.add(purchaseOrderApprovalGroupEmpDSBean.delete((POPPurchaseOrderApprovalGroupEmployees) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEORDERAPPROVALGROUPEMPLOYEES:
                theDataList.add(purchaseOrderApprovalGroupEmpDSBean.getNumRows(POPPurchaseOrderApprovalGroupEmployees.class, userData));
                break;
            case GETDATA_POPPURCHASEORDERAPPROVALGROUPEMPLOYEES:
                theDataList = (List<Object>) purchaseOrderApprovalGroupEmpDSBean.getDataInRange(POPPurchaseOrderApprovalGroupEmployees.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEORDERAPPROVALGROUPEMPLOYEES:
                theDataList.add(purchaseOrderApprovalGroupEmpDSBean.validateField(dataList.get(1).toString(), (POPPurchaseOrderApprovalGroupEmployees) dataList.get(2), userData));
                break;


            //POPSupplierReceivedJournalLines
            case INSERT_POPSUPPLIERRECEIVEDJOURNALLINES:
                theDataList.add(creditorsSupplierReceivedJournalLinesBean.insert((POPSupplierReceivedJournalLines) dataList.get(1), userData));
                break;
            case UPDATE_POPSUPPLIERRECEIVEDJOURNALLINES:
                theDataList.add(creditorsSupplierReceivedJournalLinesBean.update((POPSupplierReceivedJournalLines) dataList.get(1), userData));
                break;
            case DELETE_POPSUPPLIERRECEIVEDJOURNALLINES:
                theDataList.add(creditorsSupplierReceivedJournalLinesBean.delete((POPSupplierReceivedJournalLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPSUPPLIERRECEIVEDJOURNALLINES:
                theDataList.add(creditorsSupplierReceivedJournalLinesBean.getNumRows(POPSupplierReceivedJournalLines.class, userData));
                break;
            case GETDATA_POPSUPPLIERRECEIVEDJOURNALLINES:
                theDataList = (List<Object>) creditorsSupplierReceivedJournalLinesBean.getDataInRange(POPSupplierReceivedJournalLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPSUPPLIERRECEIVEDJOURNALLINES:
                theDataList.add(creditorsSupplierReceivedJournalLinesBean.validateField(dataList.get(1).toString(), (POPSupplierReceivedJournalLines) dataList.get(2), userData));
                break;
            //POPSupplierReceivedJournalMaster
            case INSERT_POPSUPPLIERRECEIVEDJOURNALMASTER:
                theDataList.add(creditorsSupplierReceivedJournalMasterBean.insert((POPSupplierReceivedJournalMaster) dataList.get(1), userData));
                break;
            case UPDATE_POPSUPPLIERRECEIVEDJOURNALMASTER:
                theDataList.add(creditorsSupplierReceivedJournalMasterBean.update((POPSupplierReceivedJournalMaster) dataList.get(1), userData));
                break;
            case DELETE_POPSUPPLIERRECEIVEDJOURNALMASTER:
                theDataList.add(creditorsSupplierReceivedJournalMasterBean.delete((POPSupplierReceivedJournalMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPSUPPLIERRECEIVEDJOURNALMASTER:
                theDataList.add(creditorsSupplierReceivedJournalMasterBean.getNumRows(POPSupplierReceivedJournalMaster.class, userData));
                break;
            case GETDATA_POPSUPPLIERRECEIVEDJOURNALMASTER:
                theDataList = (List<Object>) creditorsSupplierReceivedJournalMasterBean.getDataInRange(POPSupplierReceivedJournalMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPSUPPLIERRECEIVEDJOURNALMASTER:
                theDataList.add(creditorsSupplierReceivedJournalMasterBean.validateField(dataList.get(1).toString(), (POPSupplierReceivedJournalMaster) dataList.get(2), userData));
                break;
            //POP Purchase Post Master
            case INSERT_POPPURCHASEPOSTMASTER:
                theDataList.add(creditorsPurchasePostMasterBean.insert((POPPurchasePostMaster) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEPOSTMASTER:
                theDataList.add(creditorsPurchasePostMasterBean.update((POPPurchasePostMaster) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEPOSTMASTER:
                theDataList.add(creditorsPurchasePostMasterBean.delete((POPPurchasePostMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEPOSTMASTER:
                theDataList.add(creditorsPurchasePostMasterBean.getNumRows(POPPurchasePostMaster.class, userData));
                break;
            case GETDATA_POPPURCHASEPOSTMASTER:
                theDataList = (List<Object>) creditorsPurchasePostMasterBean.getDataInRange(POPPurchasePostMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEPOSTMASTER:
                theDataList.add(creditorsPurchasePostMasterBean.validateField(dataList.get(1).toString(), (POPPurchasePostMaster) dataList.get(2), userData));
                break;
            case CREATE_POST:
                theDataList.add(creditorsPurchasePostMasterBean.createPost(userData.getUserData().get(0).toString(), dataList.get(1).toString(), (Boolean) dataList.get(2), userData));
                break;
            case GENERATE_POST_REPORT:
                theDataList = purchaseOrderPostingReportBean.getReportResult(dataList, userData);
                break;
            case RECEIVE_PURCHASEORDER:
                creditorsPurchasePostMasterBean.receivePurchaseOrder((String) userData.getUserData(0), userData);
                break;
            case POST_PURCHASEORDER:
                theDataList.add(creditorsPurchasePostMasterBean.postPurchaseOrder(dataList.get(1).toString(), userData));
                break;
            case RELEASE_PO:
                theDataList.add(creditorsPurchasePostMasterBean.releaseBlanketOrder((String) userData.getUserData(0), userData));
                break;
            case CHECK_SERIAL_BATCH:
                theDataList.add(creditorsPurchasePostMasterBean.checkBatchSerial((String) dataList.get(1), userData));
                break;
            case POST_BLANKETORDER:
                theDataList.add(creditorsPurchasePostMasterBean.postBlanketOrder(dataList.get(1).toString(), userData));
                break;
            case PRINT_LABELS:
                theDataList.add(creditorsPurchasePostMasterBean.printLabels((String) dataList.get(1), userData));
                break;
            case CHECH_NUMLABELS:
                theDataList.add(labelsBean.checkNumLabels((String) dataList.get(1), userData));
                break;
            case RETURN_TO_SUPPLIER:
                creditorsPurchasePostMasterBean.returnToSupplier((String) dataList.get(1), (Boolean) dataList.get(2), userData);
                break;
            //POP Purchase Post Lines
            case INSERT_POPPURCHASEPOSTLINES:
                theDataList.add(creditorsPurchasePostLinesBean.insert((POPPurchasePostLines) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEPOSTLINES:
                theDataList.add(creditorsPurchasePostLinesBean.update((POPPurchasePostLines) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEPOSTLINES:
                theDataList.add(creditorsPurchasePostLinesBean.delete((POPPurchasePostLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEPOSTLINES:
                theDataList.add(creditorsPurchasePostLinesBean.getNumRows(POPPurchasePostLines.class, userData));
                break;
            case GETDATA_POPPURCHASEPOSTLINES:
                theDataList = (List<Object>) creditorsPurchasePostLinesBean.getDataInRange(POPPurchasePostLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEPOSTLINES:
                theDataList.add(creditorsPurchasePostLinesBean.validateField(dataList.get(1).toString(), (POPPurchasePostLines) dataList.get(2), userData));
                break;
            //POP Purchase Post Setup Table
            case INSERT_POPPURCHASEPOSTSETUPTABLE:
                theDataList.add(creditorsPurchasePostSetupTableBean.insert((POPPurchasePostSetupTable) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEPOSTSETUPTABLE:
                theDataList.add(creditorsPurchasePostSetupTableBean.update((POPPurchasePostSetupTable) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEPOSTSETUPTABLE:
                theDataList.add(creditorsPurchasePostSetupTableBean.delete((POPPurchasePostSetupTable) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEPOSTSETUPTABLE:
                theDataList.add(creditorsPurchasePostSetupTableBean.getNumRows(POPPurchasePostSetupTable.class, userData));
                break;
            case GETDATA_POPPURCHASEPOSTSETUPTABLE:
                theDataList = (List<Object>) creditorsPurchasePostSetupTableBean.getDataInRange(POPPurchasePostSetupTable.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEPOSTSETUPTABLE:
                theDataList.add(creditorsPurchasePostSetupTableBean.validateField(dataList.get(1).toString(), (POPPurchasePostSetupTable) dataList.get(2), userData));
                break;
            //POPPurchasePostMasterDS
            case INSERT_POPPURCHASEPOSTMASTERDS:
                theDataList.add(postMasterDSBean.insert((POPPurchasePostMasterDS) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEPOSTMASTERDS:
                theDataList.add(postMasterDSBean.update((POPPurchasePostMasterDS) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEPOSTMASTERDS:
                theDataList.add(postMasterDSBean.delete((POPPurchasePostMasterDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEPOSTMASTERDS:
                theDataList.add(postMasterDSBean.getNumRows(POPPurchasePostMasterDS.class, userData));
                break;
            case GETDATA_POPPURCHASEPOSTMASTERDS:
                theDataList = (List<Object>) postMasterDSBean.getDataInRange(POPPurchasePostMasterDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEPOSTMASTERDS:
                theDataList.add(postMasterDSBean.validateField(dataList.get(1).toString(), (POPPurchasePostMasterDS) dataList.get(2), userData));
                break;
            //POPDeliveryTerms
            case INSERT_POPDELIVERYTERMS:
                theDataList.add(deliveryTermsBean.insert((POPDeliveryTerms) dataList.get(1), userData));
                break;
            case UPDATE_POPDELIVERYTERMS:
                theDataList.add(deliveryTermsBean.update((POPDeliveryTerms) dataList.get(1), userData));
                break;
            case DELETE_POPDELIVERYTERMS:
                theDataList.add(deliveryTermsBean.delete((POPDeliveryTerms) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPDELIVERYTERMS:
                theDataList.add(deliveryTermsBean.getNumRows(POPDeliveryTerms.class, userData));
                break;
            case GETDATA_POPDELIVERYTERMS:
                theDataList = (List<Object>) deliveryTermsBean.getDataInRange(POPDeliveryTerms.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPDELIVERYTERMS:
                theDataList.add(deliveryTermsBean.validateField(dataList.get(1).toString(), (POPDeliveryTerms) dataList.get(2), userData));
                break;

            //POPDeliveryModes
            case INSERT_POPDELIVERYMODES:
                theDataList.add(deliveryModesBean.insert((POPDeliveryModes) dataList.get(1), userData));
                break;
            case UPDATE_POPDELIVERYMODES:
                theDataList.add(deliveryModesBean.update((POPDeliveryModes) dataList.get(1), userData));
                break;
            case DELETE_POPDELIVERYMODES:
                theDataList.add(deliveryModesBean.delete((POPDeliveryModes) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPDELIVERYMODES:
                theDataList.add(deliveryModesBean.getNumRows(POPDeliveryModes.class, userData));
                break;
            case GETDATA_POPDELIVERYMODES:
                theDataList = (List<Object>) deliveryModesBean.getDataInRange(POPDeliveryModes.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPDELIVERYMODES:
                theDataList.add(deliveryModesBean.validateField(dataList.get(1).toString(), (POPDeliveryModes) dataList.get(2), userData));
                break;

            //POPPurchaseOrderLinesDS
            case INSERT_POPPURCHASEORDERLINESDS:
                theDataList.add(purchaseOrderlinesDSBean.insert((POPPurchaseOrderLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEORDERLINESDS:
                theDataList.add(purchaseOrderlinesDSBean.update((POPPurchaseOrderLinesDS) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEORDERLINESDS:
                theDataList.add(purchaseOrderlinesDSBean.delete((POPPurchaseOrderLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEORDERLINESDS:
                theDataList.add(purchaseOrderlinesDSBean.getNumRows(POPPurchaseOrderLinesDS.class, userData));
                break;
            case GETDATA_POPPURCHASEORDERLINESDS:
                theDataList = (List<Object>) purchaseOrderlinesDSBean.getDataInRange(POPPurchaseOrderLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEORDERLINESDS:
                theDataList.add(purchaseOrderlinesDSBean.validateField(dataList.get(1).toString(), (POPPurchaseOrderLinesDS) dataList.get(2), userData));
                break;
            //POPPurchasePostLinesDS
            case INSERT_POPPURCHASEPOSTLINESDS:
                theDataList.add(purchasePostLinesDSBean.insert((POPPurchasePostLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_POPPURCHASEPOSTLINESDS:
                theDataList.add(purchasePostLinesDSBean.update((POPPurchasePostLinesDS) dataList.get(1), userData));
                break;
            case DELETE_POPPURCHASEPOSTLINESDS:
                theDataList.add(purchasePostLinesDSBean.delete((POPPurchasePostLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPURCHASEPOSTLINESDS:
                theDataList.add(purchasePostLinesDSBean.getNumRows(POPPurchasePostLinesDS.class, userData));
                break;
            case GETDATA_POPPURCHASEPOSTLINESDS:
                theDataList = (List<Object>) purchasePostLinesDSBean.getDataInRange(POPPurchasePostLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPURCHASEPOSTLINESDS:
                theDataList.add(purchasePostLinesDSBean.validateField(dataList.get(1).toString(), (POPPurchasePostLinesDS) dataList.get(2), userData));
                break;
            //POPSupplierReceivedJournalLinesDS
            case INSERT_POPSUPPLIERRECEIVEDJOURNALLINESDS:
                theDataList.add(recievedJournalLinesDS.insert((POPSupplierReceivedJournalLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_POPSUPPLIERRECEIVEDJOURNALLINESDS:
                theDataList.add(recievedJournalLinesDS.update((POPSupplierReceivedJournalLinesDS) dataList.get(1), userData));
                break;
            case DELETE_POPSUPPLIERRECEIVEDJOURNALLINESDS:
                theDataList.add(recievedJournalLinesDS.delete((POPSupplierReceivedJournalLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPSUPPLIERRECEIVEDJOURNALLINESDS:
                theDataList.add(recievedJournalLinesDS.getNumRows(POPSupplierReceivedJournalLinesDS.class, userData));
                break;
            case GETDATA_POPSUPPLIERRECEIVEDJOURNALLINESDS:
                theDataList = (List<Object>) recievedJournalLinesDS.getDataInRange(POPSupplierReceivedJournalLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPSUPPLIERRECEIVEDJOURNALLINESDS:
                theDataList.add(recievedJournalLinesDS.validateField(dataList.get(1).toString(), (POPSupplierReceivedJournalLinesDS) dataList.get(2), userData));
                break;
            //POPQualityTestType
            case INSERT_POPQUALITYTESTTYPE:
                theDataList.add(qualityBean.insert((POPQualityTestType) dataList.get(1), userData));
                break;
            case UPDATE_POPQUALITYTESTTYPE:
                theDataList.add(qualityBean.update((POPQualityTestType) dataList.get(1), userData));
                break;
            case DELETE_POPQUALITYTESTTYPE:
                theDataList.add(qualityBean.delete((POPQualityTestType) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPQUALITYTESTTYPE:
                theDataList.add(qualityBean.getNumRows(POPQualityTestType.class, userData));
                break;
            case GETDATA_POPQUALITYTESTTYPE:
                theDataList = (List<Object>) qualityBean.getDataInRange(POPQualityTestType.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPQUALITYTESTTYPE:
                theDataList.add(qualityBean.validateField(dataList.get(1).toString(), (POPQualityTestType) dataList.get(2), userData));
                break;
            //POPParameters
            case INSERT_POPPARAMETERS:
                theDataList.add(parametersBean.insert((POPParameters) dataList.get(1), userData));
                break;
            case UPDATE_POPPARAMETERS:
                theDataList.add(parametersBean.update((POPParameters) dataList.get(1), userData));
                break;
            case DELETE_POPPARAMETERS:
                theDataList.add(parametersBean.delete((POPParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPARAMETERS:
                theDataList.add(parametersBean.getNumRows(POPParameters.class, userData));
                break;
            case GETDATA_POPPARAMETERS:
                theDataList = (List<Object>) parametersBean.getDataInRange(POPParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPARAMETERS:
                theDataList.add(parametersBean.validateField(dataList.get(1).toString(), (POPParameters) dataList.get(2), userData));
                break;
            case GET_PLANNED_RELEASE_ITEM_FIELD:
                theDataList.add(parametersBean.getPlannedReleaseItemFields(userData));
                break;
            //Outstanding PO report
            case PRINT_OUTSTANDINGPOREPORT:
                theDataList = outstandingPOReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Goods received report
            case PRINT_GOODSRECEIVEDREPORT:
                theDataList = goodsReceivedBean.getReportResult(dataList, userData);
                break;
            case PRINT_GOODSRETURNEDREPORT:
                theDataList = returnReportBean.getReportResult(dataList, userData);
                break;
            case PRINT_GOODS_RECEIVE_REPORT:
                theDataList = goodsReceiveBean.getReportResult(dataList, userData);
                break;
            //Cancel Purchase Order
            //Services Received Report
            case PRINT_SERVICES_RECEIVED_REPORT:
                theDataList = servicesReceivedBean.getReportResult(dataList, userData);
                break;
            //POPCancelledPurchaseOrderMaster
            case INSERT_POPCANCELLEDPURCHASEORDERMASTER:
                theDataList.add(cancelledPOMaster.insert((POPCancelledPurchaseOrderMaster) dataList.get(1), userData));
                break;
            case UPDATE_POPCANCELLEDPURCHASEORDERMASTER:
                theDataList.add(cancelledPOMaster.update((POPCancelledPurchaseOrderMaster) dataList.get(1), userData));
                break;
            case DELETE_POPCANCELLEDPURCHASEORDERMASTER:
                theDataList.add(cancelledPOMaster.delete((POPCancelledPurchaseOrderMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPCANCELLEDPURCHASEORDERMASTER:
                theDataList.add(cancelledPOMaster.getNumRows(POPCancelledPurchaseOrderMaster.class, userData));
                break;
            case GETDATA_POPCANCELLEDPURCHASEORDERMASTER:
                theDataList = (List<Object>) cancelledPOMaster.getDataInRange(POPCancelledPurchaseOrderMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPCANCELLEDPURCHASEORDERMASTER:
                theDataList.add(cancelledPOMaster.validateField(dataList.get(1).toString(), (POPCancelledPurchaseOrderMaster) dataList.get(2), userData));
                break;

            case CANCEL_PURCHASEORDER:
                theDataList.add(cancelledPOMaster.cancelPurchaseOrder((POPPurchaseOrderMaster) dataList.get(1), userData));
                break;
            //Cancel Purchase Order Line
            case CANCEL_PURCHASEORDERLINE:
                theDataList.add(purchaseOrderlinesDSBean.cancelPurchaseOrderLine((POPPurchaseOrderLinesDS) dataList.get(1), userData));
                break;
            //Print Cancelled Purchase Order
            case PRINT_CANCELLED_PO:
                theDataList = cancelledPOReportBean.getReportResult(dataList, userData);
                break;
            //POPCostChangeReason
            case INSERT_POPCOSTCHANGEREASON:
                theDataList.add(reasonBean.insert((POPCostChangeReason) dataList.get(1), userData));
                break;
            case UPDATE_POPCOSTCHANGEREASON:
                theDataList.add(reasonBean.update((POPCostChangeReason) dataList.get(1), userData));
                break;
            case DELETE_POPCOSTCHANGEREASON:
                theDataList.add(reasonBean.delete((POPCostChangeReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPCOSTCHANGEREASON:
                theDataList.add(reasonBean.getNumRows(POPCostChangeReason.class, userData));
                break;
            case GETDATA_POPCOSTCHANGEREASON:
                theDataList = (List<Object>) reasonBean.getDataInRange(POPCostChangeReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPCOSTCHANGEREASON:
                theDataList.add(reasonBean.validateField(dataList.get(1).toString(), (POPCostChangeReason) dataList.get(2), userData));
                break;
            //CostChangeSummaryDS
            case INSERT_COSTCHANGESUMMARYDS:
                theDataList.add(costChangeBean.insert((CostChangeSummaryDS) dataList.get(1), userData));
                break;
            case UPDATE_COSTCHANGESUMMARYDS:
                theDataList.add(costChangeBean.update((CostChangeSummaryDS) dataList.get(1), userData));
                break;
            case DELETE_COSTCHANGESUMMARYDS:
                theDataList.add(costChangeBean.delete((CostChangeSummaryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_COSTCHANGESUMMARYDS:
                theDataList.add(costChangeBean.getNumRows(CostChangeSummaryDS.class, userData));
                break;
            case GETDATA_COSTCHANGESUMMARYDS:
                theDataList = (List<Object>) costChangeBean.getDataInRange(CostChangeSummaryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_COSTCHANGESUMMARYDS:
                theDataList.add(costChangeBean.validateField(dataList.get(1).toString(), (CostChangeSummaryDS) dataList.get(2), userData));
                break;
            case GENERATE_GOODS_RECEIVED_REPORT:
                theDataList = goodsReceivedReportBean.getReportResult(dataList, userData);
                break;
            //POPCancelledPurchaseOrderLinesDS
            case INSERT_POPCANCELLEDPURCHASEORDERLINESDS:
                theDataList.add(cancelDSBean.insert((POPCancelledPurchaseOrderLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_POPCANCELLEDPURCHASEORDERLINESDS:
                theDataList.add(cancelDSBean.update((POPCancelledPurchaseOrderLinesDS) dataList.get(1), userData));
                break;
            case DELETE_POPCANCELLEDPURCHASEORDERLINESDS:
                theDataList.add(cancelDSBean.delete((POPCancelledPurchaseOrderLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPCANCELLEDPURCHASEORDERLINESDS:
                theDataList.add(cancelDSBean.getNumRows(POPCancelledPurchaseOrderLinesDS.class, userData));
                break;
            case GETDATA_POPCANCELLEDPURCHASEORDERLINESDS:
                theDataList = (List<Object>) cancelDSBean.getDataInRange(POPCancelledPurchaseOrderLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPCANCELLEDPURCHASEORDERLINESDS:
                theDataList.add(cancelDSBean.validateField(dataList.get(1).toString(), (POPCancelledPurchaseOrderLinesDS) dataList.get(2), userData));
                break;
            //POPGRNReprintTemp
            case INSERT_POPGRNREPRINTTEMP:
                theDataList.add(grnReprintBean.insert((POPGRNReprintTemp) dataList.get(1), userData));
                break;
            case UPDATE_POPGRNREPRINTTEMP:
                theDataList.add(grnReprintBean.update((POPGRNReprintTemp) dataList.get(1), userData));
                break;
            case DELETE_POPGRNREPRINTTEMP:
                theDataList.add(grnReprintBean.delete((POPGRNReprintTemp) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPGRNREPRINTTEMP:
                theDataList.add(grnReprintBean.getNumRows(POPGRNReprintTemp.class, userData));
                break;
            case GETDATA_POPGRNREPRINTTEMP:
                theDataList = (List<Object>) grnReprintBean.getDataInRange(POPGRNReprintTemp.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPGRNREPRINTTEMP:
                theDataList.add(grnReprintBean.validateField(dataList.get(1).toString(), (POPGRNReprintTemp) dataList.get(2), userData));
                break;
            //POPGRNReprintTempDS
            case INSERT_POPGRNREPRINTTEMPDS:
                theDataList.add(grnReprintDSBean.insert((POPGRNReprintTempDS) dataList.get(1), userData));
                break;
            case UPDATE_POPGRNREPRINTTEMPDS:
                theDataList.add(grnReprintDSBean.update((POPGRNReprintTempDS) dataList.get(1), userData));
                break;
            case DELETE_POPGRNREPRINTTEMPDS:
                theDataList.add(grnReprintDSBean.delete((POPGRNReprintTempDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPGRNREPRINTTEMPDS:
                theDataList.add(grnReprintDSBean.getNumRows(POPGRNReprintTempDS.class, userData));
                break;
            case GETDATA_POPGRNREPRINTTEMPDS:
                theDataList = (List<Object>) grnReprintDSBean.getDataInRange(POPGRNReprintTempDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPGRNREPRINTTEMPDS:
                theDataList.add(grnReprintDSBean.validateField(dataList.get(1).toString(), (POPGRNReprintTempDS) dataList.get(2), userData));
                break;
            case REPRINT_LABLES:
                theDataList.add(labelsBean.reprintLables((String) dataList.get(1), userData));
                break;
//POPGoodsReturnReason
            case INSERT_POPGOODSRETURNREASON:
                theDataList.add(returnReasonBean.insert((POPGoodsReturnReason) dataList.get(1), userData));
                break;
            case UPDATE_POPGOODSRETURNREASON:
                theDataList.add(returnReasonBean.update((POPGoodsReturnReason) dataList.get(1), userData));
                break;
            case DELETE_POPGOODSRETURNREASON:
                theDataList.add(returnReasonBean.delete((POPGoodsReturnReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPGOODSRETURNREASON:
                theDataList.add(returnReasonBean.getNumRows(POPGoodsReturnReason.class, userData));
                break;
            case GETDATA_POPGOODSRETURNREASON:
                theDataList = (List<Object>) returnReasonBean.getDataInRange(POPGoodsReturnReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPGOODSRETURNREASON:
                theDataList.add(returnReasonBean.validateField(dataList.get(1).toString(), (POPGoodsReturnReason) dataList.get(2), userData));
                break;
            case PRINT_MILL_PURCHASE_REPORT:
                theDataList = millPurchaseReportBean.getReportResult(dataList, userData);
                break;
            //POPPlannedPurchaseOrders
            case INSERT_POPPLANNEDPURCHASEORDERS:
                theDataList.add(plannedPurchaseOrderBean.insert((POPPlannedPurchaseOrders) dataList.get(1), userData));
                break;
            case UPDATE_POPPLANNEDPURCHASEORDERS:
                theDataList.add(plannedPurchaseOrderBean.update((POPPlannedPurchaseOrders) dataList.get(1), userData));
                break;
            case DELETE_POPPLANNEDPURCHASEORDERS:
                theDataList.add(plannedPurchaseOrderBean.delete((POPPlannedPurchaseOrders) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPLANNEDPURCHASEORDERS:
                theDataList.add(plannedPurchaseOrderBean.getNumRows(POPPlannedPurchaseOrders.class, userData));
                break;
            case GETDATA_POPPLANNEDPURCHASEORDERS:
                theDataList = (List<Object>) plannedPurchaseOrderBean.getDataInRange(POPPlannedPurchaseOrders.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPLANNEDPURCHASEORDERS:
                theDataList.add(plannedPurchaseOrderBean.validateField(dataList.get(1).toString(), (POPPlannedPurchaseOrders) dataList.get(2), userData));
                break;
            case FIRM_PLANNED_PURCHASE_ORDER:
                theDataList.add(plannedPurchaseOrderBean.firmOrder(dataList.get(1).toString(), userData));
                break;
            case RELEASE_PLANNED_PURCHASE_ORDER:
                theDataList.add(plannedPurchaseOrderBean.releasePlannedPO((Date) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3),
                        (Map<Long, Long>) dataList.get(4), userData));
                break;
            case FIND_DEFAULT_ITEM_VALUES:
                Object oa = plannedPurchaseOrderBean.findDefaultItemValues(dataList.get(1).toString(), userData);
                theDataList.add(oa);
                break;
            //POPPlannedPurchaseOrdersDS
            case INSERT_POPPLANNEDPURCHASEORDERSDS:
                theDataList.add(plannedPurchaseOrderDSBean.insert((POPPlannedPurchaseOrdersDS) dataList.get(1), userData));
                break;
            case UPDATE_POPPLANNEDPURCHASEORDERSDS:
                theDataList.add(plannedPurchaseOrderDSBean.update((POPPlannedPurchaseOrdersDS) dataList.get(1), userData));
                break;
            case DELETE_POPPLANNEDPURCHASEORDERSDS:
                theDataList.add(plannedPurchaseOrderDSBean.delete((POPPlannedPurchaseOrdersDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPLANNEDPURCHASEORDERSDS:
                theDataList.add(plannedPurchaseOrderDSBean.getNumRows(POPPlannedPurchaseOrdersDS.class, userData));
                break;
            case GETDATA_POPPLANNEDPURCHASEORDERSDS:
                theDataList = (List<Object>) plannedPurchaseOrderDSBean.getDataInRange(POPPlannedPurchaseOrdersDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPLANNEDPURCHASEORDERSDS:
                theDataList.add(plannedPurchaseOrderDSBean.validateField(dataList.get(1).toString(), (POPPlannedPurchaseOrdersDS) dataList.get(2), userData));
                break;
            //POP Price Arrangements
            case INSERT_POPPRICEARRANGEMENTS:
                theDataList.add(priceArrangementsBean.insert((POPPriceArrangements) dataList.get(1), userData));
                break;
            case UPDATE_POPPRICEARRANGEMENTS:
                theDataList.add(priceArrangementsBean.update((POPPriceArrangements) dataList.get(1), userData));
                break;
            case DELETE_POPPRICEARRANGEMENTS:
                theDataList.add(priceArrangementsBean.delete((POPPriceArrangements) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPRICEARRANGEMENTS:
                theDataList.add(priceArrangementsBean.getNumRows(POPPriceArrangements.class, userData));
                break;
            case GETDATA_POPPRICEARRANGEMENTS:
                theDataList = (List<Object>) priceArrangementsBean.getDataInRange(POPPriceArrangements.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPRICEARRANGEMENTS:
                theDataList.add(priceArrangementsBean.validateField(dataList.get(1).toString(), (POPPriceArrangements) dataList.get(2), userData));
                break;
            //POP Price Arrangement DS
            case INSERT_POPPRICEARRANGEMENTDS:
                theDataList.add(priceArrangementsDSBean.insert((POPPriceArrangementDS) dataList.get(1), userData));
                break;
            case UPDATE_POPPRICEARRANGEMENTDS:
                theDataList.add(priceArrangementsDSBean.update((POPPriceArrangementDS) dataList.get(1), userData));
                break;
            case DELETE_POPPRICEARRANGEMENTDS:
                theDataList.add(priceArrangementsDSBean.delete((POPPriceArrangementDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_POPPRICEARRANGEMENTDS:
                theDataList.add(priceArrangementsDSBean.getNumRows(POPPriceArrangementDS.class, userData));
                break;
            case GETDATA_POPPRICEARRANGEMENTDS:
                theDataList = (List<Object>) priceArrangementsDSBean.getDataInRange(POPPriceArrangementDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_POPPRICEARRANGEMENTDS:
                theDataList.add(priceArrangementsDSBean.validateField(dataList.get(1).toString(), (POPPriceArrangementDS) dataList.get(2), userData));
                break;

            default:
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Mapper: Method not found", userData);
                }
                break;
        }

        theDataList.add(0, retCmd);
        return theDataList;
    }
}

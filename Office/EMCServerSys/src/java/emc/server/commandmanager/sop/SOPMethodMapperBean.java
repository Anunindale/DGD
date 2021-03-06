/*  
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager.sop;

import emc.bus.sop.blanketorderstatusenquiry.SOPBlanketOrderStatusEnquiryDSLocal;
import emc.bus.sop.changedatereason.SOPSalesOrderChangeDateReasonLocal;
import emc.bus.sop.classification1.SOPCustomerClassification1Local;
import emc.bus.sop.classification2.SOPCustomerClassification2Local;
import emc.bus.sop.classification3.SOPCustomerClassification3Local;
import emc.bus.sop.classification4.SOPCustomerClassification4Local;
import emc.bus.sop.classification5.SOPCustomerClassification5Local;
import emc.bus.sop.classification6.SOPCustomerClassification6Local;
import emc.bus.sop.customergroup.SOPCustomerGroupLocal;
import emc.bus.sop.customerlabels.SOPCustomerLabelsLocal;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.bus.sop.discountsetup.SOPDiscountSetupLocal;
import emc.bus.sop.discountsetup.datasource.SOPDiscountSetupDSLocal;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.bus.sop.postlines.SOPSalesOrderPostLinesLocal;
import emc.bus.sop.postlines.datasource.SOPSalesOrderPostLinesDSLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.bus.sop.pricearangements.datasource.SOPPriceArangementsDSLocal;
import emc.bus.sop.priceaudittrail.SOPPriceAuditTrailLocal;
import emc.bus.sop.priceaudittrail.datasource.SOPPriceAuditTrailDSLocal;
import emc.bus.sop.pricechangereason.SOPPriceChangeReasonLocal;
import emc.bus.sop.repgroups.SOPSalesRepGroupsLocal;
import emc.bus.sop.repgroups.datasource.SOPSalesRepGroupsDSLocal;
import emc.bus.sop.repgroupsetup.SOPSalesRepGroupSetupLocal;
import emc.bus.sop.repgroupsetup.datasource.SOPSalesRepGroupSetupDSLocal;
import emc.bus.sop.salesbysizeenquiry.SOPSalesBySizeEnquiryLocal;
import emc.bus.sop.salesbysizeenquiry.datasource.SOPSalesBySizeEnquiryDSLocal;
import emc.bus.sop.salesenquiry.SOPSalesEnquiryLocal;

import emc.bus.sop.salesordercancelreason.SOPSalesOrderCancelReasonLocal;

import emc.bus.sop.salesorderpostregister.SOPSalesOrderPostRegisterLocal;
import emc.bus.sop.salesrepcommission.SOPSalesRepCommissionLocal;
import emc.bus.sop.salesrepcommission.datasource.SOPSalesRepCommissionDSLocal;
import emc.bus.sop.salesrepcommissionenquiry.SOPSalesRepCommissionEnquiryLocal;
import emc.bus.sop.statusenquiry.SOPStatusEnquirySummaryLocal;
import emc.commands.EMCCommands;
import emc.entity.crm.CRMProspect;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.sop.SOPCustomerClassification1;
import emc.entity.sop.SOPCustomerClassification2;
import emc.entity.sop.SOPCustomerClassification3;
import emc.entity.sop.SOPCustomerClassification4;
import emc.entity.sop.SOPCustomerClassification5;
import emc.entity.sop.SOPCustomerClassification6;
import emc.entity.sop.SOPCustomerGroup;
import emc.entity.sop.SOPCustomerLabels;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPDiscountSetup;
import emc.entity.sop.SOPParameters;
import emc.entity.sop.SOPPriceArangements;
import emc.entity.sop.SOPPriceAuditTrail;
import emc.entity.sop.SOPPriceChangeReason;
import emc.entity.sop.SOPSalesBySizeEnquiry;
import emc.entity.sop.SOPSalesEnquiry;
import emc.entity.sop.SOPSalesOrderCancelReason;
import emc.entity.sop.SOPSalesOrderChangeDateReason;
import emc.entity.sop.SOPSalesOrderPostLines;
import emc.entity.sop.SOPSalesOrderPostRegister;
import emc.entity.sop.SOPSalesRepCommission;
import emc.entity.sop.SOPSalesRepGroups;
import emc.entity.sop.SOPSalesRepGroupsSetup;
import emc.entity.sop.SOPStatusEnquirySummary;
import emc.entity.sop.datasource.SOPDiscountSetupDS;
import emc.entity.sop.datasource.SOPPriceArangementsDS;
import emc.entity.sop.datasource.SOPPriceAuditTrailDS;
import emc.entity.sop.datasource.SOPSalesBySizeEnquiryDS;
import emc.entity.sop.datasource.SOPSalesOrderPostLinesDS;
import emc.entity.sop.datasource.SOPSalesRepCommissionDS;
import emc.entity.sop.datasource.SOPSalesRepCommissionEnquiry;
import emc.entity.sop.datasource.SOPSalesRepGroupSetupDS;
import emc.entity.sop.datasource.SOPSalesRepGroupsDS;
import emc.entity.sop.datasource.blanketorderstatusenquiry.SOPBlanketOrderStatusEnquiryDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.debtors.DebtorsWebRegistrationHelper;
import emc.helpers.sop.SOPSalesBySizeEnquiryHelper;
import emc.helpers.sop.SalesEnquiryHelper;
import emc.methods.sop.ClientSOPMethods;
import emc.methods.sop.ServerSOPMethods;
import emc.reports.sop.commission.SOPCommissionreportLocal;
import emc.reports.sop.controlsheet.SOPControlSheetReportLocal;
import emc.reports.sop.customerbysalesrep.SOPCustomerBySalesRepInterface;
import emc.reports.sop.customerlables.SOPCustomerLablesLocal;
import emc.reports.sop.opensalesorders.SOPOpenSalesOrdersLocal;
import emc.reports.sop.orderschedule.SOPOrderScheduleReportLocal;
import emc.reports.sop.orderschedule.byitem.SOPOrderScheduleByItemReportLocal;
import emc.reports.sop.outstandingorders.SOPOutstandingSalesOrdersReportLocal;
import emc.reports.sop.overdueorders.SOPOverdueSalesOrdersReportLocal;
import emc.reports.sop.priceaudittrail.SOPPriceAuditTrailReportLocal;
import emc.reports.sop.rollingsalesreport.SOPRollingSalesReportLocal;
import emc.reports.sop.salesorders.SOPSalesOrdersReportLocal;
import emc.reports.sop.stockavailableforsale.SOPStockAvailableForSaleLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPMethodMapperBean implements SOPMethodMapperBeanLocal {

    @EJB
    private SOPCustomersLocal customerBean;
    @EJB
    private SOPCustomerGroupLocal customerGroupBean;
    @EJB
    private SOPCustomerClassification1Local classification1Bean;
    @EJB
    private SOPCustomerClassification2Local classification2Bean;
    @EJB
    private SOPCustomerClassification3Local classification3Bean;
    @EJB
    private SOPCustomerClassification4Local classification4Bean;
    @EJB
    private SOPCustomerClassification5Local classification5Bean;
    @EJB
    private SOPCustomerClassification6Local classification6Bean;
    @EJB
    private SOPOutstandingSalesOrdersReportLocal outstandingOrdersReportBean;
    @EJB
    private SOPSalesOrdersReportLocal salesOrdersReportBean;
    @EJB
    private SOPOverdueSalesOrdersReportLocal overdueOrdersReportBean;
    @EJB
    private SOPPriceArangementsLocal priceArangementsBean;
    @EJB
    private SOPPriceArangementsDSLocal priceArangementsDSBean;
    @EJB
    private SOPParametersLocal parameterBean;
    @EJB
    private SOPSalesRepGroupsLocal salesRepGroupsBean;
    @EJB
    private SOPSalesRepGroupsDSLocal salesRepGroupsDSBean;
    @EJB
    private SOPSalesRepGroupSetupLocal salesRepGroupSetupBean;
    @EJB
    private SOPSalesRepGroupSetupDSLocal salesRepGroupSetupDSBean;
    @EJB
    private SOPSalesRepCommissionLocal salesRepCommissionBean;
    @EJB
    private SOPSalesRepCommissionEnquiryLocal salesRepCommissionEnquiryBean;
    @EJB
    private SOPSalesOrderPostLinesLocal salesOrderPostLinesBean;
    @EJB
    private SOPSalesOrderPostLinesDSLocal salesOrderPostLinesDSBean;
    @EJB
    private SOPSalesOrderPostRegisterLocal salesOrderPostRegisterBean;
    @EJB
    private SOPSalesOrderCancelReasonLocal salesOrderCancelReasonBean;
    @EJB
    private SOPPriceChangeReasonLocal priceChangeReasonBean;
    @EJB
    private SOPControlSheetReportLocal controlSheetReportBean;
    @EJB
    private SOPDiscountSetupLocal discountSetupBean;
    @EJB
    private SOPDiscountSetupDSLocal discountSetupDSBean;
    @EJB
    private SOPCustomerLabelsLocal customerLabelsBean;
    @EJB
    private SOPBlanketOrderStatusEnquiryDSLocal blanketOrderStatusEnquiryBean;
    @EJB
    private SOPStatusEnquirySummaryLocal statusEnquiryBean;
    @EJB
    private SOPCommissionreportLocal commissionReportBean;
    @EJB
    private SOPOpenSalesOrdersLocal openSalesOrdersReportBean;
    @EJB
    private SOPStockAvailableForSaleLocal stockAvailableForSaleBean;
    @EJB
    private SOPSalesEnquiryLocal salesEnquiryBean;
    @EJB
    private SOPPriceAuditTrailLocal priceAuditTrailBean;
    @EJB
    private SOPPriceAuditTrailDSLocal priceAuditTrailDSBean;
    @EJB
    private SOPCustomerLablesLocal customerLablesBean;
    @EJB
    private SOPPriceAuditTrailReportLocal priceAuditTrailReportBean;
    @EJB
    private SOPOrderScheduleReportLocal orderScheduleReportBean;
    @EJB
    private SOPOrderScheduleByItemReportLocal orderScheduleByItemReportBean;
    @EJB
    private SOPRollingSalesReportLocal rollingSalesReportBean;
    @EJB
    private SOPSalesOrderChangeDateReasonLocal salesOrderChangeDateReason;
    @EJB
    private SOPSalesBySizeEnquiryLocal salesBySizeEnquiryBean;
    @EJB
    private SOPSalesBySizeEnquiryDSLocal salesBySizeEnquiryDSBean;
    @EJB
    private SOPSalesRepCommissionDSLocal salesRepCommissionDSBean;
    @EJB
    private SOPCustomerBySalesRepInterface customerBySalesRepBean;

    public SOPMethodMapperBean() {
    }

    @Override
    public List mapMethodSOP(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.SOP.getId());
        retCmd.setMethodId(ClientSOPMethods.GENERAL_METHOD.toString());

        switch (ServerSOPMethods.fromString(cmd.getMethodId())) {
            //SOPCustomers
            case INSERT_SOPCUSTOMERS:
                theDataList.add(customerBean.insert((SOPCustomers) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERS:
                theDataList.add(customerBean.update((SOPCustomers) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERS:
                theDataList.add(customerBean.delete((SOPCustomers) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERS:
                theDataList.add(customerBean.getNumRows(SOPCustomers.class, userData));
                break;
            case GETDATA_SOPCUSTOMERS:
                theDataList = (List<Object>) customerBean.getDataInRange(SOPCustomers.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERS:
                theDataList.add(customerBean.validateField(dataList.get(1).toString(), (SOPCustomers) dataList.get(2), userData));
                break;
            case POPULATE_CUSTOMER_FROM_PROSPECT:
                theDataList.add(customerBean.createCustomerFromProspect((CRMProspect) dataList.get(1), userData));
                break;
            case GET_CUSTOMER:
                theDataList.add(customerBean.findCustomer((String) dataList.get(1), userData));
                break;
            case GET_CUSTOMER_ACTIVITY:
                theDataList.add(customerBean.getCustomerActivity((String) dataList.get(1), userData));
                break;
            case GET_ADMIN_TOOL_CUSTOMER:
                theDataList.add(customerBean.getCustomersForAdminTool(userData));
                break;
            case CREATE_WEB_CUSTOMER:
                theDataList.add(customerBean.doWebRegistration((DebtorsWebRegistrationHelper) dataList.get(1), userData));
                break;
            case FIND_WEB_CUSTOMER:
                theDataList.add(customerBean.findWebPortalUser((DebtorsWebRegistrationHelper) dataList.get(1), userData));
                break;
            case CREATE_WEB_USER:
                theDataList.add(customerBean.createWebPortalUser((SOPCustomers) dataList.get(1), (String) dataList.get(2), userData));
                break;
            //SOPCustomerGroup
            case INSERT_SOPCUSTOMERGROUP:
                theDataList.add(customerGroupBean.insert((SOPCustomerGroup) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERGROUP:
                theDataList.add(customerGroupBean.update((SOPCustomerGroup) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERGROUP:
                theDataList.add(customerGroupBean.delete((SOPCustomerGroup) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERGROUP:
                theDataList.add(customerGroupBean.getNumRows(SOPCustomerGroup.class, userData));
                break;
            case GETDATA_SOPCUSTOMERGROUP:
                theDataList = (List<Object>) customerGroupBean.getDataInRange(SOPCustomerGroup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERGROUP:
                theDataList.add(customerGroupBean.validateField(dataList.get(1).toString(), (SOPCustomerGroup) dataList.get(2), userData));
                break;

            //SOPCustomerClassification1
            case INSERT_SOPCUSTOMERCLASSIFICATION1:
                theDataList.add(classification1Bean.insert((SOPCustomerClassification1) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERCLASSIFICATION1:
                theDataList.add(classification1Bean.update((SOPCustomerClassification1) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERCLASSIFICATION1:
                theDataList.add(classification1Bean.delete((SOPCustomerClassification1) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERCLASSIFICATION1:
                theDataList.add(classification1Bean.getNumRows(SOPCustomerClassification1.class, userData));
                break;
            case GETDATA_SOPCUSTOMERCLASSIFICATION1:
                theDataList = (List<Object>) classification1Bean.getDataInRange(SOPCustomerClassification1.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERCLASSIFICATION1:
                theDataList.add(classification1Bean.validateField(dataList.get(1).toString(), (SOPCustomerClassification1) dataList.get(2), userData));
                break;
            //SOPCustomerClassification2
            case INSERT_SOPCUSTOMERCLASSIFICATION2:
                theDataList.add(classification2Bean.insert((SOPCustomerClassification2) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERCLASSIFICATION2:
                theDataList.add(classification2Bean.update((SOPCustomerClassification2) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERCLASSIFICATION2:
                theDataList.add(classification2Bean.delete((SOPCustomerClassification2) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERCLASSIFICATION2:
                theDataList.add(classification2Bean.getNumRows(SOPCustomerClassification2.class, userData));
                break;
            case GETDATA_SOPCUSTOMERCLASSIFICATION2:
                theDataList = (List<Object>) classification2Bean.getDataInRange(SOPCustomerClassification2.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERCLASSIFICATION2:
                theDataList.add(classification2Bean.validateField(dataList.get(1).toString(), (SOPCustomerClassification2) dataList.get(2), userData));
                break;
            //SOPCustomerClassification3
            case INSERT_SOPCUSTOMERCLASSIFICATION3:
                theDataList.add(classification3Bean.insert((SOPCustomerClassification3) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERCLASSIFICATION3:
                theDataList.add(classification3Bean.update((SOPCustomerClassification3) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERCLASSIFICATION3:
                theDataList.add(classification3Bean.delete((SOPCustomerClassification3) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERCLASSIFICATION3:
                theDataList.add(classification3Bean.getNumRows(SOPCustomerClassification3.class, userData));
                break;
            case GETDATA_SOPCUSTOMERCLASSIFICATION3:
                theDataList = (List<Object>) classification3Bean.getDataInRange(SOPCustomerClassification3.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERCLASSIFICATION3:
                theDataList.add(classification3Bean.validateField(dataList.get(1).toString(), (SOPCustomerClassification3) dataList.get(2), userData));
                break;
            //SOPCustomerClassification4
            case INSERT_SOPCUSTOMERCLASSIFICATION4:
                theDataList.add(classification4Bean.insert((SOPCustomerClassification4) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERCLASSIFICATION4:
                theDataList.add(classification4Bean.update((SOPCustomerClassification4) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERCLASSIFICATION4:
                theDataList.add(classification4Bean.delete((SOPCustomerClassification4) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERCLASSIFICATION4:
                theDataList.add(classification4Bean.getNumRows(SOPCustomerClassification4.class, userData));
                break;
            case GETDATA_SOPCUSTOMERCLASSIFICATION4:
                theDataList = (List<Object>) classification4Bean.getDataInRange(SOPCustomerClassification4.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERCLASSIFICATION4:
                theDataList.add(classification4Bean.validateField(dataList.get(1).toString(), (SOPCustomerClassification4) dataList.get(2), userData));
                break;
            //SOPCustomerClassification5
            case INSERT_SOPCUSTOMERCLASSIFICATION5:
                theDataList.add(classification5Bean.insert((SOPCustomerClassification5) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERCLASSIFICATION5:
                theDataList.add(classification5Bean.update((SOPCustomerClassification5) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERCLASSIFICATION5:
                theDataList.add(classification5Bean.delete((SOPCustomerClassification5) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERCLASSIFICATION5:
                theDataList.add(classification5Bean.getNumRows(SOPCustomerClassification5.class, userData));
                break;
            case GETDATA_SOPCUSTOMERCLASSIFICATION5:
                theDataList = (List<Object>) classification5Bean.getDataInRange(SOPCustomerClassification5.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERCLASSIFICATION5:
                theDataList.add(classification5Bean.validateField(dataList.get(1).toString(), (SOPCustomerClassification5) dataList.get(2), userData));
                break;
            //SOPCustomerClassification6
            case INSERT_SOPCUSTOMERCLASSIFICATION6:
                theDataList.add(classification6Bean.insert((SOPCustomerClassification6) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERCLASSIFICATION6:
                theDataList.add(classification6Bean.update((SOPCustomerClassification6) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERCLASSIFICATION6:
                theDataList.add(classification6Bean.delete((SOPCustomerClassification6) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERCLASSIFICATION6:
                theDataList.add(classification6Bean.getNumRows(SOPCustomerClassification6.class, userData));
                break;
            case GETDATA_SOPCUSTOMERCLASSIFICATION6:
                theDataList = (List<Object>) classification6Bean.getDataInRange(SOPCustomerClassification6.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERCLASSIFICATION6:
                theDataList.add(classification6Bean.validateField(dataList.get(1).toString(), (SOPCustomerClassification6) dataList.get(2), userData));
                break;
            case PRINT_SOP_OUTSTANDING_ORDERS:
                theDataList = outstandingOrdersReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_SOP_OVERDUE_ORDERS:
                theDataList = overdueOrdersReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_SOP_SALES_ORDERS:
                theDataList = salesOrdersReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //SOPPriceArangements
            case INSERT_SOPPRICEARANGEMENTS:
                theDataList.add(priceArangementsBean.insert((SOPPriceArangements) dataList.get(1), userData));
                break;
            case UPDATE_SOPPRICEARANGEMENTS:
                theDataList.add(priceArangementsBean.update((SOPPriceArangements) dataList.get(1), userData));
                break;
            case DELETE_SOPPRICEARANGEMENTS:
                theDataList.add(priceArangementsBean.delete((SOPPriceArangements) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPPRICEARANGEMENTS:
                theDataList.add(priceArangementsBean.getNumRows(SOPPriceArangements.class, userData));
                break;
            case GETDATA_SOPPRICEARANGEMENTS:
                theDataList = (List<Object>) priceArangementsBean.getDataInRange(SOPPriceArangements.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPPRICEARANGEMENTS:
                theDataList.add(priceArangementsBean.validateField(dataList.get(1).toString(), (SOPPriceArangements) dataList.get(2), userData));
                break;
            case GET_PRICE:
                theDataList.add(priceArangementsBean.findItemPrice((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3),
                        (String) dataList.get(4), (String) dataList.get(5), (Date) dataList.get(6), (BigDecimal) dataList.get(7), userData));
                break;
            //SOPPriceArangementsDS
            case INSERT_SOPPRICEARANGEMENTSDS:
                theDataList.add(priceArangementsDSBean.insert((SOPPriceArangementsDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPPRICEARANGEMENTSDS:
                theDataList.add(priceArangementsDSBean.update((SOPPriceArangementsDS) dataList.get(1), userData));
                break;
            case DELETE_SOPPRICEARANGEMENTSDS:
                theDataList.add(priceArangementsDSBean.delete((SOPPriceArangementsDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPPRICEARANGEMENTSDS:
                theDataList.add(priceArangementsDSBean.getNumRows(SOPPriceArangementsDS.class, userData));
                break;
            case GETDATA_SOPPRICEARANGEMENTSDS:
                theDataList = (List<Object>) priceArangementsDSBean.getDataInRange(SOPPriceArangementsDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPPRICEARANGEMENTSDS:
                theDataList.add(priceArangementsDSBean.validateField(dataList.get(1).toString(), (SOPPriceArangementsDS) dataList.get(2), userData));
                break;
            case IMPORT_PRICE_ARANGEMENTS:
                theDataList.add(priceArangementsBean.importPriceArangements(dataList, userData));
                break;
            //SOPParameters
            case INSERT_SOPPARAMETERS:
                theDataList.add(parameterBean.insert((SOPParameters) dataList.get(1), userData));
                break;
            case UPDATE_SOPPARAMETERS:
                theDataList.add(parameterBean.update((SOPParameters) dataList.get(1), userData));
                break;
            case DELETE_SOPPARAMETERS:
                theDataList.add(parameterBean.delete((SOPParameters) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPPARAMETERS:
                theDataList.add(parameterBean.getNumRows(SOPParameters.class, userData));
                break;
            case GETDATA_SOPPARAMETERS:
                theDataList = (List<Object>) parameterBean.getDataInRange(SOPParameters.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPPARAMETERS:
                theDataList.add(parameterBean.validateField(dataList.get(1).toString(), (SOPParameters) dataList.get(2), userData));
                break;
            case GET_SOPPARAMETER:
                theDataList.add(parameterBean.getParameterRecord(userData));
                break;
            case DISABLE_COMMISSION_FIELDS:
                theDataList.add(parameterBean.deactivateCommissionFields(userData));
                break;
            //SOPSalesRepGroups
            case INSERT_SOPSALESREPGROUPS:
                theDataList.add(salesRepGroupsBean.insert((SOPSalesRepGroups) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESREPGROUPS:
                theDataList.add(salesRepGroupsBean.update((SOPSalesRepGroups) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESREPGROUPS:
                theDataList.add(salesRepGroupsBean.delete((SOPSalesRepGroups) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESREPGROUPS:
                theDataList.add(salesRepGroupsBean.getNumRows(SOPSalesRepGroups.class, userData));
                break;
            case GETDATA_SOPSALESREPGROUPS:
                theDataList = (List<Object>) salesRepGroupsBean.getDataInRange(SOPSalesRepGroups.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESREPGROUPS:
                theDataList.add(salesRepGroupsBean.validateField(dataList.get(1).toString(), (SOPSalesRepGroups) dataList.get(2), userData));
                break;
            //SOPSalesRepGroupsDS
            case INSERT_SOPSALESREPGROUPSDS:
                theDataList.add(salesRepGroupsDSBean.insert((SOPSalesRepGroupsDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESREPGROUPSDS:
                theDataList.add(salesRepGroupsDSBean.update((SOPSalesRepGroupsDS) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESREPGROUPSDS:
                theDataList.add(salesRepGroupsDSBean.delete((SOPSalesRepGroupsDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESREPGROUPSDS:
                theDataList.add(salesRepGroupsDSBean.getNumRows(SOPSalesRepGroupsDS.class, userData));
                break;
            case GETDATA_SOPSALESREPGROUPSDS:
                theDataList = (List<Object>) salesRepGroupsDSBean.getDataInRange(SOPSalesRepGroupsDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESREPGROUPSDS:
                theDataList.add(salesRepGroupsDSBean.validateField(dataList.get(1).toString(), (SOPSalesRepGroupsDS) dataList.get(2), userData));
                break;
            //SOPSalesRepGroupsSetup
            case INSERT_SOPSALESREPGROUPSSETUP:
                theDataList.add(salesRepGroupSetupBean.insert((SOPSalesRepGroupsSetup) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESREPGROUPSSETUP:
                theDataList.add(salesRepGroupSetupBean.update((SOPSalesRepGroupsSetup) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESREPGROUPSSETUP:
                theDataList.add(salesRepGroupSetupBean.delete((SOPSalesRepGroupsSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESREPGROUPSSETUP:
                theDataList.add(salesRepGroupSetupBean.getNumRows(SOPSalesRepGroupsSetup.class, userData));
                break;
            case GETDATA_SOPSALESREPGROUPSSETUP:
                theDataList = (List<Object>) salesRepGroupSetupBean.getDataInRange(SOPSalesRepGroupsSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESREPGROUPSSETUP:
                theDataList.add(salesRepGroupSetupBean.validateField(dataList.get(1).toString(), (SOPSalesRepGroupsSetup) dataList.get(2), userData));
                break;
            //SOPSalesRepGroupsSetupDS
            case INSERT_SOPSALESREPGROUPSETUPDS:
                theDataList.add(salesRepGroupSetupDSBean.insert((SOPSalesRepGroupSetupDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESREPGROUPSETUPDS:
                theDataList.add(salesRepGroupSetupDSBean.update((SOPSalesRepGroupSetupDS) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESREPGROUPSETUPDS:
                theDataList.add(salesRepGroupSetupDSBean.delete((SOPSalesRepGroupSetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESREPGROUPSETUPDS:
                theDataList.add(salesRepGroupSetupDSBean.getNumRows(SOPSalesRepGroupSetupDS.class, userData));
                break;
            case GETDATA_SOPSALESREPGROUPSETUPDS:
                theDataList = (List<Object>) salesRepGroupSetupDSBean.getDataInRange(SOPSalesRepGroupSetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESREPGROUPSETUPDS:
                theDataList.add(salesRepGroupSetupDSBean.validateField(dataList.get(1).toString(), (SOPSalesRepGroupSetupDS) dataList.get(2), userData));
                break;
            //SOP Sales Rep Commission
            case INSERT_SOPSALESREPCOMMISSION:
                theDataList.add(salesRepCommissionBean.insert((SOPSalesRepCommission) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESREPCOMMISSION:
                theDataList.add(salesRepCommissionBean.update((SOPSalesRepCommission) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESREPCOMMISSION:
                theDataList.add(salesRepCommissionBean.delete((SOPSalesRepCommission) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESREPCOMMISSION:
                theDataList.add(salesRepCommissionBean.getNumRows(SOPSalesRepCommission.class, userData));
                break;
            case GETDATA_SOPSALESREPCOMMISSION:
                theDataList = (List<Object>) salesRepCommissionBean.getDataInRange(SOPSalesRepCommission.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESREPCOMMISSION:
                theDataList.add(salesRepCommissionBean.validateField(dataList.get(1).toString(), (SOPSalesRepCommission) dataList.get(2), userData));
                break;
            case GET_AVAILABLE_REPS:
                theDataList.add(salesRepCommissionBean.getAvailableData(dataList.get(1).toString(), userData));
                break;
            case POPULATE_SALES_REP_COMMISSION:
                theDataList.add(salesRepCommissionBean.populateSalesRepCommission(dataList.get(1).toString(), (List<Object[]>) dataList.get(2), (List<String>) dataList.get(3), dataList.get(4).toString(), (Boolean) dataList.get(5), userData));
                break;
            case MASS_UPDATE_COMMISSION:
                theDataList.add(salesRepCommissionBean.doMassUpdate((String) dataList.get(1), (String) dataList.get(2), (String) dataList.get(3), (String) dataList.get(4), (String) dataList.get(5), (String) dataList.get(6), (String) dataList.get(7), (BigDecimal) dataList.get(8), userData));
                break;
            case GENERATE_COMMISSION_DATA:
                theDataList.add(salesRepCommissionBean.generateSalesRepCommissionRecords((String) dataList.get(1), (List<String>) dataList.get(2), (List<String>) dataList.get(3), (List<String>) dataList.get(4), (List<String>) dataList.get(5), (List<String>) dataList.get(6), (List<String>) dataList.get(7), (Boolean) dataList.get(8), (Boolean) dataList.get(9), (BigDecimal) dataList.get(10), userData));
                break;
            case GET_ALL_FIELD_VALUES:
                theDataList.add(salesRepCommissionBean.getAllFieldValues((EMCQuery) dataList.get(1), (List<String>) dataList.get(2), userData));
                break;
            case VALIDATE_SELECTED_FIELD:
                theDataList.add(salesRepCommissionBean.validateField(dataList.get(1), (EMCQuery) dataList.get(2), (List<String>) dataList.get(3), userData));
                break;
            case SAVE_COMMISSION_WIZZARD:
                salesRepCommissionBean.saveCommissionWizzard((String) dataList.remove(1), (String) dataList.remove(1), dataList, userData);
                break;
            case LOAD_COMMISSION_WIZZARD:
                theDataList = salesRepCommissionBean.loadCommissionWizzard((String) dataList.get(1), (String) dataList.get(2), userData);
                break;
            case DELETE_COMMISSION_WIZZARD:
                salesRepCommissionBean.deleteCommissionWizzard((String) dataList.get(1), userData);
                break;
            case FIND_COMMISSION_WIZZARD:
                theDataList.add(salesRepCommissionBean.findCommissionWizzard(userData));
                break;
            case VALIDATE_SALES_REP:
                theDataList.add(salesRepCommissionBean.validateSalesRep(dataList.get(1).toString(), userData));
                break;
            //SOP Sales Rep Commission Enquiry
            case INSERT_SOPSALESREPCOMMISSIONENQUIRY:
                theDataList.add(salesRepCommissionEnquiryBean.insert((SOPSalesRepCommissionEnquiry) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESREPCOMMISSIONENQUIRY:
                theDataList.add(salesRepCommissionEnquiryBean.update((SOPSalesRepCommissionEnquiry) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESREPCOMMISSIONENQUIRY:
                theDataList.add(salesRepCommissionEnquiryBean.delete((SOPSalesRepCommissionEnquiry) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESREPCOMMISSIONENQUIRY:
                theDataList.add(salesRepCommissionEnquiryBean.getNumRows(SOPSalesRepCommissionEnquiry.class, userData));
                break;
            case GETDATA_SOPSALESREPCOMMISSIONENQUIRY:
                theDataList = (List<Object>) salesRepCommissionEnquiryBean.getDataInRange(SOPSalesRepCommissionEnquiry.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESREPCOMMISSIONENQUIRY:
                theDataList.add(salesRepCommissionEnquiryBean.validateField(dataList.get(1).toString(), (SOPSalesRepCommissionEnquiry) dataList.get(2), userData));
                break;


            //SOPSalesOrderPostLines
            case INSERT_SOPSALESORDERPOSTLINES:
                theDataList.add(salesOrderPostLinesBean.insert((SOPSalesOrderPostLines) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESORDERPOSTLINES:
                theDataList.add(salesOrderPostLinesBean.update((SOPSalesOrderPostLines) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESORDERPOSTLINES:
                theDataList.add(salesOrderPostLinesBean.delete((SOPSalesOrderPostLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESORDERPOSTLINES:
                theDataList.add(salesOrderPostLinesBean.getNumRows(SOPSalesOrderPostLines.class, userData));
                break;
            case GETDATA_SOPSALESORDERPOSTLINES:
                theDataList = (List<Object>) salesOrderPostLinesBean.getDataInRange(SOPSalesOrderPostLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESORDERPOSTLINES:
                theDataList.add(salesOrderPostLinesBean.validateField(dataList.get(1).toString(), (SOPSalesOrderPostLines) dataList.get(2), userData));
                break;

            //SOPSalesOrderPostLinesDS
            case INSERT_SOPSALESORDERPOSTLINESDS:
                theDataList.add(salesOrderPostLinesDSBean.insert((SOPSalesOrderPostLinesDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESORDERPOSTLINESDS:
                theDataList.add(salesOrderPostLinesDSBean.update((SOPSalesOrderPostLinesDS) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESORDERPOSTLINESDS:
                theDataList.add(salesOrderPostLinesDSBean.delete((SOPSalesOrderPostLinesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESORDERPOSTLINESDS:
                theDataList.add(salesOrderPostLinesDSBean.getNumRows(SOPSalesOrderPostLinesDS.class, userData));
                break;
            case GETDATA_SOPSALESORDERPOSTLINESDS:
                theDataList = (List<Object>) salesOrderPostLinesDSBean.getDataInRange(SOPSalesOrderPostLinesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESORDERPOSTLINESDS:
                theDataList.add(salesOrderPostLinesDSBean.validateField(dataList.get(1).toString(), (SOPSalesOrderPostLinesDS) dataList.get(2), userData));
                break;
            //SOPSalesOrderPostRegister
            case INSERT_SOPSALESORDERPOSTREGISTER:
                theDataList.add(salesOrderPostRegisterBean.insert((SOPSalesOrderPostRegister) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESORDERPOSTREGISTER:
                theDataList.add(salesOrderPostRegisterBean.update((SOPSalesOrderPostRegister) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESORDERPOSTREGISTER:
                theDataList.add(salesOrderPostRegisterBean.delete((SOPSalesOrderPostRegister) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESORDERPOSTREGISTER:
                theDataList.add(salesOrderPostRegisterBean.getNumRows(SOPSalesOrderPostRegister.class, userData));
                break;
            case GETDATA_SOPSALESORDERPOSTREGISTER:
                theDataList = (List<Object>) salesOrderPostRegisterBean.getDataInRange(SOPSalesOrderPostRegister.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESORDERPOSTREGISTER:
                theDataList.add(salesOrderPostRegisterBean.validateField(dataList.get(1).toString(), (SOPSalesOrderPostRegister) dataList.get(2), userData));
                break;
            //SOPSalesOrderCancelReason
            case INSERT_SOPSALESORDERCANCELREASON:
                theDataList.add(salesOrderCancelReasonBean.insert((SOPSalesOrderCancelReason) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESORDERCANCELREASON:
                theDataList.add(salesOrderCancelReasonBean.update((SOPSalesOrderCancelReason) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESORDERCANCELREASON:
                theDataList.add(salesOrderCancelReasonBean.delete((SOPSalesOrderCancelReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESORDERCANCELREASON:
                theDataList.add(salesOrderCancelReasonBean.getNumRows(SOPSalesOrderCancelReason.class, userData));
                break;
            case GETDATA_SOPSALESORDERCANCELREASON:
                theDataList = (List<Object>) salesOrderCancelReasonBean.getDataInRange(SOPSalesOrderCancelReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESORDERCANCELREASON:
                theDataList.add(salesOrderCancelReasonBean.validateField(dataList.get(1).toString(), (SOPSalesOrderCancelReason) dataList.get(2), userData));
                break;
            case VALIDATE_CANCEL_REASON:
                theDataList.add(salesOrderCancelReasonBean.validateCancelReason(dataList.get(1).toString(), userData));
                break;
            //SOPPriceChangeReason
            case INSERT_SOPPRICECHANGEREASON:
                theDataList.add(priceChangeReasonBean.insert((SOPPriceChangeReason) dataList.get(1), userData));
                break;
            case UPDATE_SOPPRICECHANGEREASON:
                theDataList.add(priceChangeReasonBean.update((SOPPriceChangeReason) dataList.get(1), userData));
                break;
            case DELETE_SOPPRICECHANGEREASON:
                theDataList.add(priceChangeReasonBean.delete((SOPPriceChangeReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPPRICECHANGEREASON:
                theDataList.add(priceChangeReasonBean.getNumRows(SOPPriceChangeReason.class, userData));
                break;
            case GETDATA_SOPPRICECHANGEREASON:
                theDataList = (List<Object>) priceChangeReasonBean.getDataInRange(SOPPriceChangeReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPPRICECHANGEREASON:
                theDataList.add(priceChangeReasonBean.validateField(dataList.get(1).toString(), (SOPPriceChangeReason) dataList.get(2), userData));
                break;

            //Control Sheet Report
            case PRINT_SOP_CONTROLL_SHEET:
                theDataList = controlSheetReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //SOPDiscountSetup
            case INSERT_SOPDISCOUNTSETUP:
                theDataList.add(discountSetupBean.insert((SOPDiscountSetup) dataList.get(1), userData));
                break;
            case UPDATE_SOPDISCOUNTSETUP:
                theDataList.add(discountSetupBean.update((SOPDiscountSetup) dataList.get(1), userData));
                break;
            case DELETE_SOPDISCOUNTSETUP:
                theDataList.add(discountSetupBean.delete((SOPDiscountSetup) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPDISCOUNTSETUP:
                theDataList.add(discountSetupBean.getNumRows(SOPDiscountSetup.class, userData));
                break;
            case GETDATA_SOPDISCOUNTSETUP:
                theDataList = (List<Object>) discountSetupBean.getDataInRange(SOPDiscountSetup.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPDISCOUNTSETUP:
                theDataList.add(discountSetupBean.validateField(dataList.get(1).toString(), (SOPDiscountSetup) dataList.get(2), userData));
                break;
            //SOP Discount Setup DS
            case INSERT_SOPDISCOUNTSETUPDS:
                theDataList.add(discountSetupDSBean.insert((SOPDiscountSetupDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPDISCOUNTSETUPDS:
                theDataList.add(discountSetupDSBean.update((SOPDiscountSetupDS) dataList.get(1), userData));
                break;
            case DELETE_SOPDISCOUNTSETUPDS:
                theDataList.add(discountSetupDSBean.delete((SOPDiscountSetupDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPDISCOUNTSETUPDS:
                theDataList.add(discountSetupDSBean.getNumRows(SOPDiscountSetupDS.class, userData));
                break;
            case GETDATA_SOPDISCOUNTSETUPDS:
                theDataList = (List<Object>) discountSetupDSBean.getDataInRange(SOPDiscountSetupDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPDISCOUNTSETUPDS:
                theDataList.add(discountSetupDSBean.validateField(dataList.get(1).toString(), (SOPDiscountSetupDS) dataList.get(2), userData));
                break;






            //SOPCustomerLabels
            case INSERT_SOPCUSTOMERLABELS:
                theDataList.add(customerLabelsBean.insert((SOPCustomerLabels) dataList.get(1), userData));
                break;
            case UPDATE_SOPCUSTOMERLABELS:
                theDataList.add(customerLabelsBean.update((SOPCustomerLabels) dataList.get(1), userData));
                break;
            case DELETE_SOPCUSTOMERLABELS:
                theDataList.add(customerLabelsBean.delete((SOPCustomerLabels) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPCUSTOMERLABELS:
                theDataList.add(customerLabelsBean.getNumRows(SOPCustomerLabels.class, userData));
                break;
            case GETDATA_SOPCUSTOMERLABELS:
                theDataList = (List<Object>) customerLabelsBean.getDataInRange(SOPCustomerLabels.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPCUSTOMERLABELS:
                theDataList.add(customerLabelsBean.validateField(dataList.get(1).toString(), (SOPCustomerLabels) dataList.get(2), userData));
                break;
            case FIND_CUSTOMER_LABLES_FOR_SO:
                theDataList.add(customerLabelsBean.getCustomerLablesForSO(dataList.get(1).toString(), userData));
                break;
            //Blanket Order Status Enquiry DS
            case INSERT_SOPBLANKETORDERSTATUSENQUIRYDS:
                theDataList.add(blanketOrderStatusEnquiryBean.insert((SOPBlanketOrderStatusEnquiryDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPBLANKETORDERSTATUSENQUIRYDS:
                theDataList.add(blanketOrderStatusEnquiryBean.update((SOPBlanketOrderStatusEnquiryDS) dataList.get(1), userData));
                break;
            case DELETE_SOPBLANKETORDERSTATUSENQUIRYDS:
                theDataList.add(blanketOrderStatusEnquiryBean.delete((SOPBlanketOrderStatusEnquiryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPBLANKETORDERSTATUSENQUIRYDS:
                theDataList.add(blanketOrderStatusEnquiryBean.getNumRows(SOPBlanketOrderStatusEnquiryDS.class, userData));
                break;
            case GETDATA_SOPBLANKETORDERSTATUSENQUIRYDS:
                theDataList = (List<Object>) blanketOrderStatusEnquiryBean.getDataInRange(SOPBlanketOrderStatusEnquiryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPBLANKETORDERSTATUSENQUIRYDS:
                theDataList.add(blanketOrderStatusEnquiryBean.validateField(dataList.get(1).toString(), (SOPBlanketOrderStatusEnquiryDS) dataList.get(2), userData));
                break;
            //SOPStatusEnquirySummary
            case INSERT_SOPSTATUSENQUIRYSUMMARY:
                theDataList.add(statusEnquiryBean.insert((SOPStatusEnquirySummary) dataList.get(1), userData));
                break;
            case UPDATE_SOPSTATUSENQUIRYSUMMARY:
                theDataList.add(statusEnquiryBean.update((SOPStatusEnquirySummary) dataList.get(1), userData));
                break;
            case DELETE_SOPSTATUSENQUIRYSUMMARY:
                theDataList.add(statusEnquiryBean.delete((SOPStatusEnquirySummary) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSTATUSENQUIRYSUMMARY:
                theDataList.add(statusEnquiryBean.getNumRows(SOPStatusEnquirySummary.class, userData));
                break;
            case GETDATA_SOPSTATUSENQUIRYSUMMARY:
                theDataList = (List<Object>) statusEnquiryBean.getDataInRange(SOPStatusEnquirySummary.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSTATUSENQUIRYSUMMARY:
                theDataList.add(statusEnquiryBean.validateField(dataList.get(1).toString(), (SOPStatusEnquirySummary) dataList.get(2), userData));
                break;
            case POPULATE_SALES_STATUS_ENQUIRY:
                theDataList.add(statusEnquiryBean.validatePeriodSelection((String) dataList.get(1), (String) dataList.get(2),
                        (String) dataList.get(3), userData));
                break;
            //Commission Reports
            case PRINT_DETAILED_COMMISSION_REPORT:
                theDataList = commissionReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_COMMISSION_SUMMARY_REPORT:
                theDataList = commissionReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_COMMISSION_REPORT:
                theDataList = commissionReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_OPEN_SALES_ORDERS_REPORT:
                theDataList = openSalesOrdersReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            case PRINT_STOCK_AVAILABLE_FOR_SALE:
                theDataList = stockAvailableForSaleBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //SOPSalesEnquiry
            case INSERT_SOPSALESENQUIRY:
                theDataList.add(salesEnquiryBean.insert((SOPSalesEnquiry) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESENQUIRY:
                theDataList.add(salesEnquiryBean.update((SOPSalesEnquiry) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESENQUIRY:
                theDataList.add(salesEnquiryBean.delete((SOPSalesEnquiry) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESENQUIRY:
                theDataList.add(salesEnquiryBean.getNumRows(SOPSalesEnquiry.class, userData));
                break;
            case GETDATA_SOPSALESENQUIRY:
                theDataList = (List<Object>) salesEnquiryBean.getDataInRange(SOPSalesEnquiry.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESENQUIRY:
                theDataList.add(salesEnquiryBean.validateField(dataList.get(1).toString(), (SOPSalesEnquiry) dataList.get(2), userData));
                break;
            case PREPARE_SOPSALESENQUIRY:
                theDataList.add(salesEnquiryBean.validatePeriodSelection((SalesEnquiryHelper) dataList.get(1), userData));
                break;
            case POPULATE_SOPSALESENQUIRY:
                salesEnquiryBean.populateSalesEnquiry((GLFinancialPeriods) dataList.get(1), (Integer) dataList.get(2), (SalesEnquiryHelper) dataList.get(3), userData);
                break;
            case CLEANUP_SOPSALESENQUIRY:
                salesEnquiryBean.cleanupSalesEnquiry((String) dataList.get(1), true, userData);
                break;
            //SOPPriceAuditTrail
            case INSERT_SOPPRICEAUDITTRAIL:
                theDataList.add(priceAuditTrailBean.insert((SOPPriceAuditTrail) dataList.get(1), userData));
                break;
            case UPDATE_SOPPRICEAUDITTRAIL:
                theDataList.add(priceAuditTrailBean.update((SOPPriceAuditTrail) dataList.get(1), userData));
                break;
            case DELETE_SOPPRICEAUDITTRAIL:
                theDataList.add(priceAuditTrailBean.delete((SOPPriceAuditTrail) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPPRICEAUDITTRAIL:
                theDataList.add(priceAuditTrailBean.getNumRows(SOPPriceAuditTrail.class, userData));
                break;
            case GETDATA_SOPPRICEAUDITTRAIL:
                theDataList = (List<Object>) priceAuditTrailBean.getDataInRange(SOPPriceAuditTrail.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPPRICEAUDITTRAIL:
                theDataList.add(priceAuditTrailBean.validateField(dataList.get(1).toString(), (SOPPriceAuditTrail) dataList.get(2), userData));
                break;
            case POPULATE_APPROVAL_HISTORY:
                priceAuditTrailBean.populateApprovalHistory((Date) dataList.get(1), userData);
                break;
            case POPULATE_CHANGE_HISTORY:
                priceAuditTrailBean.populateChangeHistory((Date) dataList.get(1), userData);
                break;
            //SOPPriceAuditTrailDS
            case INSERT_SOPPRICEAUDITTRAILDS:
                theDataList.add(priceAuditTrailDSBean.insert((SOPPriceAuditTrailDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPPRICEAUDITTRAILDS:
                theDataList.add(priceAuditTrailDSBean.update((SOPPriceAuditTrailDS) dataList.get(1), userData));
                break;
            case DELETE_SOPPRICEAUDITTRAILDS:
                theDataList.add(priceAuditTrailDSBean.delete((SOPPriceAuditTrailDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPPRICEAUDITTRAILDS:
                theDataList.add(priceAuditTrailDSBean.getNumRows(SOPPriceAuditTrailDS.class, userData));
                break;
            case GETDATA_SOPPRICEAUDITTRAILDS:
                theDataList = (List<Object>) priceAuditTrailDSBean.getDataInRange(SOPPriceAuditTrailDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPPRICEAUDITTRAILDS:
                theDataList.add(priceAuditTrailDSBean.validateField(dataList.get(1).toString(), (SOPPriceAuditTrailDS) dataList.get(2), userData));
                break;
            case PRINT_CUSTOMER_LABLES:
                theDataList.add(customerLablesBean.printCustomerLables((EMCQuery) dataList.get(1), dataList.get(2).toString(), userData));
                break;
            //Audit Trail Reports
            case PRINT_PRICE_LIST_AUDIT_TRAIL:
            case PRINT_APPROVAL_AUDIT_TRAIL:
                theDataList = priceAuditTrailReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //Order Schedule Reports
            case PRINT_ORDER_SCHEDULE:
                theDataList = orderScheduleReportBean.getReportResult(dataList, userData);
                break;
            case PRINT_ORDER_SCHEDULE_BY_ITEM:
                theDataList = orderScheduleByItemReportBean.getReportResult(dataList, userData);
                break;
            case PRINT_ROLLING_SALES_REPORT:
                theDataList = rollingSalesReportBean.getReportResult(dataList, cmd.getReportConfig(), userData);
                break;
            //SOPSalesOrderChangeDateReason
            case INSERT_SOPSALESORDERCHANGEDATEREASON:
                theDataList.add(salesOrderChangeDateReason.insert((SOPSalesOrderChangeDateReason) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESORDERCHANGEDATEREASON:
                theDataList.add(salesOrderChangeDateReason.update((SOPSalesOrderChangeDateReason) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESORDERCHANGEDATEREASON:
                theDataList.add(salesOrderChangeDateReason.delete((SOPSalesOrderChangeDateReason) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESORDERCHANGEDATEREASON:
                theDataList.add(salesOrderChangeDateReason.getNumRows(SOPSalesOrderChangeDateReason.class, userData));
                break;
            case GETDATA_SOPSALESORDERCHANGEDATEREASON:
                theDataList = (List<Object>) salesOrderChangeDateReason.getDataInRange(SOPSalesOrderChangeDateReason.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESORDERCHANGEDATEREASON:
                theDataList.add(salesOrderChangeDateReason.validateField(dataList.get(1).toString(), (SOPSalesOrderChangeDateReason) dataList.get(2), userData));
                break;
            //SOPSalesBySizeEnquiry
            case INSERT_SOPSALESBYSIZEENQUIRY:
                theDataList.add(salesBySizeEnquiryBean.insert((SOPSalesBySizeEnquiry) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESBYSIZEENQUIRY:
                theDataList.add(salesBySizeEnquiryBean.update((SOPSalesBySizeEnquiry) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESBYSIZEENQUIRY:
                theDataList.add(salesBySizeEnquiryBean.delete((SOPSalesBySizeEnquiry) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESBYSIZEENQUIRY:
                theDataList.add(salesBySizeEnquiryBean.getNumRows(SOPSalesBySizeEnquiry.class, userData));
                break;
            case GETDATA_SOPSALESBYSIZEENQUIRY:
                theDataList = (List<Object>) salesBySizeEnquiryBean.getDataInRange(SOPSalesBySizeEnquiry.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESBYSIZEENQUIRY:
                theDataList.add(salesBySizeEnquiryBean.validateField(dataList.get(1).toString(), (SOPSalesBySizeEnquiry) dataList.get(2), userData));
                break;
            case POPULATE_SALES_BY_SIZE_ENQUIRY:
                theDataList.add(salesBySizeEnquiryBean.populateSalesBySizeEnquiry((SOPSalesBySizeEnquiryHelper) dataList.get(1), userData));
                break;
            case CLEAR_SALES_BY_SIZE_ENQUIRY:
                salesBySizeEnquiryBean.clearSalesBySize((String) dataList.get(1), userData);
                break;
            //SOPSalesBySizeEnquiryDS
            case INSERT_SOPSALESBYSIZEENQUIRYDS:
                theDataList.add(salesBySizeEnquiryDSBean.insert((SOPSalesBySizeEnquiryDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESBYSIZEENQUIRYDS:
                theDataList.add(salesBySizeEnquiryDSBean.update((SOPSalesBySizeEnquiryDS) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESBYSIZEENQUIRYDS:
                theDataList.add(salesBySizeEnquiryDSBean.delete((SOPSalesBySizeEnquiryDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESBYSIZEENQUIRYDS:
                theDataList.add(salesBySizeEnquiryDSBean.getNumRows(SOPSalesBySizeEnquiryDS.class, userData));
                break;
            case GETDATA_SOPSALESBYSIZEENQUIRYDS:
                theDataList = (List<Object>) salesBySizeEnquiryDSBean.getDataInRange(SOPSalesBySizeEnquiryDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESBYSIZEENQUIRYDS:
                theDataList.add(salesBySizeEnquiryDSBean.validateField(dataList.get(1).toString(), (SOPSalesBySizeEnquiryDS) dataList.get(2), userData));
                break;

            //SOP Sales Rep Commission DS
            case INSERT_SOPSALESREPCOMMISSIONDS:
                theDataList.add(salesRepCommissionDSBean.insert((SOPSalesRepCommissionDS) dataList.get(1), userData));
                break;
            case UPDATE_SOPSALESREPCOMMISSIONDS:
                theDataList.add(salesRepCommissionDSBean.update((SOPSalesRepCommissionDS) dataList.get(1), userData));
                break;
            case DELETE_SOPSALESREPCOMMISSIONDS:
                theDataList.add(salesRepCommissionDSBean.delete((SOPSalesRepCommissionDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_SOPSALESREPCOMMISSIONDS:
                theDataList.add(salesRepCommissionDSBean.getNumRows(SOPSalesRepCommissionDS.class, userData));
                break;
            case GETDATA_SOPSALESREPCOMMISSIONDS:
                theDataList = (List<Object>) salesRepCommissionDSBean.getDataInRange(SOPSalesRepCommissionDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_SOPSALESREPCOMMISSIONDS:
                theDataList.add(salesRepCommissionDSBean.validateField(dataList.get(1).toString(), (SOPSalesRepCommissionDS) dataList.get(2), userData));
                break;
            case PRINT_SOPCUSTOMER_BY_SALES_REP_REPORT:
                theDataList = customerBySalesRepBean.getReportResult(dataList, cmd.getReportConfig(), userData);
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

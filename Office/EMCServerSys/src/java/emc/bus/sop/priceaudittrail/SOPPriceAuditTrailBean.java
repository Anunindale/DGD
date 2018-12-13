/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.priceaudittrail;

import emc.bus.sop.discountsetup.SOPDiscountSetupLocal;
import emc.bus.sop.pricearangements.SOPPriceArangementsLocal;
import emc.entity.sop.SOPPriceArangements;
import emc.entity.sop.SOPPriceAuditTrail;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
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
public class SOPPriceAuditTrailBean extends EMCEntityBean implements SOPPriceAuditTrailLocal {

    @EJB
    private SOPPriceArangementsLocal priceBean;
    @EJB
    private SOPDiscountSetupLocal discountSetupBean;

    @Override
    public void logApproval(SOPPriceAuditTrailType logType, EMCEntityClass recordOne, EMCEntityClass recordTwo, Date logDate, Date logTime, EMCUserData userData) throws EMCEntityBeanException {
        SOPPriceAuditTrail record = new SOPPriceAuditTrail();
        Date nowDate = new Date();
        SOPSalesOrderMaster salesMaster;
        SOPSalesOrderLines salesLine;
        switch (logType) {
            case PRICE_APPROVAL:
                salesMaster = (SOPSalesOrderMaster) recordOne;
                salesLine = (SOPSalesOrderLines) recordTwo;
                record.setRecordType(SOPPriceAuditTrailType.PRICE_APPROVAL.toString());
                record.setUserId(userData.getUserName());
                if (logDate == null) {
                    record.setLogDate(nowDate);
                } else {
                    record.setLogDate(logDate);
                }
                if (logTime == null) {
                    record.setLogTime(nowDate);
                } else {
                    record.setLogTime(logTime);
                }
                record.setCustomerId(salesMaster.getCustomerNo());
                record.setItemId(salesLine.getItemId());
                record.setDimension1(salesLine.getDimension1());
                record.setDimension2(salesLine.getDimension2());
                record.setDimension3(salesLine.getDimension3());
                record.setQuantity(salesLine.getQuantity());
                record.setSourceRecordId(salesMaster.getRecordID());
                record.setOriginalPrice(salesLine.getOriginalPrice());
                record.setPrice(priceBean.findItemPrice(salesMaster.getCustomerNo(), salesLine.getItemId(), salesLine.getDimension1(), salesLine.getDimension2(),
                        salesLine.getDimension3(), salesLine.getRequiredDate(), salesLine.getQuantity(), userData));
                record.setUpdatedPrice(salesLine.getPrice());
                record.setChangeReason(salesLine.getPriceChangeReason());
                break;
            case DISCOUNT_APPROVAL:
                salesMaster = (SOPSalesOrderMaster) recordOne;
                salesLine = (SOPSalesOrderLines) recordTwo;
                record.setRecordType(SOPPriceAuditTrailType.DISCOUNT_APPROVAL.toString());
                record.setUserId(userData.getUserName());
                if (logDate == null) {
                    record.setLogDate(nowDate);
                } else {
                    record.setLogDate(logDate);
                }
                if (logTime == null) {
                    record.setLogTime(nowDate);
                } else {
                    record.setLogTime(logTime);
                }
                record.setCustomerId(salesMaster.getCustomerNo());
                record.setItemId(salesLine.getItemId());
                record.setDimension1(salesLine.getDimension1());
                record.setDimension2(salesLine.getDimension2());
                record.setDimension3(salesLine.getDimension3());
                record.setQuantity(salesLine.getQuantity());
                record.setSourceRecordId(salesMaster.getRecordID());
                record.setOriginalPrice(salesLine.getOriginalDiscountPerc());
                record.setPrice(discountSetupBean.getDiscountPercentage(salesMaster.getCustomerNo(), salesLine.getItemId(), salesLine.getDimension1(),
                        salesLine.getDimension2(), salesLine.getDimension3(), salesLine.getRequiredDate(), userData));
                record.setUpdatedPrice(salesLine.getDiscountPerc());
                record.setChangeReason(salesLine.getDiscountChangeReason());
                break;
        }
        this.insert(record, userData);
    }

    @Override
    public void logChanges(SOPPriceAuditTrailType logType, SOPPriceArangements priceLine, SOPPriceArangements persistedPriceLine, Date logDate, Date logTime, EMCUserData userData) throws EMCEntityBeanException {
        SOPPriceAuditTrail record = new SOPPriceAuditTrail();
        Date nowDate = new Date();
        switch (logType) {
            case PRICE_LIST_INSERT:
                record.setRecordType(SOPPriceAuditTrailType.PRICE_LIST_INSERT.toString());
                record.setUserId(userData.getUserName());
                if (logDate == null) {
                    record.setLogDate(nowDate);
                } else {
                    record.setLogDate(logDate);
                }
                if (logTime == null) {
                    record.setLogTime(nowDate);
                } else {
                    record.setLogTime(logTime);
                }
                record.setCustomerType(priceLine.getCustomerType());
                record.setPriceGroup(priceLine.getPriceGroup());
                record.setCustomerId(priceLine.getCustomerId());
                record.setItemId(priceLine.getItemId());
                record.setDimension1(priceLine.getDimension1());
                record.setDimension2(priceLine.getDimension2());
                record.setDimension3(priceLine.getDimension3());
                record.setFromDate(priceLine.getFromDate());
                record.setToDate(priceLine.getToDate());
                record.setQuantity(priceLine.getQuantity());
                record.setUpdatedPrice(priceLine.getPrice());
                record.setSourceRecordId(priceLine.getRecordID());
                break;
            case PRICE_LIST_UPDATE:
                record.setRecordType(SOPPriceAuditTrailType.PRICE_LIST_UPDATE.toString());
                record.setUserId(userData.getUserName());
                if (logDate == null) {
                    record.setLogDate(nowDate);
                } else {
                    record.setLogDate(logDate);
                }
                if (logTime == null) {
                    record.setLogTime(nowDate);
                } else {
                    record.setLogTime(logTime);
                }
                record.setCustomerType(priceLine.getCustomerType());
                record.setPriceGroup(priceLine.getPriceGroup());
                record.setCustomerId(priceLine.getCustomerId());
                record.setItemId(priceLine.getItemId());
                record.setDimension1(priceLine.getDimension1());
                record.setDimension2(priceLine.getDimension2());
                record.setDimension3(priceLine.getDimension3());
                record.setFromDate(priceLine.getFromDate());
                record.setToDate(priceLine.getToDate());
                record.setQuantity(priceLine.getQuantity());
                record.setPrice(persistedPriceLine.getPrice());
                record.setUpdatedPrice(priceLine.getPrice());
                record.setSourceRecordId(priceLine.getRecordID());
                break;
            case PRICE_LIST_DELETE:
                record.setRecordType(SOPPriceAuditTrailType.PRICE_LIST_DELETE.toString());
                record.setUserId(userData.getUserName());
                if (logDate == null) {
                    record.setLogDate(nowDate);
                } else {
                    record.setLogDate(logDate);
                }
                if (logTime == null) {
                    record.setLogTime(nowDate);
                } else {
                    record.setLogTime(logTime);
                }
                record.setCustomerType(priceLine.getCustomerType());
                record.setPriceGroup(priceLine.getPriceGroup());
                record.setCustomerId(priceLine.getCustomerId());
                record.setItemId(priceLine.getItemId());
                record.setDimension1(priceLine.getDimension1());
                record.setDimension2(priceLine.getDimension2());
                record.setDimension3(priceLine.getDimension3());
                record.setFromDate(priceLine.getFromDate());
                record.setToDate(priceLine.getToDate());
                record.setQuantity(priceLine.getQuantity());
                record.setPrice(priceLine.getPrice());
                record.setSourceRecordId(priceLine.getRecordID());
                break;
        }
        this.insert(record, userData);
    }

    @Override
    public void populateApprovalHistory(Date fromDate, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPSalesOrderMaster.class);
        query.addTableAnd(SOPSalesOrderLines.class.getName(), "salesOrderNo", SOPSalesOrderMaster.class.getName(), "salesOrderNo");
        query.addAnd("modifiedDate", fromDate, SOPSalesOrderLines.class.getName(), EMCQueryConditions.LESS_THAN);
        query.openAndConditionBracket();
        query.addStringNotBlank("priceApprovedBy", SOPSalesOrderLines.class.getName(), EMCQueryBracketConditions.NONE);
        query.addStringNotBlank("discountApprovedBy", SOPSalesOrderLines.class.getName(), EMCQueryBracketConditions.OR);
        query.closeConditionBracket();
        query.addTableAsField(SOPSalesOrderMaster.class.getName());
        query.addTableAsField(SOPSalesOrderLines.class.getName());
        List<Object[]> recordList = util.executeGeneralSelectQuery(query, userData);
        SOPSalesOrderMaster master;
        SOPSalesOrderLines line;
        for (Object[] historyData : recordList) {
            master = (SOPSalesOrderMaster) historyData[0];
            line = (SOPSalesOrderLines) historyData[1];
            if (!isBlank(line.getPriceApprovedBy())) {
                logApproval(SOPPriceAuditTrailType.PRICE_APPROVAL, master, line, line.getModifiedDate(), line.getModifiedTime(), userData);
            }
            if (!isBlank(line.getDiscountApprovedBy())) {
                logApproval(SOPPriceAuditTrailType.DISCOUNT_APPROVAL, master, line, line.getModifiedDate(), line.getModifiedTime(), userData);
            }
        }
        Logger.getLogger("emc").log(Level.INFO, "Approval History Populated", userData);
    }

    @Override
    public void populateChangeHistory(Date fromDate, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPPriceArangements.class);
        query.addAnd("createdDate", fromDate, EMCQueryConditions.LESS_THAN);
        List<SOPPriceArangements> arrangementList = util.executeGeneralSelectQuery(query, userData);
        for (SOPPriceArangements arangement : arrangementList) {
            logChanges(SOPPriceAuditTrailType.PRICE_LIST_INSERT, arangement, null, arangement.getCreatedDate(), arangement.getCreatedTime(), userData);
        }
        Logger.getLogger("emc").log(Level.INFO, "Change History Populated", userData);
    }
}

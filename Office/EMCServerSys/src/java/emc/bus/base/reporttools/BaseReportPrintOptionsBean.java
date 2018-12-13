/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.reporttools;

import emc.entity.base.reporttools.BaseReportPrintOptions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseReportPrintOptionsBean extends EMCEntityBean implements BaseReportPrintOptionsLocal {

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return super.getNumRows(theTable, userData);
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        return super.getDataInRange(theTable, userData, start, end);
    }

    @Override
    public BaseReportPrintOptions findPrinterOptions(String reportId, String queryName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseReportPrintOptions.class.getName());
        query.addAnd("companyId", userData.getCompanyId());
        query.addAnd("createdBy", userData.getUserName());
        query.addAnd("reportId", reportId);
        if (Functions.checkBlank(queryName)) {
            query.openAndConditionBracket();
            query.addOr("queryName", "Custom");
            query.addOr("queryName", "Default");
            query.closeConditionBracket();
        } else { 
            query.addAnd("queryName", queryName);
        }
        query.addOrderBy("createdDate", BaseReportPrintOptions.class.getName(), EMCQueryOrderByDirections.DESC);
        query.addOrderBy("createdTime", BaseReportPrintOptions.class.getName(), EMCQueryOrderByDirections.DESC);

        List<BaseReportPrintOptions> printOptions = util.executeGeneralSelectQuery(query, userData);

        if (printOptions == null || printOptions.isEmpty()) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseReportPrintOptions.class.getName());
            query.addAnd("companyId", userData.getCompanyId());
            query.addAnd("createdBy", userData.getUserName());
            query.addAnd("reportId", reportId);
            query.addAnd("queryName", null);
            query.addOrderBy("createdDate", BaseReportPrintOptions.class.getName(), EMCQueryOrderByDirections.DESC);
            query.addOrderBy("createdTime", BaseReportPrintOptions.class.getName(), EMCQueryOrderByDirections.DESC);

            printOptions = util.executeGeneralSelectQuery(query, userData);
        }

        if (printOptions != null && !printOptions.isEmpty()) {
            return printOptions.get(0);
        }
        
        return null;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean valid = super.doInsertValidation(vobject, userData);
        if (valid) {
            BaseReportPrintOptions record = (BaseReportPrintOptions) vobject;
            valid = doSaveValidation(record, userData);
        }
        return valid;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doUpdateValidation(vobject, userData);
        if (valid) {
            BaseReportPrintOptions record = (BaseReportPrintOptions) vobject;
            valid = doSaveValidation(record, userData);
        }
        return valid;
    }

    private boolean doSaveValidation(BaseReportPrintOptions record, EMCUserData userData) {
        if (record.isPrintDirect()) {
            if (isBlank(record.getPrinterName())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the printer to print the report to.", userData);
                return false;
            }
        }
        if (record.isExportToExcel()) {
            if (isBlank(record.getFileName())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the report export fille name.", userData);
                return false;
            }
            if (isBlank(record.getFileType())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the report export fille type.", userData);
                return false;
            }
        }
        if (record.isEmailReport()) {
            if (isBlank(record.getEmailRecipient())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the report email recipient.", userData);
                return false;
            }
            if (isBlank(record.getFileName())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the report attachment fille name.", userData);
                return false;
            }
            if (isBlank(record.getFileType())) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please select the report attachment fille type.", userData);
                return false;
            }
        }

        return true;
    }
}

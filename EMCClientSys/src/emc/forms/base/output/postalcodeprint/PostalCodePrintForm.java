/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.output.postalcodeprint;

import emc.app.components.emcHelpFile;
import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.entity.base.BasePostalCodes;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.reporttools.EnumReports;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class PostalCodePrintForm extends ReportFrame {
     
    public PostalCodePrintForm(EMCUserData userData) {
        super("Postal Codes Report", EnumReports.BASE_POSTALCODES, userData);
        this.setHelpFile(new emcHelpFile("Base/ReportsPostalCodeList.html"));
     }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        String className = BasePostalCodes.class.getName();
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(className);
        whereTable.setField("country");
        
        whereInformation.add(whereTable);
        
        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(className);
        orderTable.setField("code");
        
        orderInformation.add(orderTable);
        
        ReportIncludedTablesObject tablesNode = new ReportIncludedTablesObject(this);
        
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tablesNode);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/base/postalcodes/PostalCodesPrint.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.entity.base.BasePostalCodes");
        jasperInfo.setReportTitle("Postal Codes");
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return null;
    }
}
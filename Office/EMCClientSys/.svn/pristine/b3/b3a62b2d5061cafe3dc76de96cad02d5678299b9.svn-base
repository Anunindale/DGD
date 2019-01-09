/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.output.userlist;

import emc.app.components.emcHelpFile;
import emc.app.jasper.linkactions.BasicLinkAction;
import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.entity.base.Usertable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

 
/**
 *
 * @author rico
 */
public class userlistForm extends ReportFrame {
     
     public userlistForm(EMCUserData userData) {
        super("User List Report", EnumReports.BASE_USERLIST, userData);
        this.setHelpFile(new emcHelpFile("Base/ReportsUserList.html"));
     }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(Usertable.class.getName());
        whereTable.setField("userCompany");
        
        whereInformation.add(whereTable);
        
        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(Usertable.class.getName());
        orderTable.setField("userId");
        
        orderInformation.add(orderTable);
        
        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        
        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    public JasperInformation createJasperInfo() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/base/userlist/emcusers.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.entity.base.Usertable");
        jasperInfo.setReportTitle("User List");
        
        jasperInfo.addAction("userId", createUserIdLinkAction());

        return jasperInfo;
    }

    @Override
    public EMCCommandClass createReportCommand() {
        return null;
    }
     
    /** Creates the hyperlink action for the userId field on the report. */
    private BasicLinkAction createUserIdLinkAction() {
        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/base/userlist/emcusers.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.entity.base.Usertable");
        jasperInfo.setReportTitle("User Report");
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, Usertable.class.getName());
        
        return new BasicLinkAction(EnumReports.BASE_USERLIST, jasperInfo, query);
    }

}

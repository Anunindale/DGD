/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.output.bdloggenericreport;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.ReportOKButton;
import emc.commands.EMCCommands;
import emc.entity.base.dblog.BaseDBLog;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.output.bdloggenericreport.resources.BaseDBLogDataFetchThread;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author wikus
 */
public class BaseDBLogGenericReportForm extends ReportFrame {

    public BaseDBLogGenericReportForm(EMCUserData userData) {
        super("EMC Data Base Log", EnumReports.BASE_DB_LOG_GENERIC, userData);
        setOkButton(new DBLogGenericReportOKButton(this));
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable where = new BaseReportWhereTable();
        where.setTableName(BaseDBLog.class.getName());
        where.setField("tableName");
        whereInformation.add(where);

        where = new BaseReportWhereTable();
        where.setTableName(BaseDBLog.class.getName());
        where.setField("createdDate");
        whereInformation.add(where);

        where = new BaseReportWhereTable();
        where.setTableName(BaseDBLog.class.getName());
        where.setField("createdTime");
        whereInformation.add(where);

        where = new BaseReportWhereTable();
        where.setTableName(BaseDBLog.class.getName());
        where.setField("userName");
        whereInformation.add(where);

        where = new BaseReportWhereTable();
        where.setTableName(BaseDBLog.class.getName());
        where.setField("type");
        whereInformation.add(where);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable order = new BaseReportOrderTable();
        order.setTableName(BaseDBLog.class.getName());
        order.setField("logRecId");
        orderInformation.add(order);

        order = new BaseReportOrderTable();
        order.setTableName(BaseDBLog.class.getName());
        order.setField("createdDate");
        orderInformation.add(order);

        order = new BaseReportOrderTable();
        order.setTableName(BaseDBLog.class.getName());
        order.setField("createdTime");
        orderInformation.add(order);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        return null;
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.FETCH_DB_LOG_GENERIC_REPORT_DATA.toString());
    }
}
class DBLogGenericReportOKButton extends ReportOKButton {

    public DBLogGenericReportOKButton(ReportFrame reportFrame) {
        super(reportFrame);
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        BaseDBLogDataFetchThread datFetchThread = new BaseDBLogDataFetchThread(reportFrame);
        
        reportFrame.setGeneratingReport(true);
        
        datFetchThread.execute();
    }
}

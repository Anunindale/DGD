/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.output.customerlables;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.app.reporttools.parameters.EMCJComboBoxParameterObject;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesRepCommission;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.sop.SOPCustomerLablesType;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SOPCustomerLablesReport extends ReportFrame {

    /** Creates a new instance of  SOPCustomerLablesReport*/
    public SOPCustomerLablesReport(EMCUserData userData) {
        super("Customer Labels", EnumReports.SOP_CUSTOMER_LABLES, userData);
        addReportParameter("customerType", new EMCJComboBoxParameterObject("Customer Type", SOPCustomerLablesType.values()));
        OKButton button = new OKButton(this, userData);
        this.setOkButton(button);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable userQuery = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereList = new ArrayList<BaseReportWhereTable>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(SOPCustomers.class.getName());
        whereTable.setField("customerId");
        whereList.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(BaseEmployeeTable.class.getName());
        whereTable.setField("employeeNumber");
        whereList.add(whereTable);

        List<BaseReportOrderTable> orderList = new ArrayList<BaseReportOrderTable>();
        BaseReportOrderTable orderTable = new BaseReportOrderTable();
        orderTable.setTableName(BaseEmployeeTable.class.getName());
        orderTable.setField("employeeNumber");
        orderList.add(orderTable);

        orderTable = new BaseReportOrderTable();
        orderTable.setTableName(SOPCustomers.class.getName());
        orderTable.setField("customerId");
        orderList.add(orderTable);

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(SOPSalesRepCommission.class.getName(), "customerItemField1", SOPCustomers.class.getName(), "customerId");
        tables.addTable(BaseEmployeeTable.class.getName(), "employeeNumber", SOPSalesRepCommission.class.getName(), "repId");

        return new DefaultReportQuery(userQuery, whereList, orderList, tables);
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return null;
    }

    @Override
    protected JasperInformation createJasperInfo() {
        return null;
    }
}

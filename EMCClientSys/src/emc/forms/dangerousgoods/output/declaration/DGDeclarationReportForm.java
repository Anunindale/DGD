/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.output.declaration;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.dangerousgoods.DGDUN;
import emc.entity.dangerousgoods.DGDVehicles;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.dangerousgoods.ServerDGMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pj
 */
public class DGDeclarationReportForm extends ReportFrame{
    
    public DGDeclarationReportForm(EMCUserData userData)
    {
        super("Dangerous Goods Declaration", EnumReports.DG_DECLARATION, userData);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() 
    {
     BaseReportUserQueryTable mainQueryInfo = new BaseReportUserQueryTable();
     List<BaseReportWhereTable> whereInfo = new ArrayList<>();
        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DGDeclarationLines.class.getName());
        whereTable.setField("decNumber");
        whereInfo.add(whereTable);
     
     List<BaseReportOrderTable> orderInfo = new ArrayList<>();
     
     ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(DGDeclarationMaster.class.getName(), "decNumber", DGDeclarationLines.class.getName(), "decNumber");
        tables.addTable(DGDVehicles.class.getName(), "contactNumber", DGDeclarationMaster.class.getName(), "contactNumber");
        tables.addTable(DGDUN.class.getName(), "lineNumber", DGDeclarationLines.class.getName(), "lineNumber");
     
     return new DefaultReportQuery(mainQueryInfo, whereInfo, orderInfo, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() 
    {
        JasperInformation jasperInfo = new JasperInformation();
        
        jasperInfo.setJasperTemplate("/emc/reports/dangerousgoods/DeclarationReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.dangerousgoods.DeclarationReportDS");
        jasperInfo.setReportTitle("Dangerous Goods Declaration");
        
        return jasperInfo;
    }

    @Override
    protected EMCCommandClass createReportCommand() 
    {
        return new EMCCommandClass
                (
                EMCCommands.REPORT_COMAND.getId(),
                enumEMCModules.DG.getId(),
                ServerDGMethods.PRINT_DECLARATION.toString()
                );
    }
    
}

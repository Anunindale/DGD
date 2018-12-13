/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output.cnfinishedgoodsboxlabels;

import emc.app.reporttools.DefaultReportQuery;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportIncludedTablesObject;
import emc.commands.EMCCommands;
import emc.entity.base.reporttools.BaseReportOrderTable;
import emc.entity.base.reporttools.BaseReportUserQueryTable;
import emc.entity.base.reporttools.BaseReportWhereTable;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.register.InventoryRegister;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.reporttools.WhereLineSpecialPermissions;
import emc.forms.debtors.output.cnfinishedgoodsboxlabels.resources.CNFinishedGoodsBoxLabelsOKButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class CNFinishedGoodsBoxLabelReportForm extends ReportFrame {

    private CNFinishedGoodsBoxLabelsOKButton button;

    public CNFinishedGoodsBoxLabelReportForm(EMCUserData userData) {
        super("Credit Note Box Labels", EnumReports.DEBTORS_CN_FINISHED_GOODS_BOX_LABELS, userData);
        button = new CNFinishedGoodsBoxLabelsOKButton(this, userData);
        this.setOkButton(button);
    }

    @Override
    protected DefaultReportQuery createDefaultQuery() {
        BaseReportUserQueryTable mainQueryInformation = new BaseReportUserQueryTable();

        List<BaseReportWhereTable> whereInformation = new ArrayList<BaseReportWhereTable>();

        BaseReportWhereTable whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCreditNoteMaster.class.getName());
        whereTable.setField("invCNNumber");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE.toString());
        whereInformation.add(whereTable);

        whereTable = new BaseReportWhereTable();
        whereTable.setTableName(DebtorsCreditNoteRegister.class.getName());
        whereTable.setField("batch");
        whereTable.setSpecial(WhereLineSpecialPermissions.NO_DELETE_ALLOW_BLANK.toString());
        whereInformation.add(whereTable);

        List<BaseReportOrderTable> orderInformation = new ArrayList<BaseReportOrderTable>();

        ReportIncludedTablesObject tables = new ReportIncludedTablesObject(this);
        tables.addTable(InventoryRegister.class.getName(), "masterId", DebtorsCreditNoteMaster.class.getName(), "invCNNumber");

        return new DefaultReportQuery(mainQueryInformation, whereInformation, orderInformation, tables);
    }

    @Override
    protected JasperInformation createJasperInfo() {
        return new JasperInformation();
    }

    @Override
    protected EMCCommandClass createReportCommand() {
        return new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_DEBTORS_CN_FG_BOX_LABELS.toString());
    }
}

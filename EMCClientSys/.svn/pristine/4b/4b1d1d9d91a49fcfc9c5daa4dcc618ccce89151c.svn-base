/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.output.cnfinishedgoodsboxlabels.resources;

import emc.app.reporttools.ReportFrame;
import emc.app.reporttools.ReportOKButton;
import emc.app.reporttools.barcodeprinter.StaticBarcodePrinting;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.inventory.register.InventoryRegister;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryFieldTypes;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.debtors.ServerDebtorsMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class CNFinishedGoodsBoxLabelsOKButton extends ReportOKButton {

    private ReportFrame reportFrame;
    private EMCUserData userData;

    public CNFinishedGoodsBoxLabelsOKButton(ReportFrame reportFrame, EMCUserData userData) {
        super(reportFrame);
        this.reportFrame = reportFrame;
        this.userData = userData;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        doWork(null); //will get it from the active query report frame should be available
    }

    /**
     * creditNote may be a single Credit Note number or a range.
     */
    public void doWork(String creditNote) {
        List creditNoteNos = null;
        List batchId = null;
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_DEBTORS_CN_FG_BOX_LABELS.toString());
        if (reportFrame != null) {
            EMCQuery qu = reportFrame.getActiveQuery();
            try {
                creditNoteNos = qu.getFieldValue(DebtorsCreditNoteMaster.class.getName(), "invCNNumber", EMCQueryFieldTypes.STRING, false);
                batchId = qu.getFieldValue(InventoryRegister.class.getName(), "batch", EMCQueryFieldTypes.STRING, false);
            } catch (Exception ex) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.WARNING, "Failed to  get Credit Note Number from query.", userData);
                }
            }
        } else {

            if (creditNote != null && !creditNote.isEmpty() && (creditNote.contains(",") || creditNote.contains("~"))) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
                query.addAndCommaSeperated("invCNNumber", creditNote, DebtorsCreditNoteMaster.class.getName(), EMCQueryConditions.EQUALS);
                creditNoteNos = query.getFieldValue(DebtorsCreditNoteMaster.class.getName(), "invCNNumber", EMCQueryFieldTypes.STRING, false);
            } else {
                creditNoteNos = new ArrayList();

                creditNoteNos.add(creditNote);
            }
            batchId = new ArrayList();
        }
        List toSend = new ArrayList();
        toSend.add(creditNoteNos);
        toSend.add(batchId);
        List retList = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        if (retList.size() > 1) {
            String result = retList.get(1).toString();
            result = new EMCXMLHandler().reinstateNewLines(result);
            StaticBarcodePrinting.print(EnumReports.DEBTORS_CN_FINISHED_GOODS_BOX_LABELS, reportFrame == null ? null : reportFrame.getQueryName(), result, userData);
        }
    }
}

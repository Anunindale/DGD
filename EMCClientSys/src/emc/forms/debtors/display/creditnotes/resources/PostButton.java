/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditnotes.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.reporttools.JasperInformation;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.debtors.creditnotes.DebtorsCreditNoteReturnOptions;
import emc.enums.debtors.invoice.DebtorsInvoicePrintType;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.debtors.output.cnfinishedgoodsboxlabels.resources.CNFinishedGoodsBoxLabelsOKButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.debtors.ServerDebtorsMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @description : This button is used to post a Credit Note.
 *
 * @date        : 15 July 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class PostButton extends emcMenuButtonList {

    private CreditNoteMasterDRM masterDRM;

    /** Creates a new instance of PostButton */
    public PostButton(CreditNoteMasterDRM masterDRM) {
        super("Post", masterDRM.getTheForm());
        this.masterDRM = masterDRM;

        this.addMenuItem("Selected", null, 0, false);
        this.addMenuItem("All Approved", null, 0, false);
    }

    @Override
    public void executeCmd(String theCmd) {
        if ("Selected".equals(theCmd)) {
            if ((Long) masterDRM.getLastFieldValueAt("recordID") == 0) {
                Logger.getLogger("emc").log(Level.SEVERE, "Please save the Credit Note before attempting to post it.");
                return;
            }

            if (EMCDialogFactory.createQuestionDialog(masterDRM.getTheForm(), "Post Credit Note?", "Are you sure that you wish to post the selected Credit Note?") == JOptionPane.YES_OPTION) {
                boolean generateNewNumbers = false;
                boolean printLabels = false;
                DebtorsCreditNoteReturnOptions returnOption = DebtorsCreditNoteReturnOptions.fromString((String)masterDRM.getLastFieldValueAt("returnOption"));
                if (returnOption == DebtorsCreditNoteReturnOptions.STK) {
                    //Prompt for numbers and labels
                    generateNewNumbers = EMCDialogFactory.createQuestionDialog(masterDRM.getTheForm(), "Generate New Numbers?", "Do you want to generate new numbers?") == JOptionPane.YES_OPTION;
                    printLabels = EMCDialogFactory.createQuestionDialog(masterDRM.getTheForm(), "Print Labels?", "Do you want to print labels?") == JOptionPane.YES_OPTION;
                }
                EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.POST_CREDIT_NOTE);

                List toSend = new ArrayList();
                toSend.add(masterDRM.getLastFieldValueAt("invCNNumber"));
                toSend.add(generateNewNumbers);

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterDRM.getUserData());

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                    EMCUserData userData = masterDRM.getUserData().copyUserData();
                    
                    if (returnOption != null) {
                        Logger.getLogger("emc").log(Level.INFO, "Successfully posted Credit Note - " + masterDRM.getLastFieldValueAt("invCNNumber") + ".  Stock received.");
                    } else {
                        //Not a stock Credit Note
                        Logger.getLogger("emc").log(Level.INFO, "Successfully posted Credit Note.", userData);
                    }

                    masterDRM.refreshRecordIgnoreFocus(masterDRM.getLastRowAccessed());

                    if (returnOption == DebtorsCreditNoteReturnOptions.FSTR) {
                        utilFunctions.logMessage(Level.INFO, "Stock issued to Factory Store from Credit Note - " + masterDRM.getLastFieldValueAt("invCNNumber") + ".", userData);
                    }

                    //Print Credit Note                    
                    userData.setUserData(0, DebtorsInvoicePrintType.CREDIT_NOTE.toString());

                    cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_CUSTOMER_INVOICE.toString());

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setJasperTemplate("/emc/reports/debtors/customerinvoice/DebtorsCustomerInvoiceReport.jrxml");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerinvoice.DebtorsCustomerInvioceReportDS");
                    jasperInfo.setReportTitle("Credit Note");

                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
                    query.addAnd("invCNNumber", masterDRM.getLastFieldValueAt("invCNNumber"));

                    toSend = new ArrayList();
                    toSend.add(query);

                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEBTORS_CUSTOMER_INVOICE, toSend, userData);

                    if (returnOption == DebtorsCreditNoteReturnOptions.STK && printLabels) {
                        CNFinishedGoodsBoxLabelsOKButton reportOKButton = new CNFinishedGoodsBoxLabelsOKButton(null, userData);
                        reportOKButton.doWork((String) masterDRM.getLastFieldValueAt("invCNNumber"));
                    }
                } else {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to post Credit Note.");
                }
            }
        } else if ("All Approved".equals(theCmd)) {
            if (EMCDialogFactory.createQuestionDialog(masterDRM.getTheForm(), "Post All Approved Credit Notes?", "Are you sure that you wish to post all approved Credit Notes?") == JOptionPane.YES_OPTION) {
                EMCCommandClass cmd = new EMCCommandClass(ServerDebtorsMethods.POST_ALL_APPROVED_CN);

                List toSend = new ArrayList();

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, masterDRM.getUserData());

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof List && !((List) toSend.get(1)).isEmpty()) {
                    Logger.getLogger("emc").log(Level.INFO, "Successfully posted all approved Credit Notes.");
                    masterDRM.refreshDataIgnoreFocus();

                    //Print Credit Note
                    EMCUserData userData = masterDRM.getUserData().copyUserData();

                    userData.setUserData(0, DebtorsInvoicePrintType.CREDIT_NOTE.toString());

                    cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.DEBTORS.getId(), ServerDebtorsMethods.PRINT_CUSTOMER_INVOICE.toString());

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setJasperTemplate("/emc/reports/debtors/customerinvoice/DebtorsCustomerInvoiceReport.jrxml");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.debtors.customerinvoice.DebtorsCustomerInvioceReportDS");
                    jasperInfo.setReportTitle("Credit Note");

                    List<String> postedCreditNotes = new ArrayList<String>();
                    StringBuilder postedSTKCreditNotes = new StringBuilder();
                    boolean printFinishedGoodsInstructions = false;

                    for (Object[] values : (List<Object[]>) toSend.get(1)) {
                        postedCreditNotes.add((String) values[0]);
                        DebtorsCreditNoteReturnOptions returnOption = DebtorsCreditNoteReturnOptions.fromString((String) values[1]);

                        if (returnOption != null) {
                            printFinishedGoodsInstructions = true;
                        }

                        if (returnOption == DebtorsCreditNoteReturnOptions.STK) {
                            if (postedSTKCreditNotes.length() != 0) {
                                postedSTKCreditNotes.append(",");
                                postedSTKCreditNotes.append((String) values[0]);
                            } else {
                                postedSTKCreditNotes.append((String) values[0]);
                            }
                        }
                    }
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
                    query.addAndInList("invCNNumber", postedCreditNotes, true, false);

                    toSend = new ArrayList();
                    toSend.add(query);

                    EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.DEBTORS_CUSTOMER_INVOICE, toSend, userData);

                    if (postedSTKCreditNotes.length() != 0) {
                        CNFinishedGoodsBoxLabelsOKButton reportOKButton = new CNFinishedGoodsBoxLabelsOKButton(null, userData);
                        reportOKButton.doWork(postedSTKCreditNotes.toString());
                    }
                }
            } else {
                Logger.getLogger("emc").log(Level.INFO, "Failed to post all approved Credit Notes.");
            }
        } else {
            super.executeCmd(theCmd);
        }
    }
}

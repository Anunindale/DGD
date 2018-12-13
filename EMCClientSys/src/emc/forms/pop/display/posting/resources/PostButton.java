/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting.resources;

import emc.app.components.emcDialogue;
import emc.app.components.emcMenuButton;
import emc.app.reporttools.JasperInformation;
import emc.app.reporttools.barcodeprinter.StaticBarcodePrinting;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCReportWSManager;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.base.reporttools.EnumReports;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.purchaseorders.PurchaseOrderReceivedLabelTypes;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.forms.pop.display.posting.PurchasePostMasterDRM;
import emc.forms.pop.display.posting.PurchasePostSetupDRM;
import emc.forms.pop.display.purchaseorders.PurchaseOrderMasterDataRelationManager;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.xml.EMCXMLHandler;
import emc.methods.inventory.ServerInventoryMethods;
import emc.methods.pop.ServerPOPMethods;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class PostButton extends emcMenuButton {

    private PurchaseOrderMasterDataRelationManager purchaseOrderMasterDRM;
    private PurchasePostMasterDRM drm;
    private PurchasePostSetupDRM setupDRM;
    private EMCUserData userData;
    private boolean close = true;

    public PostButton(String label, EMCMenuItem buttonitem, BaseInternalFrame form, int relatedFormIndex, boolean lines, PurchasePostMasterDRM drm, PurchaseOrderMasterDataRelationManager purchaseOrderMasterDRM) {
        super(label, buttonitem, form, relatedFormIndex, lines);
        this.drm = drm;
        this.setupDRM = drm.getSetupDRM();
        this.userData = drm.getUserData();
        this.purchaseOrderMasterDRM = purchaseOrderMasterDRM;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        boolean post = (Boolean) setupDRM.getFieldValueAt(0, "post");
        boolean print = (Boolean) setupDRM.getFieldValueAt(0, "print");

        int confirm = new emcDialogue("Are you sure?", "Print: " + (print ? "Yes" : "No") + "\nPost : " + (post ? "Yes" : "No") + "\nIs this correct?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION).getDialogueResult();

        //User data keeps track of whether a PO is posted
        userData.setUserData(7, post);

        if (confirm == JOptionPane.YES_OPTION) {
            DocumentTypes docType = DocumentTypes.fromString(((String) (drm.getSetupDRM().getUserData().getUserData(3))));

            switch (docType) {
                case PURCHASE_ORDER:
                    postPurchaseOrder(post, print);
                    break;
                case BLANKET_ORDER:
                    postBlanketOrder(post, print);
                    break;
                case GOODS_RECEIVED_NOTE:
                    receivePurchaseOrder(post, print);
                    break;
                case RECEIVING_LIST:
                    doReceivingList(post, print);
                    break;
                case RELEASE_PO:
                    releaseBlanketOrder(post, print);
                    break;
                case RETURN_GOODS:
                    returnGoods(post, print);
                    break;
            }

            if (close) {
                drm.getTheForm().dispose();

                //Allow nulls when posting from somewhere other than the purchase order form
                if (purchaseOrderMasterDRM != null) {
                    if (docType.equals(DocumentTypes.RELEASE_PO)) {
                        purchaseOrderMasterDRM.refreshData();
                    } else {
                        purchaseOrderMasterDRM.refreshRecordIgnoreFocus(purchaseOrderMasterDRM.getLastRowAccessed());
                    }
                }
            }


        }

    }

    /**
     * Posts a purchase order.
     */
    private void postPurchaseOrder(boolean post, boolean print) {

        String status = (String) purchaseOrderMasterDRM.getFieldValueAt(purchaseOrderMasterDRM.getLastRowAccessed(), "status");

        if (post) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.POST_PURCHASEORDER.toString());

            ArrayList toSend = new ArrayList();
            toSend.add(drm.getFieldValueAt(drm.getLastRowAccessed(), "purchaseOrderId"));

            EMCWSManager.executeGenericWS(cmd, toSend, userData);
        }

        if (print) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.GENERATE_POST_REPORT.toString());

            JasperInformation jasperInfo = new JasperInformation();
            jasperInfo.setReportTitle("Purchase Order");
            jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");

            ArrayList toSend = new ArrayList();
            toSend.add(userData.getUserData().get(0));

            if ( (!post && (status.equals(PurchaseOrderStatus.REQUISITION.toString()))) || (status.equals(PurchaseOrderStatus.APPROVED.toString()) && !post)) {
                jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/ProFormaPurchaseOrderPostingReport.jrxml");
            } else {
                jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderPostingReport.jrxml");
            }

            close = EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.PURCHASE_ORDER, toSend, userData);
        }

    }

    /**
     * Posts a blanket order.
     */
    private void postBlanketOrder(boolean post, boolean print) {

        String status = (String) purchaseOrderMasterDRM.getFieldValueAt(purchaseOrderMasterDRM.getLastRowAccessed(), "status");

        if (post) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.POST_BLANKETORDER.toString());

            ArrayList toSend = new ArrayList();
            toSend.add(drm.getFieldValueAt(drm.getLastRowAccessed(), "purchaseOrderId"));

            EMCWSManager.executeGenericWS(cmd, toSend, userData);
        }

        if (print) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.GENERATE_POST_REPORT.toString());

            ArrayList toSend = new ArrayList();
            toSend.add(userData.getUserData().get(0));

            JasperInformation jasperInfo = new JasperInformation();
            jasperInfo.setReportTitle("Blanket Order");
            jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");

            if (status.equals(PurchaseOrderStatus.REQUISITION.toString()) || (status.equals(PurchaseOrderStatus.APPROVED.toString()) && !post)) {
                jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/ProFormaBlanketOrderPostingReport.jrxml");
            } else {
                jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/BlanketOrderPostingReport.jrxml");
            }

            close = EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.BLANKET_ORDER, toSend, userData);
        }

    }

    /**
     * Receive a purchase order
     */
    private void receivePurchaseOrder(boolean post, boolean print) {

        if (checkBatchAndSerialHonoured((String) drm.getFieldValueAt(drm.getLastRowAccessed(), "postSetupId"))) {
            boolean postFailed = false;

            boolean validDocuments = checkDocuments();
            boolean validateNumLabels = checkNumLabels((String) drm.getFieldValueAt(drm.getLastRowAccessed(), "postMasterId"));
            String status = (String) purchaseOrderMasterDRM.getFieldValueAt(purchaseOrderMasterDRM.getLastRowAccessed(), "status");
            if (validDocuments && validateNumLabels) {
                if (post) {
                    if (status.equals(PurchaseOrderStatus.REQUISITION.toString())) {
                        java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Purchase order not approved.  Cannot receive.", userData);
                        close = false;
                        return;
                    } else {
                        close = true;
                        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.RECEIVE_PURCHASEORDER.toString());

                        ArrayList toSend = new ArrayList();
                        toSend.add(userData.getUserData().get(0));

                        cmd = (EMCCommandClass) EMCWSManager.executeGenericWS(cmd, toSend, userData).get(0);

                        if (cmd.getCommandId() == EMCCommands.SERVER_EXCEPTION_OCCURRED.getId()) {
                            postFailed = true;
                            java.util.logging.Logger.getLogger("emc").log(Level.WARNING, "Failed to post.  Cannot print", userData);
                            close = false;
                        }
                    }
                }

                if (print && !postFailed) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.GENERATE_POST_REPORT.toString());

                    ArrayList toSend = new ArrayList();
                    toSend.add(userData.getUserData().get(0));

                    JasperInformation jasperInfo = new JasperInformation();
                    jasperInfo.setReportTitle("Goods Received");
                    jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");

                    if (status.equals(PurchaseOrderStatus.REQUISITION.toString()) || !post) {
                        //Add pro forma
                        jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/ProFormaPurchaseOrderPostingRecievedNote.jrxml");
                    } else {
                        jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderPostingRecievedNote.jrxml");
                    }

                    close = EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.GOODS_RECEIVED_NOTE, toSend, userData);
                }

                if (close && post) {
                    printLabels((String) drm.getFieldValueAt(drm.getLastRowAccessed(), "postMasterId"), userData);
                }
            } else {
                close = false;
            }


        } else {
            close = false;
        }
    }

    private void doReceivingList(boolean post, boolean print) {

        String status = (String) purchaseOrderMasterDRM.getFieldValueAt(purchaseOrderMasterDRM.getLastRowAccessed(), "status");
        if (print) {
            if (!status.equals(PurchaseOrderStatus.REQUISITION.toString())) {
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.GENERATE_POST_REPORT.toString());

                ArrayList toSend = new ArrayList();
                toSend.add(userData.getUserData().get(0));

                JasperInformation jasperInfo = new JasperInformation();
                jasperInfo.setReportTitle("Receipts List");
                jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderReceiptsList.jrxml");
                jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");

                close = EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.RECEIVING_LIST, toSend, userData);
            } else {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Purchase order not approved.  Cannot print a receiving list.");
            }
        }

    }

    /**
     * Check that all posts have documents.
     */
    private boolean checkDocuments() {
        boolean allDocuments = true;
        boolean currentDocument = true;
        close = false;

        int numRows = drm.getRowCount();

        for (int i = 0; i < numRows; i++) {
            currentDocument = !Functions.checkBlank(drm.getFieldValueAt(i, "documentNumber"));

            if (!currentDocument) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, drm.getFieldValueAt(i, "purchaseOrderId") + " must have a document number (Delivery Note) set.", drm.getUserData());
                drm.setSelectedRow(i, 3);
            }

            allDocuments = allDocuments && currentDocument;
        }

        if (allDocuments) {
            close = true;
        }
        return allDocuments;
    }

    /**
     * Releases a blanket order.
     */
    private void releaseBlanketOrder(boolean post, boolean print) {

        if (post) {
            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.RELEASE_PO.toString());

            ArrayList toSend = new ArrayList();
            toSend.add(userData.getUserData().get(0));

            boolean success = (Boolean) ((EMCWSManager.executeGenericWS(cmd, toSend, userData).get(1)));

            if (!success) {
                close = false;
            } else {
                purchaseOrderMasterDRM.refreshDataIgnoreFocus();
                close = true;
            }
        } else {
            close = false;
        }
    }

    /**
     * This method ensures that batch and serial numbers are honoured
     */
    private boolean checkBatchAndSerialHonoured(String postSetupId) {
        DocumentTypes docType = DocumentTypes.fromString(((String) (drm.getSetupDRM().getUserData().getUserData(3))));
        EMCCommandClass cmd;
        if (docType.equals(DocumentTypes.RETURN_GOODS)) {
            cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.CHECK_REMOVED_REG_ALL.toString());
        } else {
            cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.CHECK_SERIAL_BATCH.toString());
        }
        ArrayList toSend = new ArrayList();
        toSend.add(postSetupId);
        userData.setUserData(8, drm.getFieldValueAt(drm.getLastRowAccessed(), "postMasterId"));
        List l = EMCWSManager.executeGenericWS(cmd, toSend, userData);
        return (Boolean) l.get(1);
    }

    private boolean checkNumLabels(String postMasterId) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.CHECH_NUMLABELS.toString());
        List toSend = new ArrayList();
        toSend.add(postMasterId);
        Boolean ret = (Boolean) EMCWSManager.executeGenericWS(cmd, toSend, userData).get(1);

        if (!ret) {
            Logger.getLogger("emc").log(Level.SEVERE, "Number of labels on the lines can't be blank.", userData);
        }
        return ret;
    }

    private void printLabels(String postId, EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.PRINT_LABELS.toString());
        ArrayList toSend = new ArrayList();
        toSend.add(postId);

        Map<String, String> labels = (Map<String, String>) EMCWSManager.executeGenericWS(cmd, toSend, userData).get(1);

        if (labels.containsKey(PurchaseOrderReceivedLabelTypes.GRN.toString())) {
            String grnString = new EMCXMLHandler().reinstateNewLines(labels.get(PurchaseOrderReceivedLabelTypes.GRN.toString()));
            StaticBarcodePrinting.print(EnumReports.POP_LABELS, null, grnString, userData);
//            if (!close) {
//                utilFunctions.logMessage(Level.SEVERE, "Failed to print GRN labels.", userData);
//            }
        }

        if (labels.containsKey(PurchaseOrderReceivedLabelTypes.FG_BOX.toString())) {
            String fgBoxLabelString = new EMCXMLHandler().reinstateNewLines(labels.get(PurchaseOrderReceivedLabelTypes.FG_BOX.toString()));
            StaticBarcodePrinting.print(EnumReports.POP_FG_BOX_LABELS, null, fgBoxLabelString, userData);
//            if (!close) {
//                utilFunctions.logMessage(Level.SEVERE, "Failed to print FG Box labels.", userData);
//            }
        }
        
        if (labels.containsKey(PurchaseOrderReceivedLabelTypes.CRATE.toString())) {
            String crateLabelString = new EMCXMLHandler().reinstateNewLines(labels.get(PurchaseOrderReceivedLabelTypes.CRATE.toString()));
            StaticBarcodePrinting.print(EnumReports.POP_CRATE_LABELS, null, crateLabelString, userData);
//            if (!close) {
//                utilFunctions.logMessage(Level.SEVERE, "Failed to print Crate labels.", userData);
//            }
        }
    }

    private void returnGoods(boolean post, boolean print) {
        if (checkBatchAndSerialHonoured((String) drm.getFieldValueAt(drm.getLastRowAccessed(), "postMasterId"))) {
            close = true;
            if (post) {
                emcDialogue d = new emcDialogue("Receive later?", "Do you want to receive replacement stock?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.RETURN_TO_SUPPLIER.toString());

                ArrayList toSend = new ArrayList();
                toSend.add(userData.getUserData().get(0));
                toSend.add(d.getDialogueResult() == JOptionPane.YES_OPTION);

                cmd = (EMCCommandClass) EMCWSManager.executeGenericWS(cmd, toSend, userData).get(0);

                if (cmd.getCommandId() == EMCCommands.SERVER_EXCEPTION_OCCURRED.getId()) {
                    java.util.logging.Logger.getLogger("emc").log(Level.WARNING, "Failed to post.  Cannot print", userData);
                    close = false;
                } else {
                    close = true;
                }
            }
            if (print && close) {
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.REPORT_COMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.GENERATE_POST_REPORT.toString());

                ArrayList toSend = new ArrayList();
                toSend.add(userData.getUserData().get(0));

                JasperInformation jasperInfo = new JasperInformation();
                jasperInfo.setReportTitle("Goods Returned");
                jasperInfo.setXmlNodePath("/emcmsg/emc.reports.pop.purchaseorderposting.POPPurchaseOrderPostingReportDS");
                jasperInfo.setJasperTemplate("/emc/reports/pop/purchaseorderposting/PurchaseOrderPostingReturnedGoods.jrxml");

                close = EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.POP_GOODSRETURNED, toSend, userData);
            }
        } else {
            close = false;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.purchaseorders;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.emcDialogue;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.pop.POPPurchaseOrderLines;
import emc.entity.pop.POPPurchaseOrderMaster;
import emc.entity.pop.cancelledpurchaseorders.POPCancelledPurchaseOrderMaster;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.enums.pop.purchaseorders.PurchaseOrderStatus;
import emc.forms.pop.display.purchaseorders.resources.CancelButton;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.methods.pop.ServerPOPMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riaan
 */
public class PurchaseOrderMasterDataRelationManager extends emcDataRelationManagerUpdate {

    boolean busyDialog = false;
    boolean updateLinesWarehouse = false;
    private CancelButton cancelButton;
    private EMCUserData userData;

    /** Creates a new instance of PurchaseOrderMasterDataRelationManager */
    public PurchaseOrderMasterDataRelationManager(emcGenericDataSourceUpdate dataSource, EMCUserData userData) {
        super(dataSource, userData);
        this.userData = userData;
    }

    @Override
    public void updatePersist(int rowIndex) {
        super.updatePersist(rowIndex);

        if (updateLinesWarehouse) {
            updateLinesWarehouse = false;
            refreshRecordIgnoreFocus(rowIndex);
        }
    }

    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if (!busyDialog) {
            if (columnIndex.equals("warehouse")) {
                if (this.getFieldValueAt(rowIndex, columnIndex) == null || !this.getFieldValueAt(rowIndex, columnIndex).toString().equals(aValue.toString())) {
                    busyDialog = true;
                    int dialogResult = new emcDialogue("Update address?", "Change address to warehouse address?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION).getDialogueResult();

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        //Position 3 indicates whether address should be updated
                        getUserData().setUserData(3, "UPDATE");
                    } else if (dialogResult == JOptionPane.NO_OPTION) {
                        //Position 3 indicates whether address should be updated
                        getUserData().setUserData(3, "NOTUPDATE");
                    }

                    emcDataRelationManagerUpdate linesDRM = this.getLinesTable();
                    if ((Long) getFieldValueAt(rowIndex, "recordID") != 0 && !((Long) linesDRM.getFieldValueAt(0, "recordID") == 0 && linesDRM.getRowCount() == 1)) {
                        dialogResult = new emcDialogue("Update lines warehouse?", "Update lines warehouse? (Change will only take place when saving purchase order.)", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION).getDialogueResult();

                        if (dialogResult == JOptionPane.YES_OPTION) {
                            //Position 4 indicates whether lines warehouse should be updated
                            getUserData().setUserData(4, "UPDATE");
                            updateLinesWarehouse = true;
                        } else if (dialogResult == JOptionPane.NO_OPTION) {
                            //Position 4 indicates whether lines warehouse should be updated
                            getUserData().setUserData(4, "NOTUPDATE");
                        }
                    }

                    busyDialog = false;
                }
            } else if (columnIndex.equals("vatCode")) {
                busyDialog = true;
                int dialogResult = new emcDialogue("Update lines vat code?", "Update lines vat code? (Change will only take place when saving purchase order.)", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION).getDialogueResult();

                if (dialogResult == JOptionPane.YES_OPTION) {
                    //Position 2 indicates whether vat code should be updated
                    getUserData().setUserData(2, "UPDATE");
                } else if (dialogResult == JOptionPane.NO_OPTION) {
                    //Position 2 indicates whether vat code should be updated
                    getUserData().setUserData(2, "NOTUPDATE");
                }
                busyDialog = false;
            } else if (columnIndex.equals("requestedDeliveryDate") && (Long) super.getFieldValueAt(super.getLastRowAccessed(), "recordID") != 0) {
                busyDialog = true;
                int dialogResult = EMCDialogFactory.createQuestionDialog(this.getTheForm(), "Update Lines?", "Do you want to update the PO Lines Delivery Date?");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.UPDATE_LINES_DATE.toString());
                    List toSend = new ArrayList();
                    toSend.add(super.getFieldValueAt(super.getLastRowAccessed(), "purchaseOrderId"));
                    toSend.add(aValue);
                    EMCWSManager.executeGenericWS(cmd, toSend, userData);
                    super.refreshRecord(super.getLastRowAccessed());
                }
                busyDialog = false;
            }

            super.setFieldValueAt(rowIndex, columnIndex, aValue);
        }

    }

    @Override
    public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData) {
        Object obj1 = super.getFieldValueAt(this.getLastRowAccessed(), this.getHeaderColumnID());
        if (obj1 == null) {
            obj1 = "";
        }//return linesUserData;    
        List x = new ArrayList();
        x.add(0, "SELECT u FROM POPPurchaseOrderLines u WHERE u.companyId = '" + linesUserData.getCompanyId() + "' AND u.purchaseOrderId = '" +
                obj1.toString() + "' ORDER BY u.lineNo");
        x.add(1, "SELECT COUNT(*) FROM POPPurchaseOrderLines u WHERE u.companyId = '" + linesUserData.getCompanyId() + "' AND u.purchaseOrderId = '" +
                obj1.toString() + "'");
        linesUserData.setUserData(x);
        return linesUserData;
    }

    private EMCUserData buildUserData(EMCUserData formUserData) {
        Object purchaseOrderId = this.getFieldValueAt(this.getLastRowAccessed(), "purchaseOrderId");

        if (purchaseOrderId != null) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class.getName());
            query.addAnd("purchaseOrderId", purchaseOrderId);
            query.addAnd("companyId", formUserData.getCompanyId());

            formUserData.setUserData(new ArrayList());
            formUserData.setUserData(0, query.toString());
            formUserData.setUserData(1, query.getCountQuery());
        }
        formUserData.setUserData(2, this);
        return formUserData;
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        Long recordId = (Long) this.getFieldValueAt(this.getLastRowAccessed(), "recordID");
        Object o;
        EMCQuery query;
        List x;
        switch (Index) {
            case 1:
                o = super.getFieldValueAt(this.getLastRowAccessed(), "purchaseOrderId");
                query = new EMCQuery(enumQueryTypes.SELECT, POPSupplierReceivedJournalMaster.class.getName());
                query.addAnd("purchaseOrderId", o.toString());
                query.addAnd("companyId", formUserData.getCompanyId());
                x = new ArrayList();
                x.add(0, query.toString());
                x.add(1, query.getCountQuery());
                formUserData.setUserData(x);
                break;
            case 2:
                formUserData.setUserData(new ArrayList());
                //Uses default query;
                break;
            case 3:
                o = super.getFieldValueAt(this.getLastRowAccessed(), "purchaseOrderId");
                query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderLines.class.getName());
                query.addAnd("purchaseOrderId", o.toString());
                query.addAnd("companyId", formUserData.getCompanyId());
                query.addAnd("costChange", true);
                x = new ArrayList();
                x.add(0, query.toString());
                x.add(1, query.getCountQuery());
                formUserData.setUserData(x);
                break;
            case 4:
                o = super.getFieldValueAt(this.getLastRowAccessed(), "purchaseOrderId");
                query = new EMCQuery(enumQueryTypes.SELECT, POPCancelledPurchaseOrderMaster.class.getName());
                query.addAnd("purchaseOrderId", String.valueOf(o));
                query.addAnd("companyId", formUserData.getCompanyId());

                formUserData.setUserData(0, query.toString());
                formUserData.setUserData(1, query.getCountQuery());

                break;
            case 5:
                o = super.getFieldValueAt(this.getLastRowAccessed(), "purchaseOrderId");
                query = new EMCQuery(enumQueryTypes.SELECT, POPPurchaseOrderMaster.class);
                query.addAnd("purchaseOrderId", String.valueOf(o));
                formUserData.setUserData(0, query);
                break;
            case -1000:
                //Document type stored in position 3
                formUserData = this.buildUserData(formUserData);
                formUserData.setUserData(3, DocumentTypes.PURCHASE_ORDER.toString());

                if (recordId == 0) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot post an unsaved purchase order.", getUserData());
                }
                break;
            case -1001:
                formUserData = this.buildUserData(formUserData);
                //Document type stored in position 3
                formUserData.setUserData(3, DocumentTypes.GOODS_RECEIVED_NOTE.toString());

                if (recordId == 0) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot receive an unsaved purchase order.", getUserData());
                }
                break;
            case -1002:
                formUserData = this.buildUserData(formUserData);
                //Document type stored in position 3
                formUserData.setUserData(3, DocumentTypes.RECEIVING_LIST.toString());

                if (recordId == 0) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot receive an unsaved purchase order.", getUserData());
                }
                break;

            case -1003:
                formUserData = buildUserData(formUserData);
                //Document type stored in position 3
                formUserData.setUserData(3, DocumentTypes.RELEASE_PO.toString());

                if (recordId == 0) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot release an unsaved blanket order.", getUserData());
                }

                break;

            case -1004:
                formUserData = buildUserData(formUserData);
                //Document type stored in position 3
                formUserData.setUserData(3, DocumentTypes.BLANKET_ORDER.toString());

                if (recordId == 0) {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot post an unsaved blanket order.", getUserData());
                }

                break;
            //Approve
            case -1005:
                String purchaseOrderId = (String) this.getFieldValueAt(this.getLastRowAccessed(), "purchaseOrderId");
                if (purchaseOrderId != null && !purchaseOrderId.equals("")) {

                    //emcDialogue d = new emcDialogue("Approve?", "Are you sure you want to approve this purchase order?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                    //if (d.getDialogueResult() == 0) {

                    EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.APPROVE_PURCHASEORDER.toString());

                    ArrayList toSend = new ArrayList();
                    toSend.add(purchaseOrderId);

                    EMCWSManager.executeGenericWS(cmd, toSend, this.getUserData());

                    this.refreshRecord(-1);
                //}
                } else {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot approve an empty purchase order.", this.getUserData());
                }
                break;
            //UnApprove
            case -1006:
                String status = this.getFieldValueAt(this.getLastRowAccessed(), "status").toString();
                if (status.equals(PurchaseOrderStatus.APPROVED.toString())) {
                    String purchaseOrder = (String) this.getFieldValueAt(this.getLastRowAccessed(), "purchaseOrderId");

                    if (purchaseOrder != null && !purchaseOrder.equals("")) {
                        emcDialogue d = new emcDialogue("Approve?", "Are you sure you want to unapprove this purchase order?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                        if (d.getDialogueResult() == 0) {

                            EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.POP.getId(), ServerPOPMethods.UNAPPROVE_PURCHASEORDER.toString());

                            ArrayList toSend = new ArrayList();
                            toSend.add(purchaseOrder);

                            EMCWSManager.executeGenericWS(cmd, toSend, this.getUserData());

                            this.refreshRecord(-1);
                        }
                    } else {
                        java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Cannot approve an empty purchase order.", this.getUserData());
                    }
                } else {
                    java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "This purchase order cannot be unapproved.", this.getUserData());
                }
                break;
            case -1007:
                formUserData = this.buildUserData(formUserData);
                //Document type stored in position 3
                formUserData.setUserData(3, DocumentTypes.RETURN_GOODS.toString());
                formUserData.setUserData(4, "RT");
                break;
            case -1008:
                formUserData = this.buildUserData(formUserData);
                //Document type stored in position 3
                formUserData.setUserData(3, DocumentTypes.RETURN_GOODS.toString());
                formUserData.setUserData(4, "QC");
                break;
        }
        return formUserData;
    }

    @Override
    public void doRelation(int rowIndex) {
        super.doRelation(rowIndex);

        String status = (String) this.getFieldValueAt(rowIndex, "status");

        if (cancelButton != null) {
            if (status.equals(PurchaseOrderStatus.ORDERED.toString()) || status.equals(PurchaseOrderStatus.PARTIALLY_RECEIVED.toString())) {
                if (!cancelButton.isEnabled()) {
                    cancelButton.setEnabled(true);
                }
            } else {
                cancelButton.setEnabled(false);
            }
        }
    }

    /** Sets the cancel button. */
    public void setCancelButton(CancelButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        if (rowIndex == -1) {
            //Get selected row
            rowIndex = getLastRowAccessed();
        }
        String status = (String) getFieldValueAt(rowIndex, "status");

        if (!(status.equals(PurchaseOrderStatus.APPROVED.toString())) && !(status.equals(PurchaseOrderStatus.REQUISITION.toString()))) {
            Logger.getLogger("emc").log(Level.SEVERE, "A Purchase Order with a status of Ordered or higher may not be deleted.  Use the Cancel PO button.", getUserData());
        } else {
            if (!Functions.checkBlank(getFieldValueAt(rowIndex, "blanketOrderRef"))) {
                getUserData().setUserData(3, EMCDialogFactory.createQuestionDialog(this.getTheForm(), "Update Blanket Order?", "Do you want the quantities on the lines of the selected order to be added back to the Blanket Order from which it was released?") == JOptionPane.YES_OPTION);
            }
            super.deleteRowCache(rowIndex);
        }
    }
}

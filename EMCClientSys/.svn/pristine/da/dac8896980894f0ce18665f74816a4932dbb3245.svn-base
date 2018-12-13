/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.transactionsettlement.resources;

import emc.app.components.documents.EMCFormDocumentInterface;
import emc.app.components.emcJTextField;
import emc.app.components.emcpicker.EMCFormPicker;
import emc.app.components.emcpicker.EMCPickerDocument;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.datatypes.sop.customers.foreignkeys.CustomerIdFK;
import emc.entity.sop.SOPCustomers;
import emc.forms.debtors.display.transactionsettlement.TransactionSettlementForm;
import emc.framework.EMCCommandClass;
import emc.functions.Functions;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import emc.methods.sop.ServerSOPMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description : This is the lookup used on the Debtors transaction settlement form.
 *
 * @date        : 22 Jun 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class TransactionSettlementLookup extends EMCLookup implements EMCFormPicker {

    private TransactionSettlementForm settlementForm;
    private emcJTextField txtCustomerName;
    private String lastCustomerId;

    /** Creates a new instance of TransactionSettlementLookup */
    public TransactionSettlementLookup(TransactionSettlementForm settlementForm, emcJTextField txtCustomerName) {
        super(new SOPCustomersMenu());
        this.settlementForm = settlementForm;
        this.txtCustomerName = txtCustomerName;

        this.setDocument(new SettlementLookupDocument());
        this.handleFocusEvents();
    }

    @Override
    public void setValue(Object value) {
        //Only fetch new customer if value changed.
        if (!Functions.checkObjectsEqual(value, lastCustomerId)) {
            EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_CUSTOMER);

            List toSend = new ArrayList<String>();
            //Customer id.
            toSend.add(value);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, settlementForm.getUserData());

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof SOPCustomers) {
                SOPCustomers customer = (SOPCustomers) toSend.get(1);

                super.setValue(value);
                this.lastCustomerId = (String)value;

                txtCustomerName.setText(customer.getCustomerName());

                settlementForm.loadData(customer.getCustomerId(), null, settlementForm.getUserData());
            } else {
                super.setValue(lastCustomerId);  //If user typed value in, go back to previous value.
                Logger.getLogger("emc").log(Level.SEVERE, "Invalid customer", settlementForm.getUserData());
            }
        }
    }

    public void setValueDoNotCreateData(String value, Long sessionId) {
        //Only fetch new customer if value changed.
        if (!Functions.checkObjectsEqual(value, lastCustomerId)) {
            EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_CUSTOMER);

            List toSend = new ArrayList<String>();
            //Customer id.
            toSend.add(value);

            toSend = EMCWSManager.executeGenericWS(cmd, toSend, settlementForm.getUserData());

            if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof SOPCustomers) {
                SOPCustomers customer = (SOPCustomers) toSend.get(1);
                this.lastCustomerId = (String)value;
            
                super.setValue(value);

                txtCustomerName.setText(customer.getCustomerName());

                settlementForm.loadData(customer.getCustomerId(), sessionId, settlementForm.getUserData());
            } else {
                super.setValue(lastCustomerId);  //If user typed value in, go back to previous value.
                Logger.getLogger("emc").log(Level.SEVERE, "Invalid customer", settlementForm.getUserData());
            }
        }
    }

    public void doFocusGained() {
        //Do nothing
    }

    public void doFocusLost() {
        setValue(this.getTextField().getText());
    }

    private class SettlementLookupDocument extends EMCPickerDocument implements EMCFormDocumentInterface {

        public void setRowIndex(int rowIndex) {
            //Do nothing
        }

        public void UpdateCache() {
            //Do nothing
        }

        public void setFocusDRM() {
            //Do nothing
        }

        public void handleFocusLost() {
            //Do nothing
        }

        public emcDataRelationManagerUpdate getDataRelationManager() {
            //Do nothing
            return null;
        }

        public String getFieldName() {
            //Do nothing
            return null;
        }

        @Override
        public EMCDataType getDefaultDataType() {
            return new CustomerIdFK();
        }
    }
}

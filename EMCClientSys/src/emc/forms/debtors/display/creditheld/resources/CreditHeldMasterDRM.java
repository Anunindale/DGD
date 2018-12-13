/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.creditheld.resources;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.sop.SOPCustomers;
import emc.enums.debtors.creditheld.DebtorsCreditHeldRefType;
import emc.forms.debtors.display.creditheld.CreditHeldForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.sop.ServerSOPMethods;
import java.util.ArrayList;
import java.util.List;

/**
 * @description : Master DRM for the Credit Held form.
 *
 * @date        : 30 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
public class CreditHeldMasterDRM extends emcDRMViewOnly {

    private CreditHeldForm form;

    /** Creates a new instance of CreditHeldMasterDRM */
    public CreditHeldMasterDRM(emcGenericDataSourceUpdate tableDataSource, CreditHeldForm form, EMCUserData userData) {
        super(tableDataSource, userData);
        this.form = form;
    }

    @Override
    public void tableRowChanged(int rowIndex) {
        EMCUserData newLinesUD = getUserData().copyUserData();

        DebtorsCreditHeldRefType refType = DebtorsCreditHeldRefType.fromString((String) this.getFieldValueAt(rowIndex, "referenceType"));
        String ref = (String) this.getFieldValueAt(rowIndex, "reference");

        if (ref == null || refType == null) {
            //Do nothing
            return;
        }

        this.form.masterRowChanged(ref, refType, newLinesUD);
        super.tableRowChanged(rowIndex);
    }

    @Override
    public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData) {
        linesUserData = super.generateRelatedLinesUserData(linesUserData);

        //Only show credit held lines.
        EMCQuery query = (EMCQuery) linesUserData.getUserData(0);
        query.addAnd("creditHeld", true);

        return linesUserData;
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 1:
                //Fetch customer in order to pass required information through to the balance form.
                EMCCommandClass cmd = new EMCCommandClass(ServerSOPMethods.GET_CUSTOMER);

                List toSend = new ArrayList();
                toSend.add(this.getLastFieldValueAt("customerId"));

                toSend = EMCWSManager.executeGenericWS(cmd, toSend, formUserData);

                if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof SOPCustomers) {
                    SOPCustomers customer = (SOPCustomers)toSend.get(1);
                    formUserData.setUserData(3, customer.getCustomerId());
                    formUserData.setUserData(4, customer.getCustomerName());
                    formUserData.setUserData(5, customer.getCreaditLimit());
                    break;
                }
                //Total Credit Held Form
            case 2:
                formUserData.setUserData(3, this.getLastFieldValueAt("customerId"));
                break;

        }
        return formUserData;
    }
}

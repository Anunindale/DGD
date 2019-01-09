/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.debtors.display.customerinvoice.resources;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class CustomerInvoiceMasterDRM extends emcDataRelationManagerUpdate {

    /** Creates a new instance of CustomerInvoiceMasterDRM */
    public CustomerInvoiceMasterDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        if ((!this.checkFocus()) && (getLinesTable() != null)) {
            getLinesTable().deleteRowCache(rowIndex);
        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "You are not allowed to delete rows from this table. Rather cancel the Invoice.", getUserData());
        }
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);

        switch (Index) {
            case 1:
                //Open Credit Notes
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteMaster.class);
                query.addAnd("refInvoiceNo", this.getLastFieldValueAt("invCNNumber"));
                query.addAnd("status", DebtorsInvoiceStatus.POSTED.toString(), EMCQueryConditions.NOT);
                query.addAnd("status", DebtorsInvoiceStatus.CANCELED.toString(), EMCQueryConditions.NOT);
                query.addAnd("status", DebtorsInvoiceStatus.DISTRIBUTION.toString(), EMCQueryConditions.NOT);

                formUserData.setUserData(0, query);
                break;
        }

        return formUserData;
    }
}

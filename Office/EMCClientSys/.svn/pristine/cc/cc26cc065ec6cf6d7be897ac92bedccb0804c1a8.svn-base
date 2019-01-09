/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class PurchasePostMasterDRM extends emcDataRelationManagerUpdate {

    private PurchasePostSetupDRM setupDRM;

    /** Creates a new instance of PurchasePostMasterDRM */
    public PurchasePostMasterDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }

    @Override
    public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData) {
        Object obj1 = super.getFieldValueAt(this.getLastRowAccessed(), this.getHeaderColumnID());
        if (obj1 == null) return linesUserData;
        List x = new ArrayList();
        x.add(0, "SELECT u FROM POPPurchasePostLines u WHERE u.companyId = '" + linesUserData.getCompanyId() + "' AND u.postMasterId = '" +
                obj1.toString() + "' ORDER BY u.lineNumber");
        x.add(1, "SELECT COUNT(*) FROM POPPurchasePostLines u WHERE u.companyId = '" + linesUserData.getCompanyId() + "' AND u.postMasterId = '" +
                obj1.toString() + "'");
        x.add(((PostingForm) getTheForm()).getFormType());
        linesUserData.setUserData(x);

        return linesUserData;
    }

    public PurchasePostSetupDRM getSetupDRM() {
        return setupDRM;
    }

    public void setSetupDRM(PurchasePostSetupDRM setupDRM) {
        this.setupDRM = setupDRM;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.debtors.display.creditheld.resources;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;

/**
 *
 * @author riaan
 */
public class CreditHeldLinesDRM extends emcDRMViewOnly {

    /** Creates a new instance of CreditHeldLinesDRM. */
    public CreditHeldLinesDRM(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData) {
        super(tableDataSource, userData);
    }
}

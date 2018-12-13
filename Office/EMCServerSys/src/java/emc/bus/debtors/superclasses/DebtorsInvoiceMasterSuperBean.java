/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.superclasses;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsInvoiceMasterSuper.  This bean contains
 *                logic common to Invoices and Credit Notes.
 *
 * @date        : 19 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsInvoiceMasterSuperBean extends EMCEntityBean implements DebtorsInvoiceMasterSuperLocal {

    /** Creates a new instance of DebtorsInvoiceMasterSuperBean */
    public DebtorsInvoiceMasterSuperBean() {

    }
}

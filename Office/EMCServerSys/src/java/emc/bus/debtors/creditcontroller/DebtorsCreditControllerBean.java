/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.creditcontroller;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCreditController.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCreditControllerBean extends EMCEntityBean implements DebtorsCreditControllerLocal {

    /** Creates a new instance of DebtorsCreditControllerBean */
    public DebtorsCreditControllerBean() {

    }
}

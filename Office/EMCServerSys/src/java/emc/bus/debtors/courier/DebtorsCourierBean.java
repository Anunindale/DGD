/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.courier;

import emc.framework.EMCEntityBean;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsCourier.
 *
 * @date        : 06 Jul 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsCourierBean extends EMCEntityBean implements DebtorsCourierLocal {

    /** Creates a new instance of DebtorsCourierBean */
    public DebtorsCourierBean() {

    }
}

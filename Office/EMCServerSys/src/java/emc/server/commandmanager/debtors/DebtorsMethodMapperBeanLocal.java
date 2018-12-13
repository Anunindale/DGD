/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.commandmanager.debtors;

import emc.framework.EMCEntityBeanException;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DebtorsMethodMapperBeanLocal {

    public java.util.List mapMethodDebtors(emc.framework.EMCCommandClass cmd, java.util.List<java.lang.Object> dataList, emc.framework.EMCUserData userData) throws EMCEntityBeanException;

}

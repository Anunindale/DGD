/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.commandmanager.pop;

import emc.framework.EMCEntityBeanException;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface POPMethodMapperBeanLocal {

    public java.util.List mapMethodPOP(emc.framework.EMCCommandClass cmd, java.util.List<java.lang.Object> dataList, emc.framework.EMCUserData userData) throws EMCEntityBeanException;

}

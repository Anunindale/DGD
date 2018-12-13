/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.commandmanager.base;

import emc.framework.EMCEntityBeanException;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface BaseMethodMapperBeanLocal {

    public java.util.List mapMethodBase(emc.framework.EMCCommandClass cmd, java.util.List<java.lang.Object> dataList, emc.framework.EMCUserData userData) throws EMCEntityBeanException;

}

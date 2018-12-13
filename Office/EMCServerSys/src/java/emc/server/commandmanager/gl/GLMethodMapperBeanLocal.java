/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.commandmanager.gl;

import emc.framework.EMCEntityBeanException;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface GLMethodMapperBeanLocal {

    public java.util.List mapMethodGL(emc.framework.EMCCommandClass cmd, java.util.List<java.lang.Object> dataList, emc.framework.EMCUserData userData) throws EMCEntityBeanException;

}

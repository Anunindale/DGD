/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.commandmanager.developertools;

import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DeveloperToolsMethodMapperBeanLocal {
    public List mapMethodDeveloperTools(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException;
}

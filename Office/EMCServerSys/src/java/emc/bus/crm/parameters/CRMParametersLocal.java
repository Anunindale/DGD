/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.crm.parameters;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface CRMParametersLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.crm.CRMParameters getParameter(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}

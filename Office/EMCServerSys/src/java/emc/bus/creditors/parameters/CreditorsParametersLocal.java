/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.creditors.parameters;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author claudette
 */
@Local
public interface CreditorsParametersLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.creditors.CreditorsParameters getParameter(emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}

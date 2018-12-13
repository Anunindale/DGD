/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.customerlabels;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface SOPCustomerLabelsLocal extends EMCEntityBeanLocalInterface {

    public java.util.List getCustomerLablesForSO(java.lang.String salesOrderId, emc.framework.EMCUserData userData);
}

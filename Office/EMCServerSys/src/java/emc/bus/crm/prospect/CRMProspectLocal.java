/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.crm.prospect;

import emc.entity.crm.CRMProspect;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface CRMProspectLocal extends EMCEntityBeanLocalInterface {

    public void createJob(CRMProspect prospect, EMCUserData userData) throws EMCEntityBeanException;
}

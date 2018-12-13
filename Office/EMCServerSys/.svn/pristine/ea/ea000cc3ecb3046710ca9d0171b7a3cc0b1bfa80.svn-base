/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.numbersequences;

import emc.entity.base.numbersequences.BaseNumberSequence;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface BaseNumberSequenceLocal extends EMCEntityBeanLocalInterface {

    public void populateModuleId(EMCUserData userData) throws EMCEntityBeanException;

    public boolean regenerateRandomNumbers(BaseNumberSequence numSeq, EMCUserData userData) throws EMCEntityBeanException;
}

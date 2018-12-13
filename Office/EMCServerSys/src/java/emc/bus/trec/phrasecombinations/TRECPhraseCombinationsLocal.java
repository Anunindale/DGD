/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.phrasecombinations;

import emc.entity.trec.TRECPhraseCombinations;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface TRECPhraseCombinationsLocal extends EMCEntityBeanLocalInterface {
    /**
     * Special method to help with encryption
     * @param phrase
     * @param userData
     */
    public void updateEncryptedFields(TRECPhraseCombinations ph,EMCUserData userData);
}

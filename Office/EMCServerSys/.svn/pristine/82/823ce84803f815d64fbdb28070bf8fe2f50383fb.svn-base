/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.chemicals;

import emc.entity.trec.TRECChemicals;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface TRECChemicalsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Special method to help with encryption
     *
     * @param chemical
     * @param originalRef
     * @param userData
     */
    public void updateEncryptedFields(TRECChemicals chemical, EMCUserData userData);

    public boolean restructureChemicals(EMCUserData userData) throws EMCEntityBeanException;

    public emc.entity.trec.TRECChemicals fetchChemical(int chemicalId, emc.framework.EMCUserData userData);

    public void fixChemicalPhrases(EMCUserData userData);

    public boolean validatePhraseToBeAdded(String phraseId, String phraseType, EMCUserData userData);

    public void checkTRECChemicals(emc.framework.EMCUserData userData);
}

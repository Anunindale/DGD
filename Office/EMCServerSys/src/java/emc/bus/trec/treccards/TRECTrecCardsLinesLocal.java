/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.treccards;

import emc.entity.trec.TRECTrecCardsLines;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface TRECTrecCardsLinesLocal extends EMCEntityBeanLocalInterface {

    public void updateLinesFields(String masterId, String emergencyNumber, String preparedBy, EMCUserData userData);

    public boolean approveTREC(long recordID, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
    
    public boolean validatePackingGroup(TRECTrecCardsLines record, EMCUserData userData);

    public java.lang.String getPackingGroup(java.lang.String UNNumber, emc.framework.EMCUserData userData);
}

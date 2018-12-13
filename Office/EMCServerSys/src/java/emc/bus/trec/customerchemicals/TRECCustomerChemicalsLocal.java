
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.customerchemicals;

import emc.entity.trec.TRECCustomerChemicals;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author asd_admin
 */
@Local
public interface TRECCustomerChemicalsLocal extends EMCEntityBeanLocalInterface {

    public void updateEncryptedFields(TRECCustomerChemicals chemical, EMCUserData userData);

    public void updateEncryptedFields(long chemRecId, EMCUserData userData);

    public long createCustomerChemical(long trecLineRecId, String customer, String unNumber, String shippingName, EMCUserData userData) throws EMCEntityBeanException;

    public TRECCustomerChemicals fetchCustomerChemical(long recordID, EMCUserData userData);

    public boolean resetCustomerChemical(long trecLineRecId, EMCUserData userData) throws EMCEntityBeanException;

    public java.util.Map<java.lang.String, java.lang.String> fetchPhrasesForChemical(int chemicalId, java.lang.String phraseType, boolean allPhrases, emc.framework.EMCUserData userData);

    public void checkEPhrases(emc.entity.trec.TRECCustomerChemicals custChem, emc.framework.EMCUserData userData);
}

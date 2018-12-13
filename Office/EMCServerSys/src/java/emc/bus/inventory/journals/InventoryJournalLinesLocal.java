/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.journals;

import emc.entity.inventory.journals.InventoryJournalLines;
import emc.enums.inventory.journals.InventoryJournalTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface InventoryJournalLinesLocal extends EMCEntityBeanLocalInterface {

    /**
     * returns the journal type
     * @param line
     * @param userData
     * @return
     */
    public InventoryJournalTypes getJournalType(InventoryJournalLines line, EMCUserData userData);

    /**
     * Returns a Journal Line matching the specified parameters, or null, if no Journal Line is found.
     * @param journalNumber Journal number.
     * @param transId Transaction id.
     * @param userData User data.
     * @return A Journal Line matching the specified parameters, or null, if no Journal Line is found.
     */
    public InventoryJournalLines findJournalLine(String journalNumber, String transId, EMCUserData userData);

    public boolean massUpdateLineDate(String journalId, Date theDate, EMCUserData userData) throws EMCEntityBeanException;

    public boolean generateRegistration(String masterId, String transId, String batchPrefix, String batchNo, String batchSuffix, String serialNo, String location, BigDecimal quantity, String batchGroupId, EMCUserData userData) throws EMCEntityBeanException;

    public void doUpdate(Object dobject, EMCUserData userData);
}

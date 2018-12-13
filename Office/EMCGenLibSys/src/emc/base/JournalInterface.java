/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.base;

/**
 * @description : This interface marks an entity class as representing a
 * journal. It also defines methods which should be common to all journal entity
 * classes.
 *
 * @date : 12 May 2010
 *
 * @author : Riaan Nel
 *
 * @version : 1.0
 */
public interface JournalInterface {

    public void setJournalStatus(String journalStatus);

    public String getJournalStatus();

    public void setJournalDefinition(String journalDefinition);

    public String getJournalDefinition();

    public void setJournalNumber(String journalNumber);

    public String getJournalNumber();

    public void setJournalContraAccount(String journalContraAccount);

    public String getJournalContraAccount();

    public void getJournalContraType(String journalConter);
}

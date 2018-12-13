/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.debtors.journals;

import emc.bus.base.journals.accessgroups.BaseJournalAccessGroupsLocal;
import emc.bus.base.journals.superclass.JournalMasterSuperBean;
import emc.bus.debtors.transactions.logic.DebtorsTransactionLogicLocal;
import emc.entity.base.journals.superclass.JournalMasterSuperClass;
import emc.entity.debtors.journals.DebtorsJournalMaster;
import emc.enums.base.journals.JournalStatus;
import emc.enums.base.journals.Modules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.EMCMessageEnum;
import emc.messages.ServerBaseMessageEnum;
import emc.messages.ServerDebtorsMessageEnum;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @description : Entity bean for DebtorsJournalMaster.
 *
 * @date        : 04 May 2010
 *
 * @author      : Riaan Nel
 *
 * @version     : 1.0
 */
@Stateless
public class DebtorsJournalMasterBean extends JournalMasterSuperBean implements DebtorsJournalMasterLocal {

    @EJB
    private DebtorsTransactionLogicLocal transactionLogicBean;
    @EJB
    private BaseJournalAccessGroupsLocal accessBean;

    /** Creates a new instance of DebtorsJournalMasterBean */
    public DebtorsJournalMasterBean() {

    }

    /**
     * Returns a DebtorsJournalMaster record.
     * 
     * @param journalNumber Journal number of journal to return.
     * @param userData User data.
     * @return A DebtorsJournalMaster instance, or null, if no record is found.
     */
    public DebtorsJournalMaster getDebtorsJournalMaster(String journalNumber, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsJournalMaster.class);
        query.addAnd("journalNumber", journalNumber);

        return (DebtorsJournalMaster)util.executeSingleResultQuery(query, userData);
    }
    
    @Override
    protected boolean doPost(JournalMasterSuperClass journal, EMCUserData userData) throws EMCEntityBeanException {
        journal.setJournalPostedBy(userData.getUserName());
        journal.setJournalPostedDate(Functions.nowDate());

        journal.setJournalStatus(JournalStatus.POSTED.toString());

        transactionLogicBean.postDebtorsJournal((DebtorsJournalMaster)journal, userData);
        
        this.update(journal, userData);

        return true;
    }

    @Override
    protected EMCMessageEnum getValidateSuccessMessage() {
        return ServerDebtorsMessageEnum.VAL_SUCC;
    }

    @Override
    protected EMCMessageEnum getValidateFailMessage() {
        return ServerDebtorsMessageEnum.VAL_FAIL;
    }

    @Override
    protected Modules getModule() {
        return Modules.DEBTORS;
    }

    @Override
    protected Class getJournalClass() {
        return DebtorsJournalMaster.class;
    }

    @Override
    public boolean doFieldValidation(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Boolean valid = (Boolean) super.doFieldValidation(fieldNameToValidate, theRecord, userData);
        DebtorsJournalMaster master = (DebtorsJournalMaster) theRecord;
        
        if (fieldNameToValidate.equals("journalDefinitionId")) {
                boolean validDef = false;
                List validDefs = accessBean.getDefinitionList(userData);
                for(int i = 0; i < validDefs.size(); i++){
                    if(validDefs.get(i).toString().equalsIgnoreCase(master.getJournalDefinitionId())){
                        validDef = true;
                        break;
                    }
                }
                if(!validDef) this.logMessage(Level.SEVERE, ServerBaseMessageEnum.NOACCESS, userData);
                valid = valid && validDef;
        }
        
        return valid;
    }

}

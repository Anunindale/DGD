/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.web;

import emc.entity.trec.TRECTrecCardsLines;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.helpers.trec.TRECWebInvoiceHelper;
import emc.helpers.trec.TRECWebOrderProcessHelper;
import javax.ejb.Local;

/**
 *
 * @author stuart
 */
@Local
public interface TRECWebOrderProcessLogicBeanLocal extends EMCEntityBeanLocalInterface {

//    public TRECWebOrderProcessHelper insertFetchTrecMasterLinesFromWeb(TRECWebOrderProcessHelper helper, Long customerRecordId, String UNNumber, EMCUserData userData) throws EMCEntityBeanException;

    public TRECWebOrderProcessHelper updateCardMaster(TRECWebOrderProcessHelper helper, EMCUserData userData);

    public TRECWebOrderProcessHelper createUpdateBasketWeb(TRECWebOrderProcessHelper trecWebHelper, EMCUserData userData);
    
    public boolean updateBasketMaster(TRECWebOrderProcessHelper trecWebHelper, EMCUserData userData);

    public TRECWebOrderProcessHelper createUpdateDebtorsLines(TRECWebOrderProcessHelper helper, EMCUserData userData);
    
    public TRECTrecCardsLines fetchTrecCardLine(Long trecCardRecordId, EMCUserData userData);
    
    public TRECWebOrderProcessHelper createDebtorsInvoice(TRECWebOrderProcessHelper helper, EMCUserData userData)throws EMCEntityBeanException;
    
    public boolean deleteBasketLine(TRECWebInvoiceHelper line,  EMCUserData userData);
    
    //public boolean emailCustomerInvoice(TRECWebOrderProcessHelper helper, String emailAddress, String attachment, EMCUserData userData) throws EMCEntityBeanException;

    public emc.entity.trec.TRECTrecCardsLines updateCardLines(emc.entity.trec.TRECTrecCardsLines lines, emc.framework.EMCUserData userData);

    
    public boolean emailCustomerInvoice(emc.helpers.trec.TRECWebOrderProcessHelper helper, java.lang.String emailAddress, java.lang.String attachment, boolean printInstruction, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

   
    public emc.helpers.trec.TRECWebOrderProcessHelper insertFetchTrecMasterLinesFromWeb(emc.helpers.trec.TRECWebOrderProcessHelper helper, java.lang.Long customerRecordId, java.lang.String UNNumber, java.lang.Long trecLineRecId, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

   
    public emc.helpers.trec.TRECWebOrderProcessHelper updateBasketLine(emc.helpers.trec.TRECWebOrderProcessHelper helper, emc.entity.debtors.DebtorsBasketLines newLine, emc.framework.EMCUserData userData);

    public boolean removeBasketLine(emc.entity.debtors.DebtorsBasketLines line, emc.framework.EMCUserData userData);

}

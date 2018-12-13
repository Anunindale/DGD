/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.gl.transactions.logic;

import emc.bus.gl.transactions.logic.debtors.DebtorsGLTransactionLogicLocal;
import emc.bus.gl.transactions.logic.gl.GLTransactionLogicLocal;
import emc.bus.gl.transactions.logic.posthelpers.GLTransactionPostHelper;
import emc.bus.gl.transactions.logic.posthelpers.debtors.DebtorsCreditNotePostHelper;
import emc.bus.gl.transactions.logic.posthelpers.debtors.DebtorsCustomerInvoicePostHelper;
import emc.bus.gl.transactions.logic.posthelpers.gl.GLJournalPostHelper;
import emc.bus.gl.transactions.logic.posthelpers.production.ProductionPickingListPostHelper;
import emc.bus.gl.transactions.logic.production.ProductionGLTransactionLogicLocal;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.functions.StopWatchFactory;
import emc.messages.ServerGLMessageEnum;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 *
 * @author riaan
 */
@Stateless
public class GLTransactionPostBean extends EMCBusinessBean implements GLTransactionPostLocal {

    @EJB
    private GLTransactionLogicLocal glTransactionBean;
    @EJB
    private ProductionGLTransactionLogicLocal productionTransactionBean;
    @EJB
    private DebtorsGLTransactionLogicLocal debtorsTransactionBean;
    @Resource
    protected TransactionSynchronizationRegistry registry;

    /**
     * Creates a new instance of GLTransactionPostBean.
     */
    public GLTransactionPostBean() {
    }

    /**
     * This method forwards requests for GL transaction posting to the relevant 
     * beans and returns the posting result.
     * @param type Indicates which posting routine should be called.
     * @param postHelper Helper class containing parameters for post.  This can be
     * any class which implements the GLTransactionPostHelper instance.
     * @param userData User data.
     * @return A boolean indicating that posting was successful.
     * @throws EMCEntityBeanException If posting fails.
     */
    public boolean postTransactions(GLTransactionPostType type, GLTransactionPostHelper postHelper, EMCUserData userData) throws EMCEntityBeanException {
        try {
            boolean success;
            switch (type.getModule()) {
                case GENERAL_LEDGER:
                    success = postGLTransactions(type, postHelper, userData);
                    break;
                case DEBTORS:
                    success = postDebtorsTransactions(type, postHelper, userData);
                    break;
                case PRODUCTION:
                    success = postProductionTransactions(type, postHelper, userData);
                    break;
                    
                default:
                    throw new EMCEntityBeanException("Module not mapped for posting: " + type.getModule());
            }

            if (postHelper.getTransactions().isEmpty()) {
                throw new EMCEntityBeanException(getMessage(ServerGLMessageEnum.NO_TRANSACTIONS_FOUND, userData));
            } else {
                StopWatchFactory fact = new StopWatchFactory(true);
                for (GLTransactionsDetail detail : postHelper.getTransactions()) {
                    fact.start("consol");
                    glTransactionBean.consolidateTransaction(registry.getTransactionKey(), detail, userData);
                    System.out.println("Consolidated transaction: " + fact.stop("consol") + " ms.");
                }
            }

            return success;
        } catch (GLTransactionException ex) {
            throw new EMCEntityBeanException(ex);
        }
    }

    /**
     * Posts GL transactions for source data in the GL module.
     * @param type Indicates which posting routine should be called.
     * @param postHelper Helper class containing parameters for post.  This can be
     * any class which implements the GLTransactionPostHelper instance.
     * @param userData User data.
     * @return A boolean indicating whether transactions were posted succesfully.
     */
    private boolean postGLTransactions(GLTransactionPostType type, GLTransactionPostHelper postHelper, EMCUserData userData) throws GLTransactionException {
        switch (type) {
            case POST_GL_JOURNAL:
                return glTransactionBean.postGLJournal((GLJournalPostHelper) postHelper, userData);
            default:
                throw new GLTransactionException("Module not mapped for posting: " + type.getModule());
        }
    }

    /**
     * Posts GL transactions for source data in the debtors module.
     * @param type Indicates which posting routine should be called.
     * @param postHelper Helper class containing parameters for post.  This can be
     * any class which implements the GLTransactionPostHelper instance.
     * @param userData User data.
     * @return A boolean indicating whether transactions were posted succesfully.
     */
    private boolean postDebtorsTransactions(GLTransactionPostType type, GLTransactionPostHelper postHelper, EMCUserData userData) throws GLTransactionException {
        switch (type) {
            case POST_DEBTORS_CREDIT_NOTE:
                return debtorsTransactionBean.postCreditNote((DebtorsCreditNotePostHelper) postHelper, userData);
            case POST_DEBTORS_CUSTOMER_INVOICE:
                return debtorsTransactionBean.postCustomerInvoice((DebtorsCustomerInvoicePostHelper) postHelper, userData);
            default:
                throw new GLTransactionException("Module not mapped for posting: " + type.getModule());
        }
    }

    /**
     * Posts GL transactions for source data in the production module.
     * @param type Indicates which posting routine should be called.
     * @param postHelper Helper class containing parameters for post.  This can be
     * any class which implements the GLTransactionPostHelper instance.
     * @param userData User data.
     * @return A boolean indicating whether transactions were posted succesfully.
     */
    private boolean postProductionTransactions(GLTransactionPostType type, GLTransactionPostHelper postHelper, EMCUserData userData) throws GLTransactionException {
        switch (type) {
            case POST_PRODUCTION_PICKING_LIST:
                return productionTransactionBean.postProductionPickingList((ProductionPickingListPostHelper) postHelper, userData);
            default:
                throw new GLTransactionException("Module not mapped for posting: " + type.getModule());
        }
    }
}

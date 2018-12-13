/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.commandmanager;

import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface EMCCommandManagerLocal {
    public java.lang.String executeCommand(java.lang.String toDo, emc.framework.EMCUserData userData);
    public byte[] executeCommandByte(byte[] toDo, emc.framework.EMCUserData userData);
    public java.util.List mapCommand(emc.framework.EMCCommandClass cmd, java.util.List<java.lang.Object> dataList, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
    //special for transactions 
    public java.util.List getActiveTransactions();
    /**
     * Add queries to execute if transaction succeeds.
     * Only supports update and delete
     * Command manager executes these automatically for the transaction that came into the container.
     * IF you use this mechanism in a NEW transaction you must do the cleanup yourself.
     * @param txInfo - transaction object
     * @param query
     */
    public void setTransactionSucceedQuery(java.lang.Object txInfo, java.lang.String query);
    /**
     * Add failure queries to execute if transaction fails.
     * Only supports update and delete
     * Command manager executes these automatically for the transaction that came into the container.
     * IF you use this mechanism in a NEW transaction you must do the cleanup yourself.
     * @param txInfo - transaction object
     * @param query
     */
    public void setTransactionFailQuery(java.lang.Object txInfo, java.lang.String query);
    /**
     * Only call this method internal to the Command manager
     * @param qToEx
     */
    @javax.ejb.TransactionAttribute(value = javax.ejb.TransactionAttributeType.REQUIRES_NEW)
    public void executeFailQueries(java.util.List<java.lang.String> qToEx);
}

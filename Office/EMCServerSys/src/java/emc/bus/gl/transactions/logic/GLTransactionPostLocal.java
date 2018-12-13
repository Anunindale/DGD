/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.transactions.logic;

import emc.bus.gl.transactions.logic.posthelpers.GLTransactionPostHelper;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface GLTransactionPostLocal {

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
    public boolean postTransactions(GLTransactionPostType type, GLTransactionPostHelper postHelper, EMCUserData userData) throws EMCEntityBeanException;
}

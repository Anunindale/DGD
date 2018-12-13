/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.framework;

import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface EMCMultiProcessingLocal {

    public int reserveThreads(int threadCount, EMCUserData userData);

    public void unreserveThreads(int threadCount, EMCUserData userData);

    public void setThreadVariables(EMCUserData userData);
}

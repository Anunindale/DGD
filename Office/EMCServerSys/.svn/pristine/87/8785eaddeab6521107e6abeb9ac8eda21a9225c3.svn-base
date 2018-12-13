/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.batchconsolidation;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface InventoryBatchConsolidationMasterLocal extends EMCEntityBeanLocalInterface {

    public boolean createBatchConsolidation(String consolidationNumber, String warehouse, String location, String productGroup, BigDecimal consolidationCrateQty, BigDecimal maxCrateQty, int maxNumberOfCrates, EMCUserData userData) throws EMCEntityBeanException;

    public java.util.List<java.lang.String> getMovementJournalsForConsolidationApproval(java.lang.String consolidationNumber, emc.framework.EMCUserData userData);

    public java.util.List<java.lang.String> getTransferJournalsForConsolidationApproval(java.lang.String consolidationNumber, emc.framework.EMCUserData userData);

    public boolean approveConsolidation(java.lang.String consolidationNumber, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List<java.lang.String> getTransferJournalsForConsolidationPosting(java.lang.String consolidationNumber, emc.framework.EMCUserData userData);

    public java.util.List<java.lang.String> getMovementJournalsForConsolidationPosting(java.lang.String consolidationNumber, emc.framework.EMCUserData userData);

    public boolean postConsolidation(java.lang.String consolidationNumber, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List<java.lang.String> getMovementJournalsForConsolidationUnApproval(java.lang.String consolidationNumber, emc.framework.EMCUserData userData);

    public java.util.List<java.lang.String> getTransferJournalsForConsolidationUnApproval(java.lang.String consolidationNumber, emc.framework.EMCUserData userData);

    public boolean unApproveConsolidation(java.lang.String consolidationNumber, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}

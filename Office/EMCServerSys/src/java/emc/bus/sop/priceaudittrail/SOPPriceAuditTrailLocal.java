/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.priceaudittrail;

import emc.framework.EMCEntityBeanLocalInterface;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPPriceAuditTrailLocal extends EMCEntityBeanLocalInterface {

    public void logApproval(emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType logType, emc.framework.EMCEntityClass recordOne, emc.framework.EMCEntityClass recordTwo, Date logDate, Date logTime, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void logChanges(emc.enums.sop.priceaudittrail.SOPPriceAuditTrailType logType, emc.entity.sop.SOPPriceArangements priceLine, emc.entity.sop.SOPPriceArangements persistedPriceLine, Date logDate, Date logTime, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void populateApprovalHistory(java.util.Date fromDate, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public void populateChangeHistory(java.util.Date fromDate, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;
}

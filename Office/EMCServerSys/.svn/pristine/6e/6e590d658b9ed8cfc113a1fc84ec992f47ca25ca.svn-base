/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesenquiry;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPSalesEnquiryLocal extends EMCEntityBeanLocalInterface {

    public java.util.List<emc.entity.gl.GLFinancialPeriods> validatePeriodSelection(emc.helpers.sop.SalesEnquiryHelper helper, emc.framework.EMCUserData userData);

    public void populateSalesEnquiry(emc.entity.gl.GLFinancialPeriods period, int columnCount, emc.helpers.sop.SalesEnquiryHelper helper, emc.framework.EMCUserData userData);

    public void cleanupSalesEnquiry(String userId, boolean clearAll, EMCUserData userData) throws EMCEntityBeanException;
}

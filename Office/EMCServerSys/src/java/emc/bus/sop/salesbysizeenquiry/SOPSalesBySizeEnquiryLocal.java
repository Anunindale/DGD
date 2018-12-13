/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesbysizeenquiry;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import emc.helpers.sop.SOPSalesBySizeEnquiryHelper;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface SOPSalesBySizeEnquiryLocal extends EMCEntityBeanLocalInterface {

    public Map populateSalesBySizeEnquiry(SOPSalesBySizeEnquiryHelper helper, EMCUserData userData);

    public void clearSalesBySize(String userDataId, EMCUserData userData);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.discountsetup;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface SOPDiscountSetupLocal extends EMCEntityBeanLocalInterface {

    public BigDecimal getDiscountPercentage(String customerId, String itemId, String dimension1, String dimension2, String dimension3, Date lineDate, EMCUserData userData);
}

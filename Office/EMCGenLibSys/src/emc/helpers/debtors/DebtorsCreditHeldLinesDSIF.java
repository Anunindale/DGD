/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.helpers.debtors;

import java.math.BigDecimal;

/**
 * Helper interface for records displayed on the Credit Held form in the Debtors module.
 * @author riaan
 */
public interface DebtorsCreditHeldLinesDSIF {

    public BigDecimal getTotalHeld();
    public void setTotalHeld(BigDecimal totalHeld);

    public boolean isVATIncluded();
    public void setVATIncluded(boolean vatIncluded);
}

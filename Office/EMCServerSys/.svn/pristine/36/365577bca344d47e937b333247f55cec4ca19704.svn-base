package emc.bus.debtors.creditstopreason;

import emc.entity.debtors.DebtorsCreditStopReason;
import emc.enums.debtors.creditstopreasons.DebtorsCreditStopReasons;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import emc.tables.EMCTable;
import java.util.logging.Level;
import javax.ejb.Stateless;

/**
 * 
 * @Author wikus
 */
@Stateless
public class DebtorsCreditStopReasonBean extends EMCEntityBean implements DebtorsCreditStopReasonLocal {

    /** Creates a new instance of DebtorsCreditStopReasonBean. */
    public DebtorsCreditStopReasonBean() {
        
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
    
        if (ret) {
            DebtorsCreditStopReason reason = (DebtorsCreditStopReason)vobject;
            if (checkSystemReason(reason.getReason(), userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.SYSTEM_REASON, userData, reason.getReason());
                return false;
            }
        }
        
        return ret;
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doDeleteValidation(vobject, userData);

        if (ret) {
            DebtorsCreditStopReason reason = (DebtorsCreditStopReason)vobject;
            if (checkSystemReason(reason.getReason(), userData)) {
                logMessage(Level.SEVERE, ServerDebtorsMessageEnum.SYSTEM_REASON, userData, reason.getReason());
                return false;
            }
        }

        return ret;
    }

    /**
     * Returns a boolean indicating whether the specified reason code is a system reason code.
     * System reason codes may not be inserted, deleted or updated by the user.
     * @param reasonCode Reason code to check.
     * @param userData User data.
     * @return A boolean indicating whether the specified reason code is a system reason code.
     */
    private boolean checkSystemReason(String reasonCode, EMCUserData userData) {
        return DebtorsCreditStopReasons.fromString(reasonCode) != null;
    }
}

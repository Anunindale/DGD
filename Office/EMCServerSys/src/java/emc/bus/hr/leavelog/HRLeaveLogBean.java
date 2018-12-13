/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.hr.leavelog;

import emc.entity.hr.HRLeaveLog;
import emc.framework.EMCEntityBean;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class HRLeaveLogBean extends EMCEntityBean implements HRLeaveLogLocal {

    @Override
    public boolean doFieldValidation(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = super.doFieldValidation(fieldNameToValidate, theRecord, userData);
        if (ret) {
            HRLeaveLog record = (HRLeaveLog) theRecord;
            if (fieldNameToValidate.equals("startDate")) {
                if (!isBlank(record.getStartDate()) && !isBlank(record.getReturnDate())) {
                    if (!record.getStartDate().before(record.getReturnDate())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The start date can`t be before the return date.", userData);
                        return false;
                    }

                }
            } else if (fieldNameToValidate.equals("returnDate")) {
                if (!isBlank(record.getStartDate()) && !isBlank(record.getReturnDate())) {
                    if (!record.getReturnDate().after(record.getStartDate())) {
                        Logger.getLogger("emc").log(Level.SEVERE, "The return date can`t be after the start date.", userData);
                        return false;
                    }
                }
            }
        }
        return ret;
    }
}

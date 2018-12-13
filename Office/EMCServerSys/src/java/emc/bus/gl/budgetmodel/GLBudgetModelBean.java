package emc.bus.gl.budgetmodel;

import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.entity.gl.GLBudgetModel;
import emc.entity.gl.GLFinancialPeriods;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.messages.ServerGLMessageEnum;
import emc.tables.EMCTable;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 *
 * @author claudette
 */
@Stateless
public class GLBudgetModelBean extends EMCEntityBean implements GLBudgetModelLocal {

    @EJB
    private GLFinancialPeriodsLocal financialBean;

    /** Creates a new instance of GLBudgetModelBean. */
    public GLBudgetModelBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean result = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        GLBudgetModel model = (GLBudgetModel) theRecord;
        if (fieldNameToValidate.equals("toPeriod") || fieldNameToValidate.equals("startPeriod")) {
            result = result && validatePeriods(model.getFromPeriod(), model.getToPeriod(), userData);
        }
        return result;
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        GLBudgetModel model = (GLBudgetModel) vobject;
        boolean result = super.doInsertValidation(vobject, userData);
        result = result && validatePeriods(model.getFromPeriod(), model.getToPeriod(), userData);
        return result;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        GLBudgetModel model = (GLBudgetModel) vobject;
        boolean result = super.doUpdateValidation(vobject, userData);
        result = result && validatePeriods(model.getFromPeriod(), model.getToPeriod(), userData);
        return result;
    }

    private boolean validatePeriods(String fromPeriod, String toPeriod, EMCUserData userData) {
        if (!isBlank(fromPeriod) && !isBlank(toPeriod)) {
            GLFinancialPeriods fPeriod = financialBean.findFinancialPeriod(toPeriod, userData);
            GLFinancialPeriods tPeriod = financialBean.findFinancialPeriod(fromPeriod, userData);

            if (dateHandler.compareDatesIgnoreTime(tPeriod.getEndDate(), fPeriod.getEndDate(), userData) >= 0) {
                this.logMessage(Level.SEVERE, ServerGLMessageEnum.PERIOD_INVALID, userData);
                return false;
            }

        }
        return true;
    }
}
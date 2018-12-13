package emc.bus.gl.budgetperiod;

import emc.entity.gl.GLBudgetLines;
import emc.entity.gl.GLBudgetModel;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import javax.ejb.Stateless;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.messages.ServerGLMessageEnum;
import java.math.BigDecimal;
import java.util.logging.Level;
import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.entity.gl.GLBudgetPeriod;
import emc.entity.gl.GLFinancialPeriods;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/** 
 *
 * @author claudette
 */
@Stateless
public class GLBudgetPeriodBean extends EMCEntityBean implements GLBudgetPeriodLocal {

    @EJB
    private GLFinancialPeriodsLocal financialBean;
    @EJB
    private GLBudgetPeriodLocal periodBean;

    /** Creates a new instance of GLBudgetPeriodBean. */
    public GLBudgetPeriodBean() {
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (ret) {
            GLBudgetPeriod period = (GLBudgetPeriod) theRecord;
            if (fieldNameToValidate.equals("budgetPeriod")) {
                return validatePeriod(period.getBudgetPeriod(), period.getLineRecordId(), userData);
            }
        }
        return ret;
    }
    /**
     *
     * @param lineRecordId
     * @param amount
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     * This method gets the amount the user typed in and then divides it evenly over the amount of month in the period selected.
     */
    public boolean splitAmounts(long lineRecordId, BigDecimal amount, EMCUserData userData) throws EMCEntityBeanException {
        deleteData(lineRecordId, userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLBudgetModel.class);
        query.addTableAnd(GLBudgetLines.class.getName(), "modelId", GLBudgetModel.class.getName(), "modelId");
        query.addAnd("recordID", lineRecordId, GLBudgetLines.class.getName());
        query.addField("toPeriod", GLBudgetModel.class.getName());
        query.addField("fromPeriod", GLBudgetModel.class.getName());

        Object[] result = (Object[]) util.executeSingleResultQuery(query, userData);

        if (result == null) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.NO_PERIOD, userData);
            return false;
        }
        String toPeriod = (String) result[0];
        String fromPeriod = (String) result[1];

        List<GLFinancialPeriods> periods = financialBean.findAllPeriodsBetween(toPeriod, fromPeriod, userData);
        amount = amount.divide(new BigDecimal(periods.size()), new MathContext(21, RoundingMode.HALF_UP));

        if (periods.size() == 0) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.NO_PERIOD, userData);
            return false;
        }
        for (GLFinancialPeriods period : periods) {
            GLBudgetPeriod budgetPeriod = new GLBudgetPeriod();
            budgetPeriod.setLineRecordId(lineRecordId);
            budgetPeriod.setBudgetPeriod(period.getPeriodId());
            budgetPeriod.setAmount(amount);
            periodBean.insert(budgetPeriod, userData);
        }
        return true;
    }

    /**
     * 
     * @param lineRecord
     * @param userData
     * @return
     * This method deletes any previous amounts split over a certain amount of periods.
     */
    public boolean deleteData(long lineRecord, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.DELETE, GLBudgetPeriod.class);
        query.addAnd("lineRecordId", lineRecord);
        return util.executeUpdate(query, userData);
    }

    /**
     * 
     * @param periodId
     * @param lineRecordId
     * @param userData
     * @return
     * This method validates whether or not a period chosen fall within the period selected for the budget.
     */
    public boolean validatePeriod(String periodId, long lineRecordId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLBudgetModel.class);
        query.addTableAnd(GLBudgetLines.class.getName(), "modelId", GLBudgetModel.class.getName(), "modelId");
        query.addAnd("recordID", lineRecordId, GLBudgetLines.class.getName());
        query.addField("toPeriod", GLBudgetModel.class.getName());
        query.addField("fromPeriod", GLBudgetModel.class.getName());

        Object[] result = (Object[]) util.executeSingleResultQuery(query, userData);

        if (result == null) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.NO_PERIOD, userData);
            return false;
        }
        String toPeriod = (String) result[0];
        String fromPeriod = (String) result[1];

        query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("periodId", fromPeriod);
        query.addField("startDate");

        Date endDate = (Date) util.executeSingleResultQuery(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("periodId", toPeriod);
        query.addField("startDate");

        Date startDate = (Date) util.executeSingleResultQuery(query, userData);

        query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("periodId", periodId);
        query.addField("startDate");

        Date currentDate = (Date) util.executeSingleResultQuery(query, userData);

        if (dateHandler.compareDatesIgnoreTime(currentDate, startDate, userData) >= 0 && dateHandler.compareDatesIgnoreTime(currentDate, endDate, userData) <= 0) {
            return true;
        } else {
            logMessage(Level.SEVERE, ServerGLMessageEnum.PERIOD_NOT_IN_RANGE, userData);
            return false;
        }
    }
}
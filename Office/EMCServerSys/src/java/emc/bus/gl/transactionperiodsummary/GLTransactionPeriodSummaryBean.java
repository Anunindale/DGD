package emc.bus.gl.transactionperiodsummary;

import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.gl.transactions.GLTransactionPeriodSummary;


import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.emcquery.EMCQueryOrderByDirections;
import emc.enums.enumQueryTypes;
import emc.enums.gl.FinancialPeriodTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerGLMessageEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 *
 * @author claudette
 */
@Stateless
public class GLTransactionPeriodSummaryBean extends EMCEntityBean implements GLTransactionPeriodSummaryLocal {

    @EJB
    private GLFinancialPeriodsLocal financialPeriodsBean;

            /** Creates a new instance of GLTransactionPeriodSummaryBean. */
    public GLTransactionPeriodSummaryBean() {
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        //Override getNumRows(), EMCQuery doesn't handle COUNT on group by queries 
        //well.
        EMCQuery query = (EMCQuery)userData.getUserData(0);
        return String.valueOf(util.executeGeneralSelectQuery(query, userData).size());
    }

    public void setUniqueKey(GLTransactionPeriodSummary summary, EMCUserData userData) {
        StringBuilder builder = new StringBuilder();
        builder.append(summary.getAccountNumber());
        builder.append(summary.getAnalysisCode1());
        builder.append(summary.getAnalysisCode2());
        builder.append(summary.getAnalysisCode3());
        builder.append(summary.getAnalysisCode4());
        builder.append(summary.getAnalysisCode5());
        builder.append(summary.getAnalysisCode6());
        builder.append(summary.getFinancialPeriod());

        summary.setUniqueKey(builder.toString());
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        GLTransactionPeriodSummary summary = (GLTransactionPeriodSummary) uobject;
        setUniqueKey(summary, userData);
        return super.update(uobject, userData);
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        GLTransactionPeriodSummary summary = (GLTransactionPeriodSummary) iobject;
        setUniqueKey(summary, userData);
        return super.insert(iobject, userData);
    }

    /**
     * Creates opening balances for all accounts with transactions falling
     * in the year before the specified opening period.
     * @param openingPeriodId Opening period for which balances should be saved.
     * @param userData User data.
     * @return A boolean indicating success.
     * @throws EMCEntityBeanException
     */
    public boolean generateOpeningBalances(String openingPeriodId, EMCUserData userData) throws EMCEntityBeanException {
        GLFinancialPeriods openingPeriod = financialPeriodsBean.findFinancialPeriod(openingPeriodId, userData);

        if (openingPeriod == null || FinancialPeriodTypes.fromString(openingPeriod.getPeriodType()) != FinancialPeriodTypes.OPENING) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.NOT_OPENING_PERIOD, userData);
            return false;
        }

        //Get last opening period (before this period)
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLFinancialPeriods.class);
        query.addAnd("periodType", FinancialPeriodTypes.OPENING.toString());
        query.addAnd("startDate", openingPeriod.getStartDate(), EMCQueryConditions.LESS_THAN);
        query.addOrderBy("startDate", GLFinancialPeriods.class.getName(), EMCQueryOrderByDirections.DESC);

        List<GLFinancialPeriods> openingPeriods = util.executeLimitedResultGeneralSelectQuery(query, 0, 1, userData);

        query= new EMCQuery(enumQueryTypes.SELECT, GLTransactionPeriodSummary.class);
        query.addField("accountNumber");    //0
        query.addField("analysisCode1");    //1
        query.addField("analysisCode2");    //2
        query.addField("analysisCode3");    //3
        query.addField("analysisCode4");    //4
        query.addField("analysisCode5");    //5
        query.addField("analysisCode6");    //6
        query.addFieldAggregateFunction("debit", "SUM");
        query.addFieldAggregateFunction("credit", "SUM");
        query.addTableAnd(GLFinancialPeriods.class.getName(), "financialPeriod", GLTransactionPeriodSummary.class.getName(), "periodId");
        query.addAnd("startDate", openingPeriod.getStartDate(), GLFinancialPeriods.class.getName(), EMCQueryConditions.LESS_THAN);
        query.addAnd("periodType", FinancialPeriodTypes.OPENING, GLFinancialPeriods.class.getName(), EMCQueryConditions.NOT);
        query.addGroupBy("accountNumber");
        query.addGroupBy("analysisCode1");
        query.addGroupBy("analysisCode2");
        query.addGroupBy("analysisCode3");
        query.addGroupBy("analysisCode4");
        query.addGroupBy("analysisCode5");
        query.addGroupBy("analysisCode6");

        if (openingPeriods.size() == 1) {
            //Select all periods from last opening period to new opening period.
            query.addAnd("startDate", openingPeriods.get(0).getStartDate(), GLFinancialPeriods.class.getName(), EMCQueryConditions.GREATER_THAN_EQ);
        }

        List<Object[]> periodBalances = (List<Object[]>)util.executeGeneralSelectQuery(query, userData);

        for (Object[] periodBalance : periodBalances) {
            GLTransactionPeriodSummary periodSummary = new GLTransactionPeriodSummary();
            periodSummary.setFinancialPeriod(openingPeriodId);
            periodSummary.setAccountNumber((String)periodBalance[0]);
            periodSummary.setAnalysisCode1((String)periodBalance[1]);
            periodSummary.setAnalysisCode2((String)periodBalance[2]);
            periodSummary.setAnalysisCode3((String)periodBalance[3]);
            periodSummary.setAnalysisCode4((String)periodBalance[4]);
            periodSummary.setAnalysisCode5((String)periodBalance[5]);
            periodSummary.setAnalysisCode6((String)periodBalance[6]);
            periodSummary.setDebit((BigDecimal)periodBalance[7]);
            periodSummary.setCredit((BigDecimal)periodBalance[8]);

            insert(periodSummary, userData);
        }

        return true;
    }
}
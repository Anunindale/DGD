package emc.bus.gl.transactiondaysummary;

import emc.entity.gl.transactions.GLTransactionDaySummary;
import emc.framework.EMCEntityBeanException;


import emc.framework.EMCEntityBean;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/** 
 *
 * @author claudette
 */
@Stateless
public class GLTransactionDaySummaryBean extends EMCEntityBean implements GLTransactionDaySummaryLocal {

    /** Creates a new instance of GLTransactionDaySummaryBean. */
    public GLTransactionDaySummaryBean() {
    }

    public void setUniqueKey(GLTransactionDaySummary summary, EMCUserData userData) {
        StringBuilder builder = new StringBuilder();
        builder.append(summary.getAccountNumber());
        builder.append(summary.getAnalysisCode1());
        builder.append(summary.getAnalysisCode2());
        builder.append(summary.getAnalysisCode3());
        builder.append(summary.getAnalysisCode4());
        builder.append(summary.getAnalysisCode5());
        builder.append(summary.getAnalysisCode6());
        builder.append(summary.getFinancialDate());

        summary.setUniqueKey(builder.toString());
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        GLTransactionDaySummary summary = (GLTransactionDaySummary) uobject;
        setUniqueKey(summary, userData);
        return super.update(uobject, userData);
    }
    
    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        GLTransactionDaySummary summary = (GLTransactionDaySummary) iobject;
        setUniqueKey(summary, userData);
        return super.insert(iobject, userData);
    }
}
package emc.bus.gl.transactionsdetail;

import emc.bus.gl.financialperiods.GLFinancialPeriodsLocal;
import emc.entity.gl.GLFinancialPeriods;
import emc.entity.gl.transactions.GLTransactionsDetail;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.messages.ServerGLMessageEnum;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 *
 * @author claudette
 */
@Stateless
public class GLTransactionsDetailBean extends EMCEntityBean implements GLTransactionsDetailLocal {

    @EJB
    private GLFinancialPeriodsLocal financialPeriodBean;

    /** Creates a new instance of GLTransactionsDetailBean. */
    public GLTransactionsDetailBean() {
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        GLTransactionsDetail detail = (GLTransactionsDetail) iobject;

        setGroupFields(detail, userData);
        
        return super.insert(iobject, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        GLTransactionsDetail detail = (GLTransactionsDetail) uobject;

        GLTransactionsDetail persisted = (GLTransactionsDetail)util.findDetachedPersisted(detail, userData);
        setGroupFields(detail, userData);

        return super.insert(uobject, userData);
    }
    /**
     * Sets grouping fields (period id and week number) on the specified transaction.
     * @param detail Transaction on which fields should be set.
     * @param userData User data.
     * @throws EMCEntityBeanException
     */
    private void setGroupFields(GLTransactionsDetail detail, EMCUserData userData) throws EMCEntityBeanException {
        detail.setGroupWeek(dateHandler.getWeekNumber(detail.getTransactionDate(), userData));
        GLFinancialPeriods period = financialPeriodBean.findPeriodForDate(detail.getTransactionDate(), userData);
        if (period != null) {
            detail.setGroupPeriod(period.getPeriodId());
        } else {
            throw new EMCEntityBeanException(getMessage(ServerGLMessageEnum.NO_PERIOD_FOR_DATE, userData, dateHandler.date2String(detail.getTransactionDate())));
        }
    }
}
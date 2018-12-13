package emc.bus.gl.budgetlines;

import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import javax.ejb.Stateless;
import emc.entity.gl.GLBudgetLines;
import emc.entity.gl.GLChartOfAccounts;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerGLMessageEnum;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;

/** 
 *
 * @author claudette
 */
@Stateless
public class GLBudgetLinesBean extends EMCEntityBean implements GLBudgetLinesLocal {

    @EJB
    GLBudgetLinesLocal linesBean;

    /** Creates a new instance of GLBudgetLinesBean. */
    public GLBudgetLinesBean() {
    }

    /**
     *
     * @param modelId
     * @param accountId
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     * This method gets all the account entered by the user and then adds it to a model.
     */
    public boolean getAccounts(String modelId, String accountId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLChartOfAccounts.class);
        query.addField("accountNumber", GLChartOfAccounts.class.getName());
        query.addAndCommaSeperated("accountNumber", accountId, GLChartOfAccounts.class.getName(), EMCQueryConditions.EQUALS);

        List<String> result = util.executeGeneralSelectQuery(query, userData);

        if (result == null || result.isEmpty()) {
            logMessage(Level.SEVERE, ServerGLMessageEnum.INVALID_ACCOUNT, userData);
            return false;
        }

        query = new EMCQuery(enumQueryTypes.SELECT, GLBudgetLines.class);
        query.addFieldAggregateFunction("lineNumber", "MAX");
        query.addAnd("modelId", modelId);

        Double lineNumber = (Double) util.executeSingleResultQuery(query, userData);

        if (lineNumber == null) {
            lineNumber = 10.0;
        } else {
            lineNumber = lineNumber + 10;
        }

        for (String account : result) {
            GLBudgetLines budgetLines = new GLBudgetLines();
            budgetLines.setModelId(modelId);
            budgetLines.setLineNumber(lineNumber);
            budgetLines.setAccount(account);
            linesBean.insert(budgetLines, userData);
            lineNumber = lineNumber + 10;
        }
        return true;
    }
}
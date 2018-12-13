/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.royaltysetup;

import emc.bus.debtors.parameters.DebtorsParametersLocal;
import emc.entity.debtors.DebtorsParameters;
import emc.entity.debtors.DebtorsRoyaltySetup;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerDebtorsMessageEnum;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsRoyaltySetupBean extends EMCEntityBean implements DebtorsRoyaltySetupLocal {

    @EJB
    private DebtorsParametersLocal parameterBean;

    /** Creates a new instance of SOPRoyaltySetupBean. */
    public DebtorsRoyaltySetupBean() {
    }

    /** Returns an object array containing the fields names of the royalty fields set up on DebtorsParameters.
     * 
     * @param userData User data.
     * @return An object array containing the fields names of the royalty fields set up on DebtorsParameters.
     */
    public Object[] getRoyaltyFields(EMCUserData userData) {
        Object[] fields = new Object[3];

        DebtorsParameters parameters = parameterBean.getDebtorsParameters(userData);

        if (parameters == null) {
            logMessage(Level.SEVERE, ServerDebtorsMessageEnum.PARAM_NOT_FOUND, userData);
        } else {
            fields[0] = parameters.getRoyaltyField1();
            fields[1] = parameters.getRoyaltyField2();
            fields[2] = parameters.getRoyaltyField3();
        }

        return fields;
    }

    /**
     * Checks whether royalty records have been set up for the company specified
     * in userData.
     * @param userData User data.
     * @return A boolean indicating whether royalties have been set up in the
     *          current company.
     */
    public boolean checkRoyaltySetupExists(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsRoyaltySetup.class);
        query.addFieldAggregateFunction("recordID", "COUNT");
        
        Long rowCount = (Long)util.executeSingleResultQuery(query, userData);

        return rowCount != null && rowCount > 0;
    }
}

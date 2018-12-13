/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.vatcodes;

import emc.bus.gl.parameters.GLParametersLocal;
import emc.entity.gl.GLParameters;
import emc.entity.gl.GLVATCode;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */  
@Stateless
public class GLVATCodeBean extends EMCEntityBean implements GLVATCodeLocal {
 
    @EJB
    private GLParametersLocal parametersBean;
    
    /** Creates a new instance of GLVATCodeBean */
    public GLVATCodeBean() {
        
    }
    
    /** Returns the VAT percentage associated with a VAT code. */
    public double getVatPercentage(String vatCode, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLVATCode.class.getName());
        query.addAnd("vatId", vatCode);
        query.addField("percentage");
        
        Object ret = util.executeSingleResultQuery(query, userData);
        
        if (ret == null) {
            Logger.getLogger("emc").log(Level.WARNING, "VAT code does not exist", userData);
            return 0;
        } else {
            return (Double)ret;
        }
    }

    /**
     * Returns the VAT input account for the specified account.
     * @param accountNumber Account number.
     * @param userData User data.
     * @return The account number of the VAT input account for the specified account.
     */
    public String getVATInputAccount(String accountNumber, EMCUserData userData) {
        GLParameters parameters = parametersBean.getGLParameters(userData);
        if (parameters != null) {
            return parameters.getVatInputAccount();
        } else {
            return null;
        }
    }

     /**
     * Returns the VAT outut account for the specified account.
     * @param accountNumber Account number.
     * @param userData User data.
     * @return The account number of the VAT input account for the specified account.
     */
    public String getVATOutputAccount(String accountNumber, EMCUserData userData) {
        GLParameters parameters = parametersBean.getGLParameters(userData);
        if (parameters != null) {
            return parameters.getVatOutputAccount();
        } else {
            return null;
        }
    }
}

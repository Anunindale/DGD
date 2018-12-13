/*
 * BaseCompanyLocal.java
 *
 * Created on 04 December 2007, 08:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.entity.base.BaseCompanyTable;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface BaseCompanyLocal extends EMCEntityBeanLocalInterface {

    //boolean exists(String _companyId, EMCUserData userData);
    //long noOfCompanies(EMCUserData userData);
    public BaseCompanyTable getUserCompany(EMCUserData userData);

    /**
     * Returns a fax number for the specified user id.  This method accepts user
     * id as a parameter in order to allow the system to fetch a fax number for
     * any user, not just the active user.
     *
     * The system will first attempt to find an employee record for the specified
     * user id.  If an employee record is found and the fax number specified on
     * the record is not blank, the fax number on the record will be returned.
     * If no fax number is found for the user, the system will attempt to find
     * a fax number for the specified module (may be stored on the parameter
     * table for that module; e.g. POPParameters, SOPParameters).  If no fax
     * number can be found for the specified module, the system will find the
     * active company using the company in userData and return the fax number
     * specified on the company record, even if the fax number is blank.
     *
     * @param userId User for which a fax number should be fetched.
     * @param module Module requesting a fax number.  (Fax numbers may differ
     * between departments.)
     * @param userData User data.
     * @return A fax number for the specified user, module and company.
     */
    public String getFaxNumber(String userId, enumEMCModules module, EMCUserData userData);

    public boolean createCompanyLogoRef(long recordID, EMCUserData userData) throws EMCEntityBeanException;

    public boolean createLetterHead(long recordID, EMCUserData userData) throws EMCEntityBeanException;
}

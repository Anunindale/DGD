/* 
 * BaseCompanyBean.java
 *
 * Created on 04 December 2007, 08:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.bus.pop.POPParametersLocal;
import emc.bus.sop.parameters.SOPParametersLocal;
import emc.entity.base.BaseCompanyTable;
import emc.entity.base.BaseDocuRefTable;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.pop.POPParameters;
import emc.entity.sop.SOPParameters;
import emc.enums.base.docref.DocRefSummary;
import emc.enums.basetables.docuref.DocurefTypes;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;

/**
 *
 * @author rico
 */
@Stateless
public class BaseCompanyBean extends EMCEntityBean implements BaseCompanyLocal {

    @EJB
    private BaseLicenceLocal baseLicenceBean;
    @EJB
    private BaseEmployeeLocal employeeBean;
    @EJB
    private SOPParametersLocal sopParamsBean;
    @EJB
    private POPParametersLocal popParamsBean;
    @EJB
    private BaseDocRefLocal docRefBean;
    @PersistenceContext
    private EntityManager em;

    /** Creates a new instance of BaseCompanyBean */
    public BaseCompanyBean() {
        this.setCompanySuperTable(true);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean allowPersist = true;
        allowPersist = super.doInsertValidation(vobject, userData);

        BaseCompanyTable toBeTested = (BaseCompanyTable) vobject;

        if (allowPersist && baseLicenceBean.findDefaultCompany().NumberOfCompanies() <= this.noOfCompanies(userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The number of companies exceed the number of companies purchased.", userData);
            allowPersist = false;
        }

        return allowPersist;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {

        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        BaseCompanyTable toBeTested = (BaseCompanyTable) theRecord;

        if (baseLicenceBean.findDefaultCompany().NumberOfCompanies() <= this.noOfCompanies(userData) && toBeTested.getRecordID() == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "The number of companies exceed the number of companies purchased.", userData);
            ret = false;
        }

        if (fieldNameToValidate.equals("companyName")) {
            if ((toBeTested.getCompanyName() == null) || (toBeTested.getCompanyName().equals(""))) {
                Logger.getLogger("emc").log(Level.SEVERE, "Company Name may not be empty", userData);
                ret = false;
            }
        }

        if (fieldNameToValidate.equals("companyId")) {
            if (toBeTested.getCompanyId() == null || toBeTested.getCompanyId().equals("")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Company Id may not be empty.", userData);
                ret = false;
            } else if (this.exists(toBeTested.getCompanyId(), userData) == true) {
                Logger.getLogger("emc").log(Level.SEVERE, "The company Id specified already exists.", userData);
                ret = false;
            }
        }
        return ret;
    }

    public boolean exists(String _companyId, EMCUserData userData) {
        Query q = em.createQuery("SELECT c.companyId FROM BaseCompanyTable c WHERE c.companyId = '" + _companyId + "'");
        if (q.getResultList().size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public long noOfCompanies(EMCUserData userData) {
        Query q = em.createQuery("SELECT count(c) FROM BaseCompanyTable c");
        Iterator results = q.getResultList().iterator();
        if (results.hasNext()) {
            Object row = results.next();
            long count = (Long) row;
            return count;
        } else {
            return 0;
        }
    }

    /**
     * Returns the given users' company.
     *
     * @param userData User data.
     * @return
     */
    public BaseCompanyTable getUserCompany(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, emc.entity.base.BaseCompanyTable.class.getName());
        query.addAnd("companyId", userData.getCompanyId());

        return (BaseCompanyTable) util.executeSingleResultQuery(query, userData);

    }

    @Override
    protected String getQuery(Class theTable, int pos, EMCUserData userData) {
        EMCQuery query = null;
        if (userData.getUserData() == null || userData.getUserData().size() == 0) {
            query = new EMCQuery(enumQueryTypes.SELECT, theTable.getName());
        } else if (userData.getUserData(0) instanceof EMCQuery) {
            query = (EMCQuery) userData.getUserData(0);

            //TODO: Hack. If the company form was opened via a related form, companyId will be in the where clause twice.
            String queryString = query.toString();
            queryString = queryString.substring(queryString.indexOf("companyId") + "companyId".length());
            if (queryString.contains("companyId")) {
                return pos == 0 ? query.toString() : query.getCountQuery().toString();
            }

            query.removeAnd("companyId");
        } else {
            String stringQuery = (String) userData.getUserData(pos);
            return stringQuery;
        }
        if (pos == 0) {
            return query.toString();
        } else {
            return query.getTableInfoQuery();
        }
    }

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
    public String getFaxNumber(String userId, enumEMCModules module, EMCUserData userData) {
        //Check employee
        BaseEmployeeTable employee = employeeBean.findEmployeeRecord(userId, userData);

        if (employee != null && !isBlank(employee.getFaxNumber())) {
            return employee.getFaxNumber();
        }

        //Check module
        switch (module) {
            case DEBTORS:  //Fall through to Sales Orders
            case SALESORDERPROS:
                SOPParameters sopParams = sopParamsBean.getParameterRecord(userData);
                if (sopParams != null && !isBlank(sopParams.getFaxNumber())) {
                    return sopParams.getFaxNumber();
                } else {
                    //Continue to company
                    break;
                }
            case POP:
                POPParameters popParams = popParamsBean.getPOPParameters(userData);
                if (popParams != null && !isBlank(popParams.getFaxNumber())) {
                    return popParams.getFaxNumber();
                } else {
                    //Continue to company
                    break;
                }
            default:
                //Continue to company
                break;
        }

        //Get company fax number
        BaseCompanyTable company = getUserCompany(userData);

        return company.getCoFaxNr();
    }

    /**
     * This method will create a new line in the document handler. This line will have a description Company Logo and
     * will allow the user to attach a new file.
     * @param recordID
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean createCompanyLogoRef(long recordID, EMCUserData userData) throws EMCEntityBeanException {
        docRefBean.createSpecificAttachment(recordID,DocRefSummary.LOGO.toString(),BaseCompanyTable.class.getSimpleName(),userData);
        return true;
    }

    /**
     * This method will create a new line in the document handler. This line will have a description Company Letter Head and
     * will allow the user to attach a new file.
     * @param recordID
     * @param userData
     * @return
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean createLetterHead(long recordID, EMCUserData userData) throws EMCEntityBeanException {
        docRefBean.createSpecificAttachment(recordID,"Letter Head",BaseCompanyTable.class.getSimpleName(),userData);
        return true;
    }
}

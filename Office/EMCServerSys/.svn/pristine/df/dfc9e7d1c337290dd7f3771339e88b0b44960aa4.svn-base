/*   
 * BaseEmployeeBean.java
 *
 * Created on 11 December 2007, 03:46 
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.bus.base.employeecategoryhistory.BaseEmployeeCategoryHistoryLocal;
import emc.bus.base.systemtables.SystemTableLogicLocal;
import emc.bus.base.webportal.BaseWebPortalUsersLocal;
import emc.bus.hr.internalemploymenthistory.HRInternalEmploymentHistoryLocal;
import emc.bus.hr.terminationlog.HRTerminationLogLocal;
import emc.constants.systemConstants;
import emc.entity.base.BaseEmployeeCategoryHistory;
import emc.entity.base.BaseEmployeeTable;
import emc.entity.base.employeeaccessgroup.BaseEmployeeAccessGroup;
import emc.entity.base.employeeaccessgroupemployees.BaseEmployeeAccessGroupEmployees;
import emc.entity.base.webportal.BaseWebPortalUsers;
import emc.entity.hr.HRInternalEmploymentHistory;
import emc.entity.hr.HROFOCodes;
import emc.entity.hr.HRTerminationLog;
import emc.entity.hr.HRTerminationType;
import emc.entity.workflow.WorkFlowDepartment;
import emc.entity.workflow.WorkFlowJobTitles;
import emc.enums.base.webportalusers.WebPortalUsersReferenceType;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.helpers.base.EMCEmail;
import emc.messages.ServerBaseMessageEnum;
import emc.server.mailmanager.EMCMailManagerLocal;
import emc.server.utility.EMCServerUtilityLocal;
import emc.tables.EMCTable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rico
 */
@Stateless
public class BaseEmployeeBean extends EMCEntityBean implements BaseEmployeeLocal {
    
    @EJB
    private EMCServerUtilityLocal eMCServerUtility;
    @EJB
    private SystemTableLogicLocal systemTableBean;
    @EJB
    private HRTerminationLogLocal terminationBean;
    @EJB
    private HRInternalEmploymentHistoryLocal internalEmploymentBean;
    @EJB
    private BaseEmployeeCategoryHistoryLocal employeeCategoryHistoryBean;
    @EJB
    private EMCMailManagerLocal mailManager;
    @EJB
    private BaseWebPortalUsersLocal webPortalUsersBean;
    @EJB
    private BaseCompanyLocal companyBean;
    @PersistenceContext
    private EntityManager em;

    /**
     * Creates a new instance of BaseEmployeeBean
     */
    public BaseEmployeeBean() {
        //this.setSystemTable(true);
    }
    
    private boolean validateManager(Object theRecord, EMCUserData userData) {
        BaseEmployeeTable toBeTested = (BaseEmployeeTable) theRecord;
        if (!eMCServerUtility.exists("BaseEmployeeTable", "employeeNumber", toBeTested.getManager(), userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "The manager specified is not valid.", userData);
            return false;
        }
        return true;
    }
    
    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        
        if (ret) {
            BaseEmployeeTable toBeTested = (BaseEmployeeTable) theRecord;
            if (fieldNameToValidate.equals("manager")) {
                if (!this.validateManager(theRecord, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Manager does not exist", userData);
                    ret = false;
                }
            }
            if (fieldNameToValidate.equals("userId")) {
                if (this.userIdUsed(toBeTested.getUserId(), toBeTested.getEmployeeNumber(), userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "User Id already in use", userData);
                    ret = false;
                }
            }
            if (fieldNameToValidate.equals("disabilityType")) {
                if (!toBeTested.isDisability()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected employee does not have a disability.", userData);
                    ret = false;
                }
            }
            if (fieldNameToValidate.equals("disability")) {
                if (!toBeTested.isDisability()) {
                    toBeTested.setDisabilityType(null);
                    return toBeTested;
                }
            }
        }
        
        return ret;
    }
    
    public String findEmployee(String _userId, EMCUserData userData) {
        EMCUserData employeeTableUserData = userData.copyUserData();
        
        String queryStr = "SELECT c.employeeNumber FROM BaseEmployeeTable c "
                + "WHERE "
                + "c.userId = '" + _userId + "' AND "
                + "c.companyId = '" + (systemTableBean.isTableSystemTable(BaseEmployeeTable.class.getName()) ? systemConstants.defaultCompanyId() : userData.getCompanyId()) + "'";
        
        Query q = em.createQuery(queryStr);
        Iterator result = q.getResultList().iterator();
        if (result.hasNext()) {
            return result.next().toString();
            
        } else {
            return "";
        }
    }

    /**
     * Returns a String containing the name and surname for the employee with
     * the given employee number, or a blank String if the employee is not
     * found.
     */
    public String findEmployeeNameAndSurname(String empNum, EMCUserData userData) {
        
        String compId = this.isSystemTable("emc.entity.base.BaseEmployeeTable") ? emc.constants.systemConstants.defaultCompanyId() : userData.getCompanyId();
        
        String queryStr = "SELECT c.forenames, c.surname FROM BaseEmployeeTable c "
                + "WHERE "
                + "c.employeeNumber = '" + empNum + "' AND "
                + "c.companyId = '" + compId + "'";
        
        Query q = em.createQuery(queryStr);
        Iterator result = q.getResultList().iterator();
        if (result.hasNext()) {
            //The information to return
            Object[] userInfo = (Object[]) result.next();

            //Returns the information
            return userInfo[0] + " " + userInfo[1];

//            BaseEmployeeTable bet = (BaseEmployeeTable)result.next();
//            
//            return bet.getForenames() + " " + bet.getSurname();

        } else {
            return "";
        }
    }
    
    public boolean userIdUsed(String _userId, String _curEmplNum, EMCUserData userData) {
        EMCUserData employeeTableUserData = userData.copyUserData();
        
        String queryStr = "SELECT c.employeeNumber FROM BaseEmployeeTable c "
                + "WHERE "
                + "c.userId = '" + _userId + "' AND "
                + "c.employeeNumber <> '" + _curEmplNum + "' AND "
                + "c.companyId = '" + (systemTableBean.isTableSystemTable(BaseEmployeeTable.class.getName()) ? systemConstants.defaultCompanyId() : userData.getCompanyId()) + "'";
        
        Query q = em.createQuery(queryStr);
        Iterator result = q.getResultList().iterator();
        if (result.hasNext()) {
            return true;
            
        } else {
            return false;
        }
    }

    //Retrieves all employees celebrating a birthday "today"
    public ArrayList<String> findBirthdays(EMCUserData userData) {
        
        String compId = null;
        
        if (this.isSystemTable("emc.entity.base.BaseEmployeeTable")) {
            compId = emc.constants.systemConstants.defaultCompanyId();
        } else {
            compId = userData.getCompanyId();
        }

        //The ArrayList that will be returned
        ArrayList<String> returnList = new ArrayList<String>();

        //The query which will be executed
        String queryStr = "SELECT be "
                + "FROM BaseEmployeeTable be "
                + "WHERE SUBSTRING(be.employeeID, 3, 2) = MONTH(NOW()) AND "
                + "SUBSTRING(be.employeeID, 5, 2) = DAY(NOW()) AND be.companyId = '" + compId + "'";
        
        Query q = em.createQuery(queryStr);
        Iterator result = q.getResultList().iterator();
        
        while (result.hasNext()) {
            //Represents the current record
            BaseEmployeeTable bet = (BaseEmployeeTable) result.next();

            //The String that will be added to the ArrayList
            String empString = "";

            //String representing the optional forenames field
            String forenames = bet.getForenames();

            //String representing the optional knownAs field
            String knownAs = bet.getKnownAs();

            //According to the design spec, there will always be at least a surname and forenames
            if (knownAs != null) {
                //Use knownAs
                empString = empString + knownAs + " ";
            } else {
                //Use forenames
                empString = empString + forenames + " ";
            }

            //The employee's surname and employeeNumber are added to empString
            empString = empString + bet.getSurname();

            //Add record id, so that employee form can be open on client.
            empString += "|" + bet.getRecordID();

            //Adds the current employee's information to the ArrayList
            returnList.add(empString);
        }
        
        return returnList;
    }

    //This method is used to retrieve an employee's surname using their employee number
    public String findSurname(String empNumber, EMCUserData userData) {
        EMCUserData employeeTableUserData = userData.copyUserData();
        
        String queryStr = "SELECT be.surname FROM BaseEmployeeTable be "
                + " WHERE be.employeeNumber = '" + empNumber + "' "
                + " AND be.companyId = '" + util.getCompanyId(BaseEmployeeTable.class.getName(), userData) + "'";
        
        Query q = em.createQuery(queryStr);
        Iterator result = q.getResultList().iterator();
        if (result.hasNext()) {
            return result.next().toString();
        } else {
            return "";
        }
    }

    //This method is used to retrieve a list of a manager's employees
    public ArrayList<String> findEmployeesOfManager(EMCUserData userData) {
        //The employeeId of the manager
        String manager = this.findEmployee(userData.getUserName(), userData);

        //The list to return
        ArrayList<String> employeeList = new ArrayList<String>();

        //Query to select all employees of the manager
        String query = "SELECT emp.employeeNumber FROM BaseEmployeeTable emp "
                + "WHERE emp.manager = '" + manager + "' "
                + "AND emp.companyId = '" + util.getCompanyId(BaseEmployeeTable.class.getName(), userData) + "'";

        //Creates a query object
        Query qr = em.createQuery(query);

        //Get results from the query
        List results = qr.getResultList();

        //Creates an iterator from the result list
        Iterator it = results.iterator();

        //Iterate through results
        while (it.hasNext()) {
            //Add the employee to the list
            employeeList.add(it.next().toString());
        }

        //Return the list
        return employeeList;
    }
    
    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseEmployeeTable currentEmpRec = (BaseEmployeeTable) iobject;
        
        handleHRTables(currentEmpRec, null, userData);
        handleEmpCategory(currentEmpRec, null, userData);
        
        return super.insert(iobject, userData);
    }
    
    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseEmployeeTable currentEmpRec = (BaseEmployeeTable) uobject;
        BaseEmployeeTable presistedEmpRec = (BaseEmployeeTable) super.findSQLVersionForEntity(currentEmpRec, userData);
        
        handleHRTables(currentEmpRec, presistedEmpRec, userData);
        handleEmpCategory(currentEmpRec, presistedEmpRec, userData);
        
        return super.update(uobject, userData);
    }
    
    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        boolean ret = super.doInsertValidation(vobject, userData);
        if (ret) {
            ret = doSaveValidation((BaseEmployeeTable) vobject, userData);
        }
        return ret;
    }
    
    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        if (ret) {
            ret = doSaveValidation((BaseEmployeeTable) vobject, userData);
        }
        return ret;
    }
    
    private boolean doSaveValidation(BaseEmployeeTable record, EMCUserData userData) {
        if (record.isDisability() && isBlank(record.getDisabilityType())) {
            Logger.getLogger("emc").log(Level.SEVERE, "Please set the disability type.", userData);
            return false;
        }
        return true;
    }
    
    @Override
    public void fixEmployeeIds(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        List<BaseEmployeeTable> empList = util.executeGeneralSelectQuery(query, userData);
        util.detachEntities(userData);
        int length;
        String empNum;
        int count = 0;
        for (BaseEmployeeTable emp : empList) {
            try {
                Double.parseDouble(emp.getEmployeeNumber());
            } catch (NumberFormatException ex) {
                continue;
            }
            count++;
            length = emp.getEmployeeNumber().length();
            empNum = "";
            for (int i = 0; i < 5 - length; i++) {
                empNum = empNum.concat("0");
            }
            empNum = empNum.concat(emp.getEmployeeNumber());
            emp.setEmployeeNumber(empNum);
            super.update(emp, userData);
        }
        logMessage(Level.INFO, count + " records where fixed.", userData);
    }
    
    private void handleHRTables(BaseEmployeeTable currentEmpRec, BaseEmployeeTable presistedEmpRec, EMCUserData userData) throws EMCEntityBeanException {
        if (presistedEmpRec != null) {
            //Termination Log
            if (!isBlank(currentEmpRec.getTerminationType()) && !isBlank(currentEmpRec.getTerminationType())) {
                if (isBlank(presistedEmpRec.getTerminationType())) {
                    HRTerminationLog termination = new HRTerminationLog();
                    termination.setEmployeeNumber(currentEmpRec.getEmployeeNumber());
                    termination.setTerminationDate(currentEmpRec.getTerminationDate());
                    termination.setTerminationType(currentEmpRec.getTerminationType());
                    terminationBean.insert(termination, userData);
                    logMessage(Level.INFO, ServerBaseMessageEnum.TERMINLOGCREATED, userData);
                } else {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, HRTerminationLog.class.getName());
                    query.addAnd("employeeNumber", presistedEmpRec.getEmployeeNumber());
                    query.addAnd("terminationDate", presistedEmpRec.getTerminationDate());
                    HRTerminationLog log = (HRTerminationLog) util.executeSingleResultQuery(query, userData);
                    if (log == null) {
                        HRTerminationLog termination = new HRTerminationLog();
                        termination.setEmployeeNumber(currentEmpRec.getEmployeeNumber());
                        termination.setTerminationDate(currentEmpRec.getTerminationDate());
                        termination.setTerminationType(currentEmpRec.getTerminationType());
                        terminationBean.insert(termination, userData);
                        logMessage(Level.INFO, ServerBaseMessageEnum.TERMINLOGCREATED, userData);
                    } else {
                        log.setTerminationDate(currentEmpRec.getTerminationDate());
                        log.setTerminationType(currentEmpRec.getTerminationType());
                        terminationBean.update(log, userData);
                    }
                }
                
            }
            //Internal Employment History
            if (!isBlank(presistedEmpRec.getJobTitle()) && !isBlank(presistedEmpRec.getDepartment()) && !isBlank(presistedEmpRec.getBranch())
                    && !isBlank(presistedEmpRec.getEmploymentSection())) {
                if (!currentEmpRec.getJobTitle().equalsIgnoreCase(presistedEmpRec.getJobTitle())
                        || !currentEmpRec.getDepartment().equalsIgnoreCase(presistedEmpRec.getDepartment())
                        || !currentEmpRec.getBranch().equalsIgnoreCase(presistedEmpRec.getBranch())
                        || !currentEmpRec.getEmploymentSection().equalsIgnoreCase(presistedEmpRec.getEmploymentSection())) {
                    HRInternalEmploymentHistory internalHistory = new HRInternalEmploymentHistory();
                    internalHistory.setEmployeeNumber(currentEmpRec.getEmployeeNumber());
                    internalHistory.setCompany(currentEmpRec.getCompanyId());
                    internalHistory.setBranch(currentEmpRec.getBranch());
                    internalHistory.setDepartment(currentEmpRec.getDepartment());
                    internalHistory.setSectionId(currentEmpRec.getEmploymentSection());
                    internalHistory.setJobTitle(currentEmpRec.getJobTitle());
                    internalHistory.setGrade(currentEmpRec.getEmployeeGrade());
                    internalHistory.setFromDate(currentEmpRec.getDateStartedInPos() == null ? currentEmpRec.getDateOfEmployment() : currentEmpRec.getDateStartedInPos());
                    internalHistory.setToDate(Functions.nowDate());
                    internalEmploymentBean.insert(internalHistory, userData);
                    //Set date started on employee record
                    currentEmpRec.setDateStartedInPos(Functions.nowDate());
                    logMessage(Level.INFO, ServerBaseMessageEnum.EMPHISTCREATED, userData);
                }
            }
        }
    }
    
    public EMCQuery checkAccessGroup(EMCUserData userData) {
        String employeeId = findEmployee(userData.getUserName(), userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeAccessGroup.class);
        query.addTableAnd(BaseEmployeeAccessGroupEmployees.class.getName(), "accessGroup", BaseEmployeeAccessGroup.class.getName(), "accessGroup");
        query.addAnd("employeeId", employeeId, BaseEmployeeAccessGroupEmployees.class.getName());
        query.addAnd("accessAll", true, BaseEmployeeAccessGroup.class.getName());
        if (util.exists(query, userData)) {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        } else {
            query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
            query.addTableAnd(WorkFlowDepartment.class.getName(), "department", BaseEmployeeTable.class.getName(), "department");
            query.addTableAnd(BaseEmployeeAccessGroupEmployees.class.getName(), "accessGroup", WorkFlowDepartment.class.getName(), "accessGroup");
            query.addAnd("employeeId", employeeId, BaseEmployeeAccessGroupEmployees.class.getName());
        }

        //If a query already exists in userData, assume that the query is from
        //a related form and that it should be taken into account.
        if (userData.getUserData(0) instanceof EMCQuery) {
            EMCQuery relatedFormQuery = (EMCQuery) userData.getUserData(0);
            relatedFormQuery.addField("employeeNumber");
            query.addAnd("employeeNumber", relatedFormQuery, BaseEmployeeTable.class.getName(), EMCQueryConditions.IN);
        }
        
        System.out.println("Final Query: " + query.getNativeQuery());
        return query;
    }
    
    public BaseEmployeeTable getEmployeeRecord(String empNum, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        query.addAnd("employeeNumber", empNum);
        return (BaseEmployeeTable) util.executeSingleResultQuery(query, userData);
    }

    /**
     * Returns a BaseEmployeeTable instance for the specified user if, or null
     * if none is found.
     *
     * @param _userId User id to select on.
     * @param userData User data.
     * @return The BaseEmployeeTable record for the specified user.
     */
    public BaseEmployeeTable findEmployeeRecord(String _userId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeTable.class);
        query.addAnd("userId", _userId);
        return (BaseEmployeeTable) util.executeSingleResultQuery(query, userData);
    }
    
    private void handleEmpCategory(BaseEmployeeTable currentEmpRec, BaseEmployeeTable presistedEmpRec, EMCUserData userData) throws EMCEntityBeanException {
        String currentCategory = currentEmpRec.getEmployeeGrade();
        String previousCategory = (presistedEmpRec == null ? null : presistedEmpRec.getEmployeeGrade());
        
        if (!util.checkObjectsEqual(currentCategory, previousCategory)) {
            BaseEmployeeCategoryHistory history = new BaseEmployeeCategoryHistory();
            history.setEmployeeNumber(currentEmpRec.getEmployeeNumber());
            history.setCategoryChangeDate(dateHandler.nowDate());
            history.setFromCategory(previousCategory);
            history.setToCategory(currentCategory);
            employeeCategoryHistoryBean.insert(history, userData);
        }
    }
    
    public boolean createWebPortalUser(BaseEmployeeTable employee, EMCUserData userData) throws EMCEntityBeanException {
        BaseWebPortalUsers webUser;
        
        if (!isBlank(employee.getWebUID())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
            query.addAnd("recordID", employee.getWebUID());
            webUser = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);
            if (webUser.isActive()) {
                throw new EMCEntityBeanException("Employee already has a web account.");
            } else {
                webUser.setUserId(employee.getEmailAddress());
                webUser.setPassword(employee.getEmployeeNumber());
                webUser.setActive(true);
                webPortalUsersBean.update(webUser, userData);
                return true;
            }
        }
        
        if (isBlank(employee.getEmailAddress())) {
            throw new EMCEntityBeanException("Employee does not have an email account. Can not create a web user for the employee");
        }
        
        webUser = new BaseWebPortalUsers();
        webUser.setUserId(employee.getEmailAddress());
        webUser.setPassword(employee.getEmployeeNumber());
        webUser.setActive(true);
        webUser.setLinkToSourceType(WebPortalUsersReferenceType.LECTURER.toString());
        webUser.setLinkToSourceRecId(employee.getRecordID());
        webPortalUsersBean.insert(webUser, userData);
        
        employee.setWebUID(String.valueOf(webUser.getRecordID()));
        this.update(employee, userData);
        
        try {
            EMCEmail email = new EMCEmail();
            email.addRecipient(employee.getEmployeeNumber(), employee.getEmailAddress());
            email.setSubject(companyBean.getUserCompany(userData).getCoTradingAs() + " Web Credentials");
           mailManager.sendEmail(email, userData);
        } catch (Exception ex) {
            throw new EMCEntityBeanException(ex);
        }
        
        return true;
    }
    
    public List<String> createSETAExportFile(EMCQuery query, EMCUserData userData) {
        List<BaseEmployeeTable> employeeList = util.executeGeneralSelectQuery(query, userData);
        
        if (employeeList.isEmpty()) {
            Logger.getLogger("emc").log(Level.SEVERE, "No employees found for your selection.", userData);
            return null;
        }
        
        Map<String, WorkFlowJobTitles> jobTitleMap = new HashMap<String, WorkFlowJobTitles>();
        WorkFlowJobTitles jobTitle;
        
        Map<String, HROFOCodes> ofoCodeMap = new HashMap<String, HROFOCodes>();
        HROFOCodes ofoCode = null;
        
        Map<String, HRTerminationType> terminationTypeMap = new HashMap<String, HRTerminationType>();
        HRTerminationType terminationType = null;
        
        StringBuilder dataLine;
        List<String> dataList = new ArrayList<String>();
        
        String jobStatus;
        
        dataLine = new StringBuilder();
        
        wrapDataField(dataLine, "First Name", true);
        wrapDataField(dataLine, "Last Name", true);
        wrapDataField(dataLine, "Employee Number", true);
        wrapDataField(dataLine, "National ID", true);
        wrapDataField(dataLine, "Alt ID Number", true);
        wrapDataField(dataLine, "Alt ID Type", true);
        wrapDataField(dataLine, "Nationality", true);
//        wrapDataField(dataLine, "OFO Major Selection", true);
        wrapDataField(dataLine, "OFO Code", true);
        wrapDataField(dataLine, "Occupational Category", true);
        wrapDataField(dataLine, "Actual Occupation", true);
        wrapDataField(dataLine, "Level of Experience within the Occupation", true);
        wrapDataField(dataLine, "Min Qualification required for the Occupation", true);
        wrapDataField(dataLine, "Highest Qualification of Individual", true);
        wrapDataField(dataLine, "Citizen Status", true);
        wrapDataField(dataLine, "Socio Status", true);
        wrapDataField(dataLine, "Equity Code", true);
        wrapDataField(dataLine, "Gender Code", true);
        wrapDataField(dataLine, "Disability Status Code", true);
        wrapDataField(dataLine, "Home Language Code", true);
        wrapDataField(dataLine, "Age", true);
        wrapDataField(dataLine, "Province Of Employment", true);
        wrapDataField(dataLine, "Province of Residence", true);
        wrapDataField(dataLine, "Municipality", true);
        wrapDataField(dataLine, "Employment Status", true);
        wrapDataField(dataLine, "Job Status", true);
        wrapDataField(dataLine, "Date of Change", true);
        wrapDataField(dataLine, "Type", false);
        
        dataList.add(dataLine.toString());
        
        for (BaseEmployeeTable employee : employeeList) {
            jobTitle = null;
            ofoCode = null;
            jobStatus = null;
            terminationType = null;
            
            jobTitle = jobTitleMap.get(employee.getJobTitle());
            if (jobTitle == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, WorkFlowJobTitles.class);
                query.addAnd("jobTitle", employee.getJobTitle());
                
                jobTitle = (WorkFlowJobTitles) util.executeSingleResultQuery(query, userData);
                
                jobTitleMap.put(employee.getJobTitle(), jobTitle);
            }
            if (jobTitle != null) {
                ofoCode = ofoCodeMap.get(jobTitle.getOfoCode());
                if (ofoCode == null) {
                    query = new EMCQuery(enumQueryTypes.SELECT, HROFOCodes.class);
                    query.addAnd("ofoCode", jobTitle.getOfoCode());
                    
                    ofoCode = (HROFOCodes) util.executeSingleResultQuery(query, userData);
                    
                    ofoCodeMap.put(jobTitle.getOfoCode(), ofoCode);
                }
            }
            
            dataLine = new StringBuilder();
            //First Name
            wrapDataField(dataLine, employee.getForenames(), true);
            //Last Name
            wrapDataField(dataLine, employee.getSurname(), true);
            if (employee.getEmployeeNumber().matches("\\d*")) {
                //Employee Number
                wrapDataField(dataLine, "D" + employee.getEmployeeNumber(), true);
            } else {
                //Employee Number
                wrapDataField(dataLine, employee.getEmployeeNumber(), true);
            }
            //National ID
            wrapDataField(dataLine, employee.getEmployeeID(), true);
            //Alt ID Number
            wrapDataField(dataLine, employee.getAltIdNumber(), true);
            //Alt ID Type
            wrapDataField(dataLine, employee.getAltIdType(), true);
            //Nationality
            wrapDataField(dataLine, employee.getNationality(), true);
            if (ofoCode != null) {
//                //OFO Major Selection
//                wrapDataField(dataLine, ofoCode.getOfoCode() + " - " + ofoCode.getDescription(), true);
                //OFO Code
                wrapDataField(dataLine, ofoCode.getOfoCode(), true);
            } else {
//                //OFO Major Selection
//                wrapDataField(dataLine, "", true);
                //OFO Code
                wrapDataField(dataLine, "", true);
            }
            if (jobTitle != null) {
                //Occupational Category
                wrapDataField(dataLine, jobTitle.getJobCatagory(), true);
            } else {
                //Occupational Category
                wrapDataField(dataLine, "", true);
            }
            if (jobTitle != null) {
                //Actual Occupation
                wrapDataField(dataLine, jobTitle.getJobTitle(), true);
            } else {
                //Actual Occupation
                wrapDataField(dataLine, employee.getActualOccupation(), true);
            }
            //Level of Experience within the Occupation
            wrapDataField(dataLine, employee.getLevelOfExperience(), true);
            if (jobTitle != null) {
                //Min Qualification required for the Occupation
                wrapDataField(dataLine, jobTitle.getEduLevel(), true);
            } else {
                //Min Qualification required for the Occupation
                wrapDataField(dataLine, "", true);
            }
            //Highest Qualification of Individual
            wrapDataField(dataLine, employee.getHighestQualification(), true);
            //Citizen Status
            wrapDataField(dataLine, employee.getCitizenStatus(), true);
            //Socio Status
            wrapDataField(dataLine, employee.getSocioEcoStatus(), true);
            //Equity Code
            wrapDataField(dataLine, employee.getEquityCode(), true);
            //Gender Code
            wrapDataField(dataLine, employee.getGender(), true);
            if (isBlank(employee.getDisabilityType())) {
                //Disability Status Code
                wrapDataField(dataLine, "N", true);
            } else {
                //Disability Status Code
                wrapDataField(dataLine, employee.getDisabilityType(), true);
            }
            //Home Language Code
            wrapDataField(dataLine, employee.getHomeLanguage(), true);
            //Age
            wrapDataField(dataLine, calcAge(employee.getDateOfBirth()), true);
            //Province Of Employment
            wrapDataField(dataLine, "5", true);
            //Province of Residence
            wrapDataField(dataLine, "5", true);
            //Municipality
            wrapDataField(dataLine, "Ethekwini Metropolitan Municipality", true);
            if (isBlank(employee.getTerminationDate())) {
                //Employment Status
                wrapDataField(dataLine, "Employed (18.1)", true);
                //Job Status
                wrapDataField(dataLine, (jobStatus = calcJobStatus(employee.getDateOfEmployment())), true);
                if ("Unchanged/Current Employee".equals(jobStatus)) {
                    //Date of Change
                    wrapDataField(dataLine, "", true);
                } else {
                    //Date of Change
                    wrapDataField(dataLine, isBlank(employee.getDateOfEmployment()) ? "" : Functions.date2String(employee.getDateOfEmployment()), true);
                }
            } else {
                //Employment Status
                wrapDataField(dataLine, "Unemployed (18.2)", true);
                terminationType = terminationTypeMap.get(employee.getTerminationType());
                if (terminationType == null && !isBlank(employee.getTerminationType())) {
                    query = new EMCQuery(enumQueryTypes.SELECT, HRTerminationType.class);
                    query.addAnd("terminationType", employee.getTerminationType());
                    terminationType = (HRTerminationType) util.executeSingleResultQuery(query, userData);
                    terminationTypeMap.put(employee.getTerminationType(), terminationType);
                    
                }
                if (terminationType == null) {
                    //Job Status
                    wrapDataField(dataLine, employee.getTerminationType(), true);
                } else {
                    //Job Status
                    wrapDataField(dataLine, terminationType.getDescription(), true);
                }
                //Date of Change
                wrapDataField(dataLine, Functions.date2SQLString(employee.getTerminationDate()), true);
            }
            //Type
            wrapDataField(dataLine, employee.getTypeOfEmployment(), false);
            
            dataList.add(dataLine.toString());
        }
        
        return dataList;
    }
    
    private void wrapDataField(StringBuilder dataLine, String fieldValue, boolean addDeliminator) {
        dataLine.append(isBlank(fieldValue) ? "" : fieldValue);
        if (addDeliminator) {
            dataLine.append("|");
        }
    }
    
    private String calcAge(Date dateOfBirth) {
        if (dateOfBirth == null) {
            return "";
        }
        
        Calendar nowDateCal = Calendar.getInstance();
        
        Calendar dateOfBirthCal = Calendar.getInstance();
        dateOfBirthCal.setTime(dateOfBirth);
        
        int age = nowDateCal.get(Calendar.YEAR) - dateOfBirthCal.get(Calendar.YEAR);
        
        return String.valueOf(age);
    }
    
    private String calcJobStatus(Date dateOfEmployment) {
        if (isBlank(dateOfEmployment)) {
            return "Unchanged/Current Employee";
        }
        
        Calendar cutOfCal = Calendar.getInstance();
        cutOfCal.set(Calendar.MONTH, Calendar.APRIL);
        cutOfCal.set(Calendar.DAY_OF_MONTH, 1);
        
        Calendar employmentCal = Calendar.getInstance();
        employmentCal.setTime(dateOfEmployment);
        
        if (employmentCal.before(cutOfCal)) {
            return "Unchanged/Current Employee";
        } else {
            return "New Employee";
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.webportal;

import emc.bus.base.BaseCompanyLocal;
import emc.entity.base.webportal.BaseWebPortalUsers;
import emc.entity.sop.SOPCustomers;
import emc.enums.base.webportalusers.WebPortalUsersReferenceType;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.base.EMCEmail;
import emc.server.mailmanager.EMCMailManagerLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @name : BaseWebPortalUsersBean
 *
 * @description : Bean for the BaseWebPortalUsers entity class.
 *
 * @date : 18 Nov 2009
 *
 * @author : riaan
 */
@Stateless
public class BaseWebPortalUsersBean extends EMCEntityBean implements BaseWebPortalUsersLocal {

    @EJB
    private BaseCompanyLocal companyBean;
    @EJB
    private EMCMailManagerLocal mailManager;

    /**
     * Creates a new instance of BaseWebPortalUsersBean.
     */
    public BaseWebPortalUsersBean() {
    }

    /**
     * Attempts to log a web portal user into the system.
     *
     * @param userName User name of user to be logged in.
     * @param password User password.
     * @param userData User data.
     * @return The web portal user with the specified username and password, or
     * null if the username/password combination was not found.
     */
    @Override
    public BaseWebPortalUsers loginWebPortalUser(String userName, String password, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
        query.addAnd("userId", userName);
        query.addAnd("password", password);

        //return (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);
        Object BaseWebPortalUsers = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);

        return (BaseWebPortalUsers) BaseWebPortalUsers;
    }

    /**
     * Attempts to find a student id number or passport number;
     *
     * @param userName
     * @param userData
     * @return id or passport or null if none found
     */
    public String getStudentIdNumber(String userName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
        query.addAnd("userId", userName);

        BaseWebPortalUsers user = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);

        return null;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseWebPortalUsers record = (BaseWebPortalUsers) uobject;

        if (userData.getUserData() != null && userData.getUserData(5) != null && userData.getUserData(5).equals(Boolean.TRUE)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
            query.addAnd("recordID", record.getLinkToSourceRecId());
            SOPCustomers customer = (SOPCustomers) util.executeSingleResultQuery(query, userData);


            StringBuilder message = new StringBuilder();
            String customerID = "";
            String website = companyBean.getUserCompany(userData).getCoWebsite();
            if (customer != null) {
                customerID = customer.getCustomerId();

            } else {
                customerID = record.getUserId();
            }
            message.append("Dear " + customer.getCustomerName() + "\n");
            message.append("\n");
            message.append("Your password has been successfully updated as per your request.\n");
            message.append("\n");
            message.append("You can log on to " + website + " with the following credentials below: \n");
            message.append("\n");
            message.append("<b>Username :</b> " + record.getUserId() + "\n");
            message.append("<b>Password : </b>" + record.getPassword() + "\n");
            message.append("\n");
            message.append("Regards\n");
            message.append(isBlank(companyBean.getUserCompany(userData).getCoTradingAs()) ? companyBean.getUserCompany(userData).getCompanyName() : companyBean.getUserCompany(userData).getCoTradingAs());


            EMCEmail email = new EMCEmail();
            if (!util.checkObjectsEqual(record.getUserId(), customer.getEmail())) {
             email.addRecipient(customerID, record.getUserId());
            }else{
              email.addRecipient(customerID, customer.getEmail());  
            }
            email.setSubject(isBlank(companyBean.getUserCompany(userData).getCoTradingAs()) ? companyBean.getUserCompany(userData).getCompanyName() + "Username / Password update confirmation " : companyBean.getUserCompany(userData).getCoTradingAs() + " Username / Password update confirmation");
            email.setMessage(message.toString());
            mailManager.sendEmail(email, userData);
            logMessage(Level.INFO, "An email has been sent to " + customer.getEmail() + " with the updated login credentials.", userData);

            userData.setUserData(5, null);
        }
        return super.update(uobject, userData);
    }

    public boolean activateWebUser(long webUserRecordID, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
        query.addAnd("recordID", webUserRecordID);
        BaseWebPortalUsers webUser = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);

        if (webUser == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find Web User Record.", userData);
            return false;
        }

        webUser.setActive(true);

        update(webUser, userData);

        return true;
    }

    public boolean deactivateWebUser(long webUserRecordID, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
        query.addAnd("recordID", webUserRecordID);
        BaseWebPortalUsers webUser = (BaseWebPortalUsers) util.executeSingleResultQuery(query, userData);

        if (webUser == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to find Web User Record.", userData);
            return false;
        }

        webUser.setActive(false);

        update(webUser, userData);

        return true;
    }

    public boolean deactivateAllWebUsers(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseWebPortalUsers.class);
        query.addAnd("active", true);
        List<BaseWebPortalUsers> webUserList = util.executeGeneralSelectQuery(query, userData);

        for (BaseWebPortalUsers webUser : webUserList) {
            webUser.setActive(false);

            update(webUser, userData);
        }

        return true;
    }
}

/*
 * BaseEmployeeLocal.java
 *
 * Created on 11 December 2007, 03:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.bus.base;

import emc.entity.base.BaseEmployeeTable;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rico
 */
@Local
public interface BaseEmployeeLocal extends EMCEntityBeanLocalInterface {

    String findEmployee(String _userId, EMCUserData userData);

    /** Returns a BaseEmployeeTable instance for the specified user if, or null
     * if none is found.
     * @param _userId User id to select on.
     * @param userData User data.
     * @return The BaseEmployeeTable record for the specified user.
     */
    public BaseEmployeeTable findEmployeeRecord(String _userId, EMCUserData userData);

    ArrayList findBirthdays(EMCUserData userData);

    String findSurname(String empNumber, EMCUserData userData);

    String findEmployeeNameAndSurname(String empNum, EMCUserData userData);

    List findEmployeesOfManager(EMCUserData userData);

    public void fixEmployeeIds(EMCUserData userData) throws EMCEntityBeanException;

    public EMCQuery checkAccessGroup(EMCUserData userData);

    public BaseEmployeeTable getEmployeeRecord(String empNum, EMCUserData userData);

    public boolean createWebPortalUser(BaseEmployeeTable employee, EMCUserData userData) throws EMCEntityBeanException;

    public List<String> createSETAExportFile(EMCQuery query, EMCUserData userData);
}

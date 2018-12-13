/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.journals.accessgroups;

import emc.bus.base.BaseEmployeeBean;
import emc.bus.base.BaseEmployeeLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroupEmployees;
import emc.entity.base.journals.accessgroups.BaseJournalAccessGroups;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseJournalAccessGroupsBean extends EMCEntityBean implements BaseJournalAccessGroupsLocal{
    @EJB
    BaseEmployeeLocal empBean;
    /** Creates a new instance of BaseJournalAccessGroupsBean. */
    public BaseJournalAccessGroupsBean() {
        
    }

    public List getDefinitionList(EMCUserData userData){
        String empId = empBean.findEmployee(userData.getUserName(), userData);
        EMCQuery jDefQ = new EMCQuery(enumQueryTypes.SELECT, BaseJournalDefinitionTable.class);
        jDefQ.addTableAnd(BaseJournalAccessGroupEmployees.class.getName(),"journalAccessGroup",BaseJournalDefinitionTable.class.getName(),"accessGroupId");
        jDefQ.addField("journalDefinitionId");
        jDefQ.addAnd("employeeId", empId,BaseJournalAccessGroupEmployees.class.getName());
        jDefQ.setSelectDistinctAll(true);

        List res =  util.executeGeneralSelectQuery(jDefQ, userData);
        if(res == null) res.add("NothingFoundSoThere");
        return res;
    }
}

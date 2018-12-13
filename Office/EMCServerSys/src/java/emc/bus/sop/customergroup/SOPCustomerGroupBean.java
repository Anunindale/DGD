package emc.bus.sop.customergroup;

import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Stateless;

/**
 * 
 * @Author wikus
 */
@Stateless
public class SOPCustomerGroupBean extends EMCEntityBean implements SOPCustomerGroupLocal {

    /** Creates a new instance of SOPCustomerGroupBean. */
    public SOPCustomerGroupBean() {

    }

    /**
     * Returns a list of all customer in the specified customer group.
     * @param customerGroup Customer group identifier.
     * @param userData User data.
     * @return A list of all customer in the specified customer group.
     */
    public List<SOPCustomers> getGroupCustomers(String customerGroup, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
        query.addAnd("customerGroup", customerGroup);

        return (List<SOPCustomers>)util.executeGeneralSelectQuery(query, userData);
    }
    
}

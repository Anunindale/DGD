/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.receivedjournals;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class ReceivedJournalsDRM extends emcDRMViewOnly {

    /** Creates a new instance of ReceivedJournalsDRM */
    public ReceivedJournalsDRM(emcGenericDataSourceUpdate dataSource,EMCUserData userData) {
        super(dataSource, userData);
    }
    
    @Override
    public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData){
        Object obj1 = super.getFieldValueAt(this.getLastRowAccessed(),this.getHeaderColumnID());
        if(obj1== null) return linesUserData;    
        List x = new ArrayList();
        x.add(0,"SELECT u FROM POPSupplierReceivedJournalLines u WHERE u.companyId = '"
                   + linesUserData.getCompanyId() +"' AND u.receivedId = '" +
                   obj1.toString() + "' ORDER BY u.lineNo");
        x.add(1,"SELECT COUNT(*) FROM POPSupplierReceivedJournalLines u WHERE u.companyId = '"
                   + linesUserData.getCompanyId() +"' AND u.receivedId = '" +
                   obj1.toString() + "'");
        linesUserData.setUserData(x); 
        return linesUserData;
     }
}

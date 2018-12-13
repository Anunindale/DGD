/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.adminusers;

import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class AminUsersDRM extends emcDRMViewOnly {
    public AminUsersDRM(emcGenericDataSourceUpdate dataSource,EMCUserData userData){
        super(dataSource,userData);
    }

    @Override
    public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
        formUserData = super.generateRelatedFormUserData(formUserData, Index);
        switch(Index){
            case 0: List data = new ArrayList();
                    data.add(this.getFieldValueAt(this.getLastRowAccessed(), "sessionNumber"));
                    data.add(this.getFieldValueAt(this.getLastRowAccessed(), "userName"));
                    formUserData.setUserData(data);
                    break;
            default: break;
        }
        return formUserData;
    }
    
}

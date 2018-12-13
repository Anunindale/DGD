/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.personal.display.users;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.entity.base.Usertable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class usersformDRM extends emcDataRelationManagerUpdate {
    
    public usersformDRM(emcGenericDataSourceUpdate tableDataSource,EMCUserData userData){
        super(tableDataSource, userData);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, Usertable.class.getName());
        query.addAnd("userId", userData.getUserName());
        userData.setUserData(0, query.toString());
        userData.setUserData(1, query.getCountQuery());
        setUserData(userData);
    }
    
    
    

    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        Logger.getLogger("emc").log(Level.SEVERE,"You cannot add any new users here.", this.getUserData());
    }

    @Override
    public void deleteRowCache(int rowIndex) {
        Logger.getLogger("emc").log(Level.SEVERE,"You cannot delete yourself.", this.getUserData());
    }
    
    

    
}

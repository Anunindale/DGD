/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base;

import emc.bus.base.onlineusers.OnlineUsersLocal;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCUserData;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class SessionsBean extends EMCDataSourceBean implements SessionsLocal {

    @EJB
    private OnlineUsersLocal onlineUsersBean;
            
    /** Creates a new instance of SessionsBean */
    public SessionsBean() {
        
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) {
        return null;
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) {
        return null;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) {
        return null;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return true;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        //Always display all users
        // TODO Check that this works.
        return  String.valueOf(onlineUsersBean.getSessions().size()) + ", " + Long.MAX_VALUE;
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        return onlineUsersBean.getSessions();
    }
}

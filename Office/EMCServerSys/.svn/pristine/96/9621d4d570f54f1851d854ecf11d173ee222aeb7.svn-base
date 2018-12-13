/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.hr.absenteeismlog.datasource;

import emc.entity.hr.datasource.HRAbsenteeismLogDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class HRAbsenteeismLogDSBean extends EMCDataSourceBean implements HRAbsenteeismLogDSLocal {

    public HRAbsenteeismLogDSBean() {
        this.setDataSourceClassName(HRAbsenteeismLogDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        HRAbsenteeismLogDS ds = (HRAbsenteeismLogDS) dataSourceInstance;

        int totalMin = ds.getAbsenteeismTime();
        ds.setAbsentHourse(totalMin / 60);
        ds.setAbsentMinutes(totalMin - (60 * ds.getAbsentHourse()));

        return ds;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean valid = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if (valid) {
            HRAbsenteeismLogDS ds = (HRAbsenteeismLogDS) theRecord;
            if (fieldNameToValidate.equals("absentHourse") || fieldNameToValidate.equals("absentMinutes")) {
                ds.setAbsenteeismTime((60 * ds.getAbsentHourse()) + ds.getAbsentMinutes());

                int totalMin = ds.getAbsenteeismTime();
                ds.setAbsentHourse(totalMin / 60);
                ds.setAbsentMinutes(totalMin - (60 * ds.getAbsentHourse()));

                return ds;
            }
        }
        return valid;
    }
}

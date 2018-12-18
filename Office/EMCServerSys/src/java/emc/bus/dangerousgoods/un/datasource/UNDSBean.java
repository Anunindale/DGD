/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.un.datasource;

import emc.bus.dangerousgoods.declarationlines.DGDeclarationLinesLocal;
import emc.entity.dangerousgoods.datasource.UNDS;
import emc.entity.dangerousgoods.datasource.VehiclesDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author pj
 */

@Stateless
public class UNDSBean extends EMCDataSourceBean implements UNDSLocal{
   
    @EJB
    private DGDeclarationLinesLocal linesBean;
    
    public UNDSBean()
    {
        this.setDataSourceClassName(UNDS.class.getName());
    }
    
    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) 
    {
     UNDS ds = (UNDS) dataSourceInstance;
     
     if (!isBlank(ds.getDescription())) {
            ds.setDescription(linesBean.findDescriptionByNumber(ds.getLineNumber(), userData));
        }

        return ds;
    }
}

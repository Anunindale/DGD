/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.bus.base;

import emc.entity.base.BaseUnitsOfMeasure;
import emc.enums.base.uom.SystemUOM;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseUnitsOfMeasureBean extends EMCEntityBean implements BaseUnitsOfMeasureLocal {

    /** Creates a new instance of BaseUnitsOfMeasureBean */
    public BaseUnitsOfMeasureBean() {
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        generateSystem(userData);
        return super.getNumRows(theTable, userData);
    }
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void generateSystem(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUnitsOfMeasure.class);
        ArrayList<BaseUnitsOfMeasure> list = (ArrayList<BaseUnitsOfMeasure>) util.executeGeneralSelectQuery(query, userData);
        ArrayList<SystemUOM> uoms = new ArrayList(Arrays.asList(SystemUOM.values()));

        for (BaseUnitsOfMeasure measure : list) {
            for (int i = 0; i < uoms.size(); i++) {
                if (measure.getUnit().equalsIgnoreCase(uoms.get(i).toString())) {
                    uoms.remove(i);
                    continue;
                }
            }
        }
        try {
            for (SystemUOM system : uoms) {

                BaseUnitsOfMeasure measure = new BaseUnitsOfMeasure();
                measure.setUnit(system.toString());
                measure.setType(system.getType());
                //mandatory fields
                this.insert(measure, userData);
            }

        } catch (EMCEntityBeanException ex) {
            Logger.getLogger(BaseUnitsOfMeasureBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

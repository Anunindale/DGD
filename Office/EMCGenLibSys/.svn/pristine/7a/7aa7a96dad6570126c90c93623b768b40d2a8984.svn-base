/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "DevOldColourConversionTable")
public class DevOldColourConversionTable extends EMCEntityClass {

    private String oldColour;
    private String newColour;

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("newColour", new Dimension3FK());
        return toBuild;
    }

    public String getNewColour() {
        return newColour;
    }

    public void setNewColour(String newColour) {
        this.newColour = newColour;
    }

    public String getOldColour() {
        return oldColour;
    }

    public void setOldColour(String oldColour) {
        this.oldColour = oldColour;
    }
}

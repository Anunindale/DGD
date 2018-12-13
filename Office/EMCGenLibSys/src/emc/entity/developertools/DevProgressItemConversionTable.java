/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.developertools;

import emc.datatypes.EMCDataType;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension1FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension2FKNotMandatory;
import emc.datatypes.inventory.dimensions.foreignkeys.Dimension3FKNotMandatory;
import emc.datatypes.inventory.itemmaster.foreignkeys.ItemReferenceFK;
import emc.framework.EMCEntityClass;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Name         : DevProgressItemConversionTable
 *
 * @Date         : 07 Jul 2009
 * 
 * @Description  : Used to map items in N & L progress system to colours in EMC.
 *
 * @author       : riaan
 */
@Entity
@Table(name = "DevProgressItemConversionTable")
public class DevProgressItemConversionTable extends EMCEntityClass {

    private String oldItem;
    private String emcItem;
    private String dimension1;
    private String dimension2;
    private String dimension3;

    /** Creates a new instance of DevProgressItemConversionTable. */
    public DevProgressItemConversionTable() {

    }

    public String getOldItem() {
        return oldItem;
    }

    public void setOldItem(String oldItem) {
        this.oldItem = oldItem;
    }

    public String getEmcItem() {
        return emcItem;
    }

    public void setEmcItem(String emcItem) {
        this.emcItem = emcItem;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getDimension3() {
        return dimension3;
    }

    public void setDimension3(String dimension3) {
        this.dimension3 = dimension3;
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();

        toBuild.put("emcItem", new ItemReferenceFK());
        toBuild.put("dimension1", new Dimension1FKNotMandatory());
        toBuild.put("dimension2", new Dimension2FKNotMandatory());
        toBuild.put("dimension3", new Dimension3FKNotMandatory());

        return toBuild;
    }
}

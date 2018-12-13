/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.productgroup;

import emc.entity.inventory.InventoryItemGroup;
import emc.entity.inventory.InventoryItemMaster;
import emc.entity.inventory.InventoryProductGroup;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryItemDimension1Setup;
import emc.entity.inventory.dimensions.InventoryItemDimensionCombinations;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.inventory.ItemProductGroupWebHelper;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class InvenotyProductGroupBean extends EMCEntityBean implements InventoryProductGroupLocal {
    
    @Override
    public List<ItemProductGroupWebHelper> getProductGroups(EMCUserData userData) {
        List<ItemProductGroupWebHelper> helperList = new ArrayList<>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryItemDimensionCombinations.class);
        query.addAnd("itemId", "PLCI");
        query.addField("dimension1Id");
        query.addOrderBy("dimension1Id");
        query.setSelectDistinctAll(true);

        List<String> selectedData = util.executeGeneralSelectQuery(query, userData);

        ItemProductGroupWebHelper helper;

        for (String selected : selectedData) {
            EMCQuery query1 = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);
            query1.addAnd("dimensionId", selected);
            query1.addField("description");
            String desc = (String) util.executeSingleResultQuery(query1, userData);
            
            helper = new ItemProductGroupWebHelper();
            helper.setProductGroup(selected);
            helper.setProductGroupDescription(desc);

            helperList.add(helper);
        }

        return helperList;
    }
}

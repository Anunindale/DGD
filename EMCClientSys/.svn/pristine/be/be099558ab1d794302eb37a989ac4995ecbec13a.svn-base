/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.inventory;

import emc.framework.EMCMenu;
import emc.menus.inventory.menuitems.display.AdditionalDimensions;
import emc.menus.inventory.menuitems.display.ColourDesignMaster;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension1GroupSetup;
import emc.menus.inventory.menuitems.display.Dimension1Groups;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension2GroupSetup;
import emc.menus.inventory.menuitems.display.Dimension2Groups;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.Dimension3GroupSetup;
import emc.menus.inventory.menuitems.display.Dimension3Groups;
import emc.menus.inventory.menuitems.display.DimensionGroupSetup;
import emc.menus.inventory.menuitems.display.ItemDimension1Setup;
import emc.menus.inventory.menuitems.display.ItemDimension2Setup;
import emc.menus.inventory.menuitems.display.ItemDimension3Setup;
import emc.menus.inventory.menuitems.display.ItemDimensionCombinations;

/**
 *
 * @author riaan
 */
public class InventoryControlDimensions extends EMCMenu {

    private AdditionalDimensions additionalDimensions = new AdditionalDimensions();
    private Dimension1 dimension1 = new Dimension1();
    private Dimension2 dimension2 = new Dimension2();
    private Dimension3 dimension3 = new Dimension3();
    private Dimension1Groups dimension1Groups = new Dimension1Groups();
    private Dimension2Groups dimension2Groups = new Dimension2Groups();
    private Dimension3Groups dimension3Groups = new Dimension3Groups();
    private Dimension1GroupSetup dimension1GroupSetup = new Dimension1GroupSetup();
    private Dimension2GroupSetup dimension2GroupSetup = new Dimension2GroupSetup();
    private Dimension3GroupSetup dimension3GroupSetup = new Dimension3GroupSetup();
    private ItemDimension1Setup itemDimension1Setup = new ItemDimension1Setup();
    private ItemDimension2Setup itemDimension2Setup = new ItemDimension2Setup();
    private ItemDimension3Setup itemDimension3Setup = new ItemDimension3Setup();
    private ItemDimensionCombinations combinations = new ItemDimensionCombinations();
    private DimensionGroupSetup dimensionGroupSetup = new DimensionGroupSetup();
    private ColourDesignMaster colourDesign = new ColourDesignMaster();

    /** Creates a new instance of InventoryControlDimensions */
    public InventoryControlDimensions() {
        this.setMenuName("Dimensions");
        
        this.setMenuList(additionalDimensions);
        
        this.setMenuList(dimension1);
        this.setMenuList(dimension3);
        this.setMenuList(dimension2);

        this.setMenuList(colourDesign);

        this.setMenuList(dimension1Groups);
        this.setMenuList(dimension3Groups);
        this.setMenuList(dimension2Groups);

        this.setMenuList(dimension1GroupSetup);
        this.setMenuList(dimension3GroupSetup);
        this.setMenuList(dimension2GroupSetup);

        this.setMenuList(itemDimension1Setup);
        this.setMenuList(itemDimension3Setup);
        this.setMenuList(itemDimension2Setup);

        this.setMenuList(dimensionGroupSetup);
        this.setMenuList(combinations);
    }
}

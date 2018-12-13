/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.inventory;

/**
 * Represents an entity containing an item and dimensions
 *
 * @author riaan
 */
public interface ItemDimensionInterface {

    public void setItemId(String itemId);

    public String getItemId();

    public void setDimension1(String dimension1);

    public String getDimension1();

    public void setDimension2(String dimension2);

    public String getDimension2();

    public void setDimension3(String dimension3);

    public String getDimension3();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.inventory;

/**
 * This interface is intended to be implemented on data source entities where
 * dimensions have to be set according to a dimension id..
 *
 * @author wikus
 */
public interface DimensionIDInterface {

    public String getItemId();

    public void setItemId(String itemId);

    public void setDimensionId(long recordID);

    public long getDimensionId();

    public void setDimension1(String dimension1);

    public String getDimension1();

    public void setDimension2(String dimension2);

    public String getDimension2();

    public void setDimension3(String dimension3);

    public String getDimension3();

    public void setWarehouse(String warehouse);

    public String getWarehouse();

    public void setLocation(String location);

    public String getLocation();

    public void setSerial(String serial);

    public String getSerial();

    public void setBatch(String batch);

    public String getBatch();

    public void setPallet(String pallet);

    public String getPallet();
}

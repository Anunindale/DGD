package emc.datatypes.base.employeetable;

import emc.datatypes.EMCDouble;

/** 
 *
 * @author wikus
 */
public class CostPricePerHour extends EMCDouble { 

/** Creates a new instance of CostPricePerHour */
public CostPricePerHour() {
this.setEmcLabel("Cost Price Per Hour");
this.setEditable(true);
this.setNumberDecimalsDisplay(2);
}
}
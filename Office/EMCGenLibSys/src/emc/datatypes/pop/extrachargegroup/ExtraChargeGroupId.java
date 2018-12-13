package emc.datatypes.pop.extrachargegroup;


import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ExtraChargeGroupId extends EMCString {

    /** Creates a new instance of ExtraChargeGroupId */
    public ExtraChargeGroupId() {
        this.setEmcLabel("Extra Charge Group Id");
        this.setMandatory(true);
        this.setEditable(true);
    }
}

package emc.datatypes.hr.remunerantiontype;

import emc.datatypes.EMCString;

/** 
 *
 * @author claudette
 */
public class RemunerationType extends EMCString {

    /** Creates a new instance of RemunerationType. */
    public RemunerationType() {
        this.setEmcLabel("Remuneration Type");
        this.setMandatory(true);
    }
}
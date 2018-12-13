package emc.datatypes.base.numbersequences;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class SuffixString extends EMCString {

    /** Creates a new instance of SuffixString */
    public SuffixString() {
        this.setEmcLabel("Suffix String");
        this.setEditable(true);
        this.setStringSize(50);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.sop;

/**
 *
 * @author riaan
 */
public enum SalesDeliveryRules {

    STK(0, "STK"),
    DESP(1, "DESP");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of ProductionWorkTypes */
    SalesDeliveryRules(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public static SalesDeliveryRules fromString(final String str) {
        for (SalesDeliveryRules e : SalesDeliveryRules.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static SalesDeliveryRules fromId(final int id) {
        for (SalesDeliveryRules e : SalesDeliveryRules.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

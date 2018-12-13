/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.gl;

/**
 *
 * @author claudette
 */
public enum GroupGranularityTypes {

    DAY(0, "Day"),
    WEEK(1, "Week"),
    PERIOD(2, "Period"),
    NONE(3, "None");
    
    final int id;
    final String label;

    private GroupGranularityTypes(final int id, final String label) {
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

    public static GroupGranularityTypes fromString(final String str) {
        for (GroupGranularityTypes e : GroupGranularityTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static GroupGranularityTypes fromId(final int id) {
        for (GroupGranularityTypes e : GroupGranularityTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

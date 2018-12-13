/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.trec;

/**
 *
 * @author wikus
 */
public enum TRECPackingGroupEnum {

    I(0, "I"),
    II(1, "II"),
    III(2, "III");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  TRECPackingGroupEnum*/
    TRECPackingGroupEnum(final int id, final String label) {
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

    public static TRECPackingGroupEnum fromString(final String str) {
        for (TRECPackingGroupEnum e : TRECPackingGroupEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static TRECPackingGroupEnum fromId(final int id) {
        for (TRECPackingGroupEnum e : TRECPackingGroupEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

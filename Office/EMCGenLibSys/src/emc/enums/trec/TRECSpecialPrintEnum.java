/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.trec;

/**
 *
 * @author rico
 */
public enum TRECSpecialPrintEnum {

    NONE(0, "None"),
    PRINT_DIAMOND(1, "Diamond"),
    PRINT_PLACCARD(2, "Placard");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  TRECPackingGroupEnum*/
    TRECSpecialPrintEnum(final int id, final String label) {
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

    public static TRECSpecialPrintEnum fromString(final String str) {
        for (TRECSpecialPrintEnum e : TRECSpecialPrintEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static TRECSpecialPrintEnum fromId(final int id) {
        for (TRECSpecialPrintEnum e : TRECSpecialPrintEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

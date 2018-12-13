/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.trec;

/**
 *
 * @author rico
 */
public enum TRECTypeEnum {
  
    H(0, "H"),
    P(1, "P"),
    Q(2, "Q"),
    D(3, "D"),
    S(4, "S"),
    F(5, "F"),
    A(6, "A"),
    E(7, "E");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  TRECPackingGroupEnum*/
    TRECTypeEnum(final int id, final String label) {
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

    public static TRECTypeEnum fromString(final String str) {
        for (TRECTypeEnum e : TRECTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static TRECTypeEnum fromId(final int id) {
        for (TRECTypeEnum e : TRECTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

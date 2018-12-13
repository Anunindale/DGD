/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.trec;

/**
 *
 * @author rico
 */
public enum TRECWebStoreTypeEnum {

    NONE(0, "None"),
    PLACARD(1, "Placard"),
    STANDARD(2, "Standard");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  TRECPackingGroupEnum*/
    TRECWebStoreTypeEnum(final int id, final String label) {
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

    public static TRECWebStoreTypeEnum fromString(final String str) {
        for (TRECWebStoreTypeEnum e : TRECWebStoreTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static TRECWebStoreTypeEnum fromId(final int id) {
        for (TRECWebStoreTypeEnum e : TRECWebStoreTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

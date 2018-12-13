/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.trec;

/**
 *
 * @author rico
 */
public enum TRECPrintTypeEnum {
  
    
    PRINT_SELF(0, "PRINT SELF"),
    PLEASE_PRINT(1, "PLEASE PRINT"),
    PLEASE_LAMINATE(2, "PLEASE LAMINATE");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  TRECPackingGroupEnum*/
    TRECPrintTypeEnum(final int id, final String label) {
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

    public static TRECPrintTypeEnum fromString(final String str) {
        for (TRECPrintTypeEnum e : TRECPrintTypeEnum.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static TRECPrintTypeEnum fromId(final int id) {
        for (TRECPrintTypeEnum e : TRECPrintTypeEnum.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

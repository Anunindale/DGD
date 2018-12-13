/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.enums.debtors.creditnotes;


/**
 *
 * @author riaan
 */
public enum DebtorsCreditNoteReturnOptions {

    STK(0, "STK"),
    FSTR(1, "FSTR");
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  DebtorsCreditNoteReturnOptions. */
    DebtorsCreditNoteReturnOptions(final int id, final String label) {
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

    public static DebtorsCreditNoteReturnOptions fromString(final String str) {
        for (DebtorsCreditNoteReturnOptions e : DebtorsCreditNoteReturnOptions.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static DebtorsCreditNoteReturnOptions fromId(final int id) {
        for (DebtorsCreditNoteReturnOptions e : DebtorsCreditNoteReturnOptions.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

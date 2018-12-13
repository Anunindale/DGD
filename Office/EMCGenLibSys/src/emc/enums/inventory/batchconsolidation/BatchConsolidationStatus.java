/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.inventory.batchconsolidation;

/**
 *
 * @author wikus
 */
public enum BatchConsolidationStatus {

    CREATED(0, "Created"),
    CAPTURE_SHORT(1, "Capture Short"),
    APPROVED(2, "Approved"),
    POSTED(3, "Posted");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of ItemClassifications
     */
    BatchConsolidationStatus(final int id, final String label) {
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

    public static BatchConsolidationStatus fromString(final String str) {
        for (BatchConsolidationStatus e : BatchConsolidationStatus.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static BatchConsolidationStatus fromId(final int id) {
        for (BatchConsolidationStatus e : BatchConsolidationStatus.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
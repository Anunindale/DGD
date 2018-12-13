/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.EMCImage;

/**
 *
 * @author wikus
 */
public enum enumImageButton {
    
    EMC_IMAGE_BUTTON(0, "EMC Image Button");
    //Enum variables
    final int id;
    final String label;

    /**
     * Creates a new instance of WhereLineSpecialPermissions
     */
    enumImageButton(final int id, final String label) {
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

    /**
     * Returns the enum value for the given String, or null if no value is
     * found.
     */
    public static enumImageButton fromString(final String str) {
        for (enumImageButton e : enumImageButton.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Returns the enum value for the given id, or null if no value is found.
     */
    public static enumImageButton fromId(final int id) {
        for (enumImageButton e : enumImageButton.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

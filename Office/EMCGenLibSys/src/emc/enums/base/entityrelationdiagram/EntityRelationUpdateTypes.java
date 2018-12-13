/*
 * enumMenuTypes.java
 *
 * Created on 18 September 2007, 11:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.enums.base.entityrelationdiagram;

/**
 *
 * @author rico
 */
public enum EntityRelationUpdateTypes {

    //Update
    UPDATE_CASCADE_SQL(0, "UPDATE_CASCADE_SQL"),
    UPDATE_CASCADE_BEAN(1, "UPDATE_CASCADE_BEAN"),
    UPDATE_CLEARFIELD_SQL(2, "UPDATE_CLEARFIELD_SQL"),
    UPDATE_CLEARFIELD_BEAN(3, "UPDATE_CLEARFIELD_BEAN"),
    UPDATE_RESTRICT_SQL(4, "UPDATE_RESTRICT_SQL"),
    UPDATE_RESTRICT_BEAN(5, "UPDATE_RESTRICT_BEAN"),
    UPDATE_IGNORE_SQL(6, "UPDATE_IGNORE_SQL"),
    UPDATE_IGNORE_BEAN(7, "UPDATE_IGNORE_BEAN"),
    //Delete
    DELETE_CASCADE_SQL(8, "DELETE_CASCADE_SQL"),
    DELETE_CASCADE_BEAN(9, "DELETE_CASCADE_BEAN"),
    DELETE_CLEARFIELD_SQL(10, "DELETE_CLEARFIELD_SQL"),
    DELETE_CLEARFIELD_BEAN(11, "DELETE_CLEARFIELD_BEAN"),
    DELETE_RESTRICT_SQL(12, "DELETE_RESTRICT_SQL"),
    DELETE_RESTRICT_BEAN(13, "DELETE_RESTRICT_BEAN"),
    DELETE_IGNORE_SQL(14, "DELETE_IGNORE_SQL"),
    DELETE_IGNORE_BEAN(15, "DELETE_IGNORE_BEAN");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    EntityRelationUpdateTypes(final int id, final String label) {
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

    public static EntityRelationUpdateTypes fromString(final String str) {
        for (EntityRelationUpdateTypes e : EntityRelationUpdateTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EntityRelationUpdateTypes fromId(final int id) {
        for (EntityRelationUpdateTypes e : EntityRelationUpdateTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

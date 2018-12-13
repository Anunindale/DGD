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
public enum EntityRelationTypes {

    //Update
    ONE(0, "ONE"),
    MANY(1, "MANY");
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of DocumentTypes */
    EntityRelationTypes(final int id, final String label) {
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

    public static EntityRelationTypes fromString(final String str) {
        for (EntityRelationTypes e : EntityRelationTypes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EntityRelationTypes fromId(final int id) {
        for (EntityRelationTypes e : EntityRelationTypes.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.enums.base.timedoperations;

import emc.enums.modules.enumEMCModules;

/**
 *
 * @author rico
 */
public enum EnumTimedOperations {

    /*  NAME(0, "Module", "NAME")  */
    //Demand Management
    ACTIVE_MODEL_EXPLOSIONS(0, "Demand Management", "ACTIVE_MODEL_EXPLOSIONS"),
    //Work Flow
    ACTIVITY_ALERTS(1, "Work Flow", "ACTIVITY_ALERTS"),
    //MPS
    POPULATE_MPS(2, enumEMCModules.MASTERPRODSCH.toString(), "POPULATE_MPS"),
    RELEASE_SMOOTHING(201, enumEMCModules.MASTERPRODSCH.toString(), "RELEASE_SMOOTHING"),
    //BASE
    RUN_BATCH_PROCESS(3, enumEMCModules.BASE.toString(), "RUN_BATCH_PROCESS"),
    AUTO_EMAIL_SMS(301, enumEMCModules.BASE.toString(), "AUTO_EMAIL_SMS"),
    //Inventory
    UPDATE_EXPLODED_REQUIREMENTS(4, enumEMCModules.INVENTORY.toString(), "UPDATE_EXPLODED_REQUIREMENTS"),
    //MRP 
    RUN_MRP(5, enumEMCModules.MATERIALPLAN.toString(), "RUN_MRP"),
    //GL
    GL_CONSOLIDATION(6, enumEMCModules.GENERAL_LEDGER.toString(), "GL_CONSOLIDATION"),
    //SOP
    SOP_POPULATE_DAILY_FIGURES(700, enumEMCModules.SOP.toString(), "SOP_POPULATE_DAILY_FIGURES");
    //Enum variables
    final int id;
    final String module;
    final String label;

    /**
     * Creates a new instance of EnumReports
     */
    EnumTimedOperations(final int id, final String module, final String label) {
        this.id = id;
        this.module = module;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getModule() {
        return module;
    }

    @Override
    public String toString() {
        return label;
    }

    public static EnumTimedOperations fromString(final String str) {
        for (EnumTimedOperations e : EnumTimedOperations.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static EnumTimedOperations fromId(final int id) {
        for (EnumTimedOperations e : EnumTimedOperations.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.workflow;

/**
 *
 * @author rico
 */
public enum ClientWorkFlowMethods {
    //Skills 
   GENERAL_METHOD(0,"GENERAL_METHOD");
   private final int id;
    private final String label;
 
    ClientWorkFlowMethods(final int id, final String label) {
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
    public static ClientWorkFlowMethods fromString(final String str) {
        for (ClientWorkFlowMethods e : ClientWorkFlowMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
 
    public static ClientWorkFlowMethods fromId(final int id) {
        for (ClientWorkFlowMethods e : ClientWorkFlowMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

}

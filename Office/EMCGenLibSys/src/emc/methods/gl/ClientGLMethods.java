/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.gl;

/**
 *
 * @author riaan
 */
public enum ClientGLMethods {
    GENERAL_METHOD(0,"GENERAL_METHOD");
    private final int id;
    private final String label;
 
    ClientGLMethods(final int id, final String label) {
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
    public static ClientGLMethods fromString(final String str) {
        for (ClientGLMethods e : ClientGLMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }
}

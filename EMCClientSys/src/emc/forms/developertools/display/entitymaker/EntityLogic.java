/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.entitymaker;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.DefaultListModel;
/**
 *
 * @author wikus
 */
public class EntityLogic {
    private static String finalCode;
    private static List<String> uniqueKeys;

    public static DefaultListModel addFieldKey(String fieldType, String fieldName, DefaultListModel model) {
        model.addElement(fieldType + " " + fieldName);
        return model;
    }

    public static void removeFieldKey(Object[] items, DefaultListModel model) {
        for (Object o : items) {
            model.removeElement(o);
        }
    }

    public static void setUniqueConstraints(Object[] items) {
        String variable;
        if (uniqueKeys == null) {
            uniqueKeys = new ArrayList<String>();
        }
        for (Object o : items) {
            variable = o.toString();
            uniqueKeys.add(variable.substring(variable.indexOf(" ") + 1, variable.length()));
        }
    }

    public static String generateCode(String packageName, String className, DefaultListModel model) {
        finalCode = "package " + packageName + "; \n";
        finalCode += "import emc.framework.EMCEntityClass; \n";
        finalCode += "import javax.persistence.Entity; \n";
        finalCode += "import javax.persistence.Table; \n";
        finalCode += "import javax.persistence.UniqueConstraint; \n";
        finalCode += "import java.util.HashMap; \n";
        finalCode += "@Entity \n";
        addUniqueConstraints(className);
        finalCode += "public class " + className + " extends EMCEntityClass { \n";
        addVariables(model);
        finalCode += "public " + className + "() { \n } \n";
        addGettersAndSetters(model);
        addBuildFieldList();
        finalCode += "}";
        return finalCode;
    }

    private static void addUniqueConstraints(String className) {
        finalCode += "@Table(name = \"" + className + "\", uniqueConstraints = {@UniqueConstraint(columnNames = {\"companyId\"";
        if (uniqueKeys != null) {
            for (String s : uniqueKeys) {
                finalCode += ", \"" + s + "\"";
            }
        }
        finalCode += "})}) \n";
    }

    private static void addVariables(DefaultListModel model) {
        Enumeration e = model.elements();
        String variable;
        while (e.hasMoreElements()) {
            variable = e.nextElement().toString();
            finalCode += "private " + variable + "; \n";
        }
    }

    private static void addGettersAndSetters(DefaultListModel model) {
        Enumeration e = model.elements();
        String variable;
        String type;
        String name;
        while (e.hasMoreElements()) {
            variable = e.nextElement().toString();
            type = variable.substring(0, variable.indexOf(" "));
            name = variable.substring(variable.indexOf(" ") + 1, variable.length());
            finalCode += "public void set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length()) + "(" + type + " " + name + ") { \n" +
                    "this." + name + " = " + name + "; \n } \n";
            finalCode += "public " + type + " get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length()) + "() { \n" +
                    "return this." + name + "; \n } \n";
        }
    }

    private static void addBuildFieldList() {
        finalCode += "@Override \n" +
                "public HashMap buildFieldList() { \n" +
                "HashMap toBuild = super.buildFieldList(); \n\n" +
                "return toBuild; \n } \n";
    }
}

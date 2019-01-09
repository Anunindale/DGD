/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.resources;

import emc.app.components.emcJButton;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.enums.modules.enumEMCModules;
import emc.forms.developertools.display.querytester.QueryTesterForm;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author riaan
 */
public class ExecuteButton extends emcJButton {

    private EMCCommandClass executeCmd;
    private ArrayList<Object> toSend = new ArrayList<Object>();
    private QueryTesterForm form;
    private List<Object> results;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root;
    private List<String> classesToDisplay;
    private DefaultMutableTreeNode curNode;
    private Object curObject;
    private List<Field> fields;

    //Creates a new instance of ExecuteButton.  This button may only be added to an instance of QueryForm 
    public ExecuteButton(String text) {
        super(text);

        classesToDisplay = new ArrayList<String>();
        classesToDisplay.add("Boolean");
        classesToDisplay.add("String");
        classesToDisplay.add("Integer");
        classesToDisplay.add("Double");
        classesToDisplay.add("Long");
        classesToDisplay.add("Object");

        executeCmd = new EMCCommandClass();
        executeCmd.setCommandId(emc.commands.EMCCommands.SERVER_GENERAL_COMMAND.getId());
        executeCmd.setModuleNumber(enumEMCModules.DEVELOPERTOOLS.getId());
        executeCmd.setMethodId(emc.methods.developertools.ServerDeveloperToolMethods.TESTQUERY.toString());
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        form = (QueryTesterForm) utilFunctions.findParent(this, QueryTesterForm.class);

        toSend.clear();
        toSend.add(form.getQueryArea().getText());

        results = EMCWSManager.executeGenericWS(executeCmd, toSend, form.getUserData());

        //form.getResultArea().setText(proccessResults(results));
        proccessResults(results);

        form.getResultArea().setModel(treeModel);
    }

    private void proccessResults(List<Object> results) {
        root = new DefaultMutableTreeNode("Query Results (" + (results.size() - 1) + ")");
        treeModel = new DefaultTreeModel(root);

        //Command
        if (results.size() != 0) {
            results.remove(0);
        }

        for (Object ob : results) {
            root = makeNode(root, ob.getClass(), ob);
        }

        treeModel.reload();
    }

    private DefaultMutableTreeNode makeNode(DefaultMutableTreeNode parent, Class theClass, Object theInstance) {

//        if (theInstance != null) {
//            if (classesToDisplay.contains(theClass.getSimpleName())) {
//                curNode = new DefaultMutableTreeNode(theClass.getSimpleName() + " : " + theInstance);
//            } else {
//                fields = findFieldsUpToSuperClass(theClass, Object.class);
//                curNode = new DefaultMutableTreeNode(theClass.getSimpleName());
//
//                for (Field field : fields) {
//                    try {
//                        field.setAccessible(true);
//                        curNode = makeNode(curNode, field.getType(), field.get(theInstance));
//                    } catch (IllegalAccessException ex) {
//                        if (EMCDebug.getDebug()) {
//                            java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Could not access field: " + field.getName());
//                        }
//                    }
//                }
//            }
//        } else {
//            curNode = new DefaultMutableTreeNode(theClass.getSimpleName() + " : " + theInstance);
//        }
//        
//        parent.add(curNode);
//        
//        return parent;

        curNode = new DefaultMutableTreeNode(theClass.getSimpleName());

        curObject = null;

        if (classesToDisplay.contains(theClass.getSimpleName())) {
            parent.add(new DefaultMutableTreeNode(theClass.getSimpleName() + " : " + theInstance.toString()));
            return parent;
        } else {
            fields = findFieldsUpToSuperClass(theClass, Object.class);
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    curObject = field.get(theInstance);

                    if (curObject != null && classesToDisplay.contains(curObject.getClass())) {
                        curNode = makeNode(curNode, curObject.getClass(), curObject);
                    } else {
                        curNode.add(new DefaultMutableTreeNode(field.getName() + " : " + field.get(theInstance)));
                    }
                } catch (IllegalAccessException ex) {
                    if (EMCDebug.getDebug()) {
                        java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Could not access field: " + field.getName());
                    }
                }
            }
        }

        parent.add(curNode);

        return parent;
    }

    //This method is used to find fields of a class, up to the fields in the given superclass
    public ArrayList<Field> findFieldsUpToSuperClass(Class theClass, Class theSuperClass) {
        ArrayList<Field> ret = new ArrayList<Field>();

        while (theClass != theSuperClass) {
            ret.addAll(Arrays.asList(theClass.getDeclaredFields()));
            theClass = theClass.getSuperclass();

            if (theClass == null) {
                break;
            }
        }

        return ret;
    }
}

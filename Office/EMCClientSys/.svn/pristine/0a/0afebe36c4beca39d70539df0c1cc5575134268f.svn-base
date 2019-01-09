/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.usermenusetup.resources;

import emc.app.components.dialogs.EMCDialogFactory;
import emc.framework.EMCDebug;
import emc.functions.xml.EMCXMLHandler;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author rico
 */
public class DecodeUserTree {
    public DecodeUserTree(){
        
    }
    /**
     * Used to decode xml string holding user permissions
     * @param toParse
     * @return List positions 0 is a tree node 1 is a hashmap of valid menu items
     */
    public static List decodeTreeAndBuldHashmap(String toParse){
        toParse = new EMCXMLHandler().reinstateNewLines(toParse);
        List ret = new ArrayList();
        HashMap theMap = new HashMap();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("EMC");
        DefaultTreeModel theModel = new DefaultTreeModel(root);
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(toParse));
            
            int type = 0;
            UserTreePersistedObject node = null;
            
            DefaultMutableTreeNode n = root;
            
            while (reader.hasNext()) {
                type = reader.next();
                
                switch(type) {
                    case XMLStreamConstants.START_ELEMENT: 
                        
                        String name = reader.getLocalName(); 
                        if (name.equalsIgnoreCase("UserTreePersistedObject")) {
                            node = new UserTreePersistedObject();
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if ("THEPATH".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setThePath(reader.getAttributeValue(i));
                                }
                            }
                            DefaultMutableTreeNode child;
                            if(node.getThePath().equals("EMC")){
                                child = new DefaultMutableTreeNode("EMC"); 
                            }else{
                                Class pClass = Class.forName(node.getThePath());
                                Constructor c = pClass.getConstructor(new Class[]{});
                                Object o = c.newInstance(new Object[]{});
                                child = new DefaultMutableTreeNode(o);  
                                //build hashmap
                                theMap.put(o.getClass().getName(), true);
                            }
                            
                            theModel.insertNodeInto(child, n, n.getChildCount());
                            n = child;
                            break;
                        }
                        
                    case XMLStreamConstants.END_ELEMENT: 
                        if (reader.getLocalName().equalsIgnoreCase("UserTreePersistedObject")) {
                            n = (DefaultMutableTreeNode) n.getParent();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            EMCDialogFactory.createMessageDialog(null, "Failed to construct menu", "Failed to construct menu - " + e.getMessage());
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct menu - " + e.getMessage());
        }
        ret.add(theModel.getRoot());
        ret.add(theMap);
        return ret;
        
    }
    /**
     * decodes xml string of user persmissons
     * @param toParse
     * @return tree node holding valid permissions
     */
    public static DefaultMutableTreeNode decodeTree(String toParse) {   
        toParse = new EMCXMLHandler().reinstateNewLines(toParse);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("EMC");
        DefaultTreeModel theModel = new DefaultTreeModel(root);
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(toParse));
            
            int type = 0;
            UserTreePersistedObject node = null;
            
            DefaultMutableTreeNode n = root;
            
            while (reader.hasNext()) {
                type = reader.next();
                
                switch(type) {
                    case XMLStreamConstants.START_ELEMENT: 
                        
                        String name = reader.getLocalName(); 
                        if (name.equalsIgnoreCase("UserTreePersistedObject")) {
                            node = new UserTreePersistedObject();
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if ("THEPATH".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setThePath(reader.getAttributeValue(i));
                                }
                            }
                            DefaultMutableTreeNode child;
                            if(node.getThePath().equals("EMC")){
                                child = new DefaultMutableTreeNode("EMC"); 
                            }else{
                                
                                try{
                                    Class pClass = Class.forName(node.getThePath());
                                    Constructor c = pClass.getConstructor(new Class[]{});
                                    Object o = c.newInstance(new Object[]{});
                                    child = new DefaultMutableTreeNode(o);  
                                }catch(java.lang.ClassNotFoundException e){
                                    child = new DefaultMutableTreeNode("No Item");
                                    Logger.getLogger("emc").log(Level.WARNING,"A menu component has moved: "+ node.getThePath(),e);
                                }
                            }
                            
                            theModel.insertNodeInto(child, n, n.getChildCount());
                            n = child;
                            break;
                        }
                        
                    case XMLStreamConstants.END_ELEMENT: 
                        if (reader.getLocalName().equalsIgnoreCase("UserTreePersistedObject")) {
                            n = (DefaultMutableTreeNode) n.getParent();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            if(EMCDebug.getDebug())Logger.getLogger("emc").log(Level.WARNING,"Failed to parse UserTreePersisted",e);
        }
        return (DefaultMutableTreeNode)theModel.getRoot();
    }                      


}

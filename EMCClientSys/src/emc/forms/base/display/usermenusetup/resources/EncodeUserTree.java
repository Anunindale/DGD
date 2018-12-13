/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.usermenusetup.resources;


import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author rico
 */
public class EncodeUserTree {
    
    public EncodeUserTree(){
        
    }
    public static String encodeUserTree(DefaultMutableTreeNode root){
         
         StringBuffer xml = new StringBuffer();
         encode(root, xml);
         xml.insert(0, "<tree>\r\n ");
         xml.append("\r\n</tree>");
        return xml.toString();
    }
    private static void encode(DefaultMutableTreeNode node, StringBuffer xml) {
        Enumeration e = node.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();

            if (xml.length() > 0) {
                xml.append("\r\n");
            }
           
            xml.append(encode(n.getUserObject()));
            if (n.getChildCount() == 0) {
                xml.append("</userTreePersistedObject>");
            } else {
                encode(n, xml);
                xml.append("\r\n</userTreePersistedObject>");
            }
        }
    }
    private static String encode (Object node){
        if (node != null) {
            return "<userTreePersistedObject thePath='" + node.getClass().getName() +  "'>";
        }
        return "";
        
    }

}

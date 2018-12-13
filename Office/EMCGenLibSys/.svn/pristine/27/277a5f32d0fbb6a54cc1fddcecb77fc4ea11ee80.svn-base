/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.gl;

import emc.framework.EMCDebug;
import emc.functions.xml.EMCXMLHandler;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author claudette
 */
public class DecodeEncodeAnalysisHier {

    public DecodeEncodeAnalysisHier() {
    }

    public String encodeTree(DefaultMutableTreeNode root){

         StringBuffer xml = new StringBuffer();
         //xml.append(encode(root.getUserObject()));
         encode(root, xml);
         xml.insert(0, "<tree>\r\n ");
         xml.append("\r\n</tree>");
        return xml.toString();
    }
    private  void encode(DefaultMutableTreeNode node, StringBuffer xml) {
        Enumeration e = node.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();

            if (xml.length() > 0) {
                xml.append("\r\n");
            }

            xml.append(encode(n.getUserObject()));
            if (n.getChildCount() == 0) {
                xml.append("</AnalysisDS>");
            } else {
                encode(n, xml);
                xml.append("\r\n</AnalysisDS>");
            }
        }
    }

    private  String encode (Object node){
        if (node != null) {
            AnalysisCodeDS obj = (AnalysisCodeDS) node;
            return "<AnalysisDS analysis='" + obj.getAnalysis() + "' recordID='" +Long.toString(obj.getRecordId()) +
                    "' analysisEntityClassName='" + obj.getAnalysisEntityClassName() +"' description='" + obj.getDescription() +"'>";
        }
        return "";
    }

    public DefaultMutableTreeNode decodeTree(String toParse){
        toParse = new EMCXMLHandler().reinstateNewLines(toParse);
        AnalysisCodeDS analysisDS = new AnalysisCodeDS();
        analysisDS.setAnalysis("Analysis Group");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(analysisDS);
        DefaultTreeModel theModel = new DefaultTreeModel(root);
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(toParse));

            int type = 0;
            AnalysisCodeDS node = null;

            DefaultMutableTreeNode n = root;

            while (reader.hasNext()) {
                type = reader.next();

                switch(type) {
                    case XMLStreamConstants.START_ELEMENT:

                        String name = reader.getLocalName();
                        if (name.equalsIgnoreCase("AnalysisDS")) {
                            node = new AnalysisCodeDS();
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if ("analysis".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setAnalysis(reader.getAttributeValue(i));
                                }
                                if ("recordId".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setRecordId(Long.parseLong(reader.getAttributeValue(i)));
                                }
                                if ("analysisEntityClassName".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setAnalysisEntityClassName(reader.getAttributeValue(i));
                                }
                                if ("description".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    node.setDescription(reader.getAttributeValue(i));
                                }
                            }
                            DefaultMutableTreeNode child = new DefaultMutableTreeNode(node);
                            theModel.insertNodeInto(child, n, n.getChildCount());
                            n = child;
                            break;
                        }

                    case XMLStreamConstants.END_ELEMENT:
                        if (reader.getLocalName().equalsIgnoreCase("AnalysisDS")) {
                            n = (DefaultMutableTreeNode) n.getParent();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            if(EMCDebug.getDebug())Logger.getLogger("emc").log(Level.WARNING,"Failed to parse AnalysisDS",e);
        }
        return (DefaultMutableTreeNode)theModel.getRoot();

    }
}

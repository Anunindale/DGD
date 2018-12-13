/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.permissions;

import emc.bus.base.permissions.info.BasePermissionsInfoLocal;
import emc.entity.base.permissions.BasePermissionsTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author wikus
 */
@Stateless
public class BasePermissionsTableBean extends EMCEntityBean implements BasePermissionsTableLocal {

    @EJB
    private BasePermissionsInfoLocal permissionInfoBean;

    public BasePermissionsTable getUserPermissions(String userId, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BasePermissionsTable.class.getName());
        query.addAnd("userId", userId);
        BasePermissionsTable permissions = (BasePermissionsTable) util.executeSingleResultQuery(query, userData);
        if (permissions == null) {
            permissions = new BasePermissionsTable();
            permissions.setUserId(userId);
            this.insert(permissions, userData);
        }
        return permissions;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);
        if (!util.exists(query, userData)) {
            BasePermissionsTable permissions = new BasePermissionsTable();
            permissions.setUserId(EMCQuery.getFieldValueFromQuery("userId", query.toString()));
            try {
                this.insert(permissions, userData);
            } catch (EMCEntityBeanException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to find permissions.", userData);
            }
        }
        return super.getNumRows(theTable, userData);
    }

    /**
     * Returns a List containing the class names of all menu items that the
     * specified user has access to.
     *
     * @param userId ID of user for whom permissions should be fetched.
     * @param userData User data.
     * @return A list of menu items that the specified has access to
     */
    public List<String> getUserMenuItems(String userId, EMCUserData userData) {
        List<String> menuItems = new ArrayList<String>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BasePermissionsTable.class);
        query.addField("userMenu");
        query.addAnd("userId", userId);

        String userMenu = (String) util.executeSingleResultQuery(query, userData);

        //Decode user menu tree
        DefaultMutableTreeNode userMenuTree = decodeUserMenuXML(userMenu);

        this.addMenuItems(userMenuTree, menuItems, userData);
        
        return menuItems;
    }

    /** Decodes user menu XML. */
    private DefaultMutableTreeNode decodeUserMenuXML(String userMenuXML) {
        userMenuXML = new EMCXMLHandler().reinstateNewLines(userMenuXML);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("EMC");
        DefaultTreeModel theModel = new DefaultTreeModel(root);
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(userMenuXML));

            String path = null;

            int type = 0;

            DefaultMutableTreeNode n = root;

            while (reader.hasNext()) {
                type = reader.next();

                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String name = reader.getLocalName();
                        if (name.equalsIgnoreCase("UserTreePersistedObject")) {
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if ("THEPATH".equalsIgnoreCase(reader.getAttributeLocalName(i))) {
                                    path = reader.getAttributeValue(i);
                                }
                            }
                            DefaultMutableTreeNode child;
                            if ("EMC".equals(path)) {
                                child = new DefaultMutableTreeNode("EMC");
                            } else {
                                child = new DefaultMutableTreeNode(path);
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
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to parse UserTreePersisted", e);
            }
        }

        return root;
    }

    private void addMenuItems(DefaultMutableTreeNode menuNode, List<String> menuItems, EMCUserData userData) {
        if (menuNode.isLeaf()) {
            menuItems.add((String)menuNode.getUserObject());
        } else {
            Enumeration<DefaultMutableTreeNode> children = menuNode.children();

            while (children.hasMoreElements()) {
                addMenuItems(children.nextElement(), menuItems, userData);
            }
        }
    }
}

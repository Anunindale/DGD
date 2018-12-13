/*  
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.permissions.info;

import emc.bus.base.permissions.BasePermissionsTableLocal;
import emc.entity.base.Usertable;
import emc.entity.base.permissions.BasePermissionsTable;
import emc.entity.base.permissions.BaseUserPermissionsTable;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.xml.EMCXMLHandler;
import java.io.StringReader;
import java.util.ArrayList;
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
public class BasePermissionsInfoBean extends EMCBusinessBean implements BasePermissionsInfoLocal {

    @EJB
    private BasePermissionsTableLocal permissionsBean;

    @Override
    public List validateUserId(String userId, EMCUserData userData) {
        List ret = new ArrayList();
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, Usertable.class);
        query.addAnd("userId", userId);
        Usertable userRec = (Usertable) util.executeSingleResultQuery(query, userData);
        if (userRec == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "The value entered for User Id is invalid, please reselect", userData);
            ret.add(false);
            return ret;
        }
        ret.add(true);
        ret.add(userRec.getUserName());
        return ret;
    }

    @Override
    public List getPermissionInfoByUser(String userId, EMCUserData userData) {
        List ret = new ArrayList();

        BasePermissionsTable permissions;
        try {
            permissions = permissionsBean.getUserPermissions(userId, userData);
        } catch (EMCEntityBeanException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to get user permissions.", userData);
            return ret;
        }
        ret.add(decodeMenuTree(permissions.getUserMenu()));
        return ret;
    }

    private String decodeMenuTree(String toParse) {
        toParse = new EMCXMLHandler().reinstateNewLines(toParse);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("EMC");
        DefaultTreeModel theModel = new DefaultTreeModel(root);
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(toParse));

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
        return new EMCXMLHandler().encodeTree((DefaultMutableTreeNode) theModel.getRoot());
    }

    @Override
    public BaseUserPermissionsTable getDetailedPermissionInfoByUser(String userId, String formClassName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUserPermissionsTable.class);
        query.addAnd("userId", userId);
        query.addAnd("formClassName", formClassName);
        BaseUserPermissionsTable permission = (BaseUserPermissionsTable) util.executeSingleResultQuery(query, userData);
        if (permission == null) {
            return null;
        } else {
            return permission;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.permissions.resources;

import emc.app.components.emcJTree;
import emc.app.reporttools.JasperInformation;
import emc.app.wsmanager.EMCReportWSManager;
import emc.enums.base.reporttools.EnumReports;
import emc.framework.EMCCommandClass;
import emc.framework.EMCMenuItem;
import emc.framework.EMCMenuSuper;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author wikus
 */
public class PermissionInfoPopup extends JPopupMenu implements ActionListener {

    private emcJTree permissionTree;
    private EMCUserData userData;
    private JMenuItem allUseres = new JMenuItem("Who has Permission?");

    public PermissionInfoPopup(emcJTree permissionTree, EMCUserData userData) {
        this.permissionTree = permissionTree;
        this.userData = userData;

        allUseres.addActionListener(this);
        this.add(allUseres);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareTo("Who has Permission?") == 0) {
            printPermissionsReport();
        }
    }

    private void printPermissionsReport() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) permissionTree.getSelectionPath().getLastPathComponent();

        Object nodeObject = node.getUserObject();
        if (nodeObject instanceof EMCMenuSuper) {
            DefaultMutableTreeNode adminRoot = new DefaultMutableTreeNode("Admin");
            adminRoot = emc.app.util.populateMenu.getNode(adminRoot, (EMCMenuSuper) nodeObject);
            node = adminRoot;
        }

        List toSend = new ArrayList();

        populateToSend(node, toSend);

        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.PRINT_PERMISSION_INFO);

        JasperInformation jasperInfo = new JasperInformation();
        jasperInfo.setJasperTemplate("/emc/reports/base/permissions/BasePermissionInfoReport.jrxml");
        jasperInfo.setXmlNodePath("/emcmsg/emc.reports.base.BasePermissionsInfoReportDS");
        jasperInfo.setReportTitle("Permission Info");

        EMCReportWSManager.generateReport(cmd, jasperInfo, EnumReports.BASE_PERMISSION_INFO, toSend, userData);
    }

    private List populateToSend(DefaultMutableTreeNode node, List toSend) {
        EMCMenuItem menuItem;
        List holderList;
        Enumeration<DefaultMutableTreeNode> children = node.children();
        DefaultMutableTreeNode child;
        Object childObject;
        while (children.hasMoreElements()) {
            child = children.nextElement();
            childObject = child.getUserObject();
            if (child.isLeaf() && childObject instanceof EMCMenuItem) {
                menuItem = (EMCMenuItem) child.getUserObject();
                holderList = new ArrayList();
                holderList.add(menuItem.getClass().getName());
                holderList.add(menuItem.getClassPath());
                toSend.add(holderList);
            } else {
                populateToSend(child, toSend);
            }
        }
        return toSend;
    }
}

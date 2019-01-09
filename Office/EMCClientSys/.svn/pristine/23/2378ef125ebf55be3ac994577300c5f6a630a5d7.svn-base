/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.workflow;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.crm.CRMProspect;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDebug;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.crm.menuitems.display.CRMProspectMenu;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class WorkFlowSourceButton extends emcJButton {

    private BaseInternalFrame theForm;
    private emcDataRelationManagerUpdate drm;
    private String sourceTableField;
    private String sourceRecordIdField;

    public WorkFlowSourceButton(BaseInternalFrame theForm, emcDataRelationManagerUpdate drm, String sourceTableField, String sourceRecordIdField) {
        super("Source");
        this.theForm = theForm;
        this.drm = drm;
        this.sourceTableField = sourceTableField;
        this.sourceRecordIdField = sourceRecordIdField;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        String tableName = (String) drm.getLastFieldValueAt(sourceTableField);
        Long recordId = (Long) drm.getLastFieldValueAt(sourceRecordIdField);
        if (tableName == null || recordId == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "No source found for the given record.", drm.getUserData());
            return;
        }
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, tableName);
        query.addAnd("recordID", recordId);
        EMCUserData createUD = drm.getUserData();
        List x = new ArrayList();
        x.add(query);
        createUD.setUserData(x);
        EMCMenuItem menuItem = getMenuItem(tableName);
        if (menuItem == null) {
            if (EMCDebug.getDebug()) Logger.getLogger("emc").log(Level.SEVERE, "Menu Item not set up.", createUD);
            Logger.getLogger("emc").log(Level.SEVERE, "No source found for the given record.", drm.getUserData());
            return;
        }
        theForm.getDeskTop().createAndAdd(menuItem, -1, -1, createUD, drm, 0);
    }

    public EMCMenuItem getMenuItem(String tableName) {
        if (tableName.equals(CRMProspect.class.getName())) {
            return new CRMProspectMenu();
        } 
        return null;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.declarationmaster;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pj
 */
public class DGDeclarationMasterForm extends BaseInternalFrame{
    
    private emcDataRelationManagerUpdate drm;
    
    public DGDeclarationMasterForm(EMCUserData userData)
    {
        super("Declarations", true, true, true, true, userData);
        this.setBounds(20,20,650,290);
        
        drm = new emcDataRelationManagerUpdate
                (
                new emcGenericDataSourceUpdate(new DGDeclarationMaster(), userData),
                userData
                );
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("defConsignor");
        drm.setFormTextId2("defOperator");
        
        this.initFrame();
    }
    
    private void initFrame()
    {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Master", createMasterTab());
        this.add(tabs, BorderLayout.CENTER);
    }
    
    private emcJPanel createMasterTab()
    {
        emcJPanel panel = new emcJPanel(new GridLayout(1,1));
        
        List<String> keys = new ArrayList<>();
        keys.add("decNumber");
        keys.add("customer");
        keys.add("defConsignor");
        keys.add("defOperator");
        
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        drm.setMainTableComponent(table);
        
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);
        
        panel.add(tableScroll);
        return panel;
    }
    
}

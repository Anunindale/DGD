/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.dimension2groups;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class Dimension2GroupsForm extends BaseInternalFrame {
    
    private emcJPanel pnlDimensionGroup = new emcJPanel();
    
     //DataSource
    private Dimension2GroupsDRM dataRelation;
    
    public Dimension2GroupsForm(EMCUserData userData){
        super("Size Groups", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Inventory/InventorySizeGroups.html"));
        try{
            
            dataRelation = new Dimension2GroupsDRM(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.dimensions.InventoryDimension2Group(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("dimensionGroupId");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void tabDimensionGroups(){
        List keys = new ArrayList();
        keys.add("dimensionGroupId");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDimensionGroup.setLayout(new GridLayout(1,1));
        pnlDimensionGroup.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabDimensionGroups();
        tabbedPanetop.add("Size Groups",this.pnlDimensionGroup); 
        this.add(tabbedPanetop, BorderLayout.CENTER);
        this.add(initButtons(), BorderLayout.EAST);
    }
    
    private emcJPanel initButtons(){
        emcJPanel pnlButtons = new emcJPanel();
        pnlButtons.setLayout(new GridBagLayout());
        pnlButtons.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        pnlButtons.add(new emcMenuButton("Setup", new emc.menus.inventory.menuitems.display.Dimension2GroupSetup(), this, 0, false), localg);
        y++;
        pnlButtons.add(new emcJLabel(),emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(120, 25));
        
        return pnlButtons;
    }
}

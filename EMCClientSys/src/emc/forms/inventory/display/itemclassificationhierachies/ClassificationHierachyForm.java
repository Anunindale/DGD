/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.itemclassificationhierachies;

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
import emc.framework.EMCMenuItem;
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
public class ClassificationHierachyForm extends BaseInternalFrame {
    
     private emcJPanel pnlHierarchies = new emcJPanel();
    
     //DataSource
    private ClassificationHierarchyDRM dataRelation;
    
    public ClassificationHierachyForm(EMCUserData userData){
        super("Classification Hierarchies", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setLayout(new BorderLayout());
        
        try{
            
            dataRelation = new ClassificationHierarchyDRM(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.classifications.InventoryClassificationHierarchy(),userData),userData);
            this.setDataManager(dataRelation);
            
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("hierarchyId");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void tabHierarchies(){
        List keys = new ArrayList();
        keys.add("hierarchyId");
        keys.add("description");
        
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlHierarchies.setLayout(new GridLayout(1,1));
        pnlHierarchies.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabHierarchies();
        tabbedPanetop.add("Hierarchies",this.pnlHierarchies); 
        this.add(tabbedPanetop);
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
        
        EMCMenuItem hierachiesItem = new emc.menus.inventory.menuitems.display.ClassificationHierarchySetup();
        hierachiesItem.setDoNotOpenForm(false);
        
        emcMenuButton button = new emcMenuButton("Setup", hierachiesItem, this, 0, false);
        pnlButtons.add(button, localg);
        y++;
        pnlButtons.add(new emcJLabel(),emcSetGridBagConstraints.endPanel(y));
        pnlButtons.setPreferredSize(new Dimension(110, 25));
        
        return pnlButtons;
    }
}

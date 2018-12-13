/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.activitystatus;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class wfacstatusForm extends BaseInternalFrame {
    private emcJPanel ratings = new emcJPanel();
     //DataSource
   
    private emcDataRelationManagerUpdate dataRelation;
    
    public wfacstatusForm(EMCUserData userData){
        super("Activity Status", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowActivityStatus.html"));
        try{
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.WORKFLOW.getId(),new emc.entity.workflow.WorkFlowActivityStatus(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("activityStatus");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    private void tabRatings(){
        List keys = new ArrayList();
        keys.add("activityStatus");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        ratings.setLayout(new GridLayout(1,1));
        ratings.add(topscroll);
        this.setTablePanel(topscroll);
    }
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabRatings();
        tabbedPanetop.add("Activity Status",this.ratings); 
        this.add(tabbedPanetop);
    }

}


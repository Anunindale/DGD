/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.ratings;

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
import java.awt.GridLayout;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class ratingsForm extends BaseInternalFrame {
    private emcJPanel ratings = new emcJPanel();
     //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    public ratingsForm(EMCUserData userData){
        super("Ratings", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowSkillRatings.html"));
        try{
            
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.WORKFLOW.getId(),new emc.entity.workflow.WorkFlowRating(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("rating");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    private void tabRatings(){
        List keys = new ArrayList();
        keys.add("rating");
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
        tabbedPanetop.add("Ratings",this.ratings); 
        this.add(tabbedPanetop);
    }
}

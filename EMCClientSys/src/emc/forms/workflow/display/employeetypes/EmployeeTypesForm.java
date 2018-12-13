/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.employeetypes;

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
 * @author riaan
 */
public class EmployeeTypesForm extends BaseInternalFrame {

    private emcJPanel pnlEmpTypes = new emcJPanel();
    
     //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    
    public EmployeeTypesForm(EMCUserData userData){
        super("Employee Types", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowEmployeeTypes.html"));
        try{
            
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.WORKFLOW.getId(),new emc.entity.workflow.WorkFlowEmployeeType(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeType");
            dataRelation.setFormTextId2("description");

        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void tabEmployeeTypes(){
        List keys = new ArrayList();
        keys.add("employeeType");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlEmpTypes.setLayout(new GridLayout(1,1));
        pnlEmpTypes.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabEmployeeTypes();
        tabbedPanetop.add("EmployeeTypes",this.pnlEmpTypes); 
        this.add(tabbedPanetop);
    }
}

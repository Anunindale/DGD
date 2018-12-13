/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.activitygroupemp;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class actGroupEmpForm extends BaseInternalFrame {
    private emcJPanel ratings = new emcJPanel();
    private emcJPanel empPanel = new emcJPanel();
    private emcJLabel actGroupLabel = new emcJLabel("Activity Group");
    private EMCLookupJTableComponent lookupEmp;
    private EMCLookupTableCellEditor empEditor;
    private EMCControlLookupComponent lookupActGrp;
    private emcJLabel activtyDescLabel = new emcJLabel("Description");
    private emcJTextField activtyDesc = new emcJTextField();
   
    //DataSource
    private EMCControlLookupComponentDRM dataRelation;
    
    public actGroupEmpForm(EMCUserData userData){
        super("Activity Grp Employee", true, true,true, true,userData);
        EMCUserData copyUD = userData.copyUserData();
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("WorkFlow/WorkFlowActivityGroupsEmployees.html"));

        List x = new ArrayList();
          x.add(0,"SELECT u FROM WorkFlowActivityGroupEmp u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.activityGroup = ''");
          x.add(1,"SELECT COUNT(*) FROM WorkFlowActivityGroupEmp u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.activityGroup = ''");
        userData.setUserData(x);  
        try{
        dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.WORKFLOW.getId(),new emc.entity.workflow.datasource.WorkFlowActivityGroupEmpDS(),userData),userData);

        lookupActGrp = new EMCControlLookupComponent(new emc.menus.workflow.menuitems.display.activityGroup(), dataRelation, "activityGroup", activtyDesc, "description", "WorkFlowActivityGroupEmp");
        dataRelation.setLookup(lookupActGrp);
        List actGroupKeys = new ArrayList();
        actGroupKeys.add("activityGroup");
        actGroupKeys.add("description");
        EMCLookupPopup actGrpPopUp = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(),new emc.entity.workflow.WorkFlowActivityGroups(),
            "activityGroup",actGroupKeys, copyUD);
        lookupActGrp.setPopup(actGrpPopUp);
        
        List employeeKeys = new ArrayList();
        employeeKeys.add("employeeNumber");
        employeeKeys.add("surname");
        employeeKeys.add("forenames");
        employeeKeys.add("emailAddress");
        employeeKeys.add("internalPhoneNumber");
        lookupEmp = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.employees());
        EMCLookupPopup empPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(), 
                                "employeeNumber", employeeKeys, copyUD);
        lookupEmp.setPopup(empPopup);
        empEditor = new EMCLookupTableCellEditor(lookupEmp);
        this.setDataManager(dataRelation);
        //add the form to the data relation
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("activityGroup");
        dataRelation.setFormTextId2("employeeNumber");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        addRelations();
        initFrame();
        
    }
    private void addRelations(){
        activtyDesc.setEditable(false);
        
    }
    private void createEmpPanel(){
        empPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Activity Group"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0,y,0.1,GridBagConstraints.FIRST_LINE_START);
        empPanel.add(this.actGroupLabel,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,1,y,0.1);
        empPanel.add(this.lookupActGrp,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,2,y,0.1);
        empPanel.add(this.activtyDescLabel,localg);
        localg = emcSetGridBagConstraints.changePosition(localg,2,y,0.1);
        empPanel.add(this.activtyDesc,localg);
       
    }
    private void tabActGroupEmp(){
        int y = 0;
        GridBagConstraints localg;
        List keys = new ArrayList();
        keys.add("employeeNumber");
        keys.add("employeeName");
        
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys){
            //Override super class method
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex){
                if(columnIndex == 1) {
                    return false;
                }
                return true;
            }  
            
        };
            
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        
        //lookups
        toptable.setLookupCellEditor(0, empEditor);
        
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        ratings.setLayout(new GridBagLayout());
        createEmpPanel();
        localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        ratings.add(empPanel,localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0,y,1.0,GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.BOTH;
        localg.weighty = 1.0;
        ratings.add(topscroll,localg);
        this.setTablePanel(topscroll);
    }
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabActGroupEmp();
        tabbedPanetop.add("Activity Groups Employee",this.ratings); 
        this.add(tabbedPanetop);
    }
}

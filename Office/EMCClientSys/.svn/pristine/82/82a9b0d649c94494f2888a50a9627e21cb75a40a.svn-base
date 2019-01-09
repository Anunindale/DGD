/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.employeeskills;

import emc.app.components.emcJTextField;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class empSkillEmpLookup extends EMCLookupFormComponent {
    private emcDataRelationManagerUpdate dataRelation;
    private emcJTextField surname;
    private String lastEmpSelected;
    
    public empSkillEmpLookup(EMCMenuItem gotoMainTableItem, 
            emcDataRelationManagerUpdate dataRelation, String fieldKey,emcJTextField surname) {
        super(gotoMainTableItem,dataRelation,fieldKey);
        this.dataRelation = dataRelation;
        this.surname = surname;
        
    }
    public void valueSetFromFormDRM(Object employeeId, Object surnameObj){
        surname.setText(surnameObj.toString());
        super.setValue(employeeId);
        if(employeeId!=null && !employeeId.equals(""))
            lastEmpSelected = employeeId.toString();
    }
    public void setValueOnNew(Object toSet){
        super.setValue(toSet);
    }
    @Override
    public void setValue(Object toSet) {
          //Force the table to refresh based on the lookup
        emcDataRelationManagerUpdate popupDRM = this.getLookupPopup().getDataRelation();

       surname.setText(popupDRM.getFieldValueAt(this.getLookupPopup().getSelectedRow(),"surname").toString());
       
          EMCUserData userData = dataRelation.getUserData();
          List x = new ArrayList();
          x.add(0,"SELECT u FROM WorkFlowEmployeeSkills u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.employeeNumber = '" +
                   toSet.toString() + "'");
          x.add(1,"SELECT COUNT(*) FROM WorkFlowEmployeeSkills u WHERE u.companyId = '"
                   + userData.getCompanyId() +"' AND u.employeeNumber = '" +
                   toSet.toString() + "'");
          userData.setUserData(x);
        dataRelation.setUserData(userData);
        super.setValue(toSet);
        if(toSet!=null && !toSet.equals(""))
            lastEmpSelected = toSet.toString();
    }

    public String getLastEmpSelected() {
        return lastEmpSelected;
    }
    
}

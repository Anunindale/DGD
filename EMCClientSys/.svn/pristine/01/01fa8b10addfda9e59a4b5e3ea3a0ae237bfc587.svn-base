/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.workflow.display.employeeskills;

import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.framework.EMCUserData;

/**
 *
 * @author rico
 */
public class employeeSkillDataManager extends emcDataRelationManagerUpdate {
    private empSkillEmpLookup empLookup;
    
    public employeeSkillDataManager(emcGenericDataSourceUpdate tableDataSource, EMCUserData userData){
        super(tableDataSource,userData);
        
    }
    public void setEmpSkillLookup(empSkillEmpLookup empLookup){
        this.empLookup = empLookup;
    }
    
    

    @Override
    public void deleteRowCache(int rowIndex) {
        super.deleteRowCache(rowIndex);
        empLookup.setValueOnNew(empLookup.getLastEmpSelected());
    }
    @Override
    public void insertRowCache(int rowIndex, boolean addRowAfter) {
        super.insertRowCache(rowIndex,addRowAfter);
       empLookup.setValueOnNew(empLookup.getLastEmpSelected());
    }
    @Override
    public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
        if(columnIndex.equals("skill") || columnIndex.equals("rating")|| columnIndex.equals("ratingDate")){
            super.setFieldValueAt(rowIndex, columnIndex, aValue);
            super.setFieldValueAt(rowIndex, "employeeNumber", empLookup.getLastEmpSelected());
            
        }

    }

    @Override
    public void setUserData(EMCUserData userData) {
        super.setUserData(userData);
        if(userData.getUserData().size() == 4)
        empLookup.valueSetFromFormDRM(userData.getUserData().get(3),userData.getUserData().get(2));
    }
    

}


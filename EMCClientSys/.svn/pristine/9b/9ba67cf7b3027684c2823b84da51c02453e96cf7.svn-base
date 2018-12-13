/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.components.workflow;

import emc.app.components.documents.EMCFormDocumentInterface;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

/**
 *
 * @author rico
 */
public class workflowPrimaryIndicator extends JComboBox implements EMCFormDocumentInterface, ActionListener{
    
    private emcDataRelationManagerUpdate dataRelation;
    private int rowIndex = 0;
    private String columnIndex = "";
    private boolean busyUpdate = false;
    private String selectedVal = "";
    
    
    public workflowPrimaryIndicator(emcDataRelationManagerUpdate dataRelation,String columnIndex){
        super(new String[] {"Stage Gate","Primary","Secondary 1", "Secondary 2", "Secondary 3"});
        this.setPreferredSize(new Dimension(150,25));
        this.columnIndex = columnIndex;
        this.dataRelation = dataRelation;
        this.addActionListener(this);
        updateDocumentContents();
    }

    public void UpdateCache(){
        if(!busyUpdate)
        this.dataRelation.setFieldValueAt(this.getRowIndex(),this.columnIndex,this.getSelectedVal());
    }
    
    public void updateDocumentContents(){
        busyUpdate = true;
        this.setSelectedVal(dataRelation.getFieldValueAt(getRowIndex(),columnIndex).toString()); 
        this.setSelectedItem(this.getSelectedVal());
        busyUpdate = false;
       
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        if((this.rowIndex != rowIndex) || (rowIndex == 0)){
            this.rowIndex = rowIndex;
            this.updateDocumentContents();
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
       this.setSelectedVal(this.getSelectedItem().toString());
            UpdateCache();
    }

    public String getSelectedVal() {
        return selectedVal;
    }

    public void setSelectedVal(String selectedVal) {
        this.selectedVal = selectedVal;
    }

    public void setFocusDRM() {
        dataRelation.setHasTheFocus(true);
    }

    public void handleFocusLost() {
        dataRelation.handleFocusLostFormComponents();
    }

    @Override
    public emcDataRelationManagerUpdate getDataRelationManager() {
        return this.dataRelation;
    }

    @Override
    public String getFieldName() {
        return this.columnIndex;
    }
}

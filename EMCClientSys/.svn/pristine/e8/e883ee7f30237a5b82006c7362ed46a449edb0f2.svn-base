/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.recordInfo;

import emc.app.components.emctable.DataRelationManagerInterface;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.framework.EMCMenuItem;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author rico
 */
public class recordInfoTableModel extends emcTableModelUpdate {
    private DataRelationManagerInterface dataRelation = null;
    private List<String> sortedFields;
    
    public recordInfoTableModel(){
        
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(getDataRelation()!=null && rowIndex < sortedFields.size()){
           switch(columnIndex){
               case 0: return sortedFields.get(rowIndex);     
               case 1: return getDataRelation().getRecordInfo(rowIndex, columnIndex, sortedFields.get(rowIndex));
               default:break;
           }
           
        }
        return null;
    }
     @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }
    @Override
    public int getRowCount() {
      if(getDataRelation() !=null){
          return this.getDataRelation().getColumnCount();
      }else{
          return 1;
      }
    }

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
    @Override
    public int getColumnCount() {
        return 2;
    }
    @Override
    public String getColumnName(int columnIndex) {
        String res;
        switch(columnIndex){
            case 0: res= "Field Name"; break;
            case 1: res= "Value"; break;
            default: res=""; break;
        }
        return res;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        return String.class;
    }
    /**
     * Returns the Data Relation Manager Interface
     * @return 
     */
    public DataRelationManagerInterface getDataRelation() {
        return dataRelation;
    }

    public void setDataRelation(DataRelationManagerInterface dataRelation) {
        this.dataRelation = dataRelation;
        TreeSet<String> sortTree = new TreeSet();
        int rowCount = 0;
        if(getDataRelation() !=null){
          rowCount = getDataRelation().getColumnCount();
        }
        for(int j = 0; j<rowCount; j++){
            if(getDataRelation()!=null){
                sortTree.add(getDataRelation().getRecordInfo(j, 0,null));
            }
        }
        sortedFields = new ArrayList();
        sortedFields.addAll(sortTree);
        this.fireTableDataChanged();
    }
    @Override
    public void rowChanged(int rowIndex){
           
        }
    @Override
        public void createForm(EMCMenuItem menuItem){
            
        }
    @Override
        public EMCUserData getUserData(){
            return null;
        }
    @Override
        public void setUserData(EMCUserData userData){
           
        }
    @Override
        public Object getRow(int rowIndex){
           return null;
        }
    @Override
        public int getColumnRelationIndex(int columnIndex){
            return 0;
        }
         /*
         * used to indicate the the table has gained focus
         * if it is a header lines environment the header or line table is */
    @Override
        public void tableGainedFocus(){
            
        }

    @Override
    public emcDataRelationManagerUpdate getDataRelationManager() {
        return (emcDataRelationManagerUpdate)dataRelation;
    }



}

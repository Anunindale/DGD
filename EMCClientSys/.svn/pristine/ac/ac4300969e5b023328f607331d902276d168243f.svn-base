/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.forminformation;

import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emctable.EMCJTableSuper;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;

/**
 *
 * @author rico
 */
public class FormInformation extends BaseInternalFrame {
   private Map<String,List> data;
   private String classPath;
   private int rowCount = 0;
   private String query = "";
   public FormInformation(EMCUserData userData){
        super("Form Info", true, true,true, true,userData);
        this.data = (Map<String, List>) userData.getUserData(2);
        this.classPath = (String) userData.getUserData(3);
        this.rowCount = (Integer)userData.getUserData(4);
        this.query = userData.getUserData().get(0) == null? "null":userData.getUserData().get(0) instanceof EMCQuery ? ((EMCQuery)userData.getUserData().get(0)).getNativeQuery():userData.getUserData().get(0).toString();
        this.setBounds(20, 20, 550, 300);

        initFrame();
   }
   private emcJPanel tabFormInfoOverview(){
       emcJPanel formInfo = new emcJPanel();
       formInfo.setLayout(new GridLayout(1,1));
       List<Class> dataSource = new ArrayList();
       List<String> buttons = new ArrayList();
       if(data.containsKey("DATASOURCES")){
           dataSource = data.get("DATASOURCES");
       }
       if(data.containsKey("BUTTONS")){
          buttons = data.get("BUTTONS");
       }
       int size = buttons.size() + dataSource.size()+3;
       String [][] tableData = new String[size][2];
       int row = 0;
       tableData[row][0] = "Form Class";
       tableData[row][1] = classPath;
       row++;
       
       for(Class c:dataSource){
           tableData[row][0] = "Data Source";
           tableData[row][1] = c.getName();
           row++;
       }
       tableData[row][0] = "Row Count";
       tableData[row][1] = Integer.toString(rowCount);
       row++;
       tableData[row][0] = "Query";
       tableData[row][1] = query;
       row++;
       for(String c:buttons){
           tableData[row][0] = "Button";
           tableData[row][1] = c;
           row++;
       }
       
       String[] columnNames = {"Description","Detail"};
       EMCJTableSuper table = new EMCJTableSuper(tableData, columnNames);
       table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
       table.getColumnModel().getColumn(0).setPreferredWidth(10);
       emcJScrollPane scroll = new emcJScrollPane(table);
       formInfo.add(scroll);
       return formInfo;
   }
   private void initFrame(){
       emcJTabbedPane tabbedPanetop = new emcJTabbedPane();

       tabbedPanetop.add("Form Info",tabFormInfoOverview());
       this.add(tabbedPanetop);
   }
}

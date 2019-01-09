/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.sendmsgtoall;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.forms.base.display.sendmsgtoall.resources.SendOKAll;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;

/**
 *
 * @author rico
 */
public class SendMessageToAll extends BaseInternalFrame {
    emcJPanel msgPanel = new emcJPanel();
    emcJTextArea message = new emcJTextArea();
    emcDRMViewOnly dataRelation = new emcDRMViewOnly();    
    emcJLabel sendResult = new emcJLabel();
    SendOKAll okButton = new SendOKAll(dataRelation,message,sendResult);
    public SendMessageToAll(EMCUserData userData){
        super("Send Message to All", true, true,true, true,userData);
        this.setBounds(20, 20, 350, 200);
        this.setDataManager(dataRelation);
        this.setHelpFile(new emcHelpFile("Base/AdminMessageToAll.html"));
        dataRelation.setTheForm(this);
        initFrame();
        //EMCDebugGraphics.activateDebugForThisComponent(this);
    }
    
    private void tabMessage(){
       msgPanel.setLayout(new BorderLayout(1,1));
       msgPanel.add(new emcJScrollPane(message), BorderLayout.CENTER);

       emcJPanel southPanel = new emcJPanel(new BorderLayout());
       southPanel.add(sendResult, BorderLayout.CENTER);
       southPanel.add(okButton,BorderLayout.SOUTH);

       msgPanel.add(southPanel, BorderLayout.SOUTH);
    }
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        
        tabMessage();
        tabbedPanetop.add("Send Message",this.msgPanel); 
        this.add(tabbedPanetop);
    }

}

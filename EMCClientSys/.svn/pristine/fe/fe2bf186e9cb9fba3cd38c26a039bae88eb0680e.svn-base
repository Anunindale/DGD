/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.sendmsg;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.forms.base.display.sendmsg.resources.SendButton;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;

/**
 *
 * @author rico
 */
public class SendMessageForm extends BaseInternalFrame {
    emcJPanel msgPanel = new emcJPanel();
    emcJTextArea message = new emcJTextArea();
    SendMessageDRM dataRelation = new SendMessageDRM();    
    emcJLabel sendResult = new emcJLabel();
    SendButton okButton = new SendButton(dataRelation,message,sendResult);
    public SendMessageForm(EMCUserData userData){
        super("Message to User", true, true,true, true,userData);
        this.setBounds(20, 20, 350, 200);
        this.setDataManager(dataRelation);
        this.setHelpFile(new emcHelpFile("Base/AdminMessageToUser.html"));
        dataRelation.setTheForm(this);
        initFrame();
        dataRelation.setUserData(userData);
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

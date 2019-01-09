/*
 * licenseDocument.java
 *
 * Created on 28 November 2007, 01:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.forms.base.display.license;

import emc.app.components.emcJList;
import emc.app.components.emcJTextField;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJTextArea;
import emc.license.EMCLicense;
import emc.functions.Functions;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author rico
 */
public class licenseDocument extends EMCStringFormDocument implements DocumentListener{
    private emcJTextField validateStatus;
    private emcJTextField expiryDate;
    private emcJTextField companies;
    private emcJTextField users;
    private emcJList modules;
    private emcJTextField compName;
    private emcJTextArea licenseKey;

    private EMCLicense decrypt = new EMCLicense();
    /** Creates a new instance of licenseDocument */
    public licenseDocument(emcDataRelationManagerUpdate dataRelation,String columnIndex,emcJTextField compName, 
            emcJTextArea licenseKey,emcJTextField validateStatus, emcJTextField expiryDate,
            emcJTextField companies,emcJTextField users, emcJList modules) {
        super(dataRelation,columnIndex);
        this.validateStatus = validateStatus;
        this.expiryDate = expiryDate;
        this.companies = companies;
        this.users = users;
        this.modules = modules;
        this.compName = compName;
        this.licenseKey = licenseKey;
        this.addDocumentListener(this);
      
    }
    @Override
    public void insertUpdate(DocumentEvent ev) {

          checkLicense();  
            
       
    }
    
    @Override
    public void removeUpdate(DocumentEvent ev) {

          checkLicense();  
            
       
    }
    
    public void checkLicense(){
        decrypt.setCompanyName(this.compName.getText());
            if(decrypt.emcDecrypt(this.licenseKey.getText())){
                validateStatus.setText("OK");
                validateStatus.setForeground(Color.BLACK);
                this.expiryDate.setText(Functions.date2String(decrypt.getExpiryDate().getTime()));
                this.companies.setText(Integer.toString(decrypt.getNumCompanies()));
                this.users.setText(Integer.toString(decrypt.getNumUsers()));
                
                List <String> moduleList = new ArrayList();
                List o = decrypt.getModules();
                for (int j = 0; j < o.size(); j++){
                  
                    moduleList.add(o.get(j).toString());
                }
                modules.setListData(new Vector(moduleList));
            }
            else{
                validateStatus.setText("NOT OK");
                validateStatus.setForeground(Color.RED);
                this.expiryDate.setText("");
                this.companies.setText("");
                this.users.setText("");
                List <String> moduleList = new ArrayList();
                moduleList.add("NONE - Check code");
                modules.setListData(new Vector(moduleList));
            }
    }

    @Override
    public void changedUpdate(DocumentEvent arg0) {
        
    }
}

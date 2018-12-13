/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.developertools.display.emcdatatypewizard.resources;

import emc.forms.developertools.display.emcdatatypewizard.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author riaan
 */
public class GenerateButton extends JButton implements ActionListener {

    private DataTypeWizardForm form;
    
    public GenerateButton(DataTypeWizardForm form) {
        super("Generate Code");
        this.form = form;
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent evt) {
        this.form.setCode(generateCode());
    }
    
    private String generateCode() {
        String name = this.form.txtName.getText();
        String type = this.form.getType();
        
        if (type.equals("EMCDATE")) {
            type = "EMCDate";
        } else if (type.equals("EMCSTRING")) {
            type = "EMCString";
        } else if (type.equals("EMCBOOLEAN")) {
            type = "EMCBoolean";
        } else if (type.equals("EMCLONG")) {
            type = "EMCLong";
        } else if (type.equals("EMCINT")) {
            type = "EMCInt";
        } else if (type.equals("EMCDOUBLE")) {
            type = "EMCDouble";
        } 
        StringBuilder code = new StringBuilder("public class " + name + " extends " + type + " { \n");
        
        code.append("\n/** Creates a new instance of " + name + " */\n");
        code.append("public " + name + "() {\n");
        
        String text = this.form.txtEMCLabel.getText();
        
        if (!(text == null || text.equals(""))) {
            code.append("this.setEmcLabel(\"" + text + "\");\n");
        }
        
        text = this.form.txtColumnWidth.getText();
        if (!(text == null || text.equals(""))) {
            code.append("this.setColumnWidth(" + text + ");\n");
        }
        
        text = this.form.txtRelatedTable.getText();
        if (!(text == null || text.equals(""))) {
            code.append("this.setRelatedTable(" + text + ".class.getName());\n");
        }
        
        text = this.form.txtRelatedField.getText();
        if (!(text == null || text.equals(""))) {
            code.append("this.setRelatedField(\"" + text + "\");\n");
        }
        
        text = this.form.txtEntityFieldName.getText();
        if (!(text == null || text.equals(""))) {
            code.append("this.setEntityFieldName(\"" + text + "\");\n");
        }
        
        text = this.form.txtHelpText.getText();
        if (!(text == null || text.equals(""))) {
            code.append("this.setHelpText(\"" + text + "\");\n");
        }
        
        text = this.form.txtMenuItemPath.getText();
        if (!(text == null || text.equals(""))) {
            code.append("this.setMenuItemPath(\"" + text + "\");\n");
        }
        
        if (this.form.ysnMandatory.getSelectedItem().equals("Yes")) {
            code.append("this.setMandatory(true);\n");
        }
        
        if (this.form.ysnEditable.getSelectedItem().equals("Yes")) {
            code.append("this.setEditable(true);\n");
        }
        if (this.form.ysnNumSeqAllowed.getSelectedItem().equals("Yes")) {
            code.append("this.setNumberSeqAllowed(true);\n");
        }
        
        if (type.equals("EMCString")) {
            text = this.form.txtStringSize.getText();
            if (!(text == null || text.equals(""))) {
                code.append("this.setStringSize(" + text + ");\n");
            }
        } else if (type.equals("EMCDouble")) {
            text = this.form.txtDecimals.getText();
            if (!(text == null || text.equals(""))) {
                code.append("this.setNumberDecimalsToDisplay(" + text + ");\n");
            }
        } else if (type.equals("EMCLong")) {
            text = this.form.txtDigits.getText();
            if (!(text == null || text.equals(""))) {
                code.append("this.setNumDigetsToDisplay(" + text + ");\n");
            }
        } else if (type.equals("EMCInt")) {
            text = this.form.txtIntDigits.getText();
            if (!(text == null || text.equals(""))) {
                code.append("this.setNumberDigetsToDisplay(" + text + ");\n");
            }
        } else if (type.equals("EMCDate")) {
            text = this.form.txtDateFormat.getText();
            if (!(text == null || text.equals(""))) {
                code.append("this.setDateFormatForDisplay(" + text + ");\n");
            }
        }
        
        code.append("}\n}");
        
        return code.toString();
    }
}

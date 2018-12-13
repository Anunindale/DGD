/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.developertools.display.emcdatatypewizard;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.forms.developertools.display.emcdatatypewizard.resources.EMCDataTypesComboBox;
import emc.forms.developertools.display.emcdatatypewizard.resources.GenerateButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.datatypes.enumDataTypes;
import emc.framework.EMCUserData;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;


/**
 *
 * @author riaan
 */
public class DataTypeWizardForm extends BaseInternalFrame {
    
    private emcJLabel lblName = new emcJLabel("Name");
    private emcJLabel lblExtends = new emcJLabel("Extends");
    private emcJLabel lblEMCLabel = new emcJLabel("EMC Label");
    private emcJLabel lblColumnWidth = new emcJLabel("Column Width");
    private emcJLabel lblRelatedTable = new emcJLabel("Related Table");
    private emcJLabel lblRelatedField = new emcJLabel("Related Field");
    private emcJLabel lblEntityFieldName = new emcJLabel("Entity Field Name");
    private emcJLabel lblMandatory = new emcJLabel("Mandatory");
    private emcJLabel lblHelpText = new emcJLabel("Help Text");
    private emcJLabel lblMenuItemPath = new emcJLabel("Menu Item Path");
    private emcJLabel lblNumberSeqAllowed = new emcJLabel("Number Seq Allowed");
    private emcJLabel lblEditable = new emcJLabel("Editable");
            
    public emcJTextField txtName = new emcJTextField();
    public emcJTextField txtEMCLabel = new emcJTextField();
    public emcJTextField txtColumnWidth = new emcJTextField();
    public emcJTextField txtRelatedTable = new emcJTextField();
    public emcJTextField txtRelatedField = new emcJTextField();
    public emcJTextField txtEntityFieldName = new emcJTextField();
    public emcJTextField txtHelpText = new emcJTextField();
    public emcJTextField txtMenuItemPath = new emcJTextField();
    
    private emcJPanel pnlString = new emcJPanel();
    private emcJLabel lblStringSize = new emcJLabel("Size");
    public emcJTextField txtStringSize = new emcJTextField();
            
    private emcJPanel pnlDouble = new emcJPanel();
    private emcJLabel lblDecimals = new emcJLabel("Decimals");
    public emcJTextField txtDecimals = new emcJTextField();
     
    private emcJPanel pnlLong = new emcJPanel();
    private emcJLabel lblDigits = new emcJLabel("Digits");
    public emcJTextField txtDigits = new emcJTextField();
        
    private emcJPanel pnlBoolean = new emcJPanel();
    
    private emcJPanel pnlInt = new emcJPanel();
    private emcJLabel lblIntDigits = new emcJLabel("Digits");
    public emcJTextField txtIntDigits = new emcJTextField();
    
    private emcJPanel pnlDate = new emcJPanel();
    private emcJLabel lblDateFormat = new emcJLabel("Format");
    public emcJTextField txtDateFormat = new emcJTextField();
    
    private emcJTextArea txaCode = new emcJTextArea();
    
    private EMCDataTypesComboBox cmbTypes;
    public emcYesNoComponent ysnMandatory = new emcYesNoComponent();
    public emcYesNoComponent ysnEditable = new emcYesNoComponent();
    public emcYesNoComponent ysnNumSeqAllowed = new emcYesNoComponent();
    
    /** Creates a new instance of DataTypeWizardForm */
    public DataTypeWizardForm(EMCUserData userData) {
        super("EMC Data Type Wizard", true, true,true, true,userData);
        this.setBounds(20, 20, 500, 500);
        
        initFrame();
        
        this.setVisible(true);
    }
    
    public void setCode(String code) {
        this.txaCode.setText(code);
    }
    
    public String getType() {
        return this.cmbTypes.getSelectedItem().toString();
    }
    
    public void initFrame() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        emcJPanel pnlCards = new emcJPanel();
        CardLayout cl = new CardLayout();
        pnlCards.setLayout(cl);
               
        createBooleanPanel();
        pnlCards.add(pnlBoolean, enumDataTypes.EMCBOOLEAN.toString());        
        
        createDoublePanel();
        pnlCards.add(pnlDouble, enumDataTypes.EMCDOUBLE.toString());
        
        createStringPanel();
        pnlCards.add(pnlString, enumDataTypes.EMCSTRING.toString());
        
        createLongPanel();
        pnlCards.add(pnlLong, enumDataTypes.EMCLONG.toString());        
        
        createIntPanel();
        pnlCards.add(pnlInt, enumDataTypes.EMCINT.toString());

        createDatePanel();
        pnlCards.add(pnlDate, enumDataTypes.EMCDATE.toString());
        
        cmbTypes = new EMCDataTypesComboBox(cl, pnlCards);
         
        int y = 0;
        
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.9;
        gbc.weighty = 0.1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        emcJPanel pnlSuper = new emcJPanel();
        pnlSuper.setLayout(new GridLayout(6, 4));
        pnlSuper.setPreferredSize(new Dimension(480, (25 * 6)));
        pnlSuper.setBorder(BorderFactory.createTitledBorder("EMC Data Type Fields"));
        
        pnlSuper.add(lblName);
        pnlSuper.add(txtName);
        pnlSuper.add(lblExtends);
        pnlSuper.add(cmbTypes);
        pnlSuper.add(lblEMCLabel);
        pnlSuper.add(txtEMCLabel);
        pnlSuper.add(lblColumnWidth);
        pnlSuper.add(txtColumnWidth);
        pnlSuper.add(lblRelatedTable);
        pnlSuper.add(txtRelatedTable);
        pnlSuper.add(lblRelatedField);
        pnlSuper.add(txtRelatedField);
        pnlSuper.add(lblEntityFieldName);
        pnlSuper.add(txtEntityFieldName);
        pnlSuper.add(lblMandatory);
        pnlSuper.add(ysnMandatory);
        pnlSuper.add(lblEditable);
        pnlSuper.add(ysnEditable);
        pnlSuper.add(lblNumberSeqAllowed);
        pnlSuper.add(ysnNumSeqAllowed);
        pnlSuper.add(lblHelpText);
        pnlSuper.add(txtHelpText);
        pnlSuper.add(lblMenuItemPath);
        pnlSuper.add(txtMenuItemPath);
        
        this.add(pnlSuper, gbc);
        
        y++;
        
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        this.add(pnlCards, gbc);
        
        y++;
        
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
            
        GenerateButton button = new GenerateButton(this);
        this.add(button, gbc);
        
        y++;
        
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.ipady = 200;
        gbc.fill = GridBagConstraints.BOTH;
        
        this.add(new emcJScrollPane(txaCode), gbc);
        
        y++;
        
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.add(new emcJLabel(), gbc);
    }
    
    public void createDoublePanel() {
        txtDecimals.setPreferredSize(new Dimension(125, 25));
        pnlDouble.setBorder(BorderFactory.createTitledBorder("EMC Double"));
        pnlDouble.add(lblDecimals);
        pnlDouble.add(txtDecimals);
    }
    
    public void createStringPanel() {
        txtStringSize.setPreferredSize(new Dimension(125, 25));
        pnlString.setBorder(BorderFactory.createTitledBorder("EMC String"));
        pnlString.add(lblStringSize);
        pnlString.add(txtStringSize);
    }
    
    public void createBooleanPanel() {
        pnlBoolean.setBorder(BorderFactory.createTitledBorder("EMC Boolean"));
    }
    
    public void createIntPanel() {
        txtIntDigits.setPreferredSize(new Dimension(125, 25));
        pnlInt.setBorder(BorderFactory.createTitledBorder("EMC Long"));
        pnlInt.add(lblIntDigits);
        pnlInt.add(txtIntDigits);
    }
    
    public void createDatePanel() {
        txtDateFormat.setPreferredSize(new Dimension(125, 25));
        pnlDate.setBorder(BorderFactory.createTitledBorder("EMC Date"));
        pnlDate.add(lblDateFormat);
        pnlDate.add(txtIntDigits);
    }
    
    public void createLongPanel() {
        txtDigits.setPreferredSize(new Dimension(125, 25));
        pnlLong.setBorder(BorderFactory.createTitledBorder("EMC Long"));
        pnlLong.add(lblDigits);
        pnlLong.add(txtDigits);
    }
    
}

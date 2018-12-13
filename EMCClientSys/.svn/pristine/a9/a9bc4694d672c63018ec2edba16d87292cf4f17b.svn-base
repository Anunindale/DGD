/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.entitymaker;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJList;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;

/**
 *
 * @author wikus
 */
public class EntityForm extends BaseInternalFrame {

    private emcJTextField packageName;
    private emcJTextField entityClassName;
    private emcJTextField fieldName;
    private emcJComboBox fieldType;
    private emcJList fieldKeys;
    private DefaultListModel fieldKeysModel;
    private emcJTextArea codeDisplay;
    private emcJButton addButton;
    private emcJButton removeButton;
    private emcJButton doneButton;
    private emcJButton uniqueButton;

    public EntityForm(EMCUserData userData) {
        super("Entity Maker", true, true, true, true, userData);
        this.setBounds(10, 10, 610, 660);
        initFrame();
    }

    private void initFrame() {
        components();
        this.setContentPane(mainPanel());
    }

    private void components() {
        packageName = new emcJTextField();
        entityClassName = new emcJTextField();
        fieldName = new emcJTextField();
        fieldType = new emcJComboBox(new String[]{"String", "boolean", "int", "long", "double"});
        fieldKeysModel = new DefaultListModel();
        fieldKeys = new emcJList(fieldKeysModel);
        codeDisplay = new emcJTextArea();

        addButton = new emcJButton("Add Field") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (!(fieldName.getText() == null || fieldName.getText().isEmpty())) {
                    fieldKeysModel = EntityLogic.addFieldKey(fieldType.getSelectedItem().toString(), fieldName.getText(), fieldKeysModel);
                } else {
                    Logger.getLogger("emc").log(Level.WARNING, "Field Name Is Blank");
                }
                fieldName.setText("");
                fieldType.setSelectedIndex(0);
            }
        };

        removeButton = new emcJButton("Remove Field") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                EntityLogic.removeFieldKey(fieldKeys.getSelectedValues(), fieldKeysModel);
            }
        };

        doneButton = new emcJButton("Generate Code") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                if (!(packageName.getText() != null && entityClassName.getText() != null) || !(packageName.getText().isEmpty() && entityClassName.getText().isEmpty())) {
                    codeDisplay.append(EntityLogic.generateCode(packageName.getText(), entityClassName.getText(), fieldKeysModel));
                } else {
                    Logger.getLogger("emc").log(Level.WARNING, "Package or Class Name Is Blank");
                }
            }
        };

        uniqueButton = new emcJButton("Unique Constraints") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                EntityLogic.setUniqueConstraints(fieldKeys.getSelectedValues());
            }
        };
    }

    private emcJPanel mainPanel() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setBorder(BorderFactory.createTitledBorder("Entity Maker"));
        thePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Package Name"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(packageName, gbc);

        gbc = emcSetGridBagConstraints.createStandard(2, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Field Keys"), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        gbc = emcSetGridBagConstraints.createStandard(2, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.gridheight = 6;
        thePanel.add(new emcJScrollPane(fieldKeys), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Entity Class Name"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(entityClassName, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Field Type"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(fieldType, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel("Field Name"), gbc);

        gbc = emcSetGridBagConstraints.createStandard(1, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(fieldName, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.gridwidth = 3;
        gbc.ipadx = 30;
        gbc.insets = new java.awt.Insets(0, 30, 0, 0);
        thePanel.add(addButton, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        thePanel.add(uniqueButton, gbc);

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.ipadx = 10;
        gbc.insets = new java.awt.Insets(0, 0, 0, 30);
        thePanel.add(removeButton, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.CENTER);
        gbc.gridwidth = 3;
        thePanel.add(doneButton, gbc);

        y++;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        thePanel.add(new emcJLabel(), gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.gridheight = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        thePanel.add(new JScrollPane(codeDisplay), gbc);
        y++;
        gbc = emcSetGridBagConstraints.endPanel(y);
        thePanel.add(new emcJLabel(), gbc);

        return thePanel;
    }
}

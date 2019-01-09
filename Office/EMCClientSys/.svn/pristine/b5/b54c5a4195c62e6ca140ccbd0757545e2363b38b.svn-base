/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.hr;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.entity.base.BaseEmployeeTable;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.employees;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;

/**
 *
 * @author wikus
 */
public class HREmployeeControllLookupPanel extends emcJPanel {

    private String className;
    private String fieldKey;
    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public HREmployeeControllLookupPanel(String className, String fieldKey, EMCControlLookupComponentDRM dataRelation, EMCUserData userData) {
        super();
        this.className = className;
        this.fieldKey = fieldKey;
        this.dataRelation = dataRelation;
        this.userData = userData;
        initPanel();
    }

    private void initPanel() {
        emcJTextField txtEmployee = new emcJTextField();
        txtEmployee.setEditable(false);
        EMCControlLookupComponent lkpEmployee = new EMCControlLookupComponent(new employees(), dataRelation, fieldKey, txtEmployee, "forenames", "surname", className);
        EMCLookupPopup studentPopup = new EMCLookupPopup(new BaseEmployeeTable(), "employeeNumber", userData);
        lkpEmployee.setPopup(studentPopup);
        lkpEmployee.setTheQuery(HREmployeeAccessManager.getEmployeeAccessQuery(userData));
        dataRelation.setLookup(lkpEmployee);

        this.setBorder(BorderFactory.createTitledBorder("Employee"));
        this.setLayout(new GridBagLayout());

        int x = 0;
        int y = 0;
        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(x, y, 0.1, GridBagConstraints.LINE_START);

        this.add(new emcJLabel("Employee Number"), gbc);
        x++;
        gbc.gridx = x;
        this.add(lkpEmployee, gbc);
        x++;
        gbc.gridx = x;
        this.add(new emcJLabel("Name"), gbc);
        x++;
        gbc.gridx = x;
        this.add(txtEmployee, gbc);
        x++;
        gbc = emcSetGridBagConstraints.endRow(x, y);
        this.add(new emcJLabel(), gbc);
        y++;
        gbc = emcSetGridBagConstraints.endPanel(y);
        this.add(new emcJLabel(), gbc);
    }
}

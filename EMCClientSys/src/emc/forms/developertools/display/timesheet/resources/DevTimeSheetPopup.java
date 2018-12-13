/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.developertools.display.timesheet.resources;

import emc.app.components.emctable.scheduletable.EMCScheduleTableComponent;
import emc.app.components.emctable.scheduletable.EMCScheduleTableComponentPopup;
import emc.entity.developertools.DevTimeSheet;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;

/**
 *
 * @author wikus
 */
public class DevTimeSheetPopup extends EMCScheduleTableComponentPopup {

    private JMenuItem viewTask = new JMenuItem("View Task");
    private JMenuItem completeTask = new JMenuItem("Complete Task");

    public DevTimeSheetPopup(EMCScheduleTableComponent scheduleObject) {
        super(scheduleObject);
        
        viewTask.addActionListener(this);
        this.add(viewTask);

        completeTask.addActionListener(this);
        this.add(completeTask);        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals("View Task")) {
            DevTimeSheetDRM dataManager = (DevTimeSheetDRM) getScheduleObject().getDataManager();
            dataManager.viewTask((DevTimeSheet) getScheduleObject().getDataManager().getRowCache(getScheduleObject().getRowInCach()));
        }if (arg0.getActionCommand().equals("Complete Task")) {
            DevTimeSheetDRM dataManager = (DevTimeSheetDRM) getScheduleObject().getDataManager();
            dataManager.completeTask((DevTimeSheet) getScheduleObject().getDataManager().getRowCache(getScheduleObject().getRowInCach()));
        } else {
            super.actionPerformed(arg0);
        }
    }
}

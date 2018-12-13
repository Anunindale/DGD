/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.bugtracking.foreignkeys;

import emc.datatypes.developertools.bugtracking.BugNumber;
import emc.entity.developertools.bugtracking.DevBugsTable;

/**
 *
 * @author asd_admin
 */
public class BugNumberFK extends BugNumber{
    public BugNumberFK(){
        this.setRelatedField("bugNumber");
        this.setRelatedTable(DevBugsTable.class.getName());
        this.setMandatory(false);
        this.setEmcLabel("Parent Task");
    }
}

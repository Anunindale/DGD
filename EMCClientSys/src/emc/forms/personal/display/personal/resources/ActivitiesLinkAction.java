/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.personal.display.personal.resources;

import emc.app.components.hyperlink.GeneralLinkAction;
import emc.enums.workflow.WFMyActivitiesEnum;
import emc.forms.personal.display.personal.MyActivitiesForm;
import emc.framework.EMCMenuItem;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author riaan
 */
public class ActivitiesLinkAction extends GeneralLinkAction {

    private String originalName;
    private EMCQuery theQuery;
    private MyActivitiesForm frame;

    public ActivitiesLinkAction(WFMyActivitiesEnum action, EMCQuery query, EMCMenuItem menuItem, MyActivitiesForm theForm, EMCUserData userData) {
        super(action.toString(), null, menuItem, null, userData, theForm);
        this.originalName = action.toString();
        this.theQuery = query;
        this.frame = theForm;
    }

    public ActivitiesLinkAction(String action, EMCQuery query, EMCMenuItem menuItem, MyActivitiesForm theFrame, EMCUserData userData) {
        super(action, null, menuItem, null, userData, theFrame);
        this.originalName = action;
        this.theQuery = query;
        this.frame = theFrame;
    }

    public void refresh(Map<String, String> infoMap) {
        this.setText(this.originalName + " (" + infoMap.get(this.originalName) + ")");
    }

    @Override
    public void doMouseClicked(MouseEvent evt) {
        EMCUserData copyUD = this.getUserData().copyUserData();
        List udList = new ArrayList();
        udList.add(theQuery);
        udList.add("");
        udList.add(frame.getEmployeeNumber());
        copyUD.setUserData(udList);

        this.getTheForm().getDeskTop().createAndAdd(this.getMenuItem(), -1, -1, copyUD, null, 0);
    }
}

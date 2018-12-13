/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.action.CreateNLJournal;
import emc.menus.base.menuItems.action.EMCEmailFormMenu;
import emc.menus.base.menuItems.action.EMCEmailReview;
import emc.menus.base.menuItems.action.EMCSMSFormMenu;
import emc.menus.base.menuItems.action.SearchDBForValueMenu;
import emc.menus.base.menuItems.display.BaseEntityRelationDiagramMenu;
import emc.menus.base.menuItems.display.BaseGenerateTimeByDayMenu;
import emc.menus.base.menuItems.display.BaseUserFileAssociationsMI;
import emc.menus.base.menuItems.display.EMCEntityView;
import emc.menus.base.menuItems.display.FormInformationMenu;
import emc.menus.base.menuItems.display.Messages;
import emc.menus.base.menuItems.display.RecordInfo;
import emc.menus.base.menuItems.display.ReportPrintOptionMenu;
import emc.menus.base.menuItems.display.ReportSelection;
import emc.menus.base.menuItems.display.UserPermissions;
import emc.menus.base.menuItems.display.documentHandler;

/**
 *
 * @author rico
 */
public class BaseSystem extends EMCMenu {

    private RecordInfo recInfo = new RecordInfo();
    private UserPermissions permissions = new UserPermissions();
    private documentHandler document = new documentHandler();
    private EMCEntityView entityView = new EMCEntityView();
    private ReportSelection reportSelection = new ReportSelection();
    private ReportPrintOptionMenu reportPrint = new ReportPrintOptionMenu();
    private Messages messages = new Messages();

    public BaseSystem() {
        this.setMenuName("System");
        this.setMenuList(document);
        this.setMenuList(entityView);
        this.setMenuList(new BaseEntityRelationDiagramMenu());
        this.setMenuList(new EntityDataViewFormMI());
        this.setMenuList(messages);
        this.setMenuList(permissions);
        this.setMenuList(new FormInformationMenu());
        this.setMenuList(recInfo);
        this.setMenuList(reportPrint);
        this.setMenuList(reportSelection);
        this.setMenuList(new BaseUserFileAssociationsMI());
        this.setMenuList(new BaseGenerateTimeByDayMenu());
        this.setMenuList(new EMCEmailFormMenu());
        this.setMenuList(new EMCEmailReview());
        this.setMenuList(new EMCSMSFormMenu());
        this.setMenuList(new SearchDBForValueMenu());
        this.setMenuList(new CreateNLJournal());
    }
}

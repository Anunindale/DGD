/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.menus.base;

import emc.framework.EMCMenu;
import emc.menus.base.menuItems.display.AvailableNumbers;
import emc.menus.base.menuItems.display.BaseCityMenu;
import emc.menus.base.menuItems.display.BaseDBConnectMenu;
import emc.menus.base.menuItems.display.BaseEmployeeCategoryHistoryMI;
import emc.menus.base.menuItems.display.BaseFileAssociationsMI;
import emc.menus.base.menuItems.display.BaseHelpFileMappingsMI;
import emc.menus.base.menuItems.display.BaseLanguageMenu;
import emc.menus.base.menuItems.display.BaseNumberSequencesMenu;
import emc.menus.base.menuItems.display.BaseParametersMenu;
import emc.menus.base.menuItems.display.BaseProvenceMenu;
import emc.menus.base.menuItems.display.BaseReportTextMI;
import emc.menus.base.menuItems.display.BaseSuburbMenu;
import emc.menus.base.menuItems.display.BaseTimeByDayMenu;
import emc.menus.base.menuItems.display.BaseUOMDetailedConversionTableMI;
import emc.menus.base.menuItems.display.BaseWebFilePathsMI;
import emc.menus.base.menuItems.display.CalendarExceptionTypesMenu;
import emc.menus.base.menuItems.display.CalendarExceptionsMenu;
import emc.menus.base.menuItems.display.CalendarMenu;
import emc.menus.base.menuItems.display.CalendarShiftsMenu;
import emc.menus.base.menuItems.display.Countries;
import emc.menus.base.menuItems.display.DBLogMenu;
import emc.menus.base.menuItems.display.DBLogSetupMenu;
import emc.menus.base.menuItems.display.FilePaths;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.base.menuItems.display.SystemTables;
import emc.menus.base.menuItems.display.TimedOperationSetupMenu;
import emc.menus.base.menuItems.display.UnitsOfMeasure;
import emc.menus.base.menuItems.display.UnitsOfMeasureConversions;
import emc.menus.base.menuItems.display.WebPortalUsers;

/**
 *
 * @author riaan
 */
public class BaseSetupMenu extends EMCMenu {

    //Items used on the menu
    private PostalCodes postalCodes = new PostalCodes();
    private UnitsOfMeasure unitsOfMeasure = new UnitsOfMeasure();
    private UnitsOfMeasureConversions uomConversions = new UnitsOfMeasureConversions();
    private SystemTables systemTables = new SystemTables();
    private FilePaths filePaths = new FilePaths();
    private BaseNumberSequencesMenu numberSequences = new BaseNumberSequencesMenu();
    private Countries countries = new Countries();
    private AvailableNumbers availableNumber = new AvailableNumbers();
    private CalendarMenu calendar = new CalendarMenu();
    private CalendarExceptionsMenu calendarExceptions = new CalendarExceptionsMenu();
    private CalendarShiftsMenu calendarShifts = new CalendarShiftsMenu();
    private DBLogSetupMenu dbLogSetup = new DBLogSetupMenu();
    private DBLogMenu dbLog = new DBLogMenu();

    /**
     * Creates an instance of BaseSetupMenu
     */
    public BaseSetupMenu() {
        this.setMenuName("Setup");
        this.setMenuList(new BaseAccessGroups());
        this.setMenuList(new BaseJournalSetup());
        this.setMenuList(new BaseMailSetup());
        this.setMenuList(availableNumber);
        this.setMenuList(calendar);
        this.setMenuList(calendarExceptions);
        this.setMenuList(new CalendarExceptionTypesMenu());
        this.setMenuList(calendarShifts);
        this.setMenuList(new BaseSuburbMenu());
        this.setMenuList(new BaseCityMenu());
        this.setMenuList(new BaseProvenceMenu());
        this.setMenuList(countries);
        this.setMenuList(dbLogSetup);
        this.setMenuList(dbLog);
        this.setMenuList(new BaseFileAssociationsMI());
        this.setMenuList(filePaths);
        this.setMenuList(new BaseLanguageMenu());
        this.setMenuList(new BaseHelpFileMappingsMI());
        this.setMenuList(numberSequences);
        this.setMenuList(postalCodes);
        this.setMenuList(systemTables);
        this.setMenuList(unitsOfMeasure);
        this.setMenuList(uomConversions);
        this.setMenuList(new BaseUOMDetailedConversionTableMI());
        this.setMenuList(new TimedOperationSetupMenu());
        this.setMenuList(new BaseTimeByDayMenu());
        this.setMenuList(new WebPortalUsers());
        this.setMenuList(new BaseDBConnectMenu());
        this.setMenuList(new BaseParametersMenu());
        this.setMenuList(new BaseWebFilePathsMI());
        this.setMenuList(new BaseReportTextMI());
        this.setMenuList(new BaseEmployeeCategoryHistoryMI());
    }
}

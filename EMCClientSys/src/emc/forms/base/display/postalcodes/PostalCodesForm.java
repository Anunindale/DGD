/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.postalcodes;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseCity;
import emc.entity.base.BaseCountries;
import emc.entity.base.BaseProvence;
import emc.entity.base.BaseSuburb;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.BaseCityMenu;
import emc.menus.base.menuItems.display.BaseProvenceMenu;
import emc.menus.base.menuItems.display.BaseSuburbMenu;
import emc.menus.base.menuItems.display.Countries;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class PostalCodesForm extends BaseInternalFrame {

    private emcJPanel pnlPostalCodes = new emcJPanel();
    private EMCUserData copyUD;
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    public PostalCodesForm(EMCUserData userData) {
        super("Postal Codes", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Base/SetupPostalCodes.html"));
        try {
            copyUD = userData.copyUserData();

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.BasePostalCodes(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("code");
            dataRelation.setFormTextId2("suburb");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabPostalCodes() {
        List keys = new ArrayList();
        keys.add("country");
        keys.add("provence");
        keys.add("city");
        keys.add("suburb");
        keys.add("code");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        setupLookups(toptable);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlPostalCodes.setLayout(new GridLayout(1, 1));
        pnlPostalCodes.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabPostalCodes();
        tabbedPanetop.add("Postal Codes", this.pnlPostalCodes);
        this.add(tabbedPanetop);
    }

    private void setupLookups(emcJTableUpdate toptable) {
        EMCLookupJTableComponent lkpCountry = new EMCLookupJTableComponent(new Countries());
        lkpCountry.setPopup(new EMCLookupPopup(new BaseCountries(), "Code", copyUD));
        toptable.setLookupToColumn("country", lkpCountry);

        EMCLookupJTableComponent lkpProvence = new EMCLookupJTableComponent(new BaseProvenceMenu());
        lkpProvence.setPopup(new EMCLookupPopup(new BaseProvence(), "provence", copyUD));
        toptable.setLookupToColumn("provence", lkpProvence);

        EMCLookupJTableComponent lkpCity = new EMCLookupJTableComponent(new BaseCityMenu());
        lkpCity.setPopup(new EMCLookupPopup(new BaseCity(), "city", copyUD));
        toptable.setLookupToColumn("city", lkpCity);

        EMCLookupJTableComponent lkpSuburb = new EMCLookupJTableComponent(new BaseSuburbMenu());
        lkpSuburb.setPopup(new EMCLookupPopup(new BaseSuburb(), "suburb", copyUD));
        toptable.setLookupToColumn("suburb", lkpSuburb);
    }
}

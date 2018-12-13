/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.base;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCMultiValuePopup;
import emc.entity.base.BasePostalCodes;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.PostalCodes;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wikus
 */
public class EMCAdressPanelFactory {

    public static emcJPanel createPhysicalAddressPanel(emcDataRelationManagerUpdate drm, EMCUserData userData) {
        emcJTextField txtStreetAdress = new emcJTextField(new EMCStringFormDocument(drm, "streetAddress"));
        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("postalCode", "code");
        fieldMap.put("suburb", "suburb");
        fieldMap.put("city", "city");
        fieldMap.put("provence", "provence");
        fieldMap.put("country", "country");
        EMCLookupFormComponent lkpSuburb = new EMCLookupFormComponent(new PostalCodes(), drm, "suburb");
        lkpSuburb.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "suburb", drm, null, userData));
        EMCLookupFormComponent lkpCity = new EMCLookupFormComponent(new PostalCodes(), drm, "city");
        lkpCity.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "city", drm, null, userData));
        EMCLookupFormComponent lkpProvence = new EMCLookupFormComponent(new PostalCodes(), drm, "provence");
        lkpProvence.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "provence", drm, null, userData));
        EMCLookupFormComponent lkpCountry = new EMCLookupFormComponent(new PostalCodes(), drm, "country");
        lkpCountry.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "country", drm, null, userData));
        EMCLookupFormComponent lkpCode = new EMCLookupFormComponent(new PostalCodes(), drm, "postalCode");
        lkpCode.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "code", drm, null, userData));
        Component[][] comp = {{new emcJLabel("Street"), txtStreetAdress},
                              {new emcJLabel("Suburb"), lkpSuburb},
                              {new emcJLabel("City"), lkpCity},
                              {new emcJLabel("Provence"), lkpProvence},
                              {new emcJLabel("Country"), lkpCountry},
                              {new emcJLabel("Postal Code"), lkpCode}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Physical Address");
    }
}

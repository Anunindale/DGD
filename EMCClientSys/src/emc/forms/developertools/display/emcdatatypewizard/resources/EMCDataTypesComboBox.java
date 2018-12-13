/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.developertools.display.emcdatatypewizard.resources;

import emc.enums.datatypes.enumDataTypes;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author riaan
 */
public class EMCDataTypesComboBox extends JComboBox implements ItemListener {
    
    private CardLayout cl;
    private JPanel pnlCards;

    //Creates a new instance of EMCDataTypesComboBox
    public EMCDataTypesComboBox(CardLayout cl, JPanel pnlCards) {
        super(new String[] {enumDataTypes.EMCBOOLEAN.toString(), enumDataTypes.EMCDATE.toString(),
                            enumDataTypes.EMCDOUBLE.toString(), enumDataTypes.EMCINT.toString(), 
                            enumDataTypes.EMCLONG.toString(), enumDataTypes.EMCSTRING.toString()});
        
        this.cl = cl;
        this.pnlCards = pnlCards;

        this.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        cl.show(pnlCards, evt.getItem().toString());
    }

}

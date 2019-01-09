/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.personal.display.todo.resources;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.messagehandler.emcHandler;
import emc.datatypes.EMCString;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author rico
 */
public class todoDocument extends PlainDocument{
   
    public todoDocument(){
        try {
            this.remove(0, this.getLength());
            String input = (String) emcHandler.getPostIt();
            if (input == null) {
                input = "";
            }
            this.insertString(0, input, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(todoDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   

    @Override
    public void insertString(int offset, String stringToInsert, AttributeSet attributes) throws BadLocationException {
        super.insertString(offset, stringToInsert, attributes);
        emcHandler.setPostIt(this.getText(0,this.getLength()));
    }
    
}

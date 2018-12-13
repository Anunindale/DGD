package emc.datatypes.base.docuref;
 
import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class AttachmentFileName extends EMCString {

    /** Creates a new instance of AttachmentFileName */
    public AttachmentFileName() {
        this.setEmcLabel("Attachment File Name");
        this.setEditable(true);
    	this.setColumnWidth(399);
        this.setStringSize(256);
    }
}

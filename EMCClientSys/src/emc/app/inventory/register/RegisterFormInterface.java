/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.app.inventory.register;

/**
 * @Name         : RegisterFormInterface
 *
 * @Date         : 21 Sep 2009
 *
 * @Description  : This interface specifies the methods which should be present in the forms used for registering items.
 *
 * @author       : riaan
 */
public interface RegisterFormInterface {

    public String getItemId();
    public String getDimension1();
    public String getDimension2();
    public String getDimension3();
    
}

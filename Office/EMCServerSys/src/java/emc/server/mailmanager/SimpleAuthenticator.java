/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.mailmanager;

import javax.mail.PasswordAuthentication;

/**
 *
 * @author rico
 */
public class SimpleAuthenticator extends javax.mail.Authenticator{
    private String userName;
    private String password;

    public SimpleAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {       
        return new PasswordAuthentication(userName, password);
    }


}

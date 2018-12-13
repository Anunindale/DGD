/*
 * start.java
 *
 * Created on 25 August 2007, 10:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.app;

import emc.app.config.EMCMenuConstants;
import emc.app.config.emcAppConstants;
import emc.app.frame.EMCDesktopManager;
import emc.menus.base.menuItems.display.companies;
import emc.menus.mainMenu;
import emc.menus.personal.menuItems.display.toDo;
import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import javax.swing.SwingUtilities;
import netscape.javascript.JSObject;

/**
 *
 * @author rico
 */
public class start {

    /**
     * Creates a new instance of start
     */
    public start() {
    }

    public static void main(String args[]) {
        //Load Startup Parameters
        EMCStartupParameters.populateStatupParameters(args);

        //Set menu constants
        if (emcAppConstants.doNimbus()) {
            try {
                //Create a splash screen before login
                //Splash screen will not come up when running EMC through Netbeans
                //TODO: The splash cannot locate the image when ran through Netbeans
                final SplashScreen splash = SplashScreen.getSplashScreen();
                Graphics2D g = splash.createGraphics();
                g.setComposite(AlphaComposite.Clear);
                g.setPaintMode();
                g.setColor(Color.BLACK);
                g.drawString("Loading...", 360, 30);
                splash.update();
            } catch (Exception e) {
            }
        }

        if (EMCStartupParameters.isWebSwing()) {
            JSObject global = JSObject.getWindow(null);
            global.eval("document.title='Trec'");
        }

        EMCMenuConstants.companyMenuItem = new companies();
        EMCMenuConstants.mainMenu = new mainMenu();
        EMCMenuConstants.personalSpaceMenuItem = new toDo();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                EMCDesktopManager manager = new EMCDesktopManager();
            }
        });

    }
}

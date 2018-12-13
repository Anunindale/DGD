/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.methods.dangerousgoods;

import emc.enums.modules.enumEMCModules;
import emc.methods.EMCMethodEnum;

/**
 *
 * @author rico
 */
public enum ServerDGMethods implements EMCMethodEnum {

    //CRMInterest
    //DGCargoCheckMaster
    INSERT_DGCARGOCHECKMASTER(0, "INSERT_DGCARGOCHECKMASTER"),
    UPDATE_DGCARGOCHECKMASTER(1, "UPDATE_DGCARGOCHECKMASTER"),
    DELETE_DGCARGOCHECKMASTER(2, "DELETE_DGCARGOCHECKMASTER"),
    GETNUMROWS_DGCARGOCHECKMASTER(3, "GETNUMROWS_DGCARGOCHECKMASTER"),
    GETDATA_DGCARGOCHECKMASTER(4, "GETDATA_DGCARGOCHECKMASTER"),
    VALIDATEFIELD_DGCARGOCHECKMASTER(5, "VALIDATEFIELD_DGCARGOCHECKMASTER"),
    //DGCargoCheckLines
    INSERT_DGCARGOCHECKLINES(20, "INSERT_DGCARGOCHECKLINES"),
    UPDATE_DGCARGOCHECKLINES(21, "UPDATE_DGCARGOCHECKLINES"),
    DELETE_DGCARGOCHECKLINES(22, "DELETE_DGCARGOCHECKLINES"),
    GETNUMROWS_DGCARGOCHECKLINES(23, "GETNUMROWS_DGCARGOCHECKLINES"),
    GETDATA_DGCARGOCHECKLINES(24, "GETDATA_DGCARGOCHECKLINES"),
    VALIDATEFIELD_DGCARGOCHECKLINES(25, "VALIDATEFIELD_DGCARGOCHECKLINES"),
    //DGDContacts
    INSERT_DGDCONTACTS(40, "INSERT_DGDCONTACTS"),
    UPDATE_DGDCONTACTS(41, "UPDATE_DGDCONTACTS"),
    DELETE_DGDCONTACTS(42, "DELETE_DGDCONTACTS"),
    GETNUMROWS_DGDCONTACTS(43, "GETNUMROWS_DGDCONTACTS"),
    GETDATA_DGDCONTACTS(44, "GETDATA_DGDCONTACTS"),
    VALIDATEFIELD_DGDCONTACTS(45, "VALIDATEFIELD_DGDCONTACTS"),
    //DGDVehicles
    INSERT_DGDVEHICLES(60, "INSERT_DGDVEHICLES"),
    UPDATE_DGDVEHICLES(61, "UPDATE_DGDVEHICLES"),
    DELETE_DGDVEHICLES(62, "DELETE_DGDVEHICLES"),
    GETNUMROWS_DGDVEHICLES(63, "GETNUMROWS_DGDVEHICLES"),
    GETDATA_DGDVEHICLES(64, "GETDATA_DGDVEHICLES"),
    VALIDATEFIELD_DGDVEHICLES(65, "VALIDATEFIELD_DGDVEHICLES"),
    //VehiclesDS
    INSERT_VEHICLESDS(80, "INSERT_VEHICLESDS"),
    UPDATE_VEHICLESDS(81, "UPDATE_VEHICLESDS"),
    DELETE_VEHICLESDS(82, "DELETE_VEHICLESDS"),
    GETNUMROWS_VEHICLESDS(83, "GETNUMROWS_VEHICLESDS"),
    GETDATA_VEHICLESDS(84, "GETDATA_VEHICLESDS"),
    VALIDATEFIELD_VEHICLESDS(85, "VALIDATEFIELD_VEHICLESDS");
    
    
    //Enum variables
    final int id;
    final String label;

    /** Creates a new instance of  ServerCRMMethods*/
    ServerDGMethods(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public static ServerDGMethods fromString(final String str) {
        for (ServerDGMethods e : ServerDGMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ServerDGMethods fromId(final int id) {
        for (ServerDGMethods e : ServerDGMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    public enumEMCModules getEMCModule() {
        return enumEMCModules.DG;
    }
}

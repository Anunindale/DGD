/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.commandmanager.dangerousgoods;

import emc.bus.dangerousgoods.cargolines.DGCargoLinesLocal;
import emc.bus.dangerousgoods.cargomaster.DGCargoMasterLocal;
import emc.bus.dangerousgoods.contacts.DGDContactsLocal;
import emc.bus.dangerousgoods.declarationlines.DGDeclarationLinesLocal;
import emc.bus.dangerousgoods.declarationmaster.DGDeclarationMasterLocal;
import emc.bus.dangerousgoods.un.datasource.UNDSLocal;
import emc.bus.dangerousgoods.vehicles.datasource.VehiclesDSLocal;
import emc.commands.EMCCommands;
import emc.entity.dangerousgoods.DGCargoCheckLines;
import emc.entity.dangerousgoods.DGCargoCheckMaster;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.entity.dangerousgoods.datasource.UNDS;
import emc.entity.dangerousgoods.datasource.VehiclesDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.methods.dangerousgoods.ClientDGMethods;
import emc.methods.dangerousgoods.ServerDGMethods;
import emc.reports.dangerousgoods.DeclarationReportLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class DGMethodMapperBean implements DGMethodMapperBeanLocal{
    @EJB
    private DGCargoMasterLocal cargoMastBean;
    @EJB
    private DGCargoLinesLocal cargoLinesBean;
    @EJB
    private DGDContactsLocal contactsBean;
    @EJB
    private VehiclesDSLocal vehiclesDSBean;
    @EJB
    private DGDeclarationMasterLocal declarationMasterBean;
    @EJB
    private DGDeclarationLinesLocal declarationLinesBean;
    @EJB
    private UNDSLocal unDSBean;
    @EJB
    private DeclarationReportLocal declarationReportBean;
            
    public DGMethodMapperBean() {
    }

    public List mapMethodDangerousGoods(EMCCommandClass cmd, List<Object> dataList, EMCUserData userData) throws EMCEntityBeanException {
        List<Object> theDataList = new ArrayList();
        EMCCommandClass retCmd = new EMCCommandClass();
        retCmd.setCommandId(EMCCommands.CLIENT_GENERAL_COMMAND.getId());
        retCmd.setModuleNumber(enumEMCModules.DG.getId());
        retCmd.setMethodId(ClientDGMethods.GENERAL_METHOD.toString());

        switch (ServerDGMethods.fromString(cmd.getMethodId())) {
            //DGCargoCheckMaster
            case INSERT_DGCARGOCHECKMASTER: theDataList.add(cargoMastBean.insert((DGCargoCheckMaster)dataList.get(1), userData));
                break;
            case UPDATE_DGCARGOCHECKMASTER: theDataList.add(cargoMastBean.update((DGCargoCheckMaster)dataList.get(1), userData));
                break;
            case DELETE_DGCARGOCHECKMASTER: theDataList.add(cargoMastBean.delete((DGCargoCheckMaster)dataList.get(1), userData));
                break;
            case GETNUMROWS_DGCARGOCHECKMASTER: theDataList.add(cargoMastBean.getNumRows(DGCargoCheckMaster.class, userData));
                break;
            case GETDATA_DGCARGOCHECKMASTER:
                theDataList = (List<Object>) cargoMastBean.getDataInRange(DGCargoCheckMaster.class, userData,
                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DGCARGOCHECKMASTER: theDataList.add(cargoMastBean.validateField(dataList.get(1).toString(), (DGCargoCheckMaster)dataList.get(2), userData));
                break;
                //DGCargoCheckLines
            case INSERT_DGCARGOCHECKLINES: theDataList.add(cargoLinesBean.insert((DGCargoCheckLines)dataList.get(1), userData));
                break;
            case UPDATE_DGCARGOCHECKLINES: theDataList.add(cargoLinesBean.update((DGCargoCheckLines)dataList.get(1), userData));
                break;
            case DELETE_DGCARGOCHECKLINES: theDataList.add(cargoLinesBean.delete((DGCargoCheckLines)dataList.get(1), userData));
                break;
            case GETNUMROWS_DGCARGOCHECKLINES: theDataList.add(cargoLinesBean.getNumRows(DGCargoCheckLines.class, userData));
                break;
            case GETDATA_DGCARGOCHECKLINES:
                theDataList = (List<Object>) cargoLinesBean.getDataInRange(DGCargoCheckLines.class, userData,
                Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DGCARGOCHECKLINES: theDataList.add(cargoLinesBean.validateField(dataList.get(1).toString(), (DGCargoCheckLines)dataList.get(2), userData));
                break;
                //DGDContacts
            case INSERT_DGDCONTACTS:
                theDataList.add(contactsBean.insert((DGDContacts) dataList.get(1), userData));
                break;
            case UPDATE_DGDCONTACTS:
                theDataList.add(contactsBean.update((DGDContacts) dataList.get(1), userData));
                break;
            case DELETE_DGDCONTACTS:
                theDataList.add(contactsBean.delete((DGDContacts) dataList.get(1), userData));
                break;
            case GETNUMROWS_DGDCONTACTS:
                theDataList.add(contactsBean.getNumRows(DGDContacts.class, userData));
                break;
            case GETDATA_DGDCONTACTS:
                theDataList = (List<Object>) contactsBean.getDataInRange(DGDContacts.class, userData, Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DGDCONTACTS:
                theDataList.add(contactsBean.validateField(dataList.get(1).toString(), (DGDContacts) dataList.get(2), userData));
                break;
            //VehiclesDS
            case INSERT_VEHICLESDS:
                theDataList.add(vehiclesDSBean.insert((VehiclesDS) dataList.get(1), userData));
                break;
            case UPDATE_VEHICLESDS:
                theDataList.add(vehiclesDSBean.update((VehiclesDS) dataList.get(1), userData));
                break;
            case DELETE_VEHICLESDS:
                theDataList.add(vehiclesDSBean.delete((VehiclesDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_VEHICLESDS:
                theDataList.add(vehiclesDSBean.getNumRows(VehiclesDS.class, userData));
                break;
            case GETDATA_VEHICLESDS:
                theDataList = (List<Object>) vehiclesDSBean.getDataInRange(VehiclesDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_VEHICLESDS:
                theDataList.add(vehiclesDSBean.validateField(dataList.get(1).toString(), (VehiclesDS) dataList.get(2), userData));
                break;
            //DGDeclarationMaster
            case INSERT_DGDECLARATIONMASTER:
                theDataList.add(declarationMasterBean.insert((DGDeclarationMaster) dataList.get(1), userData));
                break;
            case UPDATE_DGDECLARATIONMASTER:
                theDataList.add(declarationMasterBean.update((DGDeclarationMaster) dataList.get(1), userData));
                break;
            case DELETE_DGDECLARATIONMASTER:
                theDataList.add(declarationMasterBean.delete((DGDeclarationMaster) dataList.get(1), userData));
                break;
            case GETNUMROWS_DGDECLARATIONMASTER:
                theDataList.add(declarationMasterBean.getNumRows(DGDeclarationMaster.class, userData));
                break;
            case GETDATA_DGDECLARATIONMASTER:
                theDataList = (List<Object>) declarationMasterBean.getDataInRange(DGDeclarationMaster.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DGDECLARATIONMASTER:
                theDataList.add(declarationMasterBean.validateField(dataList.get(1).toString(), (DGDeclarationMaster) dataList.get(2), userData));
                break;
            //DGDeclarationLines
            case INSERT_DGDECLARATIONLINES:
                theDataList.add(declarationLinesBean.insert((DGDeclarationLines) dataList.get(1), userData));
                break;
            case UPDATE_DGDECLARATIONLINES:
                theDataList.add(declarationLinesBean.update((DGDeclarationLines) dataList.get(1), userData));
                break;
            case DELETE_DGDECLARATIONLINES:
                theDataList.add(declarationLinesBean.delete((DGDeclarationLines) dataList.get(1), userData));
                break;
            case GETNUMROWS_DGDECLARATIONLINES:
                theDataList.add(declarationLinesBean.getNumRows(DGDeclarationLines.class, userData));
                break;
            case GETDATA_DGDECLARATIONLINES:
                theDataList = (List<Object>) declarationLinesBean.getDataInRange(DGDeclarationLines.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_DGDECLARATIONLINES:
                theDataList.add(declarationLinesBean.validateField(dataList.get(1).toString(), (DGDeclarationLines) dataList.get(2), userData));
                break;
            case PRINT_DECLARATION:
                theDataList = declarationReportBean.getReportResult(dataList, userData);
                break;
            //UNDS
            case INSERT_UNDS:
                theDataList.add(unDSBean.insert((UNDS) dataList.get(1), userData));
                break;
            case UPDATE_UNDS:
                theDataList.add(unDSBean.update((UNDS) dataList.get(1), userData));
                break;
            case DELETE_UNDS:
                theDataList.add(unDSBean.delete((UNDS) dataList.get(1), userData));
                break;
            case GETNUMROWS_UNDS:
                theDataList.add(unDSBean.getNumRows(UNDS.class, userData));
                break;
            case GETDATA_UNDS:
                theDataList = (List<Object>) unDSBean.getDataInRange(UNDS.class, userData,
                        Integer.parseInt(dataList.get(1).toString()), Integer.parseInt(dataList.get(2).toString()));
                break;
            case VALIDATEFIELD_UNDS:
                theDataList.add(unDSBean.validateField(dataList.get(1).toString(), (UNDS) dataList.get(2), userData));
                break;
                
            default:
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Mapper: Method not found", userData);
                }
                break;
        }

        theDataList.add(0, retCmd);
        return theDataList;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.base;

import emc.framework.EMCEntityClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author asd_admin
 */
@Entity
@Table(name="BaseUserFavourites", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "userModule", "userMenu", "companyId"})})
public class BaseUserFavourites extends EMCEntityClass{
    
    private String userId;
    private String userModule;
    private String userMenu;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserModule() {
        return userModule;
    }

    public void setUserModule(String userModule) {
        this.userModule = userModule;
    }

    public String getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(String userMenu) {
        this.userMenu = userMenu;
    }
    
}

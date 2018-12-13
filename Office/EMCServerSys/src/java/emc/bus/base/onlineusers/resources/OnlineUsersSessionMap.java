/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.onlineusers.resources;

import emc.entity.base.datasource.EMCSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wikus
 */
public class OnlineUsersSessionMap extends HashMap<Long, EMCSession> implements Map<Long, EMCSession> {

    public synchronized Object accessSessionMap(OnlineUsersMapAccess access, long sessionId, EMCSession sessionObject) {
        switch (access) {
            case GET:
                return this.get(sessionId);
            case KEY_SET:
                return new ArrayList(this.keySet());
            case SIZE:
                return this.size();
            case PUT:
                return this.put(sessionId, sessionObject);
            case REMOVE:
                return this.remove(sessionId);
            case VALUES:
                return new ArrayList(this.values());
            default:
                return null;
        }
    }

    /**
     *
     * @param key
     * @return
     * @deprecated accessSessionMap(OnlineUsersMapAccess access, long sessionId, EMCSession sessionObject)
     */
    @Override
    @Deprecated
    public EMCSession get(Object key) {
        return super.get(key);
    }

    /**
     *
     * @param key
     * @param value
     * @return
     * @deprecated accessSessionMap(OnlineUsersMapAccess access, long sessionId, EMCSession sessionObject)
     */
    @Override
    @Deprecated
    public EMCSession put(Long key, EMCSession value) {
        return super.put(key, value);
    }

    /**
     *
     * @return
     * @deprecated accessSessionMap(OnlineUsersMapAccess access, long sessionId, EMCSession sessionObject)
     */
    @Override
    @Deprecated
    public Set<Long> keySet() {
        return super.keySet();
    }

    /**
     *
     * @return
     * @deprecated accessSessionMap(OnlineUsersMapAccess access, long sessionId, EMCSession sessionObject)
     */
    @Override
    @Deprecated
    public Collection<EMCSession> values() {
        return super.values();
    }

    /**
     *
     * @return
     * @deprecated accessSessionMap(OnlineUsersMapAccess access, long sessionId, EMCSession sessionObject)
     */
    @Override
    @Deprecated
    public int size() {
        return super.size();
    }

    /**
     *
     * @param key
     * @return
     * @deprecated Use accessSessionMap(OnlineUsersMapAccess access, long sessionId, EMCSession sessionObject)
     */
    @Override
    @Deprecated
    public EMCSession remove(Object key) {
        return super.remove(key);
    }
}

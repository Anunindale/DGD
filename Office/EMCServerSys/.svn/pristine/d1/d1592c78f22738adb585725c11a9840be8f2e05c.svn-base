/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.onlineusers.resources;

import emc.server.EMCSessionMessages;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wikus
 */
public class OnlineUsersMessageMap extends HashMap<Long, EMCSessionMessages> implements Map<Long, EMCSessionMessages> {

    public synchronized Object accessMessageMap(OnlineUsersMapAccess access, long sessionId, EMCSessionMessages sessionObject) {
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
     * @deprecated accessMessageMap(OnlineUsersMapAccess access, long sessionId, EMCSessionMessages sessionObject)
     */
    @Override
    @Deprecated
    public EMCSessionMessages get(Object key) {
        return super.get(key);
    }

    /**
     *
     * @param key
     * @param value
     * @return
     * @deprecated accessMessageMap(OnlineUsersMapAccess access, long sessionId, EMCSessionMessages sessionObject)
     */
    @Override
    @Deprecated
    public EMCSessionMessages put(Long key, EMCSessionMessages value) {
        return super.put(key, value);
    }

    /**
     *
     * @return
     * @deprecated accessMessageMap(OnlineUsersMapAccess access, long sessionId, EMCSessionMessages sessionObject)
     */
    @Override
    @Deprecated
    public Set<Long> keySet() {
        return super.keySet();
    }

    /**
     *
     * @return
     * @deprecated accessMessageMap(OnlineUsersMapAccess access, long sessionId, EMCSessionMessages sessionObject)
     */
    @Override
    @Deprecated
    public Collection<EMCSessionMessages> values() {
        return super.values();
    }

    /**
     *
     * @return
     * @deprecated accessMessageMap(OnlineUsersMapAccess access, long sessionId, EMCSessionMessages sessionObject)
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
     * @deprecated Use accessMessageMap(OnlineUsersMapAccess access, long sessionId, EMCSessionMessages sessionObject)
     */
    @Override
    @Deprecated
    public EMCSessionMessages remove(Object key) {
        return super.remove(key);
    }
}

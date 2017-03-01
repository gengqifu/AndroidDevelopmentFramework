package com.example.examplelib.net;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.HttpCookie;

public class SerializableCookie implements Externalizable {

    private static final int COMMENT = 0x04;
    private static final int COMMENT_URL = 0x08;
    private static final int DOMAIN = 0x20;
    private static final int EXPIRY_DATE = 0x10;
    private static final int NAME = 0x01;
    private static final int PATH = 0x40;
    private static final int PORTS = 0x80;
    private static final int VALUE = 0x02;

    private transient HttpCookie cookie;
    private transient int nullMask = 0;

    public SerializableCookie() {
        super();
    }

    public SerializableCookie(final HttpCookie cookie) {
        super();
        this.cookie = cookie;
    }

    private String getComment() {
        return cookie.getComment();
    }

    private String getCommentURL() {
        return cookie.getCommentURL();
    }

    private String getDomain() {
        return cookie.getDomain();
    }

    private String getName() {
        return cookie.getName();
    }

    private String getPath() {
        return cookie.getPath();
    }

    private String getPorts() {
        return cookie.getPortlist();
    }

    private String getValue() {
        return cookie.getValue();
    }

    private boolean isSecure() {
        return cookie.getSecure();
    }

    private int getVersion() {
        return cookie.getVersion();
    }

    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        nullMask = in.readInt();
        String name = null;
        String value = null;
        String comment = null;
        String domain = null;
        String path = null;
        int[] ports;
        boolean isSecure;
        int version;

        if ((nullMask & SerializableCookie.NAME) == 0) {
            name = in.readUTF();
        }

        if ((nullMask & SerializableCookie.VALUE) == 0) {
            value = in.readUTF();
        }

        if ((nullMask & SerializableCookie.COMMENT) == 0) {
            comment = in.readUTF();
        }

        if ((nullMask & SerializableCookie.COMMENT_URL) == 0) {
            in.readUTF();
        }

        in.readBoolean();

        if ((nullMask & SerializableCookie.DOMAIN) == 0) {
            domain = in.readUTF();
        }

        if ((nullMask & SerializableCookie.PATH) == 0) {
            path = in.readUTF();
        }

        if ((nullMask & SerializableCookie.PORTS) == 0) {
            final int len = in.readInt();
            ports = new int[len];
            for (int i = 0; i < len; i++) {
                ports[i] = in.readInt();
            }
        }

        isSecure = in.readBoolean();
        version = in.readInt();
        final HttpCookie hc = new HttpCookie(name, value);
        hc.setComment(comment);
        hc.setDomain(domain);
        hc.setPath(path);
        hc.setSecure(isSecure);
        hc.setVersion(version);
        cookie = hc;
    }

    public String toString() {
        if (cookie == null) {
            return "null";
        } else {
            return cookie.toString();
        }
    }

    public void writeExternal(final ObjectOutput out) throws IOException {
        nullMask |= (getName() == null) ? SerializableCookie.NAME : 0;
        nullMask |= (getValue() == null) ? SerializableCookie.VALUE : 0;
        nullMask |= (getComment() == null) ? SerializableCookie.COMMENT : 0;
        nullMask |= (getCommentURL() == null) ? SerializableCookie.COMMENT_URL : 0;
        nullMask |= (getDomain() == null) ? SerializableCookie.DOMAIN : 0;
        nullMask |= (getPath() == null) ? SerializableCookie.PATH : 0;
        nullMask |= (getPorts() == null) ? SerializableCookie.PORTS : 0;

        out.writeInt(nullMask);

        if ((nullMask & SerializableCookie.NAME) == 0) {
            out.writeUTF(getName());
        }

        if ((nullMask & SerializableCookie.VALUE) == 0) {
            out.writeUTF(getValue());
        }

        if ((nullMask & SerializableCookie.COMMENT) == 0) {
            out.writeUTF(getComment());
        }

        if ((nullMask & SerializableCookie.COMMENT_URL) == 0) {
            out.writeUTF(getCommentURL());
        }

        if ((nullMask & SerializableCookie.DOMAIN) == 0) {
            out.writeUTF(getDomain());
        }

        if ((nullMask & SerializableCookie.PATH) == 0) {
            out.writeUTF(getPath());
        }

        if ((nullMask & SerializableCookie.PORTS) == 0) {
            if (getPorts() != null) {
                out.writeChars(getPorts());
            }
        }

        out.writeBoolean(isSecure());
        out.writeInt(getVersion());
    }
}

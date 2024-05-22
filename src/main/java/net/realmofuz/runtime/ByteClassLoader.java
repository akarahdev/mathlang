package net.realmofuz.runtime;

public class ByteClassLoader extends ClassLoader {
    public Class findClass(String name, byte[] ba) {
        return super.defineClass(name, ba, 0, ba.length);
    }

}

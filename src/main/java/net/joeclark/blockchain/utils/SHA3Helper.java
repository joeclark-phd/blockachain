package net.joeclark.blockchain.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

public class SHA3Helper {
    
    /** Using the Bouncy Castle library, convert a hash or key as byte array into a String. */
    public static String digestToHex(byte[] digest) {
        return Hex.toHexString(digest);
    }

    /** Helper method to hash a Serializable object. */
    public static byte[] hash256(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            byte[] digest = new SHA3.Digest256().digest(hash256(bos.toByteArray()));
            return digest;
        } catch(IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static byte[] hash256( byte[] bytes ) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256( );
        return digestSHA3.digest( bytes );
    }
}

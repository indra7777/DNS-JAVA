import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.StringJoiner;

public class DNSParser {

    public static byte[] encodeDomainName(DNS dns) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String domain = dns.getDomain();
        for (String label : domain.split("\\.")) {
            out.write(label.length());
            out.writeBytes(label.getBytes());
        }

        out.write(0);

        return out.toByteArray();
    }

    public static String parseDomainName(DataInputStream dataInputStream)
        throws IOException {
        byte b;
        StringJoiner labels = new StringJoiner(".");
        while ((b = dataInputStream.readByte()) != 0) {
            byte[] dst = new byte[b];
            dataInputStream.read(dst);
            labels.add(new String(dst));
        }
        return labels.toString();
    }
}

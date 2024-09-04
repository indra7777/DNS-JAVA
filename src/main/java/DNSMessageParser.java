import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.HashMap;

public class DNSMessageParser {

    HashMap<String, String> domainToIPMap = new HashMap<>();

    public DnsMessage parsePacket(DatagramPacket packet) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
            packet.getData()
        );
        DataInputStream dataInputStream = new DataInputStream(
            byteArrayInputStream
        );

        short id = dataInputStream.readShort();
        short flags = dataInputStream.readShort();
        short qdcount = dataInputStream.readShort();
        dataInputStream.readShort();
        short nscount = dataInputStream.readShort();
        short arcount = dataInputStream.readShort();

        char[] requestFlags = String.format(
            "%16s",
            Integer.toBinaryString(flags)
        )
            .replace(' ', '0')
            .toCharArray();
        requestFlags[0] = '1'; // QR
        requestFlags[13] = '1'; // RCODE
        flags = (short) Integer.parseInt(new String(requestFlags), 2);

        DNS dns = null;
        for (int i = 0; i < qdcount; i++) {
            String domainName = DNSParser.parseDomainName(dataInputStream);
            short type = dataInputStream.readShort();
            short classType = dataInputStream.readShort();
            dns = new DNS(domainName, "127.0.0.1", type, classType);
        }

        return new DnsMessage(
            id,
            flags,
            qdcount,
            (short) 1,
            nscount,
            arcount,
            dns
        );
    }
}

import java.nio.ByteBuffer;

public class DnsMessage {

    private DNS dns;
    private short id;
    private short flags;
    private short qdcount;

    private short ancount;
    private short nscount;
    private short arcount;

    public DnsMessage(
        short id,
        short flags,
        short qdcount,
        short ancount,
        short nscount,
        short arcount,
        DNS dns
    ) {
        this.id = id;
        this.flags = flags;
        this.qdcount = qdcount;
        this.ancount = ancount;
        this.nscount = nscount;
        this.arcount = arcount;
        this.dns = dns;
    }

    private ByteBuffer writeHeader(ByteBuffer buffer) {
        buffer.putShort(id);
        buffer.putShort(flags);
        buffer.putShort(qdcount);
        buffer.putShort(ancount);
        buffer.putShort(nscount);
        buffer.putShort(arcount);
        return buffer;
    }

    private ByteBuffer writeQuestion(ByteBuffer buffer) {
        buffer.put(DNSParser.encodeDomainName(this.dns));
        buffer.putShort((short) 1);
        buffer.putShort((short) 1);
        return buffer;
    }

    private ByteBuffer writeAnswer(ByteBuffer buffer) {
        buffer.put(DNSParser.encodeDomainName(this.dns));
        buffer.putShort((short) 1);
        buffer.putShort((short) 1);
        buffer.putInt(300);
        buffer.putShort((short) 4);
        buffer.put(new byte[] { 127, 0, 0, 1 });
        return buffer;
    }

    public byte[] array() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        writeHeader(byteBuffer);
        writeQuestion(byteBuffer);
        writeAnswer(byteBuffer);
        return byteBuffer.array();
    }
}

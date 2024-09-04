public class DNS {

    private String domain;
    private String ip;
    private short type;
    private short classType;

    public DNS(String domain, String ip, short type, short classType) {
        this.domain = domain;
        this.ip = ip;
        this.type = type;
        this.classType = classType;
    }

    public String getDomain() {
        return domain;
    }

    public String getIP() {
        return ip;
    }
}

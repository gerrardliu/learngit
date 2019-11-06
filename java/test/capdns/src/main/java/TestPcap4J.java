import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.namednumber.*;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class TestPcap4J {

    private long counter;

    public static void main(String[] args) {
        System.out.println("Hello");
        try {
            new TestPcap4J().loop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loop() throws PcapNativeException, NotOpenException {
        counter = 0L;
        String filter = "udp src port 53";
        PcapNetworkInterface nif;
        try {
            nif = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //nif = Pcaps.getDevByName("enx000ec6ac5bec");
        if (nif == null) {
            return;
        }

        final PcapHandle handle = nif.openLive(65535, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
        if (filter.length() != 0) {
            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
        }

        PacketListener listener = new PacketListener() {

            public void gotPacket(Packet packet) {
                counter++;

                System.out.println("\n" + handle.getTimestamp());
                //System.out.println(packet);

                //System.out.println("ether packet captured");
                EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                EtherType etherType = ethernetPacket.getHeader().getType();
                if (etherType.compareTo(EtherType.IPV4) != 0) {
                    System.out.println("not ipv4");
                    return;
                }

                //System.out.println("ipv4 packet captured");
                IpV4Packet ipV4Packet = (IpV4Packet) ethernetPacket.getPayload();
                //String srcAddr = ipV4Packet.getHeader().getSrcAddr().getHostAddress();
                //String dstAddr = ipV4Packet.getHeader().getDstAddr().getHostAddress();
                //System.out.println("srcAddr=" + srcAddr);
                //System.out.println("dstAddr=" + dstAddr);
                IpNumber ipProtocol = ipV4Packet.getHeader().getProtocol();
                if (ipProtocol.compareTo(IpNumber.UDP) != 0) {
                    System.out.println("not udp");
                    return;
                }

                UdpPacket udpPacket = (UdpPacket) ipV4Packet.getPayload();
                if (udpPacket == null) {
                    System.out.println("udp is null");
                    return;
                }

                DnsPacket dnsPacket = (DnsPacket) udpPacket.getPayload();
                if (dnsPacket == null) {
                    System.out.println("dns packet is null");
                    return;
                }

                DnsPacket.DnsHeader dnsHeader = dnsPacket.getHeader();
                if (dnsHeader == null) {
                    System.out.println("dns header is null");
                    return;
                }
                if (!dnsHeader.isResponse()) {
                    System.out.println("not response");
                    return;
                }
                if (dnsHeader.getOpCode() != DnsOpCode.QUERY) {
                    System.out.println("not query");
                    return;
                }
                if (dnsHeader.getrCode() != DnsRCode.NO_ERROR) {
                    System.out.println("dns has error");
                    return;
                }
                if (dnsHeader.getQdCount() < 1) {
                    System.out.println("qd count is 0");
                    return;
                }
                if (dnsHeader.getAnCount() < 1) {
                    System.out.println("an count is 0");
                    return;
                }

                List<DnsQuestion> questions = dnsHeader.getQuestions();
                for (DnsQuestion question : questions) {
                    System.out.println("domain=" + question.getQName());
                }

                List<DnsResourceRecord> answers = dnsHeader.getAnswers();
                for (DnsResourceRecord anwser : answers) {
                    //System.out.println(anwser.getDataType());
                    if (anwser.getDataType() == DnsResourceRecordType.A) {
                        DnsResourceRecord.DnsRData rData = anwser.getRData();
                        //System.out.println("rdata.len=" + rData.length());
                        if (rData.length() == 4) {
                            InetAddress address = null;
                            try {
                                address = InetAddress.getByAddress(rData.getRawData());
                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            }
                            System.out.println(address.getHostAddress());
                        }
                    }
                }
            }
        };

        try {
            handle.loop(-1, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handle.close();
    }
}

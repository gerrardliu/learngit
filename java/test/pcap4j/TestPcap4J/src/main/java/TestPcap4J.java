import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.util.NifSelector;

import java.io.IOException;

public class TestPcap4J {

    private Long counter;

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
        String filter = "tcp";
        PcapNetworkInterface nif;
        try {
            nif = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
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

                //System.out.println(handle.getTimestamp());
                //System.out.println(packet);

                //System.out.println("ether packet captured");
                EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                EtherType etherType = ethernetPacket.getHeader().getType();
                if (etherType.compareTo(EtherType.IPV4) != 0) {
                    //System.out.println("not ipv4");
                    return;
                }

                //System.out.println("ipv4 packet captured");
                IpV4Packet ipV4Packet = (IpV4Packet) ethernetPacket.getPayload();
                String srcAddr = ipV4Packet.getHeader().getSrcAddr().getHostAddress();
                String dstAddr = ipV4Packet.getHeader().getDstAddr().getHostAddress();
                //System.out.println("srcAddr=" + srcAddr);
                //System.out.println("dstAddr=" + dstAddr);
                IpNumber ipProtocol = ipV4Packet.getHeader().getProtocol();
                if (ipProtocol.compareTo(IpNumber.TCP) != 0) {
                    //System.out.println("not tcp");
                    return;
                }

                //System.out.println("tcp packet captured");
                TcpPacket tcpPacket = (TcpPacket) ipV4Packet.getPayload();
                int srcPort = tcpPacket.getHeader().getSrcPort().valueAsInt();
                int dstPort = tcpPacket.getHeader().getDstPort().valueAsInt();
                //System.out.println("srcPort=" + srcPort);
                //System.out.println("dstPort=" + dstPort);
                byte[] tcpPayLoad = tcpPacket.getPayload().getRawData();
                if (tcpPayLoad.length < 1) {
                    //System.out.println("tcpPayLoad is null");
                    return;
                }
                String jsonPacket = new String(tcpPayLoad).trim();
                //if (tcpPayLoad[0] != '{' || tcpPayLoad[tcpPayLoad.length-1] != '}') {
                if (jsonPacket.charAt(0) != '{' || jsonPacket.charAt(jsonPacket.length()-1) != '}') {
                    //System.out.println("tcpPayLoad is not json");
                    return;
                }

                System.out.println("counter=" + counter);
                System.out.println(jsonPacket);
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

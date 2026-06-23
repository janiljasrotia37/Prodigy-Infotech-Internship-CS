import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.Packet;

import java.util.List;

public class PacketAnalyzer {

    public static void main(String[] args) throws Exception {
        // List available network interfaces
        List<PcapNetworkInterface> devices = Pcaps.findAllDevs();

        if (devices.isEmpty()) {
            System.out.println("No network interfaces found. Run as Administrator.");
            return;
        }

        System.out.println("Available interfaces:");
        for (int i = 0; i < devices.size(); i++) {
            System.out.println(i + ": " + devices.get(i).getName() + " - " + devices.get(i).getDescription());
        }

        System.out.print("Select interface number: ");
        int choice = new java.util.Scanner(System.in).nextInt();
        PcapNetworkInterface device = devices.get(choice);

        int snapshotLength = 65536;
        int readTimeout = 50;
        PcapHandle handle = device.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);

        System.out.println("Starting capture... (Ctrl+C to stop)");

        PacketListener listener = packet -> analyzePacket(packet);

        int maxPackets = -1; // -1 means capture indefinitely
        handle.loop(maxPackets, listener);

        handle.close();
    }

    private static void analyzePacket(Packet packet) {
        IpV4Packet ipPacket = packet.get(IpV4Packet.class);

        if (ipPacket != null) {
            String srcIp = ipPacket.getHeader().getSrcAddr().getHostAddress();
            String dstIp = ipPacket.getHeader().getDstAddr().getHostAddress();

            System.out.println("\n[+] Packet Captured");
            System.out.println("    Source IP:      " + srcIp);
            System.out.println("    Destination IP: " + dstIp);

            TcpPacket tcpPacket = packet.get(TcpPacket.class);
            UdpPacket udpPacket = packet.get(UdpPacket.class);

            if (tcpPacket != null) {
                System.out.println("    Protocol:       TCP");
                System.out.println("    Src Port:       " + tcpPacket.getHeader().getSrcPort());
                System.out.println("    Dst Port:       " + tcpPacket.getHeader().getDstPort());
            } else if (udpPacket != null) {
                System.out.println("    Protocol:       UDP");
                System.out.println("    Src Port:       " + udpPacket.getHeader().getSrcPort());
                System.out.println("    Dst Port:       " + udpPacket.getHeader().getDstPort());
            } else {
                System.out.println("    Protocol:       OTHER");
            }
        }
    }
}
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

class PacketData {

    public String line;

    public long timestamp;
    public long counter;
    public long seq;
    public long ack;
    public int len;
    public String srcIp;
    public int srcPort;
    public String dstIp;
    public int dstPort;
    public String jsonPacket;
    public int isProcessed;

    public PacketData(String line) {

        this.line = line;

        String[] split = line.split("\t");
        if (split.length != 11) {
            System.out.println("error line");
            System.exit(1);
        }

        timestamp = Long.valueOf(split[0]);
        counter = Long.valueOf(split[1]);
        seq = Long.valueOf(split[2]);
        ack = Long.valueOf(split[3]);
        len = Integer.valueOf(split[4]);
        srcIp = split[5];
        srcPort = Integer.valueOf(split[6]);
        dstIp = split[7];
        dstPort = Integer.valueOf(split[8]);
        jsonPacket = new String(Base64.getDecoder().decode(split[9])).trim();
        isProcessed = Integer.valueOf(split[10]);
    }

    public String toLine() {

        StringBuilder sb = new StringBuilder(2048);
        sb.append(counter).append("\t");
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(timestamp))).append("\t");
        sb.append("[").append(srcIp).append(":");
        sb.append(srcPort).append(" => ");
        sb.append(dstIp).append(":");
        sb.append(dstPort).append("]\t");

        sb.append(seq).append("\t");
        sb.append(ack).append("\t");
        sb.append(len).append("\t");
        sb.append(seq + len).append("\t");
        sb.append(jsonPacket).append("\t");
        sb.append(isProcessed);
        return sb.toString();
    }

    public String getSocketPairId() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(srcIp).append(":").append(srcPort).append(" => ").append(dstIp).append(":").append(dstPort);
        return sb.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        //test1();
        //test2();
        test3();
    }

    private static void test1() {
        File file = new File("/tmp/miner/miner.out.20191021172011");
        List<String> strings = null;
        try {
            strings = FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Long, PacketData> map = new TreeMap<>();
        Map<Long, List<Long>> rmap = new HashMap<>();

        for (String line : strings) {
            //System.out.println(line);
            PacketData p = new PacketData(line);

            map.put(p.seq, p);
            if (!rmap.containsKey(p.ack)) {
                List<Long> a = new ArrayList<>();
                a.add(p.seq);
                rmap.put(p.ack, a);
            } else {
                rmap.get(p.ack).add(p.seq);
            }
        }

        long seq = 3865699372l;
        PacketData p = map.get(seq);
        while (p != null) {
            List<Long> rList = rmap.get(p.ack);
            Collections.sort(rList);
            for (Long i : rList) {
                PacketData pi = map.get(i);
                if (pi != null) {
                    System.out.println(pi.toLine());
                }
            }
            p = map.get(p.ack);
        }
    }

    private static void test2() {
        File file = new File("/tmp/miner/miner.out.20191021172011");
        List<String> strings = null;
        try {
            strings = FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Long, PacketData> map = new TreeMap<>();


        for (String line : strings) {
            //System.out.println(line);
            PacketData p = new PacketData(line);

            map.put(p.seq + p.ack, p);
        }

        int i = 0;
        for (Long seq : map.keySet()) {
            PacketData p = map.get(seq);
            System.out.println(p.toLine());
            i++;
//            Packet q = map.get(p.ack);
//            if (q != null){
//                System.out.println(q.toLine());
//            }
        }
        System.out.println(i);

    }

    private static void test3() {
        File file = new File("/tmp/miner/miner.out.20191024143039");
        List<String> strings = null;
        try {
            strings = FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Long, PacketData> packetMap = new TreeMap<>();
        for (String line : strings) {
            //System.out.println(line);
            PacketData p = new PacketData(line);
            packetMap.put(p.seq, p);


        }


        Long startSeq = 2064103932l;
        PacketData p = packetMap.get(startSeq);
        long curAck = p.ack;
        while (p != null) {
            if (curAck != p.ack) {
                PacketData reply = packetMap.get(curAck);
                System.out.println(reply.toLine());
            }
            System.out.println(p.toLine());
            curAck = p.ack;
            Long nextSeq = p.seq + p.len;
            p = packetMap.get(nextSeq);
        }
    }


}

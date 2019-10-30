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
        sb.append("[").append(getSocketPairDisplay()).append("]\t");
        sb.append(seq).append("\t");
        sb.append(ack).append("\t");
        sb.append(len).append("\t");
        sb.append(seq + len).append("\t");
        sb.append(jsonPacket).append("\t");
        sb.append(isProcessed);
        return sb.toString();
    }

    public String getSocketPairDisplay() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(srcIp).append(":").append(srcPort).append(" => ").append(dstIp).append(":").append(dstPort);
        return sb.toString();
    }
}

public class Main {

    private static long nextSeq(long seq) {
        return seq % 0x100000000L;
    }

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        test4();
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
        //File file = new File("/tmp/miner/miner.out.20191021172011");
        File file = new File("/tmp/miner/miner.out.20191025112659");
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
            long key = p.seq + p.ack;
            if (map.containsKey(key)) {
                System.out.println("dup key=" + p.seq);
            }
            map.put(key, p);
        }

        int i = 0;
        for (Long seq : map.keySet()) {
            PacketData p = map.get(seq);
            System.out.println(p.counter);
            i++;
        }
        System.out.println(i);

    }

    private static void test3() {

        String minerIp = "10.30.9.22";
        int minerPort = 35346;
        String poolIp = "203.107.46.175";
        int poolPort = 1800;

        Long startSeq = Long.MAX_VALUE;

        Map<Long, PacketData> packetMap = new TreeMap<>();

        File dir = new File("data/");
        List<File> files = (List<File>) FileUtils.listFiles(dir, null, false);
        for (File file : files) {
            if (!file.isFile() || !file.getName().startsWith("miner.out")) {
                System.out.println(String.format("skip filename=%s, not miner data", file.getName()));
                continue;
            }
            List<String> strings = null;
            try {
                System.out.println("try file=" + file.getName());
                strings = FileUtils.readLines(file, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String line : strings) {
                //System.out.println(line);
                PacketData p = new PacketData(line);
                if ((p.srcIp.equals(minerIp) && p.srcPort == minerPort && p.dstIp.equals(poolIp) && p.dstPort == poolPort) ||
                        (p.srcIp.equals(poolIp) && p.srcPort == poolPort && p.dstIp.equals(minerIp) && p.dstPort == minerPort)) {
                    if (packetMap.containsKey(p.seq)) {
                        System.out.println("seq=" + p.seq + ",already exists");
                    }
                    packetMap.put(p.seq, p);
                    if (p.seq < startSeq) {
                        startSeq = p.seq;
                    }
                }
            }
        }

        System.out.println("startSeq = " + startSeq);
        File file = new File(String.format("data/parse_%s_%d_to_%s_%d.res", minerIp, minerPort, poolIp, poolPort));
        if (file.exists()) {
            file.delete();
        }
        List<PacketData> res = new LinkedList<>();
        printSeq2(packetMap, startSeq, res);
        for (PacketData p : res) {
            //System.out.println(p.toLine());
            try {
                FileUtils.writeStringToFile(file, p.toLine() + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(res.size());
    }

    //vim miner.out.20191025125759
    private static void test4() {

        //File file = new File("/tmp/miner/miner.out.20191025125759");
        //Long startSeq = 2957490728L;
        File file = new File("data/miner.out.20191025112659");
        //Long startSeq = 2957344686L;
        Long startSeq = 2264188133L;

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
            if (packetMap.containsKey(p.seq)) {
                System.out.println("seq=" + p.seq + ",already exists");
            }
            packetMap.put(p.seq, p);
        }

        List<PacketData> res = new LinkedList<>();
        printSeq2(packetMap, startSeq, res);
        for (PacketData p : res) {
            System.out.println(p.toLine());
        }
        System.out.println(res.size());
    }

    private static void printSeq(Map<Long, PacketData> packetMap, long startSeq) {
        PacketData p = packetMap.get(startSeq);
        while (p != null) {
            System.out.println(p.toLine());

            Long nextSeq = p.seq + p.len;
            PacketData nextPacket = packetMap.get(nextSeq);
            if (nextPacket == null) {
                System.out.println("data missing, seq=" + nextSeq);
                break;
            }

            if (p.ack != nextPacket.ack) {
                p = packetMap.get(p.ack);
            } else {
                p = nextPacket;
            }
        }
    }

    private static void printSeq2(Map<Long, PacketData> packetMap, long startSeq, List<PacketData> res) {
        PacketData p = packetMap.get(startSeq);
        if (p == null) {
            System.out.println("startSeq data missing,seq=" + startSeq);
            return;
        }

        PacketData q = packetMap.get(p.ack);
        while (p != null && q != null) {
            //System.out.println(p.toLine());
            res.add(p); //p.seq == 2264191123l
            p = packetMap.get(nextSeq(p.seq + p.len));
            while (p != null && q != null && q.ack <= p.seq) {
                //System.out.println(q.toLine());
                res.add(q);
                q = packetMap.get(nextSeq(q.seq + q.len));
            }
        }
        while (p != null) {
            //System.out.println(p.toLine());
            res.add(p);
            p = packetMap.get(nextSeq(p.seq + p.len));
        }
        while (q != null) {
            //System.out.println(q.toLine());
            res.add(q);
            q = packetMap.get(nextSeq(q.seq + q.len));
        }
    }

}

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

class PacketData {

    public String line;

    public String socketId;
    public String rSocketId;

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
        sb.append("[").append(getSocketId()).append("]\t");
        sb.append(seq).append("\t");
        sb.append(ack).append("\t");
        sb.append(len).append("\t");
        sb.append(seq + len).append("\t");
        sb.append(jsonPacket).append("\t");
        sb.append(isProcessed);
        return sb.toString();
    }

    public String getSocketId() {
        if (socketId == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(srcIp).append("_").append(srcPort).append("_").append(dstIp).append("_").append(dstPort);
            socketId = sb.toString();
        }
        return socketId;
    }

    public String getReverseSocketId() {
        if (rSocketId == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(dstIp).append("_").append(dstPort).append("_").append(srcIp).append("_").append(srcPort);
            rSocketId = sb.toString();
        }
        return rSocketId;
    }

    public String getSocketSessionId() {
        List<String> hosts = new ArrayList<>();
        hosts.add(new StringBuilder(64).append(srcIp).append("_").append(srcPort).toString());
        hosts.add(new StringBuilder(64).append(dstIp).append("_").append(dstPort).toString());
        hosts.sort((o1, o2) -> o1.compareTo(o2));
        return StringUtils.join(hosts, "_");
    }
}

class SocketData {
    public String socketId;
    public String rSocketId;
    public long startSeq;
    Map<Long, PacketData> packetMap;

    public SocketData() {
        startSeq = Long.MAX_VALUE;
        packetMap = new HashMap<>();
    }

    public static SocketData createSocketDataFromPacket(PacketData packet) {
        if (packet == null) {
            return null;
        }
        SocketData socket = new SocketData();
        socket.socketId = packet.getSocketId();
        socket.rSocketId = packet.getReverseSocketId();
        socket.startSeq = packet.seq;
        socket.packetMap.put(packet.seq, packet);
        return socket;
    }
}

class SocketSessionData {
    public String socketSessionId;
    public SocketData socket1;
    public SocketData socket2;
    public Map<String, SocketData> socketMap;

    public SocketSessionData() {
        socketMap = new HashMap<>();
    }

    public static SocketSessionData createSocketSessionFromPacket(PacketData packet) {
        if (packet == null) {
            return null;
        }
        SocketSessionData session = new SocketSessionData();
        session.socketSessionId = packet.getSocketSessionId();
        session.socket1 = SocketData.createSocketDataFromPacket(packet);
        session.socketMap.put(session.socket1.socketId, session.socket1);
        session.socket2 = new SocketData();
        session.socket2.socketId = session.socket1.rSocketId;
        session.socket2.rSocketId = session.socket1.socketId;
        session.socketMap.put(session.socket2.socketId, session.socket2);
        return session;
    }

    public PacketData getStart() {
        PacketData p1 = socket1.packetMap.get(socket1.startSeq);
        PacketData p2 = socket2.packetMap.get(socket2.startSeq);
        if (p1 == null || p2 == null) {
            return p1;
        }

        if (p1.ack == p2.seq) {
            return p1;
        } else if (p2.ack == p1.seq) {
            return p2;
        } else {
            return null;
        }

    }

    private long nextSeq(long seq) {
        return seq % 0x100000000L;
    }

    public List<PacketData> getPacketStream() {
        List<PacketData> res = new LinkedList<>();

        PacketData p = getStart();
        if (p == null) {
            System.out.println(String.format("%s, startSeq data missing", socketSessionId));
            return res;
        }

        PacketData q = socketMap.get(p.getReverseSocketId()).packetMap.get(p.ack);
        while (p != null && q != null) {
            //System.out.println(p.toLine());
            res.add(p); //p.seq == 2264191123l
            p = socketMap.get(p.getSocketId()).packetMap.get(nextSeq(p.seq + p.len));
            while (p != null && q != null && q.ack <= p.seq) {
                //System.out.println(q.toLine());
                res.add(q);
                q = socketMap.get(q.getSocketId()).packetMap.get(nextSeq(q.seq + q.len));
            }
        }
        while (p != null) {
            //System.out.println(p.toLine());
            res.add(p);
            p = socketMap.get(p.getSocketId()).packetMap.get(nextSeq(p.seq + p.len));
        }
        while (q != null) {
            //System.out.println(q.toLine());
            res.add(q);
            q = socketMap.get(q.getSocketId()).packetMap.get(nextSeq(q.seq + q.len));
        }

        return res;
    }
}

public class Main {

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
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

        String filePattern = "miner\\.out(\\.\\d+)?";
        Long startSeq = Long.MAX_VALUE;

        Map<Long, PacketData> packetMap = new TreeMap<>();

        File dir = new File("/tmp/miner/");
        List<File> files = (List<File>) FileUtils.listFiles(dir, null, false);
        for (File file : files) {
            if (!file.isFile() || !file.getName().startsWith("miner.out")) {
                System.out.println(String.format("skip filename=%s, not miner data", file.getName()));
                continue;
            }
            if (!Pattern.matches(filePattern, file.getName())) {
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


    private static void test5() {

        String dataDir = "/tmp/miner/";
        String filePattern = "miner\\.out(\\.\\d+)?";
        String outputDir = "data";

        Map<String, SocketSessionData> sessionMap = new HashMap<>();

        File dir = new File(dataDir);
        List<File> files = (List<File>) FileUtils.listFiles(dir, null, false);
        for (File file : files) {
            if (!file.isFile() || !file.getName().startsWith("miner.out")) {
                System.out.println(String.format("skip filename=%s, not miner data", file.getName()));
                continue;
            }
            if (!Pattern.matches(filePattern, file.getName())) {
                System.out.println(String.format("skip filename=%s, not miner data", file.getName()));
                continue;
            }
            List<String> strings = null;
            try {
                System.out.println("try file=" + file.getName());
                strings = FileUtils.readLines(file, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            for (String line : strings) {
                //System.out.println(line);
                PacketData p = new PacketData(line);
                String sessionId = p.getSocketSessionId();
                if (!sessionMap.containsKey(sessionId)) {
                    SocketSessionData session = SocketSessionData.createSocketSessionFromPacket(p);
                    sessionMap.put(sessionId, session);
                } else {
                    String socketId = p.getSocketId();
                    SocketSessionData session = sessionMap.get(sessionId);
                    if (!session.socketMap.containsKey(socketId)) {
                        System.out.println(String.format("socketId=%s, not exist", socketId));
                        return;
                    } else {
                        SocketData socket = session.socketMap.get(socketId);
                        if (socket.packetMap.containsKey(p.seq)) {
                            System.out.println(String.format("%s seq=%d, duplicated", socketId, p.seq));
                        }
                        socket.packetMap.put(p.seq, p);

                        //update startSeq
                        if (p.seq < socket.startSeq) { //req may recycle to 0
                            long diff = socket.startSeq - p.seq;
                            if (diff < 0x80000000L || socket.startSeq == Long.MAX_VALUE) {
                                socket.startSeq = p.seq;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(String.format("total %d socket sessions", sessionMap.size()));

        for (String sessionId : sessionMap.keySet()) {

            SocketSessionData session = sessionMap.get(sessionId);

            System.out.println(String.format("session(%s), with %d socket flows", sessionId, session.socketMap.size()));

            for (String socketId : session.socketMap.keySet()) {
                SocketData socket = session.socketMap.get(socketId);
                System.out.println(String.format("\tsocket(%s), total %d frames, startSeq=%d", socketId, socket.packetMap.size(), socket.startSeq));
            }

            String outputFileName = String.format("%s/parse_%s.res", outputDir, sessionId);
            System.out.println("outputFileName=" + outputFileName);
            File file = new File(outputFileName);
            if (file.exists()) {
                file.delete();
            }

            List<PacketData> packetStream = session.getPacketStream();
            System.out.println(String.format("total %d packets", packetStream.size()));
            for (PacketData p : packetStream) {
                //System.out.println(p.toLine());
                try {
                    FileUtils.writeStringToFile(file, p.toLine() + "\n", "UTF-8", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private static long nextSeq(long seq) {
        return seq % 0x100000000L;
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

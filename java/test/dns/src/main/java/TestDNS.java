import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class TestDNS {
    public static void main(String[] args) {
        System.out.println("hi");
        String domain = "btc.hh.apxd3.antpool.com";
//        String domain = "47.94.129.4";
//        String domain = "localhost";
        System.out.println("domain=" + domain);

        List<String> allIpByName = getAllIpByName(domain);
        if (allIpByName == null) {
            System.out.println("dns failed");
            return;
        }
        for (String ip : allIpByName) {
            System.out.println(ip);
        }
    }

    private static List<String> getAllIpByName(String domain) {
        List<String> res = new ArrayList<String>();
        try {
            for (InetAddress ia : InetAddress.getAllByName(domain)) {
                res.add(ia.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return res;
    }
}

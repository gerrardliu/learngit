import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        byte[] dec = Base64.getDecoder().decode("R0VUIC8gSFRUUC8xLjENCkhvc3Q6IGNvbm5lY3Rpdml0eS1jaGVjay51YnVudHUuY29tDQpBY2NlcHQ6ICovKg0KQ29ubmVjdGlvbjogY2xvc2UNCg0K");
        String http = new String(dec);
        System.out.println(http);
        HttpPart httpPart = new Main().getHttpPart(http);
        System.out.println("url="+httpPart.url);
        System.out.println("host="+httpPart.host);
    }

    class HttpPart {
        public String method;
        public String version;
        public String host;
        public String url;
    }

    private HttpPart getHttpPart(String http) {
        if (http == null) {
//            log.error("http is null");
            return null;
        }
        int headerEndPos = http.indexOf("\r\n\r\n");
        if (headerEndPos == -1) {
//            log.error("http format wrong");
            return null;
        }
        HttpPart res = new HttpPart();
        String httpHeader = http.substring(0, headerEndPos);
        String[] headerLines = httpHeader.split("\r\n");
        for (int i = 0; i < headerLines.length; i++) {
            if (i == 0) {
                String[] firstLineParts = headerLines[i].split(" ");
                if (firstLineParts.length != 3) {
//                    log.error("first line format wrong");
                    return null;
                }
                res.method = firstLineParts[0];
                res.url = firstLineParts[1];
                res.version = firstLineParts[2];
            }
            String headerFieldName = "Host:";
            if (headerLines[i].startsWith(headerFieldName)) {
                res.host = headerLines[i].substring(headerFieldName.length());
            }
        }
        return res;
    }
}

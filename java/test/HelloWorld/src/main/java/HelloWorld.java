import java.util.Base64;

public class HelloWorld {
    public static void main(String[] args) {
        long start = System.nanoTime();
        for (int i=0;i<1;i++) {
            System.out.println("Hello World"+i);
        }
        long n = System.nanoTime() - start;
        System.out.println("elapsed:"+n+" ns");

        String str = new String(Base64.getDecoder().decode("R0VUIC8gSFRUUC8xLjENCkhvc3Q6IGNvbm5lY3Rpdml0eS1jaGVjay51YnVudHUuY29tDQpBY2NlcHQ6ICovKg0KQ29ubmVjdGlvbjogY2xvc2UNCg0K"));
        System.out.println(str);
    }
}

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Test{
	public static void main(String[] args) {
		System.out.println("hello");
		List<String> strings = Arrays.asList("abc","","bc");
		strings.forEach(System.out::println);
		//List<String> res = strings.stream().filter(e->!e.isEmpty()).collect(Collectors.toList());
		//res.forEach(System.out::println);
		String res = strings.stream().filter(e->!e.isEmpty()).collect(Collectors.joining(","));
		System.out.println(res);
	}
}

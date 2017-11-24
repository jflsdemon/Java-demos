import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.InflaterInputStream;

public class BRRead {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("按下q退出");
/**		
		char c;
		do {
			c = (char) br.read();
			System.out.println(c);
		} while (c != 'q');
**/
		String str;
		do {
			str = br.readLine();
			System.out.println(str);
		} while (!str.equals("end"));
	}
		
}

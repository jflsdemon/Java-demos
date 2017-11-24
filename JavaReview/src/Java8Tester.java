
public class Java8Tester {

	public static void main(String[] args) {
		Java8Tester tester = new Java8Tester();
		
		MathOperation addition = (int a, int b) -> a+b;
		
		System.out.println("10 + 5 = " + tester.operation(10, 5, addition));
		
		GreetingService greetingService = messages -> System.out.println("Hello " + messages);
		greetingService.sayMessage("World");

	}
	
	interface MathOperation {
		int operation(int a, int b);
	}
	
	interface GreetingService {
		void sayMessage(String message);
	}

	private int operation(int a, int b, MathOperation mathOperation) {
		return mathOperation.operation(a, b);
	}
}

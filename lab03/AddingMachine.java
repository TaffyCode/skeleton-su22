import java.util.*;

public class AddingMachine {

	public static void main (String[] args) {

		Scanner scanner = new Scanner(System.in);
		boolean isPreviousZero = false;
		int total = 0;
		int subtotal = 0;
		int input;
		int MAXIMUM_NUMBER_OF_INPUTS = 100;
        int[] listOfInputs = new int[MAXIMUM_NUMBER_OF_INPUTS];
        int index = 0;

        // TODO Add code anywhere below to complete AddingMachine
		int count = 0;
		while (true) {
			input = scanner.nextInt();
			if (input == 0) {
				if (isPreviousZero) {
					System.out.println("total " + total);
					for (int x = 0; x < count; x++) {
						System.out.println(listOfInputs[x]);
					}
					return;
				} else {
					System.out.println("subtotal " + subtotal);
					total += subtotal;
					subtotal = 0;
					isPreviousZero = true;
				}
			}
			else {
				listOfInputs[index] = input;
				index++;
				count++;
			}

			subtotal += input;
			if (input != 0) {
				isPreviousZero = false;
			}

		}
	}

}
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final double DRINK_PRICE = 15.50;
        final double STUDENT_DISCOUNT_RATE = 0.10;
        final double SENIOR_DISCOUNT_RATE = 0.05;
        final double TIP_RATE = 0.10;

        Scanner input = new Scanner(System.in);

        System.out.print("Customer name: ");
        String customerName = input.nextLine();

        System.out.print("Customer age: ");
        int customerAge = input.nextInt();

        System.out.print("Meal price: ");
        double mealPrice = input.nextDouble();

        System.out.print("Number of drinks: ");
        int drinkCount = input.nextInt();

        input.nextLine();
        System.out.print("Is the customer a student? (yes/no): ");
        boolean isStudent = input.nextBoolean();

        System.out.print("Service rating (1-5): ");
        int serviceRating = input.nextInt();

        if (serviceRating < 1 || serviceRating > 5) {
            System.out.println("Error: Service rating must be between 1 and 5.");
            return;
        }

        double drinksCost = drinkCount * DRINK_PRICE;
        double subtotal = mealPrice + drinksCost;

        double discountPercent = 0.0;
        if (isStudent && customerAge >= 60) {
            if (STUDENT_DISCOUNT_RATE > SENIOR_DISCOUNT_RATE) {
                discountPercent = STUDENT_DISCOUNT_RATE;
            } else {
                discountPercent = SENIOR_DISCOUNT_RATE;
            }
        } else if (isStudent) {
            discountPercent = STUDENT_DISCOUNT_RATE;
        } else if (customerAge >= 60) {
            discountPercent = SENIOR_DISCOUNT_RATE;
        }

        double discountAmount = subtotal * discountPercent;
        double discountedSubtotal = subtotal - discountAmount;

        double tipAmount = 0.0;
        if (serviceRating >= 4 && serviceRating <= 5) {
            tipAmount = discountedSubtotal * TIP_RATE;
        }

        double finalBill = discountedSubtotal + tipAmount;
        int wholeAmount = (int) finalBill;

        System.out.println();
        System.out.println("SMART RESTAURANT");
        System.out.println("Customer: " + customerName);
        System.out.printf("Meal: $%.2f%n", mealPrice);
        System.out.printf("Drinks: $%.2f%n", drinksCost);
        System.out.printf("Discount: $%.2f%n", discountAmount);
        System.out.printf("Tip: $%.2f%n", tipAmount);
        System.out.printf("Final bill: $%.2f%n", finalBill);
        System.out.printf("Whole amount: $%d%n", wholeAmount);

        input.close();
    }
}

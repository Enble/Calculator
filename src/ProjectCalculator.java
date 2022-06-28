public class ProjectCalculator {
    public static void main(String[] args) {
        Panel panel = new Panel();
        panel.showStartScreen();
        do {
            try {
                panel.showCurrentNum();
            } catch(DuplicatedOperatorException de) {
                System.out.println("  Operator was duplicated. Try again.");
            } catch(ArithmeticException ae) {
                System.out.println("  You can't divide by zero. Try again.");
            } catch(ExitException ee) {
                System.out.println("  Thank you for using my Calculator.");
                System.out.println("*****************************************************");
                break;
            } catch(Exception e) {
                System.out.println("  Please write exact formula. Try again");
            }
        } while(true);
    }
}
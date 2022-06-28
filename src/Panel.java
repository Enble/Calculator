class Panel {
    private final String version = "1.0.0";
    private String versionExplain = "Only natural numbers can be calculated.";
    
    private double currentNum = 0.0;

    void showStartScreen() {
        System.out.println("*****************************************************");
        System.out.println("* Welcome to Calculator by Enble // v." + version);
        System.out.println("* " + versionExplain);
        System.out.println("* You can calculate basic arithmetic operation.");
        System.out.println("* Please enter formula using only +, -, *, /.");
        System.out.println("* If you want to exit this program, enter \"EXIT\"");
        System.out.println("*****************************************************");
    }
    
    void showCurrentNum() throws DuplicatedOperatorException, ArithmeticException, ExitException {
        Calculator cal = new Calculator();
        try {
            cal.setString(); // read formula
            cal.parseString(); // parse formula with string
            currentNum = cal.calculate();
        } catch(DuplicatedOperatorException de) {
            throw de;
        } catch(ArithmeticException ae) {
            throw ae;
        } catch(ExitException ee) {
            throw ee;
        }
        System.out.println("  Current result >> " + currentNum);
    }
}
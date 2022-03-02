import java.util.*;

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

class Calculator {
    private String inputStr = "";
    ArrayList<String> arr = new ArrayList<>();
    
    void setString() throws ExitException {
        System.out.print("  Enter formula(or EXIT) >> ");
        Scanner sc = new Scanner(System.in);
        inputStr = sc.nextLine();
        if((inputStr.toLowerCase()).equals("exit")) {
            throw new ExitException();
        }
    }
    
    void parseString() throws DuplicatedOperatorException {
        String num = "";
        boolean isDuplicated = false;
        
        inputStr = inputStr.replace(" ", ""); // 공백문자 제거
        arr.clear();
        
        for(int i=0; i<inputStr.length(); i++) {
            char ch = inputStr.charAt(i);
            
            if(ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                if(isDuplicated) {
                    throw new DuplicatedOperatorException();
                }
                isDuplicated = true;
                arr.add(num);
                num = "";
                arr.add(ch + "");
            } else {
                isDuplicated = false;
                num = num + ch;
            }
        }
        arr.add(num);
        arr.remove("");
    }
    
    double calculate() throws ArithmeticException {
        double prev = 0.0;
        double cur = 0.0;
        String mode = "";
        
        // 연산자가 *와 /일 경우
        for(int i=0; i<arr.size(); i++) {
            String s = arr.get(i);
            
            if(s.equals("+")) {
                mode = "add";
            } else if(s.equals("-")) {
                mode = "sub";
            } else if(s.equals("*")) {
                mode = "mul";
            } else if(s.equals("/")) {
                mode = "div";
            } else {
                if(mode.equals("mul") || mode.equals("div")) {
                    Double first = Double.valueOf(arr.get(i - 2));
                    Double second = Double.valueOf(arr.get(i));
                    Double result = 0.0;
                    
                    if(mode.equals("mul")) {
                        result = first * second;
                    } else {
                        if(second == 0.0) {
                            throw new ArithmeticException();
                        }
                        result = first / second;
                    }                    
                    
                    arr.add(i + 1, Double.toString(result));
                    
                    for(int j=0; j<3; j++) {
                        arr.remove(i - 2);
                    }
                    i -= 2;
                }
            }
        }  // *, / 계산 끝
        
        mode = "";
        // 연산자가 +와 -일 경우
        for(String s : arr) {
            if(s.equals("+")) {
                mode = "add";
            } else if(s.equals("-")) {
                mode = "sub";
            } else {
                cur = Double.valueOf(s);
                if(mode == "add") {
                    prev += cur;
                } else if(mode == "sub") {
                    prev -= cur;
                } else {
                    prev = cur;
                }
            }
            prev = Math.round(prev * 1000000) / 1000000.0;
        }
        
        return prev;
    }
}

// 연산자가 연속된 경우 예외처리
class DuplicatedOperatorException extends Exception {}
// EXIT를 친 경우 예외처리
class ExitException extends Exception {}
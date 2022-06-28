import java.util.ArrayList;
import java.util.Scanner;

class Calculator {
    private String inputStr = "";
    ArrayList<String> arr = new ArrayList<>();
    
    void setString() throws ExitException {
        System.out.print("  Enter formula(or EXIT) >> ");
        Scanner sc = new Scanner(System.in);
        inputStr = sc.nextLine();
        sc.close();
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
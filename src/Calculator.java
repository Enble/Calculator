import java.util.*;

public class Calculator {
    public static void main(String[] args) {
        Panel panel = new Panel();
        panel.showStartScreen();
        
        panel.showMenu();
        Scanner sc = new Scanner(System.in);
        Calculate cal = new Calculate();
        cal.setString(sc.nextLine()); // read formula
        cal.parseString();
        cal.showString();
    }
}

class Panel {
    private String version = "1.0.0";
    private String versionExplain = "Only natural numbers can be calculated";

    void showStartScreen() {
        System.out.println("*******************************************************");
        System.out.println("  Welcome to calculator by Enble // v." + version);
        System.out.println("  " + versionExplain);
        System.out.println("  You can calculate basic arithmetic operation");
    }
    
    void showMenu() {
        System.out.print("  Input formula(only using +, -, *, /) >> ");
    }
    
    void showResult() {
        
    }
}

class Calculate {
    private String str = "";
    private double result = 0;
    
    ArrayList<String> arr = new ArrayList<>();
    
    void setString(String str) {
        str = this.str;
    }
    
    void parseString() {
        String num = "";
        
        str = str.replace(" ", ""); // 공백문자 제거
        arr.clear();
        
        for(int i=0; i<str.length(); i++) {
            char ch = str.charAt(i);
            
            if(ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                arr.add(num);
                num = "";
                arr.add(ch + "");
            } else {
                num = num + ch;
            }
        }
        arr.add(num);
    }
    
    void showString() {
        System.out.println(arr);
    }
}




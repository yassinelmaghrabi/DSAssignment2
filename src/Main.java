import java.util.Arrays;
import java.util.Stack;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static boolean isMathOperator(char c) {

        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    private static int evaluatePrefixExpression(Stack<String> prefixStack) {
        Stack<Integer> operandStack = new Stack<>();

        while (!prefixStack.isEmpty()) {
            String token = prefixStack.pop();

            // If the token is an operand, push its integer value onto the operand stack
            if (isOperand(token)) {
                operandStack.push(Integer.parseInt(token));
            } else { // If the token is an operator
                // Pop two operands from the operand stack
                int operand1 = operandStack.pop();
                int operand2 = operandStack.pop();

                // Apply the operator and push the result back onto the operand stack
                int result = applyOperator(token, operand1, operand2);
                operandStack.push(result);
            }
        }

        // The final result is on the top of the operand stack
        return operandStack.pop();
    }

    private static boolean isOperand(String token) {
        // Check if the token is an operand (assumes operands are numbers)
        return Character.isDigit(token.charAt(0));
    }

    private static int applyOperator(String operator, int operand1, int operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    public static void main(String[] args) {
        String xml_string =  """
                <expr type="binary">
    <operator value="+"/>
    <expr type="binary">
        <operator value="*"/>
        <expr type="atom">
            <atom value="3"/>
        </expr>
        <expr type="binary">
            <operator value="+"/>
            <expr type="atom">
                <atom value="5"/>
            </expr>
            <expr type="atom">
                <atom value="4"/>
            </expr>
        </expr>
    </expr>
    <expr type="binary">
        <operator value="*"/>
        <expr type="atom">
            <atom value="9"/>
        </expr>
        <expr type="atom">
            <atom value="8"/>
        </expr>
    </expr>
</expr>""";
        String[] lines_array=xml_string.split("\\n");
        Stack<String> linestack = new Stack<String>();
        for (String element: lines_array
             ) {
            boolean open = false;
            StringBuilder stb = new StringBuilder();
            for (int i = element.length()-1;i>0;i--){
                if (element.charAt(i) == '\"' && !open){
                    open = true;
                } else if (element.charAt(i) == '\"' && open) {
                    open = false;
                }
                if (open && (Character.isDigit(element.charAt(i)) || isMathOperator(element.charAt(i)))) {
                    stb.append(element.charAt(i));
                    linestack.push(stb.reverse().toString());
                }
            }

        }
        StringBuilder stb = new StringBuilder();
        for (int i = 0;i<linestack.toArray().length;i++){
            stb.append(linestack.toArray()[i]);
        }
        System.out.print("Prefix Expression: ");
        System.out.println(stb.toString());


        System.out.println(evaluatePrefixExpression(linestack));

    }
}
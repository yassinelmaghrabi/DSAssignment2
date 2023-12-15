import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;


public class Main {
    private static boolean isMathOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    private static int evaluatePrefixExpression(Stack<String> prefixStack) {
        Stack<Integer> operandStack = new Stack<>();

        while (!prefixStack.isEmpty()) {
            String token = prefixStack.pop();

            if (isOperand(token)) {
                operandStack.push(Integer.parseInt(token));
            } else {
                int operand1 = operandStack.pop();
                int operand2 = operandStack.pop();
                int result = applyOperator(token, operand1, operand2);
                operandStack.push(result);
            }
        }
        return operandStack.pop();
    }

    private static boolean isOperand(String token) {
        // Check if the token is an operand (assumes operands are numbers)
        return Character.isDigit(token.charAt(0));
    }

    private static int applyOperator(String operator, int operand1, int operand2) {
        return switch (operator) {
            case "+" -> operand1 + operand2;
            case "-" -> operand1 - operand2;
            case "*" -> operand1 * operand2;
            case "/" -> operand1 / operand2;
            default -> 0;
        };
    }
    private static String convertToInfixExpression(Stack<String> prefixStack) {
        Stack<String> infixStack = new Stack<>();

        while (!prefixStack.isEmpty()) {
            String token = prefixStack.pop();

            if (isOperand(token)) {
                infixStack.push(token);
            } else if (isMathOperator(token.charAt(0))) {
                String operand1 = infixStack.pop();
                String operand2 = infixStack.pop();
                String infixExpression = "(" + operand1 + token + operand2 + ")";
                infixStack.push(infixExpression);
            }
        }

        return infixStack.pop();
    }
    private static String readXmlFromFile(String filePath) {
        StringBuilder xmlContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
        } catch (Exception ignored) {

        }
        return xmlContent.toString();
    }

    public static void main(String[] args) {
        String xml_string = readXmlFromFile("src/file.xml");

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
        String infixExpression = convertToInfixExpression((Stack<String>) linestack.clone());
        System.out.print("Infix Expression: ");
        System.out.println(infixExpression);
        System.out.println(evaluatePrefixExpression(linestack));

    }
}
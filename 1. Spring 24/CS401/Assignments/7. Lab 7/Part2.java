package Lab7;

import java.util.Stack;

public class Part2 {

    public static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();

        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(Character.getNumericValue(c));
            } else {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = performOperation(operand1, operand2, c);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    private static int performOperation(int operand1, int operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        String[] infixExpressions = {"1+9*3", "9 + 6 - 3 * 2 + 5", "5 + 2 - 8 / 2 + 6 - 7 + 6 * 3"};

        for (String infix : infixExpressions) {
            String postfix = infixToPostfix(infix);
            int result = evaluatePostfix(postfix);

            System.out.println("Infix: " + infix);
            System.out.println("Postfix: " + postfix);
            System.out.println("Evaluation: " + result);
            System.out.println();
        }
    }
}

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.lang.*;

public class CalculatorServer {
    public static void main(String args[]) throws IOException {

        ServerSocket ss = new ServerSocket(4444);
        Socket s = ss.accept();


        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true) {
            // wait for input
            String input = dis.readUTF();

            if (input.equals("bye"))
                break;

            System.out.println("Equation received:-" + input);
            double result = 0.0;

            //Use StringTokenizer to break the input string to operand and operator parts
            StringTokenizer st = new StringTokenizer(input);

            //Operator and Operands
            String operation = st.nextToken();
            double oprnd1 = 0.0;
            double oprnd2 = 0.0;

            //Check number of tokens for operators that need only one operand like sin and cos
            if (st.countTokens() == 2) {
                oprnd1 = Double.parseDouble(st.nextToken());
                oprnd2 = Double.parseDouble(st.nextToken());
            } else if (st.countTokens() == 1) {
                oprnd1 = Double.parseDouble(st.nextToken());
            }

            //Perform the operation
            switch (operation) {
                case "ADD":
                    result = oprnd1 + oprnd2;
                    break;
                case "SUBTRACT":
                    result = oprnd1 - oprnd2;
                    break;
                case "MULTIPLY":
                    result = oprnd1 * oprnd2;
                    break;
                case "DIVIDE":
                    if (oprnd2 == 0) {
                        result = Double.MAX_VALUE;
                    }
                    result = oprnd1 / oprnd2;
                    break;
                case "SIN": {
                    double angle = Math.toRadians(oprnd1);
                    result = Math.sin(angle);
                    break;
                }
                case "COS": {
                    double angle = Math.toRadians(oprnd1);
                    result = Math.cos(angle);
                    break;
                }
                case "TAN": {
                    double angle = Math.toRadians(oprnd1);
                    if ((oprnd1 % 90 == 0) && (oprnd1 % 180 != 0)) {
                        if (Math.sin(angle) > 0)
                            result = Double.POSITIVE_INFINITY;
                        else
                            result = Double.NEGATIVE_INFINITY;
                    } else
                        result = Math.tan(angle);
                    break;
                }
                case "COT": {
                    double angle = Math.toRadians(oprnd1);
                    double tempCos = Math.cos(angle);
                    if (tempCos == 0)
                        result = 0.0;
                    else if ((oprnd1 % 90 != 0) && (oprnd1 % 180 == 0)) {
                        if (tempCos > 0)
                            result = Double.POSITIVE_INFINITY;
                        else
                            result = Double.NEGATIVE_INFINITY;
                    } else {
                        result = 1 / Math.tan(angle);
                    }
                    break;
                }
                default:
                    //Unknown Operator
                    break;
            }
            System.out.println("Sending the result...");

            // send the result back to the client.
            dos.writeUTF(String.valueOf(result));
        }
    }
}

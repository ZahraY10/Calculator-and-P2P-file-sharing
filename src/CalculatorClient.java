import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        int port = 4444;
        Scanner sc = new Scanner(System.in);

        Socket s = new Socket(ip, port);

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true) {
            System.out.print("Enter the equation in the following form: ");
            System.out.println("'operator operand operand'");
            System.out.println("valid operators: ADD, SUBTRACT, DIVIDE, MULTIPLY, SIN, COS, TAN and COT!");
            System.out.println("Please notice the spaces between operator and operands");

            String inp = sc.nextLine();

            if (inp.equals("END"))
                break;

            // send the equation to server
            dos.writeUTF(inp);

            // wait till request is processed and sent back to client
            String ans = dis.readUTF();
            System.out.println("Answer = " + ans);
        }
    }
}

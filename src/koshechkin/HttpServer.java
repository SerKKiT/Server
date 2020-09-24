package koshechkin;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

	/** ������� ����� �������. 
	 *  ������� ����� � ������ ����� RequestThread ��� ��������� HTTP-�������.
	 *  @see {@link koshechkin.RequestThread}
	 *  @author Vadim Koshechkin
	 */
public class HttpServer {
	/**
	 * ���������� ������� ������ �������.
	 */
	final static Path resourcePath = Paths.get("C:\\workspase\\Server\\src\\recurce");
	/**
	 *  ����� �� ������� ����������� ������.
	 */
	final static int Serversocket = 8081;
	
    public static void main(String[] args){
    	FileHandler handler = new FileHandler(resourcePath); 
    	handler.start();
    	
        try (ServerSocket serverSocket = new ServerSocket(Serversocket)) {
            System.out.println("Server started!");
            
            while (true) {
                // ������� �����������
                Socket socket = serverSocket.accept();
            	RequestThread request = new RequestThread(socket, handler);
            	request.start();
           }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
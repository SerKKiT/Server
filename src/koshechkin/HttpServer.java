package koshechkin;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

	/** Главный класс сервера. 
	 *  Слушает сокет и создаёт поток RequestThread при появлении HTTP-запроса.
	 *  @see {@link koshechkin.RequestThread}
	 *  @author Vadim Koshechkin
	 */
public class HttpServer {
	/**
	 * Директория системы файлов сервера.
	 */
	final static Path resourcePath = Paths.get("C:\\workspase\\Server\\src\\recurce");
	/**
	 *  Сокет на котором запускается сервер.
	 */
	final static int Serversocket = 8081;
	
    public static void main(String[] args){
    	FileHandler handler = new FileHandler(resourcePath); 
    	handler.start();
    	
        try (ServerSocket serverSocket = new ServerSocket(Serversocket)) {
            System.out.println("Server started!");
            
            while (true) {
                // ожидаем подключения
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
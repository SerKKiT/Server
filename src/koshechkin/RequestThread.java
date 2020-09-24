package koshechkin;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
/** 
 * ����� ��� ������ � HTTP-��������. ��� �������� ������ ���������� �������� Socket �� ������� ������ HTTP-������.
 * @see {@link java.net.Socket}
 * @author Vadim Koshechkin
 *
 */
 	class RequestThread extends Thread {
	/**
	 * socket �� ������� ������ HTTP-������.
	 * @see {@link java.net.Socket}
	 */
	private Socket socket;
	private FileHandler handler;
	
	RequestThread(Socket socket,FileHandler handler){
		this.socket = socket;
		this.handler = handler;
	}
	
	public void run() {

        // ��� ��������������� ������� ��������� ������
        // ������ � ������
        try		(
        		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        		OutputStream output = socket.getOutputStream();
        		) 
        {
        	QueryProcessor request = new QueryProcessor(input);
        	
        	switch (request.getMethod()) {
        		case GET:
        			AnswerGet(request, output);
        			break;
        		default:
        		output.write("HTTP/1.1 404 ERROR\n\r".getBytes());
        			break;
        		
        	}
        	
            System.out.println("OK: " + request.getMethod() + " " + request.getPath().toString());
            
        } catch (IOException e) {
        	System.out.println("ERROR: " + e.toString());
		}
	}
	
	private void AnswerGet(QueryProcessor request, OutputStream  output) throws IOException {
		// ���������� �����
		output.write("HTTP/1.1 200 OK\n\r".getBytes());
        output.write(handler.getHeader(request.relativeAddress()).getBytes());
        Files.copy(request.getPath(), output);
            output.flush();
        } 
}

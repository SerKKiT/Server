package koshechkin;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
  /**
   *  ласс, читающий и обрабатывающий HTTP-запрос. 
   * »спользуетс€ в {@link koshechkin.RequestThread}
   * @author Vadim Koshechkin
   *
   */
class QueryProcessor {
	
	private List<String> process = new ArrayList<String>();
	private final String[] requestLine;
	
	MethodsHTTP getMethod() {
		return  MethodsHTTP.valueOf(requestLine[0]);
	}
	Path getPath() {
		return Paths.get(HttpServer.resourcePath.toString() + (requestLine[1].equals("/") ? "/index.html" : requestLine[1]).replace("/", "\\"));
	}
	String getProtocol() {
		return requestLine[2];
	}
	String relativeAddress() {
		return requestLine[1].equals("/") ? "/index.html" : requestLine[1];
	}
	
	QueryProcessor(BufferedReader input) throws IOException{
		// ждем первой строки запроса
        while (!input.ready());
        
        // считываем и печатаем все что было отправлено клиентом
        while (input.ready()) {
        	String a = input.readLine();
        	System.out.println(a);
        	process.add(a);
        }
        
        this.requestLine = process.get(0).split(" ");
	}
}

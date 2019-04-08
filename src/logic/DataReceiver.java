package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DataReceiver {
    private URL dataURL;
    private URLConnection connection;
    private InputStreamReader inStream;
    private BufferedReader reader;

    public DataReceiver() throws IOException {
        dataURL = new URL("https://www.nasdaq.com/symbol/tsla/real-time");

    }

    public DataReceiver(String URL) throws IOException {
        dataURL = new URL(URL);
        connection = dataURL.openConnection();
        inStream = new InputStreamReader(connection.getInputStream());
        reader = new BufferedReader(inStream);
    }

    public String getLine() throws IOException {
        String line = null;
        connection = dataURL.openConnection();
        inStream = new InputStreamReader(connection.getInputStream());
        reader = new BufferedReader(inStream);

        reader.mark(60000);

        line = reader.readLine();
        while(line != null){
            if(line.contains("class=\"qwidget-dollar\">")){
                int start = line.indexOf(">$");
                if(start > 0 ){
                    line = line.substring(start+1, start + 8);
                    reader.reset();
                    return line;
                }
            }
            line = reader.readLine();
        }
        return line;
    }

}

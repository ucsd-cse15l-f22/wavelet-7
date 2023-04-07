import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler1 implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    List<String> stringList = new ArrayList<String>();
    List<String> foundList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Genevieve's List: %s \n", stringList);

        } else if (url.getPath().equals("/search")) {
            foundList.clear();
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for (int i = 0; i < stringList.size(); i++) {
                    if (stringList.get(i).contains(parameters[1])) {
                        foundList.add(stringList.get(i));
                    }
                }
                return String.format("The strings in this list: %s contain %s \n", foundList, parameters[1]);
            }
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    stringList.add(parameters[1]);
                    return String.format("Added %s! It's Genevieve's list is now %s \n", parameters[1], stringList);
                }
            }
            return "404 Not Found!";
        }
        return null;
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler1());
    }
}

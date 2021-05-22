import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class Utilities {

    public static String promptUser(String regExToMatch, String errorMessage) {
        Scanner in = new Scanner(System.in);
        String input;
        while (true) {
            input = (in.nextLine()).strip().toLowerCase();
            if (input.matches(regExToMatch)) {
                System.out.println();
                return input;
            }
            System.out.print(errorMessage);
        }
    }

    public static String promptForId() {
        System.out.print("Enter ID: ");
        String regEx = "[bB]|[\\d]+";
        String error = "Please enter a valid id format (1 or more numbers) or \n" +
                "[B] to go back: ";
        return promptUser(regEx, error);
    }

    public static String promptForName() {
        System.out.print("Enter name: ");
        String regEx = "[bB]|[a-zA-Z]{2,20}";
        String error = "Please enter a valid name format or [B] to go back. \n" +
                "(Between 2 and 20 chars long): ";
        return promptUser(regEx, error);
    }

    public static String promptForEmail(){
        System.out.print("Enter email: ");
        String regEx = "^[bB]|[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String error = "Please enter a valid email: ";
        return promptUser(regEx, error);
    }

    public static String promptForNumber(){
        System.out.print("Enter phone number: ");
        String regEx = "\\+994(50|55|51|70|77)[0-9]{7}";
        String error = "Please enter a valid phone number: ";
        return promptUser(regEx, error);
    }

    public static ConcurrentNavigableMap<Integer, Student> jsonToMap(String pathName) throws IOException {
        File f = new File(pathName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(f, new TypeReference<>() {});
        }
        catch (IOException e){
            System.out.printf("Source JSON file is empty");;
        }
        return null;
    }

    public static void mapToJson(String pathName, Map map) throws IOException {
        File f = new File(pathName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(f, map);
    }

    public static String capitalizeString(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

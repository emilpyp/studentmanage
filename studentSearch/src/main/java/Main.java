import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*Map<Integer, Student> map = new HashMap<>();
        {
            Student s1 = new Student("Emil", "Agsaqqal1", "code1", "aaa@aa.com", "00001");
            Student s2 = new Student("Emil1", "Agsaqqal2", "code2", "bbb@aa.com", "00002");
            Student s3 = new Student("Emil2", "Agsaqqal3", "code3", "ccc@aa.com", "00003");
            Student s4 = new Student("Emil3", "Agsaqqal4", "code4", "ddd@aa.com", "00004");
            Student s5 = new Student("Emil4", "Agsaqqal5", "code5", "eee@aa.com", "00005");
            Student s6 = new Student("Emil5", "Agsaqqal6", "code6", "rrr@aa.com", "00006");
            Student s7 = new Student("Emil6", "Agsaqqal7", "code7", "xxx@aa.com", "00007");

            map.put(s1.getId(), s1);
            map.put(s2.getId(), s2);
            map.put(s3.getId(), s3);
            map.put(s4.getId(), s4);
            map.put(s5.getId(), s5);
            map.put(s6.getId(), s6);
            map.put(s7.getId(), s7);
        }*/


        //String str ="src/main/resources/students.json";
        //Utilities.mapToJson(str, map);

        App app = App.app();
        app.start();
    }
}

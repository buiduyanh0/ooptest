package unit_test.get_saved_search;

import com.google.gson.Gson;
import unit_test.Contanst;
import unit_test.log_in.LoginMain;
import unit_test.log_in.ResponseLogin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetSavedSearch {


    public static void main(String[] args) throws IOException {
        testCase1();
    }

    private static void testCase1() throws IOException {
        System.out.println("_ Test 1: Search thành công");
        String phonenumber = "0846602399";
        String password ="12345678";
        String uuid ="27fccd94-4f6a-11eb-ae93-0242ac130001";

        try{
            ResponseLogin login = LoginMain.login(phonenumber,password,uuid);
            String token = login.data.token;
            int count = 10;
            int index = 1;
            ResponseGetSavedSearch search = search(token, count, index);
            assert "1000".equals(search.code) : "_Test fail:  code = " + search.code;
            assert "OK".equals(search.message) : "_ Test fail: message = " + search.message;
            System.out.println("_ Thàn công với res search code = : " + search.code + "& message = " + search.message);
        }catch (Exception e){
            System.out.println(e);
        }catch (AssertionError e){
            System.out.println(e);
        }

    }


    public static ResponseGetSavedSearch search(String token, int count, int index) throws IOException {
        URL url = new URL(Contanst.getsavedsearchsearchAPI +
                "?token="+ token
                + "&index=" + index
                + "&count=" + count
        );
        System.out.println("- Gọi API: " + url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            Gson g = new Gson();
            System.out.println(content.toString());
            return g.fromJson(content.toString(), ResponseGetSavedSearch.class);
        } finally {
//            System.out.println("- Server trả status code = "+connection.getResponseCode());
//            System.out.println("             message = "+ connection.getResponseMessage());
            connection.disconnect();
        }
    }
}
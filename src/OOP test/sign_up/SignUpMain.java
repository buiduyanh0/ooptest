package unit_test.sign_up;

import com.google.gson.Gson;
import unit_test.Contanst;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpMain {
    public static void main(String[] args) throws IOException {
        testCase1();
        testCase2();
        testCase3();
        testCase4();
        testCase5();

    }

    private static void testCase1() throws IOException {
        System.out.println("Test case 1: Đăng kí thành công");
        String phoneNumber = "0855122201";
        String password = "1234567890";
        String uuid = "f3498r";
        System.out.println("- SDT: " + phoneNumber);
        System.out.println("- Password: " + password);
        try{
            assert "0".equals(Character.toString(phoneNumber.charAt(0))):"- Test fail: SDT không hợp lệ: "+ phoneNumber;
            assert (6<=password.length() && password.length()<=10):"- Test fail: Password không hợp lệ : "+ password;
            ResponseSignUp rp = signUp(phoneNumber,password,uuid);
            assert "1000".equals(rp.code):"- Test fail: Code = "+ rp.code + " và message = "+ rp.message;
            System.out.println("- Test thành công code = "+ rp.code + " và message = "+ rp.message);
            System.out.println();
        }///bkhnk48, nguyenthanh@soict.hust.edu.vn
        catch (IOException e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }

    }

    private static void testCase2() throws IOException {
        System.out.println("Test case 2: Số điện thoại tồn tại");
        String phoneNumber = "0855122278";
        String password = "1234567890";
        String uuid = "f3498r";
        System.out.println("- SDT: " + phoneNumber);
        System.out.println("- Password: " + password);
        try{
            assert "0".equals(Character.toString(phoneNumber.charAt(0))):"- Test fail: SDT không hợp lệ: "+ phoneNumber;
            assert (6<=password.length() && password.length()<=10):"- Test fail: Password không hợp lệ : "+ password;
            ResponseSignUp rp = signUp(phoneNumber,password,uuid);
            assert "9996".equals(rp.code):"- Test fail: Code = "+ rp.code + " và message = "+ rp.message;
            System.out.println("- Test thành công code = "+ rp.code + " và message = "+ rp.message);
            System.out.println();
        }
        catch (IOException e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }

    }

    private static void testCase3() throws IOException {
        System.out.println("Test case 3: Số điện thoại không đúng định dạng");
        String phoneNumber = "18551222780";
        String password = "1234567890";
        String uuid = "f3498r";
        System.out.println("- SDT: " + phoneNumber);
        System.out.println("- Password: " + password);
        try{
            assert (!"0".equals(Character.toString(phoneNumber.charAt(0)))
            && phoneNumber.length() != 10) :"- Test fail: SDT hợp lệ: "+ phoneNumber;
            assert (6<=password.length() && password.length()<=10):"- Test fail: Password không hợp lệ : "+ password;
            ResponseSignUp rp = signUp(phoneNumber,password,uuid);
            assert !"1000".equals(rp.code):"- Test fail: Code = "+ rp.code + " và message = "+ rp.message;
            System.out.println("- Test thành công code = "+ rp.code + " và message = "+ rp.message);
            System.out.println();
        }
        catch (IOException e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }

    }

    private static void testCase4() throws IOException {
        boolean flag;
        String passwordPattern = "\\w+";
        String phoneNumber = "1789%";
        String password = "1789%";
        String uuid = "f3498r";

        System.out.println("Test case 4: Mật khẩu không đúng định dạng");
        System.out.println("- SDT: " + phoneNumber);
        System.out.println("- Password: " + password);
        try{
            System.out.println("- Check định dạng mật khẩu");
            assert  (password.length()>10 || password.length()<6): "- Test fail client: Mật khẩu đúng định dạng về độ dài: "+ password.length();
            assert  password.equals(phoneNumber):"- Test fail client: Mật khẩu không trùng với số điện thoại";
            flag = password.matches(passwordPattern);
            assert !flag:"- Test fail client: Mật khẩu không có kí tự đặc biệt";
            ResponseSignUp rp = signUp(phoneNumber,password,uuid);
            assert !"1000".equals(rp.code):"- Test fail: Code = "+ rp.code + " và message = "+ rp.message;
            System.out.println("- Test thành công code = "+ rp.code + " và message = "+ rp.message);
            System.out.println();
        }
        catch (IOException e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }

    }

    private static void testCase5() throws IOException {
        System.out.println("Test case 5: Bỏ qua input ấn nút đăng nhập");
        String phoneNumber = null;
        String password = null;
        String uuid = "f3498r";
        System.out.println("- SDT: " + phoneNumber);
        System.out.println("- Password: " + password);
        try{
            assert phoneNumber == null:"-Test fail SDT: "+ phoneNumber;
            assert password == null:"- Test fail Password : "+ password;
            ResponseSignUp rp = signUp(phoneNumber,password,uuid);
            assert !"1000".equals(rp.code):"- Test fail: Code = "+ rp.code + " và message = "+ rp.message;
            System.out.println("- Test thành công code = "+ rp.code + " và message = "+ rp.message);
            System.out.println();
        }
        catch (IOException e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }

    }




    public static ResponseSignUp signUp(String phonenumber, String password, String uuid) throws IOException {
        URL url = new URL(Contanst.signUpAPI +
                "?phonenumber="+ phonenumber
                + "&password="+password
                + "&uuid="+ uuid);
        System.out.println("CALL API: " + url);
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
            System.out.println(content.toString());
            Gson g = new Gson();
            return g.fromJson(content.toString(), ResponseSignUp.class);
        } finally {
            connection.disconnect();
        }
    }
}

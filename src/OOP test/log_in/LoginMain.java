package unit_test.log_in;

import com.google.gson.Gson;
import unit_test.Contanst;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginMain {
    public static void main(String[] args) {
        testCase1();
        testCase2();
        testCase3();
        testCase4();
        testCase5();
        testCase6();
        testCase7();
        testCase8();
        testCase9();

    }



    private static void testCase1() {
        System.out.println("Test case 1: Đăng nhập thành công");
        System.out.println("- Mong đợi: Mã 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "0855122278";
        String password = "123456";
        String uuid = "1";
        try{
            ResponseLogin res= login(phonenumber,password,uuid);
            String token = res.data.token;
            assert "1000".equals(res.code):"-Test fail: Mã trả về là "+ res.code;
            System.out.println("- Test thành công với mã "+ res.code);
            System.out.println("- Token lấy được là: "+ res.data.token + "\n");
        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }

    }
    private static void testCase2() {
        System.out.println("Test case 2:số CHƯA được đăng ký trên hệ thống");
        System.out.println("- Mong đợi: code khác 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "0855122271";
        String password = "123456";
        String uuid = "1";
        try{
            ResponseLogin res= login(phonenumber,password,uuid);
            assert !"1000".equals(res.code):"- Test fail: Mã trả về là "+ res.code;
            System.out.println("- Test thành công với mã "+ res.code +" và message = "+ res.message +"\n");
        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }


    private static void testCase3() {
        System.out.println("Test case 3: nhập số điện thoại không đúng định dạng (không đủ số hoặc thừa số hoặc không có số 0 ở đầu tiên) \nvà nhập mật khẩu đúng quy định, devtoken hợp lệ.");
        System.out.println("- Mong đợi: code khác 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "855122";
        String password = "12345643546";
        String uuid = "1";
        try{
            System.out.println("- Client kiểm tra sdt...");
            assert (!"0".equals(Character.toString(phonenumber.charAt(0))) && phonenumber.length() != 10)
                    :"Số điện thoại hợp lệ: "+ phonenumber;
            ResponseLogin res= login(phonenumber,password,uuid);
            assert !"1000".equals(res.code):"- Test fail: Mã trả về là "+ res.code;
            System.out.println("- Test thành công với mã "+ res.code +" và message = "+ res.message +"\n");
        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }

    private static void testCase4() {
        System.out.println("Test case 4: nhập đúng định dạng của số điện thoại nhưng mật khẩu không đúng định dạng (quá ngắn hoặc quá dài \nhoặc chứa ký tự đặc biệt hoặc trùng với số điện thoại), devtoken hợp lệ");
        System.out.println("- Mong đợi: code khác 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "0855122278";
        String password = "12345643546";
        String uuid = "1";
        try{
            System.out.println("- Client kiểm tra password...");
            assert  (password.length()>10 || password.length()<6): "- Password hợp lệ: "+ password.length();
            ResponseLogin res= login(phonenumber,password,uuid);
            assert !"1000".equals(res.code):"- Test fail: Mã trả về là "+ res.code;
            System.out.println("- Test thành công với mã "+ res.code +" và message = "+ res.message +"\n");
        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }


    private static void testCase5() {
        System.out.println("Test case 5: bỏ qua không nhập cả số điện thoại và mật khẩu nhưng nhấn vào nút “Đăng nhập” ");
        System.out.println("- Mong đợi: code khác 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = null;
        String password = null;
        String uuid = "1";
        try{
            System.out.println("- Client kiểm tra password && sdt...");
            assert phonenumber == null:"Số điện thoại hợp lệ: "+ phonenumber;
            assert password ==null: "- Password hợp lệ: "+ password.length();
            ResponseLogin res= login(phonenumber,password,uuid);
            assert !"1000".equals(res.code):"- Test fail: Mã trả về là "+ res.code;
            System.out.println("- Test thành công với mã "+ res.code +" và message = "+ res.message +"\n");
        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }

    private static void testCase6() {
        System.out.println("Test case 6:  Người dùng nhập đúng định dạng của số điện thoại và mật khẩu, devtoken hợp lệ nhưng không có kết nối mạng. ");
        System.out.println("- Mong đợi: code khác 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "0855122278";
        String password = "1223456";
        String uuid = "1";
        try{
            System.out.println("- Ngắt kết nối mạng...");
            System.out.println("- Không thể kết nối Internet");
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }


    private static void testCase7() {
        System.out.println("Test case 7:  nhập số điện thoại và mật khẩu có nội dung giống số điện thoại rồi nhấn vào nút “Đăng nhập”, devtoken hợp lệ ");
        System.out.println("- Mong đợi: code khác 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "0855122278";
        String password = "0855122278";
        String uuid = "1";
        try{
            System.out.println("- Client kiểm tra trùng sdt ,pass");
            assert phonenumber.equals(password):"Không trùng nhau để test\n" +
                    "SDT: " + phonenumber +
                    "Password" + password;
            ResponseLogin res = login(phonenumber,password,uuid);
            assert !"1000".equals(res.code):"-Test fail: Mã trả về là "+ res.code;
            System.out.println("- Test thành công với mã "+ res.code +" và message = "+ res.message +"\n");
        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }


    private static void testCase8() {
        System.out.println("Test case 8:Người dùng đăng nhập thành công trên máy A. Rồi đăng nhập thành công trên máy B.");
        System.out.println("- Mong đợi: Code = 1000");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "0855122278";
        String password = "123456";
        String uuid = "1";
        try{
            System.out.println("- Máy A đăng nhập:");
            ResponseLogin res= login(phonenumber,password,uuid);
            assert "1000".equals(res.code):"- Test fail: Mã trả về là "+ res.code;
            System.out.println(" + Token: "+ res.data.token);
            System.out.println(" - Máy B đăng nhập:");
            ResponseLogin res1= login(phonenumber,password,uuid);
            assert "1000".equals(res1.code):"- Test fail: Mã trả về là "+ res1.code;
            System.out.println(" + Token: "+ res1.data.token);
            System.out.println("- Test thành công với mã "+ res1.code +" và message = "+ res1.message +"\n");
            System.out.println();

        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }


    private static void testCase9() {
        System.out.println("Test case 9: gửi thông tin đăng nhập nhưng không gửi devtoken.");
        System.out.println("- Mong đợi: Lỗi thiếu tham số");
        System.out.println("- Khởi tạo thông tin cần thiết và test...");
        String phonenumber = "0855122278";
        String password = "123456";
        String uuid = null;
        try{
            assert uuid == null:"- Test fail Client: Vẫn có uuid";
            ResponseLogin res= login(phonenumber,password,uuid);
            assert !"1000".equals(res.code):"- Test fail: Mã trả về là "+ res.code;
            System.out.println("- Test thành công với mã "+ res.code +" và message = "+ res.message +"\n");
            System.out.println();

        }catch (Exception e){
            e.printStackTrace();
        }catch (AssertionError e){
            e.printStackTrace();
        }
    }



    public static ResponseLogin login(String phonenumber, String password, String uuid) throws IOException {
        URL url = new URL(Contanst.loginAPI +
                "?phonenumber="+ phonenumber
                + "&password="+password
                + "&uuid="+ uuid);
//        System.out.println("- Gọi API: " + url);
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
//            System.out.println(content.toString());
            return g.fromJson(content.toString(), ResponseLogin.class);
        } finally {
//            System.out.println("- Server trả status code = "+connection.getResponseCode());
//            System.out.println("             message = "+ connection.getResponseMessage());
            connection.disconnect();
        }
}
}


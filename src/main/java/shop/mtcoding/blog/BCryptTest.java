package shop.mtcoding.blog;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
    public static void main(String[] args) {

        String encPassword = BCrypt.hashpw("1111", BCrypt.gensalt());
        // System.out.println("encPassword : " + encPassword);
        // "1234"를 BCrypt로 해쉬

        boolean isValid = BCrypt.checkpw("1234", encPassword);
        // System.out.println(isValid);
        // 암호화한 "1234"와 비교
    }

}

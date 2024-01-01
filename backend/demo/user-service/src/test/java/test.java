import com.example.user.utils.CaptchaPool;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

/**
 * @author ToTryEveryThing
 * @date 2024/1/1 10:34
 * @Description
 */

@SpringBootTest
public class test {
    @Test
    void name() {


        CaptchaPool captchaPool = new CaptchaPool();

        // 获取验证码
        Map<String, String> availableCaptcha = captchaPool.getAvailableCaptcha();
        System.out.println("Captcha 1: " + availableCaptcha);

        // 获取另一个验证码
        Map<String, String> availableCaptcha1 = captchaPool.getAvailableCaptcha();
        System.out.println("Captcha 2: " + availableCaptcha1);

        // 释放第一个验证码
        captchaPool.releaseCaptcha(availableCaptcha1);

        // 再次获取验证码，此时应该是第一个验证码
        Map<String, String> availableCaptcha2 = captchaPool.getAvailableCaptcha();
        System.out.println("Captcha 3: " + availableCaptcha2);

    }
}

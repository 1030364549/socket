package com.umf.utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 生成验证码图片配置
 *
 * @date 2020/11/17 17:19
 * @return
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        // 图片边框，合法值：yes , no
        properties.put("kaptcha.border", "no");
        // 字体颜色，合法值： r,g,b  或者 white,black,blue.
        properties.put("kaptcha.textproducer.font.color", "black");
        // 文字间隔
        properties.put("kaptcha.textproducer.char.space", "5");

        // 图片宽
        properties.put("kaptcha.image.width", "200");
        // 图片高
        properties.put("kaptcha.image.height", "50");
        // 验证码长度
//        properties.put("kaptcha.textproducer.char.length", "4");
        // 字体大小
        properties.put("kaptcha.textproducer.font.size", "25");
        // 字体
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 添加阴影
        properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");

        //如果需要去掉干扰线
//        properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");

        //文本集合，验证码值从此集合中获取，如果需要生成算法验证码加上一下配置
//        properties.put("kaptcha.textproducer.char.string", "1234567890");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

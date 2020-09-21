package com.umf;

import com.umf.utils.LogUtil;
import com.umf.utils.MapperInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.umf.dao") // MapperScannerConfigure，生成动态代理
public class SocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocketApplication.class, args);
	}

//	@Bean
//	public LogUtil logUtil(){
//		return new LogUtil();
//	}

}

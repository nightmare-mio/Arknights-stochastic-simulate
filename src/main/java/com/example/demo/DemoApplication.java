/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 20:36:07
 * @LastEditTime: 2023-09-01 17:55:02
 * @Description: 
 */
/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 20:36:07
 * @LastEditTime: 2023-08-27 02:30:53
 * @Description: 
 */
package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.entity.Capable;
import com.example.demo.service.impl.CapableServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
@EnableWebMvc
public class DemoApplication {

	@Autowired
	private CapableServiceImpl cap;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	// @Bean
	// public ApplicationRunner executeOnStartup() {
	// 	return args -> {

	// 		List<Capable> list = cap.list();

	// 		ObjectMapper objectMapper = new ObjectMapper();

	// 		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

	// 		try {
	// 			objectMapper.writeValue(new File("src/main/resources/capables.json"), list);
	// 			System.out.println("List<Capable> 已写入 JSON 文件");
	// 		} catch (IOException e) {
	// 			e.printStackTrace();
	// 		}

	// 	};
	// }

	// @Bean
	// public ApplicationRunner executeOnStartup() {
	// return args -> {

	// try {
	// FileInputStream fileInputStream = new
	// FileInputStream("src/main/resources/latlon.txt");
	// InputStreamReader inputStreamReader = new
	// InputStreamReader(fileInputStream,StandardCharsets.UTF_8);
	// BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

	// String line;
	// while ((line = bufferedReader.readLine()) != null) {
	// String[] temps = line.split("\\|");
	// cap.save(new
	// Capable().setName(temps[0]).setProfession(temps[1]).setRarity(temps[2]).setId(temps[5]));
	// }

	// bufferedReader.close();
	// inputStreamReader.close();
	// fileInputStream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// };
	// }

	// @Bean
	// public ApplicationRunner executeOnStartup() {
	// return args -> {

	// try {
	// FileInputStream fileInputStream = new
	// FileInputStream("src/main/resources/latlon.txt");
	// InputStreamReader inputStreamReader = new
	// InputStreamReader(fileInputStream,StandardCharsets.UTF_8);
	// BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

	// String line;
	// while ((line = bufferedReader.readLine()) != null) {
	// String[] temps = line.split("\\|");
	// cap.save(new Capable().setId(temps[0]).setName(temps[1]));
	// }

	// bufferedReader.close();
	// inputStreamReader.close();
	// fileInputStream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// };
	// }

}

/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 20:36:07
 * @LastEditTime: 2023-09-03 18:29:06
 * @Description: 
 */
/*
 * @Author: nightmare-mio wanglongwei2009@qq.com
 * @Date: 2023-08-26 20:36:07
 * @LastEditTime: 2023-08-27 02:30:53
 * @Description: 
 */
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.service.impl.CapableServiceImpl;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableWebMvc
public class DemoApplication {

	@Autowired
	private CapableServiceImpl cap;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	// @Bean
	// public ApplicationRunner executeOnStartup() {
	// return args -> {

	// List<Capable> list = cap.list();

	// ObjectMapper objectMapper = new ObjectMapper();

	// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

	// try {
	// objectMapper.writeValue(new File("src/main/resources/capables.json"), list);
	// System.out.println("List<Capable> 已写入 JSON 文件");
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

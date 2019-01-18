package tk.mybatis.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.springboot.mapper.CountryMapper;
import tk.mybatis.springboot.model.Country;

import java.util.List;

@SpringBootApplication
@MapperScan({"tk.mybatis.springboot.mapper", "tk.mybatis.simple.mapper"})
public class Application implements CommandLineRunner {

    @Autowired
    private CountryMapper countryMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Country> countryList = countryMapper.selectAll();
        for (Country country : countryList) {
            System.out.println(country.getCountryname());
        }
    }
}

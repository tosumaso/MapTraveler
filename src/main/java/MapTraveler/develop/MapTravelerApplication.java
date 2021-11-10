package MapTraveler.develop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //レコードの登録、更新時に時刻を自動で登録するための設定を有効化
@SpringBootApplication
public class MapTravelerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapTravelerApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
	    var messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    return messageSource;
	}
}

package MapTraveler.develop.Config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //レコードの登録、更新時に時刻を自動で登録するための設定を有効化
public class EnableAuditing {

	@Bean
	public MessageSource messageSource() {
	    var messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    return messageSource;
	}
}

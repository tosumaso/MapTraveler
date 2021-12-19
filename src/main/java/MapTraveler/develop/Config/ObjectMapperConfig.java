package MapTraveler.develop.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

//entityを@ResponseBodyでJsonにして返すとき、lazy-fetchの子entityをSerializeしようとしてエラーが発生する,ObjectMapperの設定を変更する
@Configuration
public class ObjectMapperConfig {

	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {

		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

		// Hibernate 5 使用時のサポート
		builder.modulesToInstall(new Hibernate5Module());

		// Spring Boot のデフォルト設定に合わせる
		builder.featuresToDisable(MapperFeature.DEFAULT_VIEW_INCLUSION);
		builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		return builder;
	}
}

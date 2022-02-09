package MapTraveler.develop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplicationのクラスに他のアノテーションを追加するとTestにも反映されるため注意が必要
//例: @EnableJpaAuditing(レコードの登録時間を自動更新する)をつけるとJpaを使わないテストで読み込もうとしてエラーが発生する

@SpringBootApplication
public class MapTravelerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapTravelerApplication.class, args);
	}
}

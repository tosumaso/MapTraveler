package MapTraveler.develop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import MapTraveler.develop.Entity.Map;

public interface MapRepository extends JpaRepository<Map,Integer>{
	/*通常のfindAllだとn+1問題が発生してしまう(最初にselect m from Map mを実行、その後親entityに紐づく子entityをn回select ~ from Post where map_id=?で検索する)
	 left join fetchを使い一度のselectで参照先のレコードを検索する*/
	@Query("select m from Map m left join fetch m.post p left join fetch p.user")
	List <Map> findAll();

}

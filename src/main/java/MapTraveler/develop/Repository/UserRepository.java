package MapTraveler.develop.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import MapTraveler.develop.Entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

//	@Query("SELECT u FROM User u LEFT JOIN FETCH u.images WHERE p.id = :id")
//	Optional<User> findByFetchId(@Param("id") Integer id);
	
	Optional<User> findByUsername(String username);
}

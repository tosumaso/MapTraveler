package MapTraveler.develop.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import MapTraveler.develop.Entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	Optional<User> findByUsername(String username);
}

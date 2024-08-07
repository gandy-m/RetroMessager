package messager.repository;


import messager.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

   @Transactional
   Optional<User> findUserByUsername(String username);

}

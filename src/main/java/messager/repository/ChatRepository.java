package messager.repository;


import messager.model.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {


   @Query(value = "select * from chats where id=:id", nativeQuery = true)
   Chat getChatById(long id);


   @Query(value = "select * from chats where username1=:username or username2=:username", nativeQuery = true)
   List<Chat> getChatsByUsername(String username);


   @Query(value = "select * from chats where username1=:username1 and username2=:username2 or username2=:username1 and username1=:username2", nativeQuery = true)
   Chat getChatByMembersNames(String username1, String username2);


}

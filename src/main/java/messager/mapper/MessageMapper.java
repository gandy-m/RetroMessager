package messager.mapper;


import messager.DTO.*;
import messager.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface MessageMapper {


   MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);


   UserDTO userToUserDTO(User user);


   @Mapping(source = "sender", target = "sender")
   @Mapping(source = "recipient", target = "recipient")
   MessageDTO messageToMessageDTO(Message message);


}

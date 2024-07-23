package messager.service;


import messager.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface UserService {


   User add(String username, String password);


   User find(String username);


   User registration(String username, String password) throws Exception;


   void setImage(MultipartFile file, String username) throws IOException;
}

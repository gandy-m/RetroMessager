package messager.service;


import messager.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {


   User find(String username);


   User registration(String username, String password) throws Exception;


   void setImage(MultipartFile file, String username) throws IOException;


   void setPassword(String password, String username) throws Exception;
}

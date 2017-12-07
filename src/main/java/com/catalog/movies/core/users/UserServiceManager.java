package com.catalog.movies.core.users;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class UserServiceManager implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String getUserNameById(final Integer id) {
        return userRepository.getUserNameById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return userRepository.exists(id);
    }

    @Override
    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String validateUserRegisterDTO(final UserRegisterDTO userRegisterDTO) throws IOException {
        String error = "";


        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            error = "passwords";
        }
//        if (!(userRegisterDTO.getPhoto().isEmpty())) {
//            File file = new File("E:\\" + userRegisterDTO.getPhoto().getOriginalFilename());
//            userRegisterDTO.getPhoto().transferTo(file);
//            userRegisterDTO.setPhoto(encodeFileToBase64Binary(file));
//        } else {
//
//        }

        return error;
    }

    private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }
}

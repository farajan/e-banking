package com.example.ebanking.user.service.mapper;

import com.example.ebanking.user.db.entity.User;
import com.example.ebanking.user.dto.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper implements Mapper<User, UserRequest> {

    @Override
    public User mapToEntity(UserRequest userRequest) {
        User user = new User();
        user.setUserId(userRequest.getUserId());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setBirthday(userRequest.getBirthday());
        return user;
    }

    @Override
    public UserRequest mapFromEntity(User entity) {
        return new UserRequest(
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getBirthday()
        );
    }

}

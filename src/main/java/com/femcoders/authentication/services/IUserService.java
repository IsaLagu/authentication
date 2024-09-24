package com.femcoders.authentication.services;

import com.femcoders.authentication.dto.UserDTO;
import com.femcoders.authentication.entities.User;
import com.femcoders.authentication.exceptions.EmailExistsException;

public interface IUserService {
    User registerNewUserAccount(UserDTO accountDto) throws EmailExistsException;
}

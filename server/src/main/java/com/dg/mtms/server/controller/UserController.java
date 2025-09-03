package com.dg.mtms.server.controller;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.annotation.Controller;
import com.dg.mtms.server.annotation.Request;
import com.dg.mtms.server.annotation.RequestBody;
import com.dg.mtms.server.exception.UsernameAlreadyExistsException;
import com.dg.mtms.server.mapper.UserMapper;
import com.dg.mtms.server.model.User;
import com.dg.mtms.server.model.request.dto.UserCreateRequest;
import com.dg.mtms.server.model.response.HttpResponse;
import com.dg.mtms.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(basePath = "/v1/user")
public class UserController extends Singleton<UserController> {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public static void createInstance(UserService userService) {
        addInstance(new UserController(userService));
    }
    public static UserController getInstance() {
        return Singleton.getInstance(UserController.class);
    }

    @Request(method = "POST")
    public HttpResponse saveUser(@RequestBody UserCreateRequest request) {
        logger.info("saving user: {}", request);
        try {
            User savedUser = userService.save(UserMapper.INSTANCE.toUser(request));
            logger.info("saved user with id: {}", savedUser.getId());
            return HttpResponse.ok(UserMapper.INSTANCE.toUserCreateResponse(savedUser));
        } catch (UsernameAlreadyExistsException e) {
            logger.error("Error while saving user!", e);
            return HttpResponse.internalServerError();
        }
    }
}

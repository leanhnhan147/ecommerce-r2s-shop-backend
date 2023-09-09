package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.UserDTORequest;
import com.r2s.mockproject.dto.UserDTOResponse;
import com.r2s.mockproject.entity.User;
import com.r2s.mockproject.repository.RoleRepository;
import com.r2s.mockproject.repository.UserRepository;
import com.r2s.mockproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "All User API")
public class UserController extends BaseRestController{
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Return message and data User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTOResponse.class)
                    )
            )
    })
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = this.userService.getAllUser();
            List<UserDTOResponse> responses = users.stream()
                    .map(user -> new UserDTOResponse(user))
                    .collect(Collectors.toList());
            return super.success(responses);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody(required = true) Map<String, Object> newUser) {
        try {
            if (ObjectUtils.isEmpty(newUser)) {
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            UserDTORequest userDTORequest = new UserDTORequest(newUser);
            if (ObjectUtils.isEmpty(userDTORequest.getUsername())
                    || ObjectUtils.isEmpty(userDTORequest.getPassword())
                    || ObjectUtils.isEmpty(userDTORequest.getFullName())
                    || ObjectUtils.isEmpty(userDTORequest.getEmail())
                    || ObjectUtils.isEmpty(userDTORequest.getPhone())) {
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

//            if (ObjectUtils.isEmpty(newUser.get("username"))
//                    || ObjectUtils.isEmpty(newUser.get("password"))
//                    || ObjectUtils.isEmpty(newUser.get("fullName"))
//                    || ObjectUtils.isEmpty(newUser.get("email"))
//                    || ObjectUtils.isEmpty(newUser.get("phone"))) {
//                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
//            }

            User foundUser = this.userService.findByUsername(newUser.get("username").toString()).orElse(null);
            if (!ObjectUtils.isEmpty(foundUser)) {
                return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
                        ResponseCode.DATA_ALREADY_EXISTS.getMessage());
            }

            User insertedUser = userService.addUser(newUser);
            if (!ObjectUtils.isEmpty(insertedUser)) {
                return super.success(new UserDTOResponse(insertedUser));
//                return super.success(insertedUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id,
                                        @RequestBody(required = false) Map<String, Object> newUser) {
        try {
            if (ObjectUtils.isEmpty(newUser)) {
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            UserDTORequest userDTORequest = new UserDTORequest(newUser);
            if (ObjectUtils.isEmpty(userDTORequest.getUsername())
                    || ObjectUtils.isEmpty(userDTORequest.getPassword())
                    || ObjectUtils.isEmpty(userDTORequest.getFullName())
                    || ObjectUtils.isEmpty(userDTORequest.getEmail())
                    || ObjectUtils.isEmpty(userDTORequest.getPhone())) {
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            User foundUser = this.userService.findUserById(id);
            if (ObjectUtils.isEmpty(foundUser)) {
                return super.error(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getMessage());
            }

            User updatedUser = userService.updateUser(id, newUser);
            if (!ObjectUtils.isEmpty(updatedUser)) {
                return super.success(new UserDTOResponse(updatedUser));
//                return super.success(updatedUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}

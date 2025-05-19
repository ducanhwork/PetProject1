package dev.danh.controllers.user;

import dev.danh.controllers.websocket.UpdateController;
import dev.danh.entities.dtos.request.UserCreateRequest;
import dev.danh.entities.dtos.request.UserUpdateRequest;
import dev.danh.entities.dtos.response.APIResponse;
import dev.danh.services.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UserController {
    UserService userService;
    private final UpdateController updateController;

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse> getAllUsers() {
        return ResponseEntity.ok(
                APIResponse.builder()
                        .message("Users retrieved successfully")
                        .data(userService.getAllUsers())
                        .build()
        );
    }

    
    @PostMapping("/create")
    public ResponseEntity<APIResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        var user = userService.createUser(userCreateRequest);
        // Send the user creation event to the WebSocket topic
        updateController.sendUpdate(user);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .message("User created successfully")
                        .data(user)
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(
                APIResponse.builder()
                        .message("User updated successfully")
                        .data(userService.updateUser(id,userUpdateRequest))
                        .build()
        );
    }

}

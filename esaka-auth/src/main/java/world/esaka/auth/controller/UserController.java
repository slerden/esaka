package world.esaka.auth.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import world.esaka.auth.dto.UserProfileDTO;
import world.esaka.auth.hateoas.representation.UserResourceRepresentationService;
import world.esaka.auth.model.TokenUserDetails;
import world.esaka.auth.model.User;
import world.esaka.auth.service.api.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final UserResourceRepresentationService representationService;

    @Autowired
    ApplicationContext context;


    @Autowired
    public UserController(UserService userService,
                          UserResourceRepresentationService representationService) {
        this.userService = userService;
        this.representationService = representationService;
    }

    @PostMapping
    public UserProfileDTO create(@Validated(User.Create.class) @RequestBody User creationUserModel) {
        return representationService.getResource(userService.create(creationUserModel));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('U')")
    public UserProfileDTO get(@PathVariable Long id) {
        User user = userService.findById(id);
        return representationService.getResource(user);
    }

    @PatchMapping("/current/update")
    public ResponseEntity<UserProfileDTO> changePassword(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                                        @Validated(User.Update.class) @RequestBody User updatedUser) {
        updatedUser = userService.update(tokenUserDetails.getUsername(), updatedUser);
        return new ResponseEntity<UserProfileDTO>(representationService.getResource(updatedUser), HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = {RequestMethod.GET})
    public UserProfileDTO getCurrentUser(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        User user = userService.findByUsername(tokenUserDetails.getUsername());
        return representationService.getResource(user);
    }
}

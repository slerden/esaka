package world.esaka.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import world.esaka.auth.dto.UserProfileDto;
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

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService,
                          UserResourceRepresentationService representationService) {
        this.userService = userService;
        this.representationService = representationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileDto create(@Validated(User.Create.class) @RequestBody User creationUserModel) {
        return representationService.getResource(userService.create(creationUserModel));
    }

    @GetMapping("/{id}")
    public UserProfileDto get(@PathVariable Long id) {
        User user = userService.findById(id);
        return representationService.getResource(user);
    }

    @PatchMapping("/current")
    public ResponseEntity<UserProfileDto> changePassword(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                                         @Validated(User.Update.class) @RequestBody User updatedUser) {
        updatedUser = userService.update(tokenUserDetails.getUsername(), updatedUser);
        return new ResponseEntity<UserProfileDto>(representationService.getResource(updatedUser), HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = {RequestMethod.GET})
    public UserProfileDto getCurrentUser(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        User user = userService.findByUsername(tokenUserDetails.getUsername());
        return representationService.getResource(user);
    }
}

package world.esaka.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import world.esaka.auth.dto.ChangePasswordDto;
import world.esaka.auth.hateoas.representation.UserResourceRepresentationService;
import world.esaka.auth.model.TokenUserDetails;
import world.esaka.auth.model.User;
import world.esaka.auth.service.api.UserService;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    private final UserResourceRepresentationService representationService;

    private final Validator changePasswordDtoValidator;

    @InitBinder("changePasswordDto")
    public void setupBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(changePasswordDtoValidator);
    }

    @Autowired
    public UserController(UserService userService,
                          UserResourceRepresentationService representationService,
                          @Qualifier("changePasswordDtoValidator") Validator changePasswordDtoValidator) {
        this.userService = userService;
        this.representationService = representationService;
        this.changePasswordDtoValidator = changePasswordDtoValidator;
    }

    @PostMapping
    public Resource<User> create(@RequestBody User user) {
        return representationService.getResource(userService.create(user));
    }

    @GetMapping("/{id}")
    public Resource<User> get(@PathVariable Long id) {
        User user = userService.findById(id);
        return representationService.getResource(user);
    }

    @PostMapping("/password/change")
    public Resource<User> changePassword(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                         @Valid @RequestBody ChangePasswordDto changePasswordDto) {
        User user = userService.changePassword(tokenUserDetails.getUsername(),
                changePasswordDto.getCurrentPassword(),
                changePasswordDto.getNewPassword());
        return representationService.getResource(user);
    }

    @RequestMapping(value = "/current", method = {RequestMethod.GET, RequestMethod.POST})
    public Resource<TokenUserDetails> getCurrentUser(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        RestTemplate restTemplate = new RestTemplate();
        return new Resource<>(tokenUserDetails);
    }
}

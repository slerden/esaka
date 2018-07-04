package world.esaka.auth.hateoas.representation;

import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import world.esaka.auth.controller.UserController;
import world.esaka.auth.model.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class UserResourceRepresentationService {

    public Resource<User> getResource(User user) {
        Resource<User> result = new Resource<>(user);
        result.add(linkTo(methodOn(UserController.class).get(user.getUserId())).withRel("whois"));
        return result;
    }
}

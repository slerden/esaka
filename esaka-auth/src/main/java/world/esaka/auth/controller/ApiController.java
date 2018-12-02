package world.esaka.auth.controller;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class ApiController {

    @RequestMapping(method = RequestMethod.GET)
    public ResourceSupport links() {
        ResourceSupport result = new ResourceSupport();
        result.add(linkTo(methodOn(UserController.class).create(null)).withRel("create").withType(RequestMethod.POST.name()));
        result.add(linkTo(methodOn(TokenController.class).authToken(null)).withRel("authenticate").withType(RequestMethod.GET.name()));
        result.add(linkTo(methodOn(TokenController.class).refreshToken(null)).withRel("refreshToken").withType(RequestMethod.GET.name()));
        result.add(linkTo(methodOn(UserController.class).getCurrentUser(null)).withRel("current").withType(RequestMethod.GET.name()));
        return result;
    }

}

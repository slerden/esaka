package world.esaka.auth.hateoas.representation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;
import world.esaka.auth.controller.UserController;
import world.esaka.auth.dto.UserProfileDto;
import world.esaka.auth.model.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class UserResourceRepresentationService {

    private final ModelMapper modelMapper;

    @Autowired
    public UserResourceRepresentationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserProfileDto getResource(User user) {
        UserProfileDto result = modelMapper.map(user, UserProfileDto.class);
        addLinks(result);
        return result;
    }

    public void addLinks(ResourceSupport resourceSupport) {
        resourceSupport.add(linkTo(methodOn(UserController.class).changePassword(null, null)).withRel("update"));
        resourceSupport.add(linkTo(methodOn(UserController.class).getCurrentUser(null)).withRel("current"));
    }
}

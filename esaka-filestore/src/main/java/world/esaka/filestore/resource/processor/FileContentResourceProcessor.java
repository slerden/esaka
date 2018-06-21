package world.esaka.filestore.resource.processor;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import world.esaka.filestore.controller.FileController;
import world.esaka.filestore.model.FileContent;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class FileContentResourceProcessor implements ResourceProcessor<Resource<FileContent>> {
    @Override
    public Resource<FileContent> process(Resource<FileContent> fileContentResource) {
        final FileContent fileContent = fileContentResource.getContent();
        fileContentResource.add(linkTo(methodOn(FileController.class).patch(fileContent.getFileContentId())).withRel("patch"));
        return fileContentResource;
    }
}

package world.esaka.filestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;
import world.esaka.filestore.model.FileContent;
import world.esaka.filestore.repository.FileContentRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/files")
public class FileController {

    @Autowired
    FileContentRepository fileContentRepository;

    @GetMapping(value = "/{id}")
    public Resource<FileContent> get(@PathVariable("id") Long id) {
        Optional<FileContent> fileContent = fileContentRepository.findById(id);
        Link patch = linkTo(methodOn(FileController.class).patch(id)).withRel("patch");
        Resource<FileContent> fileContentResource = new Resource<>(fileContent.orElseGet(FileContent::new), patch);
        return fileContentResource;
    }

    @PostMapping(consumes = "application/json")
    public FileContent create(@RequestBody FileContent fileContent){
        fileContentRepository.save(fileContent);
        return fileContent;
    }

    @PutMapping(value = "/{id}")
    public String update(@PathVariable("id") Long id) {
        return "File updated";
    }

    @PatchMapping(value = "/{id}")
    public String patch(@PathVariable("id") Long id) {
        return "File patched";
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") Long id) {
        return "File deleted";
    }
}

package world.esaka.filestore.repository;

import org.springframework.data.repository.CrudRepository;
import world.esaka.filestore.model.FileContent;

public interface FileContentRepository extends CrudRepository<FileContent, Long> {

}

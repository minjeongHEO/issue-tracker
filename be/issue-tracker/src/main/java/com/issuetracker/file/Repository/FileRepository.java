package com.issuetracker.file.Repository;

import com.issuetracker.file.domain.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
}

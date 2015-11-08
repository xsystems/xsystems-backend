package org.xsystems.backend.io;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class FileService {

    public Path createPath(String first, String... more) {
        return FileSystems.getDefault().getPath(first, more);
    }

    public void moveFile (File file, Path path) throws IOException {
        Path parent = path.getParent();
        if (!Files.exists(parent)) {
            Files.createDirectories(parent);
        }
        Files.move(file.toPath(), path, REPLACE_EXISTING);
    }
}

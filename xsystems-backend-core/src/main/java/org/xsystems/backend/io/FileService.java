/**
 * The core of the backend of the xSystems web-application.
 * Copyright (C) 2015  xSystems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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

/**
 * barsuift-simlife is a life simulator programm
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife;

import java.io.File;


public final class FileTestHelper {

    private FileTestHelper() {
        // private constructor to enforce static access
    }

    public static void deleteAllFiles(File directory) throws Exception {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) {
                boolean delete = file.delete();
                if (!delete) {
                    throw new Exception("It is impossible to clean the directory : " + directory.getAbsolutePath()
                            + ". The file " + file + " cannot be deleted.");
                }
            }
        }
    }

}

package barsuift.simLife;

import java.io.File;


public final class FileTestHelper {

    private FileTestHelper() {
        // private constructor to enforce static access
    }

    public static void deleteAllFiles(File directory) throws Exception {
        System.out.println("##### CYRILLE #######");
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

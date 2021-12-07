package thread.chapter7;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PreventDupicated {
    private final static String LOCK_PATH = " C:\\Users\\86176\\Documents\\";
    private final static String LOCK_FILE = ".lock";
    private final static String PERMISSONS = "rw-------";

    public static void main(String[] args) throws IOException {
        checkRunning();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("The program received kill SIGNAL.");
            getLockFile().toFile().delete();
        }));
        for ( ; ; ){
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                System.out.println("System is running.");
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private static void checkRunning() throws IOException {
        Path path = getLockFile();
        if (path.toFile().exists())
            throw new RuntimeException("The program already running");
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString(PERMISSONS);
        Files.createFile(path, PosixFilePermissions.asFileAttribute(perms));
    }

    private static Path getLockFile() {
        return Paths.get(LOCK_PATH.substring(1), LOCK_FILE);
    }
}

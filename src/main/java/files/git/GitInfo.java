package files.git;

import java.io.File;
import java.io.IOException;

public class GitInfo {
    private final static String gitCommand = "git rev-parse HEAD";

    public static String getHeadHash(String path) {
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        ProcessBuilder pb = new ProcessBuilder();
        if (isWindows) {
            pb.command("cmd.exe", "/c", gitCommand);
        } else {
            pb.command("sh", "-c", gitCommand);
        }
        if (path == null) {
            path = ".";
        }
        File dotGit = new File(path+"/.git");
        if (!dotGit.exists()) {
            return "unknown";
        }
        pb.directory(new File(path));
        try {
            Process process = pb.start();
            byte[] out = process.getInputStream().readAllBytes();
            int exitCode = process.waitFor();
            return exitCode == 0 ? new String(out) : "";
        } catch (InterruptedException | IOException e) {
            return "error";
        }
    }
}

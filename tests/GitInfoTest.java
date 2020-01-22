import files.service.GitInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitInfoTest {

    @Test
    void checkIfVersionExists() {
        assertNotEquals(GitInfo.getHeadHash("."), "unknown");
    }

    @Test
    void checkIfVersionIsReadedCorrectly(){
        assertDoesNotThrow(()->{String tmp=GitInfo.getHeadHash(".");});
    }
}
package codigocreativo.uy.servidorapp.ws;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class MiAppTest {
    
    @Test
    void testGetClassesBasic() {
        MiApp app = new MiApp();
        Set<Class<?>> classes = app.getClasses();

        assertNotNull(classes);
        assertTrue(classes.size() > 0);
    }
    
    @Test
    void testGetClassesNoDuplicates() {
        MiApp app = new MiApp();
        Set<Class<?>> classes = app.getClasses();

        assertEquals(classes.size(), classes.stream().distinct().count());
    }
} 
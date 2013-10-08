import org.junit.Before;
import org.junit.Test;
import ro.gdgs.crawler.web.GreetServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Octavian
 * @since 1.0
 */
public class GreetServletTest {
    private GreetServlet greetServlet;

    @Before
    public void setupGuestBookServlet() {
        greetServlet = new GreetServlet();
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        when(request.getParameter("username")).thenReturn("Vasile");

        greetServlet.doGet(request, response);

        assertEquals("Salut Vasile" + System.getProperty("line.separator"), stringWriter.toString());
    }
}

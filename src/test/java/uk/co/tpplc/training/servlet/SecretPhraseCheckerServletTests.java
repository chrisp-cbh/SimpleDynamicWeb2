package uk.co.tpplc.training.servlet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SecretPhraseCheckerServletTests {
	private static final String OK_FLAG_SESSION_ATTRIBUTE = "goodegg";
	private static final String PASSPHRASE = "passphrase";
	@Mock
	HttpServletRequest mockRequest;
	@Mock
	HttpSession mockSession;
	@Mock
	RequestDispatcher mockRequestDispatcher;

	private SecretPhraseCheckerServlet secretPhraseCheckerServlet;

	@Before
	public void setup() {
		secretPhraseCheckerServlet = new SecretPhraseCheckerServlet();
		when(mockRequest.getRequestDispatcher(anyString())).thenReturn(
				mockRequestDispatcher);
		when(mockRequest.getSession()).thenReturn(mockSession);

	}

	@Test
	public void callGetsPassPhraseFromRequestParameter()
			throws ServletException, IOException {

		secretPhraseCheckerServlet.doPost(mockRequest, null);
		verify(mockRequest).getParameter(PASSPHRASE);
	}

	@Test
	public void sessionAttributeUnsetIfBadPassPhrase() throws ServletException,
			IOException {
		when(mockRequest.getParameter(PASSPHRASE)).thenReturn("wibble");

		secretPhraseCheckerServlet.doPost(mockRequest, null);
		//verify(mockSession).removeAttribute(OK_FLAG_SESSION_ATTRIBUTE);
		verify(mockSession).setAttribute(OK_FLAG_SESSION_ATTRIBUTE, "no");
	}

	@Test
	public void sessionAttributeIsSetWithCorrectPassPhrase()
			throws ServletException, IOException {
		when(mockRequest.getParameter(PASSPHRASE)).thenReturn(
				SecretPhraseCheckerServlet.CORRECT_PASSPHRASE);

		secretPhraseCheckerServlet.doPost(mockRequest, null);
		verify(mockSession).setAttribute(OK_FLAG_SESSION_ATTRIBUTE, "yes");
	}

	@Test
	public void callsRequestDispatcherWithIndexJspAndForwards()
			throws Exception {
		secretPhraseCheckerServlet.doPost(mockRequest, null);

		verify(mockRequest).getRequestDispatcher("/index.jsp");
		verify(mockRequestDispatcher).forward(mockRequest, null);
	}

	@Test
	public void nullPassphraseFailsIsValid() {
		assertThat(secretPhraseCheckerServlet.checkPassphraseIsValid(null),
				is(false));
	}

	@Test
	public void emptyPassphraseFailsIsValid() {
		assertThat(secretPhraseCheckerServlet.checkPassphraseIsValid(""),
				is(false));
	}

	@Test
	public void correctPassphrasePassesIsValid() {
		assertThat(
				secretPhraseCheckerServlet
						.checkPassphraseIsValid(SecretPhraseCheckerServlet.CORRECT_PASSPHRASE),
				is(true));
	}
}
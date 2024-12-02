package com.example.planetze.auth;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.b07.planetze.auth.AuthScreen;
import com.b07.planetze.auth.AuthUser;
import com.b07.planetze.auth.LoginModel;
import com.b07.planetze.auth.LoginPresenter;
import com.b07.planetze.auth.LoginView;
import com.b07.planetze.auth.backend.error.CredentialsError;
import com.b07.planetze.auth.backend.error.EmptyEmailError;
import com.b07.planetze.auth.backend.error.EmptyFieldsError;
import com.b07.planetze.auth.backend.error.LoginError;
import com.b07.planetze.auth.backend.error.MalformedEmailError;
import com.b07.planetze.auth.backend.error.OtherAuthError;
import com.b07.planetze.auth.backend.error.SendPasswordResetError;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.function.Consumer;
import java.util.function.Function;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
    @Mock
    LoginView view;

    @Mock
    LoginModel model;

    @Test
    public void forgotPassword() {
        var presenter = new LoginPresenter(model, view);

        presenter.forgotPassword();
        verify(view).switchScreens(AuthScreen.SEND_PASSWORD_RESET);
    }

    @Test
    public void loginEmptyFields() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<AuthUser, LoginError>>) invocation.getArguments()[2];

            callback.accept(error(new EmptyFieldsError()));
            return null;
        }).when(model).login(eq(""), eq(""), any(Consumer.class));

        presenter.login("", "");
        verify(view).showToast("All fields are required");
    }

    @Test
    public void loginMalformedEmail() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<AuthUser, LoginError>>) invocation.getArguments()[2];

            callback.accept(error(new MalformedEmailError()));
            return null;
        }).when(model).login(eq("sdfgsa"), eq("z"), any(Consumer.class));

        presenter.login("sdfgsa", "z");
        verify(view).showToast("Malformed email");
    }

    @Test
    public void loginInvalidCredentials() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<AuthUser, LoginError>>) invocation.getArguments()[2];

            callback.accept(error(new CredentialsError()));
            return null;
        }).when(model).login(eq("user@mail.com"), eq("password"), any(Consumer.class));

        presenter.login("user@mail.com", "password");
        verify(view).showToast("Invalid credentials");
    }

    @Test
    public void loginOtherError() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<AuthUser, LoginError>>) invocation.getArguments()[2];

            callback.accept(error(new OtherAuthError("some other error")));
            return null;
        }).when(model).login(eq("user@mail.com"), eq("password"), any(Consumer.class));

        presenter.login("user@mail.com", "password");
        verify(view).showToast("Error: some other error");
    }

    @Test
    public void loginUserEmailNotVerified() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<AuthUser, LoginError>>) invocation.getArguments()[2];

            callback.accept(ok(new AuthUser("user@mail.com", false)));
            return null;
        }).when(model).login(eq("user@mail.com"), eq("password"), any(Consumer.class));

        presenter.login("user@mail.com", "password");
        verify(view).switchScreens(AuthScreen.EMAIL_CONFIRMATION);
    }

    @Test
    public void loginSuccess() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<AuthUser, LoginError>>) invocation.getArguments()[2];

            callback.accept(ok(new AuthUser("user@mail.com", true)));
            return null;
        }).when(model).login(eq("user@mail.com"), eq("password"), any(Consumer.class));

        presenter.login("user@mail.com", "password");
        verify(view).showToast("Logged in as user@mail.com");
        verify(view).toHome();
    }

    @Test
    public void sendPasswordResetEmptyEmail() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<Unit, SendPasswordResetError>>) invocation.getArguments()[1];

            callback.accept(error(new EmptyEmailError()));
            return null;
        }).when(model).sendPasswordResetEmail(eq("user@mail.com"), any(Consumer.class));

        presenter.sendPasswordResetEmail("user@mail.com");
        verify(view).showToast("Enter an email");
    }

    @Test
    public void sendPasswordResetMalformedEmail() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<Unit, SendPasswordResetError>>) invocation.getArguments()[1];

            callback.accept(error(new MalformedEmailError()));
            return null;
        }).when(model).sendPasswordResetEmail(eq("usermailcom"), any(Consumer.class));

        presenter.sendPasswordResetEmail("usermailcom");
        verify(view).showToast("Malformed email");
    }

    @Test
    public void sendPasswordResetOtherError() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<Unit, SendPasswordResetError>>) invocation.getArguments()[1];

            callback.accept(error(new OtherAuthError("some other error")));
            return null;
        }).when(model).sendPasswordResetEmail(eq("user@mail.com"), any(Consumer.class));

        presenter.sendPasswordResetEmail("user@mail.com");
        verify(view).showToast("Error: some other error");
    }

    @Test
    public void sendPasswordResetSuccess() {
        var presenter = new LoginPresenter(model, view);

        doAnswer((Answer<Object>) invocation -> {
            @SuppressWarnings("unchecked")
            var callback = (Consumer<Result<Unit, SendPasswordResetError>>) invocation.getArguments()[1];

            callback.accept(ok());
            return null;
        }).when(model).sendPasswordResetEmail(eq("user@mail.com"), any(Consumer.class));

        presenter.sendPasswordResetEmail("user@mail.com");
        verify(view).showToast("Password reset email sent");
    }
}

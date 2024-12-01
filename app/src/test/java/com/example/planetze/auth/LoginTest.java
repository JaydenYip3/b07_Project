package com.example.planetze.auth;

import static org.mockito.Mockito.verify;

import com.b07.planetze.auth.AuthScreen;
import com.b07.planetze.auth.LoginModel;
import com.b07.planetze.auth.LoginPresenter;
import com.b07.planetze.auth.LoginView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
}

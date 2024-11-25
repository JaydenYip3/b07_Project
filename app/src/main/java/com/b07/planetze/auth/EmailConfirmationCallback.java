package com.b07.planetze.auth;

import android.content.Context;

public interface EmailConfirmationCallback {
    void confirmEmail();

    void resendConfirmationEmail();

}

package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class RegisterPresenter extends AuthenticatePresenter  {

    public RegisterPresenter(AuthenticateView view) {
        super(view);
    }

    public void register(String firstName, String lastName, String alias, String password, Drawable imageToUpload) {
        try {
            view.clearErrorMessage();
            view.clearInfoMessage();
            validateRegistration(firstName, lastName, alias, password, imageToUpload);

            view.displayInfoMessage("Registering...");

            Bitmap image = ((BitmapDrawable) imageToUpload).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageBytes = bos.toByteArray();
            String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

            new UserService().register(firstName, lastName, alias, password, imageBytesBase64, this);
        } catch (Exception e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password, Drawable imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (imageToUpload == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }
}

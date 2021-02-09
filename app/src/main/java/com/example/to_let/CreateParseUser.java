package com.example.to_let;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

interface CreateParseUser {
    default void signUpUserInBackground(ParseUser user){
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){

                    handleSuccessfullyCreateNewParseUser();
                }
                else{
                    handleErrorOnSignUp(e);
                }
            }
        });
    }

    void handleSuccessfullyCreateNewParseUser();
    void handleErrorOnSignUp(ParseException e);

    ParseUser user = new ParseUser();
}

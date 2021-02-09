package com.example.to_let;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.parse.ParseUser;

public class ContactUsActivity extends AppCompatActivity {

    TextView tvName, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ParseUser currentUser = ParseUser.getCurrentUser();

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

        if (currentUser != null) {
            tvName.setText(currentUser.getUsername());
            tvEmail.setText(currentUser.getEmail());
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()){

            case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            default:
                break;
                }

        return false;
    }


    public void logout(View view) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading ...");
        progress.show();
        ParseUser.logOut();
        Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        progress.dismiss();
    }

}

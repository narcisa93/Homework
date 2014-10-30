package com.example.homework.home;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework.DBFields;
import com.example.homework.R;
import com.example.homework.base.BaseActivity;
import com.example.homework.base.BaseModel;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.base.ModelSuccessResponse;

public class ActivityChangePassword extends BaseActivity {

	Button changePassBtn;
	EditText oldPassEt, newPassEt, newPassConfirmEt;
	String oldPassword, newPassword;
	ApiChangePassword api = new ApiChangePassword();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		oldPassEt = (EditText) findViewById(R.id.oldPassEt);
		newPassEt = (EditText) findViewById(R.id.newPassEt);
		newPassConfirmEt = (EditText) findViewById(R.id.newPassConfirmEt);
		changePassBtn = (Button) findViewById(R.id.changePasswordBtn);

		addApiInterface(api); //adds the controller for changing passwords in the api list
		changePassBtn.setOnClickListener(new OnClickListener() {

			//checks for password fields input and launches the change password function
			@Override
			public void onClick(View v) {
				if (!oldPassEt.getEditableText().toString()
						.equals(DBFields.pass)) {
					Toast.makeText(getApplicationContext(),
							"Wrong password", Toast.LENGTH_LONG).show();
				} else if (newPassEt.getEditableText().toString().length() < 8) {
					Toast.makeText(getApplicationContext(),
							"The new password should be at least 8 characters long", Toast.LENGTH_LONG).show();
				} else if (!newPassEt.getEditableText().toString()
						.equals(newPassConfirmEt.getEditableText().toString())) {
					Toast.makeText(getApplicationContext(),
							"Passwords do not match", Toast.LENGTH_LONG).show();
				} else {
					api.p_changePassword(
							oldPassEt.getEditableText().toString(), newPassEt
									.getEditableText().toString());
				}

			}
		});
	}

	
	//displays success if controller changes the password succesfully
	//displays an error if not
	public void onResponse(BaseModel model) {
		if (model instanceof ModelSuccessResponse) {
			Toast.makeText(getApplicationContext(),
					"Password Changed Successfully", Toast.LENGTH_LONG).show();
			finish();
		} else if (model instanceof ModelFailureResponse) {
			showErrorDialog("Username or password incorrect");
			Toast.makeText(getApplicationContext(),
					"An error occured.Try again!", Toast.LENGTH_LONG).show();
		}
	}
}

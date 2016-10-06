package com.mark.invest.p2p;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.mark.invest.p2p.api.ApiCallback;
import com.mark.invest.p2p.api.service.ApiAuthorizationService;
import com.mark.invest.p2p.config.AccountType;
import com.mark.invest.p2p.config.ApiType;
import com.mark.invest.p2p.config.ApplicationState;
import com.mark.invest.p2p.config.Configuration;
import com.mark.invest.p2p.config.ConfigurationVO;
import com.mark.invest.p2p.config.UserInformation;
import com.mark.invest.p2p.constant.ApplicationConstants;
import com.mark.invest.p2p.util.UIUtil;

public class AddAccountActivity extends Activity implements ApiCallback<Boolean> {

    private static final String TAG = ApplicationConstants.LOG_TAG;
    private static final String SHARED_PREFERENCES_KEY = "UserInformation";
    private static final String SHARED_PREFERENCES_USERNAME_KEY = "Username";
    private int actionBarTitleR = -1, userNameHintR = -1, passwordHintR = -1, infoTextR = -1, titleTextR = -1;
    private int addButtonTextR = -1, addingButtonTextR = -1;
    private EditText userNameText;
    private EditText passwordText;
    private TextView addAccountErrorText;
    private TextView addAccountInfoText;
    private TextView addAccountTitleText;
    private Button addAccountButton;
    private AccountType accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        userNameText = (EditText) findViewById(R.id.prosper_sign_in_user_name_text);
        passwordText = (EditText) findViewById(R.id.prosper_sign_in_password_text);
        addAccountErrorText = (TextView) findViewById(R.id.p2p_add_account_error_text);
        addAccountInfoText = (TextView) findViewById(R.id.p2p_add_account_info_text);
        addAccountTitleText = (TextView) findViewById(R.id.p2p_add_account_title_text);
        addAccountErrorText.setVisibility(View.INVISIBLE);
        addAccountButton = (Button) findViewById(R.id.p2p_add_account_button);

        accountType = (AccountType) getIntent().getExtras().get(ApplicationConstants.ACCOUNT_TYPE_INTENT_KEY);

        final TextView infoTextView = (TextView) findViewById(R.id.p2p_add_account_info_text);
        infoTextView.setMovementMethod(LinkMovementMethod.getInstance()); // make links clickable

        setTextResourcesBasedOnAccountType(accountType);

        userNameText.clearFocus();
        passwordText.clearFocus();
    }

    private void setTextResourcesBasedOnAccountType(AccountType accountType)
    {

        if (accountType == AccountType.Prosper) {
            actionBarTitleR = R.string.p2p_add_account_prosper_action_bar_title;
            userNameHintR = R.string.p2p_add_account_prosper_username_hint;
            passwordHintR = R.string.p2p_add_account_prosper_password_hint;
            infoTextR = R.string.p2p_add_account_prosper_info_text;
            titleTextR = R.string.p2p_add_account_prosper_title_text;
            addButtonTextR = R.string.p2p_add_account_prosper_button_text;
            addingButtonTextR = R.string.p2p_adding_account_prosper_button_text;
        }
        else if ( accountType == AccountType.LendingClub)
        {
            actionBarTitleR = R.string.p2p_add_account_lending_club_action_bar_title;
            userNameHintR = R.string.p2p_add_account_lending_club_username_hint;
            passwordHintR = R.string.p2p_add_account_lending_club_password_hint;
            infoTextR = R.string.p2p_add_account_lending_club_info_text;
            titleTextR = R.string.p2p_add_account_lending_club_title_text;
            addButtonTextR = R.string.p2p_add_account_lending_club_button_text;
            addingButtonTextR = R.string.p2p_adding_account_lending_club_button_text;
        }

        UIUtil.configureActionBar(this.getActionBar(), getResources().getString(actionBarTitleR), true);
        addAccountButton.setText(addButtonTextR);
        userNameText.setHint(userNameHintR);
        passwordText.setHint(passwordHintR);
        addAccountInfoText.setText(infoTextR);
        addAccountTitleText.setText(titleTextR);

    }

    public void onAddAccountButtonClicked(View view) {
        addAccountErrorText.setVisibility(View.INVISIBLE);
        addAccountButton.setText(addingButtonTextR);
        addAccountButton.setEnabled(false);

        // get the user and passes
        String userName = userNameText.getText().toString();
        String password = passwordText.getText().toString();

        ConfigurationVO configVO = Configuration.getConfiguration();

        // see if the information we have is correct
        boolean canTryToLogIn = configVO != null && userName.length() > 0 && password.length() > 0;
        if ( canTryToLogIn )
        {
            UserInformation userInformation = new UserInformation(userName, password);
            ApplicationState.getState().setCurrentUser(userInformation);
            Configuration.changeConfiguration(configVO);
            new ApiAuthorizationService(this).invoke();
        }
        else
        {
            onApiResponse(false); // go with the can't log in flow
        }
    }

    @Override
    public void onApiResponse(Boolean canLogIn) {
        if ( canLogIn )
        {
            addAccountErrorText.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, HomeActivity.class);
            SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES_KEY, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(SHARED_PREFERENCES_USERNAME_KEY, ApplicationState.getState().getCurrentUser().getUserName());
            editor.apply();
            startActivity(intent);
        }
        else
        {
            // Show error
            addAccountErrorText.setVisibility(View.VISIBLE);
            addAccountErrorText.setText(R.string.p2p_add_account_error_text);
            addAccountButton.setText(addButtonTextR);
            addAccountButton.setEnabled(true);
            ApplicationState.getState().removeUserInformation(); // remove the failed credientials
        }
    }

    public void onSignUpButtonClick(View view) {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        signUpIntent.putExtra(ApplicationConstants.ACCOUNT_TYPE_INTENT_KEY, accountType);
        startActivity(signUpIntent);
    }
}

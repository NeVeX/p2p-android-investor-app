package com.mark.invest.p2p;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mark.invest.p2p.config.AccountType;
import com.mark.invest.p2p.config.Configuration;
import com.mark.invest.p2p.constant.ApplicationConstants;


public class GetStartedActivity extends Activity {

    private static final String TAG = ApplicationConstants.LOG_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        Configuration.initialize(this);

        ActionBar ab = this.getActionBar();
        if ( ab != null)
        {
            ab.setTitle(R.string.p2p_get_started_action_bar_title);
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onProsperAccountButtonClicked(View view) {
        startAddAccountActivity(AccountType.Prosper);
    }

    public void onLendingClubAccountButtonClicked(View view) {
        startAddAccountActivity(AccountType.LendingClub);
    }

    private void startAddAccountActivity(AccountType accountType)
    {
        Intent addAccountIntent = new Intent(this, AddAccountActivity.class);
        addAccountIntent.putExtra(ApplicationConstants.ACCOUNT_TYPE_INTENT_KEY, accountType);
        startActivity(addAccountIntent);
    }

}

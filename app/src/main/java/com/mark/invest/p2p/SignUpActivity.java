package com.mark.invest.p2p;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import com.mark.invest.p2p.config.AccountType;
import com.mark.invest.p2p.constant.ApplicationConstants;
import com.mark.invest.p2p.util.UIUtil;


public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final WebView webView = (WebView) findViewById(R.id.p2p_account_sign_up_webview);
        webView.getSettings().setJavaScriptEnabled(true);


        AccountType accountType = (AccountType) getIntent().getExtras().get(ApplicationConstants.ACCOUNT_TYPE_INTENT_KEY);
        int urlR = -1, abTitleR = -1;
        if ( accountType == AccountType.Prosper) {
            urlR = R.string.p2p_sign_up_prosper_url;
            abTitleR = R.string.p2p_sign_up_prosper_action_bar_title;
        }
        else if ( accountType == AccountType.LendingClub)
        {
            urlR = R.string.p2p_sign_up_lending_club_url;
            abTitleR = R.string.p2p_sign_up_lending_club_action_bar_title;
        }
        webView.loadUrl(getResources().getString(urlR));
        UIUtil.configureActionBar(getActionBar(), getResources().getString(abTitleR), true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

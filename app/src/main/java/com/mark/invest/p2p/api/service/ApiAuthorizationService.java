package com.mark.invest.p2p.api.service;

import com.mark.invest.p2p.api.ApiCallback;
import com.mark.invest.p2p.api.model.AccountVO;
import com.mark.invest.p2p.config.ApiType;
import com.mark.invest.p2p.config.Configuration;

/**
 * Created by NeVeX on 9/26/2015.
 */
public class ApiAuthorizationService implements ApiCallback<AccountVO> {

    private ApiCallback<Boolean> callback;

    public ApiAuthorizationService(ApiCallback<Boolean> callback) {
        this.callback = callback;
    }

    public void invoke()
    {
        if ( Configuration.getConfiguration().getApiType() == ApiType.DotNet)
        {
            new ApiAccountService(this).invoke();
        }
        else {
            // TODO: change this to java when it's implemented
            new ApiAccountService(this).invoke();
        }
    }

    @Override
    public void onApiResponse(AccountVO data) {
        callback.onApiResponse(data != null);
    }
}

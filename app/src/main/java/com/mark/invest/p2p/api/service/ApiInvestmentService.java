package com.mark.invest.p2p.api.service;

import com.mark.invest.p2p.api.AbstractApiClient;
import com.mark.invest.p2p.api.ApiCallback;
import com.mark.invest.p2p.api.model.ApiRequestVO;
import com.mark.invest.p2p.api.model.InvestmentsVO;
import com.mark.invest.p2p.api.util.ApiUtil;

/**
 * Created by NeVeX on 9/26/2015.
 */
public class ApiInvestmentService extends AbstractApiClient<InvestmentsVO> {


    private final static String INVESTMENT_RESOURCE = "/api/Investments";

    public ApiInvestmentService(ApiCallback<InvestmentsVO> callback) {
        super(callback);
    }

    public void invokeGetAllActiveInvestments()
    {
        ApiRequestVO<InvestmentsVO> requestVO =
                new ApiRequestVO<>(
                        INVESTMENT_RESOURCE,
                        "ListingStatus eq 2", // 2 is active
                        ApiUtil.createDotNetApiHeaders(),
                        null,
                        InvestmentsVO.class,
                        0,
                        -1, // get all the results
                        true);
        execute(requestVO);
    }
}

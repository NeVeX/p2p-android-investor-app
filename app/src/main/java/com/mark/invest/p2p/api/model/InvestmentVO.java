package com.mark.invest.p2p.api.model;

import com.mark.invest.p2p.api.ApiResponse;
import com.mark.invest.p2p.api.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NeVeX on 10/4/2015.
 */
public class InvestmentVO implements ApiResponse<InvestmentVO> {

    private int listingId;

    public int getListingId() {
        return listingId;
    }

    @Override
    public InvestmentVO convertJSONObject(JSONObject jsonObject) throws JSONException {
        InvestmentVO investmentVO = new InvestmentVO();
        investmentVO.listingId = JSONUtil.tryGetInteger(jsonObject, "ListingNumber");
        return investmentVO;
    }

    @Override
    public InvestmentVO convertJSONArray(JSONArray jsonArray) throws JSONException {
        return null;
    }
}

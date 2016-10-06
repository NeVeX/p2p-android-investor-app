package com.mark.invest.p2p.api;

/**
 * Created by NeVeX on 9/24/2015.
 */
public interface ApiCallback<T> {

    void onApiResponse(T data);
}

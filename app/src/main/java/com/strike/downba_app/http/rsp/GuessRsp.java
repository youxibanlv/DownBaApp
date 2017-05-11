package com.strike.downba_app.http.rsp;


import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.bean.Guess;

public class GuessRsp extends BaseRsp {

	public ResultData resultData = new ResultData();
    public class ResultData{
        public Guess guess = new Guess();

    }
}

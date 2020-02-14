package com.example.miaosha.db.error;

import com.example.miaosha.db.response.CommonReturnType;

/**
 * @author FanQie
 * @date 2019/8/9 9:37
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}

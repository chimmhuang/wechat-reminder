package com.github.chimmhuang.wechat.push.dto;

/**
 * 微信 get_access_token 返回值
 * 微信接口文档：<a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">获取Access token</a>
 *
 * @author Chimm Huang
 * @date 2022/8/31
 */
public class AccessTokenRespDTO {

    /*
        正确的返回结果
        {
            "access_token": "60_e54mTyN8LCAedkj9XSx2pptvrRfn6HZMUGyM56hMVrYlnySH6-RUOcDXm5fNEbCBK80xo7z67bV-iELGqzLxFCqdZ3aaX4WG5eqLCbI_LrI7C7IX95qF75NRZCAnOJOFKjJkWcWf8MtthRt_LJQaABAFDK",
            "expires_in": 7200
        }
     */

    private String access_token;
    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}

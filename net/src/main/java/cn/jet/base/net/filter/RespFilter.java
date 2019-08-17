package cn.jet.base.net.filter;

import cn.jet.base.net.filter.data.RespData;

public interface RespFilter {

    RespData filter(RespChain chain) throws Exception;

    interface RespChain {

        RespData proceed(RespData data) throws Exception;

        RespData respData();
    }

}

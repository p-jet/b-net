package ai.zile.lib.net.filter;

import ai.zile.lib.net.filter.data.RespData;

public interface RespFilter {

    RespData filter(RespChain chain) throws Exception;

    interface RespChain {

        RespData proceed(RespData data) throws Exception;

        RespData respData();
    }

}

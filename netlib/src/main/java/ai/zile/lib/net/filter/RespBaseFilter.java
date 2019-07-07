package ai.zile.lib.net.filter;

import ai.zile.lib.net.filter.data.RespData;

public class RespBaseFilter implements RespFilter {

    @Override
    public RespData filter(RespChain chain) throws Exception {

        RespData respData = chain.respData();
        RespData dealRespData = chain.proceed(respData);
        if (dealRespData != null) {
            respData = dealRespData;
        }
        return respData;
    }

}

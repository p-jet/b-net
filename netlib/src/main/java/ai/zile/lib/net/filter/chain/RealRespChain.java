package ai.zile.lib.net.filter.chain;

import java.util.List;

import ai.zile.lib.net.filter.RespFilter;
import ai.zile.lib.net.filter.data.RespData;

public class RealRespChain implements RespFilter.RespChain {


    private List<RespFilter> filters;
    private RespData respData;
    private int index;

    public RealRespChain(List<RespFilter> filters, RespData respData, int index) {
        this.filters = filters;
        this.respData = respData;
        this.index = index;
    }

    @Override
    public RespData proceed(RespData data) throws Exception {

        if (index >= filters.size()) {
            return data;
        }
        RespFilter.RespChain next = new RealRespChain(filters, respData, index + 1);
        RespFilter respFilter = filters.get(index);
        RespData respData = respFilter.filter(next);

        return respData;
    }

    @Override
    public RespData respData() {
        return respData;
    }


}

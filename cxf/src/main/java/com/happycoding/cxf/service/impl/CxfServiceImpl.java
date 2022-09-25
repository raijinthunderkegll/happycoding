package com.happycoding.cxf.service.impl;

import com.happycoding.cxf.model.CxfReq;
import com.happycoding.cxf.model.CxfRes;
import com.happycoding.cxf.service.CxfService;

import javax.annotation.Resource;

public class CxfServiceImpl implements CxfService {

    @Resource(name = "webServiceImpl")
    com.happycoding.cxf.service.WebService webService;

    @Override
    public CxfRes test(CxfReq req) {
        return null;
    }
}

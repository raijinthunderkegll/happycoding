package com.happycoding.cxf.service;

import com.happycoding.cxf.model.CxfReq;
import com.happycoding.cxf.model.CxfRes;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CxfService {

    @WebMethod
    public CxfRes test(CxfReq req);
}

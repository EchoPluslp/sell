package com.liupeng.sell.service.impl;

import com.liupeng.sell.dataObject.SellerInfo;
import com.liupeng.sell.repository.SellerInfoRepository;
import com.liupeng.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}

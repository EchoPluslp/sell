package com.liupeng.sell.service.impl;

import com.liupeng.sell.config.UpYunConfig;
import com.liupeng.sell.dataObject.ProductInfo;
import com.liupeng.sell.dto.CartDTO;
import com.liupeng.sell.enums.ProductStatusEnum;
import com.liupeng.sell.enums.ResultEnum;
import com.liupeng.sell.exception.SellException;
import com.liupeng.sell.repository.ProductInfoRepository;
import com.liupeng.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 */
@Service
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Autowired
    private UpYunConfig upYunConfig;

    @Override
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> productInfoOptional = repository.findById(productId);

        productInfoOptional.ifPresent(e -> e.addImageHost(upYunConfig.getImageHost()));
        return productInfoOptional.orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode()).stream()
                .map(e -> e.addImageHost(upYunConfig.getImageHost()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        Page<ProductInfo> productInfoPage = repository.findAll(pageable);
        productInfoPage.getContent().stream()
                .forEach(e -> e.addImageHost(upYunConfig.getImageHost()));
        return productInfoPage;
    }

    @Override
//    @CachePut(key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo).addImageHost(upYunConfig.getImageHost());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}

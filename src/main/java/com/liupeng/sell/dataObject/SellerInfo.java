package com.liupeng.sell.dataObject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author liufeifei
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}

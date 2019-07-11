package com.dw.health.eportal.entity.openfire;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(name = "OFMUCAFFILIATION",schema="OPENFIRE")
public class Ofmucaffiliation {

    private Integer roomid;

    private String jid;

    private Integer affiliation = 10;

}
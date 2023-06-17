package org.tinycloud.tinyjob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyjob.mapper.HostsItemMapper;

@Service
public class HostsItemService {

    @Autowired
    private HostsItemMapper hostsItemMapper;


}

package com.dev.nawwa.controller.api;

import com.dev.nawwa.dto.ServiceProviderProfileDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ServiceProviderProfileApi {
    @PostMapping(value = "/serviceproviderprofile/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ServiceProviderProfileDto save(@RequestBody ServiceProviderProfileDto dto);

}

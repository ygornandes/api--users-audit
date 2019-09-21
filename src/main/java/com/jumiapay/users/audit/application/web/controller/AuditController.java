package com.jumiapay.users.audit.application.web.controller;

import com.jumiapay.users.audit.application.web.requests.AuditRequest;
import com.jumiapay.users.audit.application.web.responses.AuditResponse;
import com.jumiapay.users.audit.application.web.utils.Constants;
import com.jumiapay.users.audit.domain.service.AuditService;
import com.jumiapay.users.audit.domain.service.enums.DirectionEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@Api("Users Audit")
@RequestMapping("/audits")
@RequiredArgsConstructor
@Validated
public class AuditController {

    private final AuditService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creation of User Audit")
    public void create(@Valid @RequestBody AuditRequest request){
        service.enqueue(request.toAudit());
    }

    @GetMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Retrieving all Audits by User Id")
    public List<AuditResponse> getAuditsByUserId(@PathVariable @NotNull(message = Constants.ID_NOT_NULL) String id,
                                                 @RequestParam @NotNull(message = Constants.PAGE_NOT_NULL) Integer page,
                                                 @RequestParam @NotNull(message = Constants.SIZE_NOT_NULL) Integer size,
                                                 @RequestParam(defaultValue = "id") @NotBlank(message = Constants.SORT_NOT_BLANCK) String sort,
                                                 @ApiParam(allowableValues = "ASC,DESC") @RequestParam(defaultValue = "ASC") @Pattern(regexp = "DESC|ASC",message = Constants.DIRECTION_INVALID) String direction){
        return AuditResponse.toListResponse(service.getAuditsByUserId(id,page,size,sort.split(","), DirectionEnum.valueOf(direction)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Retrieving all Audits")
    public List<AuditResponse> getAudits(@RequestParam @NotNull(message = Constants.PAGE_NOT_NULL) Integer page,
                                         @RequestParam @NotNull(message = Constants.SIZE_NOT_NULL) Integer size,
                                         @RequestParam(defaultValue = "id") @NotBlank(message = Constants.SORT_NOT_BLANCK) String sort,
                                         @ApiParam(allowableValues = "ASC,DESC") @RequestParam(defaultValue = "ASC") @Pattern(regexp = "DESC|ASC",message = Constants.DIRECTION_INVALID) String direction){
        return AuditResponse.toListResponse(service.getAudits(page,size,sort.split(","), DirectionEnum.valueOf(direction)));
    }

}

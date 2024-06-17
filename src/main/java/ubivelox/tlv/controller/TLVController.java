package ubivelox.tlv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ubivelox.tlv.common.response.DataResponse;
import ubivelox.tlv.domain.TLV;
import ubivelox.tlv.service.TLVService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TLVController {

    private final TLVService tlvService;

    @PostMapping("/parse")
    public DataResponse<List<TLV>> parse(@RequestBody String tlvData) {
        List<TLV> tlvList = tlvService.parseTlv(tlvData);
        System.out.println("tlvList = " + tlvList);
        return DataResponse.of(tlvList);
    }
}
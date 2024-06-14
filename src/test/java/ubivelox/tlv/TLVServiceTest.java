package ubivelox.tlv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ubivelox.tlv.domain.TLV;
import ubivelox.tlv.service.TLVService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TLVServiceTest {

    @Autowired
    private TLVService tlvService;


    @DisplayName("TLV 문자열을 파싱한다.")
    @Test
    void parseTlv() {
        // given
        String tlvString = "6F6F8408A000000003000000A5599F6501FF9F6E06479173512E00734A06072A864886FC6B01600C060A2A864886FC6B02020101630906072A864886FC6B03640B06092A864886FC6B040215650B06092B8510864864020103660C060A2B060104012A026E01028408A000000003000000010101";

        // when
        List<TLV> tlvs = tlvService.parseTlv(tlvString);

        // then
        assertThat(tlvs.size()).isEqualTo(1);
    }

}

package ubivelox.tlv.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ubivelox.tlv.common.exception.BaseException;
import ubivelox.tlv.domain.TLV;
import ubivelox.tlv.domain.type.TagType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TLVParserTest {

    @DisplayName("16진수 문자열을 byte 배열로 변환한다")
    @Test
    void givenHexStringWhenConvertedToByteArrayThenReturnsCorrectByteArray() {
        //given
        String hexString = "6F108407A000000003";

        //when
        byte[] bytes = TLVParser.hexStringToByteArray(hexString);

        //then
        assertThat(bytes.length).isEqualTo(9);
        assertThat(bytes[0]).isEqualTo((byte) 0x6F);
    }

    @DisplayName("16진수 문자열을 byte 배열로 변환할 때 홀수 길이의 문자열이면 예외를 던진다")
    @Test
    void givenOddLengthHexStringWhenConvertedToByteArrayThenThrowsException() {
        //given
        String hexString = "6F1";

        //when, then
        assertThrows(BaseException.class, () -> TLVParser.hexStringToByteArray(hexString));
    }

    @DisplayName("16진수 문자열을 byte 배열로 변환할 때 유효하지 않은 문자가 포함되어 있으면 예외를 던진다")
    @Test
    void givenInvalidHexStringWhenConvertedToByteArrayThenThrowsException() {
        //given
        String hexString = "6F1Z";

        //when, then
        assertThrows(BaseException.class, () -> TLVParser.hexStringToByteArray(hexString));
    }


    @DisplayName("TLV Tag 데이터 정상 파싱 검증")
    @ValueSource(strings = {
            "100101",
            "110101",
            "120101",
            "130101"
    })
    @ParameterizedTest
    void givenTLVTagDataWhenParsedThenReturnsCorrectTag(String data) {
        //given, when
        List<TLV> parse = TLVParser.parse(data);

        //then
        assertThat(parse.size()).isEqualTo(1);
        assertThat(parse.get(0).getTag().getValue()).isEqualTo(data.substring(0, 2));
    }

    @DisplayName("TLV Length 데이터 정상 파싱 검증")
    @ValueSource(strings = {
            "100101",
            "110101",
            "120101",
            "130101"
    })
    @ParameterizedTest
    void givenTLVLengthDataWhenParsedThenReturnsCorrectLength(String data) {
        //given, when
        List<TLV> parse = TLVParser.parse(data);

        //then
        assertThat(parse.size()).isEqualTo(1);
        assertThat(parse.get(0).getLength()).isEqualTo(Integer.parseInt(data.substring(2, 4), 16));
    }

    @DisplayName("TLV Value 데이터 정상 파싱 검증")
    @ValueSource(strings = {
            "100120",
            "110130",
            "120140",
            "130150"
    })
    @ParameterizedTest
    void givenTLVValueDataWhenParsedThenReturnsCorrectValue(String data) {
        //given, when
        List<TLV> parse = TLVParser.parse(data);

        //then
        assertThat(parse.size()).isEqualTo(1);
        assertThat(parse.get(0).getValue()).isEqualTo(String.valueOf(Integer.parseInt(data.substring(4), 16)));
    }

    @DisplayName("TLVParser.parse 메소드가 PRIMITIVE 타입의 태그를 올바르게 생성하는지 검증")
    @Test
    void givenDataWhenParseThenReturnsPrimitiveTag() {
        //given
        String data = "0201FF";

        //when
        List<TLV> tlvList = TLVParser.parse(data);

        //then
        assertThat(tlvList.size()).isEqualTo(1);
        assertThat(tlvList.get(0).getTag().getType()).isEqualTo(TagType.PRIMITIVE);
        assertThat(tlvList.get(0).getValue()).isNotNull();
    }

    @DisplayName("TLVParser.parse 메소드가 CONSTRUCTED 타입의 태그를 올바르게 생성하는지 검증")
    @Test
    void givenDataWhenParseThenReturnsConstructedTag() {
        //given
        String data = "A2010201FF";

        //when
        List<TLV> tlvList = TLVParser.parse(data);

        //then
        assertThat(tlvList.size()).isEqualTo(2);
        assertThat(tlvList.get(0).getTag().getType()).isEqualTo(TagType.CONSTRUCTED);
        assertThat(tlvList.get(0).getValue()).isNull();
        assertThat(tlvList.get(1).getTag().getType()).isEqualTo(TagType.PRIMITIVE);
        assertThat(tlvList.get(1).getValue()).isNotNull();

    }


}
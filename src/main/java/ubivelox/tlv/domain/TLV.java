package ubivelox.tlv.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class TLV {
    private Tag tag;
    private int length;
    private String value;
    private TLV parent;

    @Builder
    public TLV(Tag tag, int length, String value, TLV parent) {
        this.tag = tag;
        this.length = length;
        this.value = value == null ? "" : value;
        this.parent = parent;
    }

    public static TLV of(Tag tag, int length, String value, TLV parent) {
        return TLV.builder()
                .tag(tag)
                .length(length)
                .value(value)
                .parent(parent)
                .build();
    }

    public static TLV of(Tag tag, int length, TLV parent) {
        return TLV.builder()
                .tag(tag)
                .length(length)
                .parent(parent)
                .build();
    }

}

package ubivelox.tlv.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ubivelox.tlv.domain.type.TagType;

@Getter
@ToString
public class Tag {
    private final String value;
    private final TagType type;

    @Builder
    public Tag(String value, TagType type) {
        this.value = value;
        this.type = type;
    }

    public static Tag create(String value, TagType type) {
        return new Tag(value, type);
    }

}


package com.navi.assignment.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Slot {
    private int start;
    private int end;

    public boolean overlaps(Slot slot) {
        return Math.max(this.start, slot.start) < Math.min(this.end, slot.end);
    }
}

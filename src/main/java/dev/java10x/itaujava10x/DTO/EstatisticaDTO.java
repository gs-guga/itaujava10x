package dev.java10x.itaujava10x.DTO;

import lombok.Getter;
import java.util.DoubleSummaryStatistics;

@Getter
public class EstatisticaDTO {
    private final Long count;
    private final Double sum;
    private final Double avg;
    private final Double min;
    private final Double max;

    public EstatisticaDTO(Long count, Double sum, Double avg, Double min, Double max) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
    }
}

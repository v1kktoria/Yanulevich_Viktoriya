package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.AnalyticsDto;
import senla.model.Analytics;
import senla.model.Property;

@Component
@RequiredArgsConstructor
public class AnalyticsMapper {

    private final ModelMapper modelMapper;

    public AnalyticsDto toDto(Analytics analytics) {
        AnalyticsDto analyticsDto = modelMapper.map(analytics, AnalyticsDto.class);
        analyticsDto.setPropertyId(analytics.getProperty() != null ? analytics.getProperty().getId() : null);
        return analyticsDto;
    }

    public Analytics toEntity(AnalyticsDto analyticsDto, Property property) {
        Analytics analytics = modelMapper.map(analyticsDto, Analytics.class);
        analytics.setProperty(property);
        return analytics;
    }

    public void updateEntity(AnalyticsDto analyticsDto, Analytics analytics) {
        modelMapper.map(analyticsDto, analytics);
    }
}

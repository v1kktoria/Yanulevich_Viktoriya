package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ImageDto;
import senla.model.Image;
import senla.model.Property;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    private final ModelMapper modelMapper;

    public ImageDto toDto(Image image) {
        ImageDto imageDto = modelMapper.map(image, ImageDto.class);
        imageDto.setPropertyId(image.getProperty() != null ? image.getProperty().getId() : null);
        return imageDto;
    }

    public Image toEntity(ImageDto imageDto, Property property) {
        Image image = modelMapper.map(imageDto, Image.class);
        image.setProperty(property);
        return image;
    }

    public void updateEntity(ImageDto imageDto, Image image) {
        modelMapper.map(imageDto, image);
    }
}

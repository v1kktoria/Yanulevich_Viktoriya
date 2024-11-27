package senla;

import senla.dao.PropertyParameterDAO;
import senla.dao.impl.*;
import senla.model.ENUM.PropertyType;
import senla.model.Property;
import senla.model.PropertyParameter;
import senla.model.User;
import senla.util.ConnectionHolder;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ConnectionHolder connectionHolder = new ConnectionHolder();
        UserDAOImpl userDAO = UserDAOImpl.getInstance(connectionHolder);
        PropertyDAOImpl propertyDAO = PropertyDAOImpl.getInstance(connectionHolder);
        ParameterDAOImpl parameterDAO = ParameterDAOImpl.getInstance(connectionHolder);
        PropertyParameterDAO propertyParameterDAO = PropertyParameterDAOImpl.getInstance(connectionHolder);

        User user = new User.Builder()
                .setUsername("egorov")
                .setPassword("password1234")
                .build();

        userDAO.create(user);
          System.out.println("Создан новый пользователь " + user);
          User user1 = userDAO.getByParam(user.getId());
        user1.setPassword("pass123456");
        userDAO.updateById(user1.getId(), user1);

        System.out.println("Обновлен пароль пользователя " + userDAO.getByParam(user.getId()));

        Property property = new Property.Builder()
                .setOwner(user1)
                .setType(PropertyType.APARTMENT)
                .setArea(85.5)
                .setPrice(1200)
                .setRooms(3)
                .setDescription("Просторная трехкомнатная квартира")
                .setCreatedAt(LocalDateTime.now())
                .build();

        propertyDAO.create(property);
        System.out.println("Создана новая недвижимость " + propertyDAO.getByParam(user1));

        propertyParameterDAO.create(new PropertyParameter.Builder()
                .setProperty(propertyDAO.getByParam(user1))
                .setParameter(parameterDAO.getByParam(1))
                .setValue("Да")
                .build());


         System.out.println("Создана новая свзяь между недвижимостью и параметром:\n" + propertyParameterDAO.getByPropertyAndParameter(propertyDAO.getByParam(user1), parameterDAO.getByParam(1)));

         propertyParameterDAO.deleteByPropertyAndParameter(propertyDAO.getByParam(property.getId()), parameterDAO.getByParam(1));
         System.out.println("Удаление пользователя");
         userDAO.deleteById(user1.getId());
         System.out.println("Удаление недвижимости");
         propertyDAO.deleteById(property.getId());
    }

}
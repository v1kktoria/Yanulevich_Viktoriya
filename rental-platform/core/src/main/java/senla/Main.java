package senla;

import senla.dao.PropertyParameterDAO;
import senla.dao.UserRoleDAO;
import senla.dao.impl.ParameterDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dao.impl.UserDAOImpl;
import senla.dicontainer.DIContainer;
import senla.model.Property;
import senla.model.PropertyParameter;
import senla.model.User;
import senla.model.constant.PropertyType;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        DIContainer.startApplication(Main.class);
        UserDAOImpl userDAO = DIContainer.getBean(UserDAOImpl.class);
        PropertyDAOImpl propertyDAO = DIContainer.getBean(PropertyDAOImpl.class);
        ParameterDAOImpl parameterDAO = DIContainer.getBean(ParameterDAOImpl.class);
        PropertyParameterDAO propertyParameterDAO = DIContainer.getBean(PropertyParameterDAO.class);
        UserRoleDAO userRoleDAO = DIContainer.getBean(UserRoleDAO.class);

        System.out.println("Все связи между пользователями и ролями: " + userRoleDAO.getAll());

        User user = User.builder()
                .username("egorov")
                .password("password1234")
                .build();

        userDAO.create(user);
          System.out.println("Создан новый пользователь " + user);
          User user1 = userDAO.getByParam(user.getId());
        user1.setPassword("pass123456");
        userDAO.updateById(user1.getId(), user1);

        System.out.println("Обновлен пароль пользователя " + userDAO.getByParam(user.getId()));

        Property property = Property.builder()
                .owner(user1)
                .type(PropertyType.APARTMENT)
                .area(85.5)
                .price(1200)
                .rooms(3)
                .description("Просторная трехкомнатная квартира")
                .createdAt(LocalDateTime.now())
                .build();

        propertyDAO.create(property);
        System.out.println("Создана новая недвижимость " + propertyDAO.getByParam(user1));

        propertyParameterDAO.create(PropertyParameter.builder()
                .property(propertyDAO.getByParam(user1))
                .parameter(parameterDAO.getByParam(1))
                .value("Да")
                .build());


         System.out.println("Создана новая свзяь между недвижимостью и параметром:\n" + propertyParameterDAO.getByPropertyAndParameter(propertyDAO.getByParam(user1), parameterDAO.getByParam(1)));

         propertyParameterDAO.deleteByPropertyAndParameter(propertyDAO.getByParam(property.getId()), parameterDAO.getByParam(1));
         System.out.println("Удаление пользователя");
         userDAO.deleteById(user1.getId());
         System.out.println("Удаление недвижимости");
         propertyDAO.deleteById(property.getId());
    }
}
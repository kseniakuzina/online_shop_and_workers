package com.ksu.online_shop.service;

import com.ksu.online_shop.DTO.UserDTO;
import com.ksu.online_shop.entities.Role;
import com.ksu.online_shop.entities.User;
import com.ksu.online_shop.repository.RoleRepository;
import com.ksu.online_shop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }
    public List<Pair<String,String>> allUsersWithRoles(){
        List<Object[]> results = userRepository.findAllUsersWithRoles();
        List<Pair<String,String>> userRolesList = new ArrayList<>();
        for(Object[] ob : results){
            String username = (String)ob[0];
            String role = ((Role)ob[1]).getName();
            userRolesList.add(Pair.of(username,role));
        }
        return userRolesList;
    }

    public List<Role> allRoles() {
        return roleRepository.findAll();
    }

    public boolean saveUser(UserDTO dto) {
        User userFromDB = userRepository.findByUsername(dto.getUsername());

        if (userFromDB != null) {
            return false;
        }

        User user = dto.toUser(passwordEncoder);
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return true;
    }
    @Transactional
    public boolean saveRole(String name) {
        Role roleFromDB = roleRepository.findByName(name);

        if (roleFromDB != null) {
            return false;
        }
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return true;
    }
    @Transactional
    public boolean grantRole(String username,String name){
        Long userId = getUserIdByUsername(username);
        if (userId == null) {
            return false; // Пользователь не найден
        }

        // Получаем id роли по имени
        Long roleId = getRoleIdByName(name);
        if (roleId == null) {
            return false; // Роль не найдена
        }
        System.out.println(userId);
        System.out.println(roleId);
        // Выполняем нативный SQL-запрос для вставки записи в таблицу customuser_role
        String sql = "INSERT INTO customuser_roles (user_id, roles_id) VALUES (:userId, :roleId)";
        int rowsInserted = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("roleId", roleId)
                .executeUpdate();

        return rowsInserted > 0;
    }
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : null; // Проверка на null
    }

    public User getUserByUsername(String username) {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        Root<User> userRequest = criteriaQuery.from(User.class);

        Predicate predicate = em.getCriteriaBuilder().equal(userRequest.get("username"), username);
        criteriaQuery.where(predicate);

        try {
            return em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null; // Или выбросьте исключение, если это более уместно
        }
    }

    public Role getRoleByName(String name) {
        CriteriaQuery<Role> criteriaQuery = em.getCriteriaBuilder().createQuery(Role.class);
        Root<Role> roleRequest = criteriaQuery.from(Role.class);

        Predicate predicate = em.getCriteriaBuilder().equal(roleRequest.get("name"), name);
        criteriaQuery.where(predicate);

        try {
            return em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null; // Или выбросьте исключение, если это более уместно
        }
    }

    public Long getUserIdByUsername(String username) {
        User user = getUserByUsername(username);
        return user != null ? user.getId() : null;
    }
    public Long getRoleIdByName(String name) {
        Role role = getRoleByName(name);
        return role != null ? role.getId() : null;
    }

    public User getCurrentUser() {
        String username = getCurrentUsername();
        if (username != null) {
            return getUserByUsername(username);
        }
        return null; // Или выбросьте исключение, если пользователь не найден
    }

    public Long getCurrentUserId() {
        String username = getCurrentUsername();
        if (username != null) {
            return getUserIdByUsername(username);
        }
        return null; // Или выбросьте исключение, если пользователь не найден
    }

    @Transactional
    public User updateUser(String firstName, String lastName, String username, String email, String phone ){
        User user = getCurrentUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        return userRepository.save(user);
    }

}
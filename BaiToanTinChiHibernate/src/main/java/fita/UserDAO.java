package fita;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            userList = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
        }
        return userList;
    }

    public boolean addUser(User user) {
        if (!User.isValidCode(user.getUserCode()) || 
            !User.isValidRole(user.getRole()) || 
            !User.isValidName(user.getFullName()) ||
            !User.isValidAddress(user.getAddress()) ||
            !user.isValidForRole()) {
            System.err.println("Dữ liệu người dùng không hợp lệ.");
            return false;
        }
        if (userExists(user.getUserCode())) {
            System.err.println("Mã người dùng đã tồn tại: " + user.getUserCode());
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        if (!User.isValidCode(user.getUserCode()) || 
            !User.isValidRole(user.getRole()) ||
            !User.isValidName(user.getFullName()) ||
            !User.isValidAddress(user.getAddress()) ||
            !user.isValidForRole()) {
            System.err.println("Dữ liệu người dùng không hợp lệ.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi cập nhật người dùng: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String userCode) {
        if (!User.isValidCode(userCode)) {
            System.err.println("Mã người dùng không hợp lệ.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM UserSubject WHERE userCode = :userCode", Long.class);
            query.setParameter("userCode", userCode);
            if (query.getSingleResult() > 0) {
                System.err.println("Không thể xóa người dùng vì đã có điểm số liên quan.");
                return false;
            }
            User user = session.get(User.class, userCode);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi xóa người dùng: " + e.getMessage());
            return false;
        }
    }

    public boolean userExists(String userCode) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, userCode);
            return user != null;
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra sự tồn tại của người dùng: " + e.getMessage());
            return false;
        }
    }

    public boolean isStudent(String userCode) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, userCode);
            return user != null && user.getRole().equalsIgnoreCase("Student");
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra vai trò người dùng: " + e.getMessage());
            return false;
        }
    }
}
package fita;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserSubjectDAO {
    private final SessionFactory sessionFactory;

    public UserSubjectDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<UserSubject> getAllUserSubjects() {
        List<UserSubject> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            list = session.createQuery("FROM UserSubject", UserSubject.class).list();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách điểm số: " + e.getMessage());
        }
        return list;
    }

    public boolean addUserSubject(UserSubject us) {
        if (!us.areScoresValid()) {
            System.err.println("Điểm số không hợp lệ.");
            return false;
        }
        if (userSubjectExists(us.getUserCode(), us.getSubjectCode())) {
            System.err.println("Người dùng đã có điểm cho môn học này.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(us);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi thêm điểm số: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUserSubject(UserSubject us) {
        if (!us.areScoresValid()) {
            System.err.println("Điểm số không hợp lệ.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(us);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi cập nhật điểm số: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUserSubject(String userCode, String subjectCode) {
        if (userCode == null || subjectCode == null) {
            System.err.println("Mã người dùng hoặc mã môn học không hợp lệ.");
            return false;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<UserSubject> query = session.createQuery(
                "FROM UserSubject WHERE userCode = :userCode AND subjectCode = :subjectCode", UserSubject.class);
            query.setParameter("userCode", userCode);
            query.setParameter("subjectCode", subjectCode);
            UserSubject us = query.uniqueResult();
            if (us != null) {
                session.remove(us);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Lỗi khi xóa điểm số: " + e.getMessage());
            return false;
        }
    }

    public boolean userSubjectExists(String userCode, String subjectCode) {
        if (userCode == null || subjectCode == null) {
            System.err.println("Mã người dùng hoặc mã môn học không hợp lệ.");
            return false;
        }

        try (Session session = sessionFactory.openSession()) {
            Query<UserSubject> query = session.createQuery(
                "FROM UserSubject WHERE userCode = :userCode AND subjectCode = :subjectCode", UserSubject.class);
            query.setParameter("userCode", userCode);
            query.setParameter("subjectCode", subjectCode);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra sự tồn tại của điểm số: " + e.getMessage());
            return false;
        }
    }

    public List<String> getFullScoreBoard() {
        List<String> result = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT u.userCode, u.fullName, s.subjectCode, s.subjectName, " +
                "us.score1, us.score2, us.score3, us.score4, us.score5, " +
                "s.he1, s.he2, s.he3, s.he4, s.he5 " +
                "FROM User u, Subject s, UserSubject us " +
                "WHERE u.userCode = us.userCode AND s.subjectCode = us.subjectCode",
                Object[].class);
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                double averageScore = (double) row[4] * (double) row[9] +
                                     (double) row[5] * (double) row[10] +
                                     (double) row[6] * (double) row[11] +
                                     (double) row[7] * (double) row[12] +
                                     (double) row[8] * (double) row[13];
                String line = String.format(
                        "User: %s (%s) | Subject: %s (%s) | Scores: %.1f, %.1f, %.1f, %.1f, %.1f | Average: %.2f",
                        row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], averageScore);
                result.add(line);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy bảng điểm: " + e.getMessage());
        }
        return result;
    }
}